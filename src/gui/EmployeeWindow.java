/*
 * Anette Molund s181083, Nanna Mjørud s180477, Charlotte Sjøthun s180495, 11.05.2012
 * 
 * Klassen EmployeeWindow oppretter vinduet som vises når man logger inn som ansatt i innloggingsvinduet.
 * Har egen indre lytterklasse.
 */

package gui;

import booking.BookingList;
import gui.extra.MyDialogWindow;
import gui.extra.MyDialogWindowPrint;
import gui.tabs.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import person.Person;
import person.employee.Employee;
import person.employee.EmployeeList;
import restaurant.Restaurant;
import room.conferenceroom.ConferenceRoomList;
import room.hotelroom.HotelRoomList;
import service.ServiceList;

public class EmployeeWindow extends JFrame
{
    private JTabbedPane tabbedPane;
    private JMenuItem menuItemClose, menuItemLogout,  menuItemAbout, menuItemSave, menuItemPrint;
    private JPanel booking, checkInCheckOut, calendar, manageEmployee, manageRooms, restaurantMenu, 
                   showBookings;
    private JLabel infoLabel;
    private EmployeeList employeeList;
    private Listener listener;
    private LoginWindow loginWindow;
    private Employee employee;
    private Restaurant restaurant;
    private HotelRoomList hotelRoomList;
    private ConferenceRoomList conferenceRoomList;
    private ServiceList serviceList;
    private BookingList bookingList;
    
    public EmployeeWindow(LoginWindow login, Restaurant restaurantList, HotelRoomList hotelRoom, 
                          ConferenceRoomList conferenceRoom, BookingList bookingL, EmployeeList employeeL, 
                          ServiceList service)
    {
        super("OSLO ICEHOTEL");
        
        employeeList = employeeL;
        listener = new Listener();
        loginWindow = login;
        restaurant = restaurantList;
        hotelRoomList = hotelRoom;
        conferenceRoomList = conferenceRoom;
        serviceList = service;
        bookingList = bookingL;
        
        JMenuBar menuBar = createMenuBar();
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        
        booking = new BookingTab(this, bookingList, hotelRoomList, conferenceRoomList, serviceList);
        tabbedPane.addTab("Booking", booking);
        
        checkInCheckOut = new CheckInCheckOutTab(this, bookingList, hotelRoomList, conferenceRoomList, 
                                                 serviceList, booking);
        tabbedPane.addTab("Sjekk inn/Sjekk ut", checkInCheckOut);
        
        showBookings = new ShowBookingsTab(this, bookingList);
        tabbedPane.addTab("Vis bookinger", showBookings);
        
        calendar = new CalendarTab(this, hotelRoomList, conferenceRoomList);
        tabbedPane.addTab("Kalender", calendar);
        
        manageEmployee = new ManageEmployeeTab(this, employeeList);
        tabbedPane.addTab("Administrer ansatt", manageEmployee);
        
        restaurantMenu = new RestaurantMenuTab(this, restaurant);
        tabbedPane.addTab("Administrer meny", restaurantMenu);
        
        manageRooms = new ManageRoomsTab(this, hotelRoomList, conferenceRoomList);        
        tabbedPane.addTab("Administrer rom", manageRooms);
        
        infoLabel = new JLabel();
        
        Container c = getContentPane();
        c.setBackground(Color.WHITE);
        c.add(tabbedPane, BorderLayout.CENTER);
        c.add(infoLabel, BorderLayout.PAGE_END);
        
        URL source = LoginWindow.class.getClassLoader().getResource("pictures/hotellogoIcon.png");

    	if (source != null)
    	{
            ImageIcon image = new ImageIcon(source);
            Image icon = image.getImage();
            setIconImage(icon);
    	}

        setJMenuBar(menuBar);
    	setSize(920, 680);
        setVisible(true);
    	setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                loginWindow.writeToFile();
                System.exit(0);
            }
        });
    } // End of constructor
    
    
    
    /* Charlotte
     * Metoden oppretter menylinjen.*/
    private JMenuBar createMenuBar()
    {
        UIManager.put("Menu.selectionBackground", new Color(0xDA,0xDD,0xED));
        UIManager.put("MenuItem.selectionBackground", new Color(0xDA,0xDD,0xED));
        UIManager.put("MenuItem.selectionForeground", Color.BLACK);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Fil");
        menuItemSave = new JMenuItem("Lagre");
        menuItemSave.setAccelerator(KeyStroke.getKeyStroke('S', Event.CTRL_MASK));
        menuItemSave.addActionListener(listener);
        menu.add(menuItemSave);
        menuItemPrint = new JMenuItem("Skriv ut");
        menuItemPrint.setAccelerator(KeyStroke.getKeyStroke('P', Event.CTRL_MASK));
        menuItemPrint.addActionListener(listener);
        menu.add(menuItemPrint);
        menu.addSeparator();
        menuItemLogout = new JMenuItem("Logg av");
        menuItemLogout.addActionListener(listener);
        menuItemLogout.setAccelerator(KeyStroke.getKeyStroke('L', Event.CTRL_MASK));
        menu.add(menuItemLogout);
        menuItemClose = new JMenuItem("Avslutt");
        menuItemClose.setAccelerator(KeyStroke.getKeyStroke('A', Event.CTRL_MASK));
        menuItemClose.addActionListener(listener);
        menuBar.add(menu);
        menu.add(menuItemClose);
        JMenu menu2 = new JMenu("Om");
        menuItemAbout = new JMenuItem("Om");
        menuItemAbout.setAccelerator(KeyStroke.getKeyStroke('O', Event.CTRL_MASK));
        menuItemAbout.addActionListener(listener);
        menu2.add(menuItemAbout);
        menuBar.add(menu2);
        
        return menuBar;
    } // End of method createMenuBar()
    
    
    
    /* Anette
     * Metoden mottar en fradato, tildato og en tekststreng med type rom i parameter. 
     * Henter inn lister for hvilke rom som er booket, og hvilke rom som finnes. Sammenligner
     * deretter listene for å finne ut hvilke rom som er ledig i det tidsintervallet
     * som har kommet inn som parameter og returnerer en JList med romnummerene til de
     * ledige rommene.*/
    public JList<Integer> getVacancy(Date fromDate, Date toDate, String roomType)
    {
        List<Integer> bookedRooms = bookingList.getBookedRooms(fromDate, toDate);
        List<Integer> hotelRooms = hotelRoomList.getRoomNrListByType(roomType);
        List<Integer> conferenceRooms = conferenceRoomList.getRoomNrListByType(roomType);
        
        JList<Integer> vacancyRooms = new JList<>();
        vacancyRooms.setModel(new DefaultListModel());
        DefaultListModel vacancyRoomsModel = (DefaultListModel) vacancyRooms.getModel();
        
        Iterator<Integer> hotelRoomsIter = hotelRooms.iterator();
        Iterator<Integer> conferenceRoomsIter = conferenceRooms.iterator();
        
        while(hotelRoomsIter.hasNext())
        {
            Integer hotelRoomNr = hotelRoomsIter.next();
            boolean noMatch = true;
            Iterator<Integer> bookedRoomsIter = bookedRooms.iterator();
            
            if (bookedRoomsIter.hasNext())
            {
                while(bookedRoomsIter.hasNext())
                {
                    Integer bookedRoomNr = bookedRoomsIter.next();
                    
                    if (hotelRoomNr.compareTo(bookedRoomNr) == 0)
                        noMatch = false;
                }// End of while
                
                if (noMatch)
                    vacancyRoomsModel.addElement(hotelRoomNr);
            }// End of if
            else
                vacancyRoomsModel.addElement(hotelRoomNr);    
        }// End of while
        
        while(conferenceRoomsIter.hasNext())
        {
            Integer conferenceRoomNr = conferenceRoomsIter.next();
            boolean noMatch = true;
            Iterator<Integer> bookedRoomsIter = bookedRooms.iterator();
            
            if(bookedRoomsIter.hasNext())
            {
                while(bookedRoomsIter.hasNext())
                {
                    Integer bookedRoomNr = bookedRoomsIter.next();

                    if(conferenceRoomNr.compareTo(bookedRoomNr) == 0)
                        noMatch = false;
                }// End of while
                
                if(noMatch)
                    vacancyRoomsModel.addElement(conferenceRoomNr);
            }// End of if
            else
                vacancyRoomsModel.addElement(conferenceRoomNr);
        }// End of while
        
        return vacancyRooms;
    } // End of method getVacancy(...)
    
    
    
    /* Charlotte
     * Metoden setter informasjon om hvem som er innlogget i info labelen.*/
    public void setInfoEmployee(Person emp)
    {
        employee = (Employee) emp;
        infoLabel.setText("Innlogget ansatt er: " + employee.getEmployeeNameAndNr());
    }
    
    
    
    /* Charlotte
     * Metoden gjør kall på checkPassword i employeeList klassen. Den kontrollerer om brukernavn og
     * passord er gyldig.*/
    public Person checkPassword(int employeeNr, String password)
    {
        return employeeList.checkPassword(employeeNr, password);
    }
    
    
    
    /* Charlotte
     * Metoden kontrollerer om objektet som kommer inn via parameteren er administrator.
     * Hvis den er det så legger den til 3 tabber som bare er for administratorer.*/
    public void ifAdmin(Person emp)
    {
        tabbedPane.remove(manageEmployee);
        tabbedPane.remove(restaurantMenu);
        tabbedPane.remove(manageRooms);
        
        if (((Employee)emp).getIsAdmin())
        {
            tabbedPane.addTab("Administrer ansatt", manageEmployee);
            tabbedPane.addTab("Administrer meny", restaurantMenu);
            tabbedPane.addTab("Administrer rom", manageRooms);
        }
        
    } // End of method ifAdmin
    
    
    
    /* Charlotte
     * Metode for å vise et JOptionPane vindu med tekst og ikon den får inn som parameter.
     * Er public fordi den brukes i andre klasser også.*/
    public void showMessage(String message, String header, int icon)
    {
        JOptionPane.showMessageDialog(this, message, header, icon);
    }
    
    
    
    // Charlotte
    public boolean regexName(String name)
    {
        return name.matches("[A-ZÆØÅ][a-zæøå]+[ -]?[A-ZÆØÅa-zæøå ]*");
    }
    
    
    
    // Charlotte
    public boolean regexFirstLastName(String name)
    {
        return name.matches("[A-ZÆØÅ][a-zæøå]+[ -]?[A-ZÆØÅ]?[a-zøæå]*[ -.]+[A-ZÆØÅ][a-zæøå]+[ -.]?[A-ZÆØÅ]?[a-zøæå]*");
    }
    
    
    
    // Charlotte
    public boolean regexAdr(String adr)
    {
        return adr.matches("[A-ZÆØÅ][a-zæøå]+\\s\\d+[a-zæøåA-ZÆØÅ]?[,]\\s\\d{4}\\s[A-ZÆØÅ][a-zæøå]+");
    }
    
    
    
    // Charlotte
    public boolean regexPhoneNr(String phoneNr)
    {
        return phoneNr.matches("\\d{8}");
    } 
    
    
    
    // Charlotte
    public boolean regexEmail(String email)
    {
        return email.matches("[A-ZÆØÅa-zæøå0-9]+[._-]?[A-ZÆØÅa-zæøå0-9]+[@][A-ZÆØÅa-zæøå0-9]+[.][A-ZÆØÅa-zæøå]{2,3}");
    }
    
    
    
    // Charlotte
    public boolean regexCompany(String company)
    {
        return company.matches("[A-ZÆØÅa-zæøå0-9]+[ -&]?[A-ZÆØÅa-zæøå0-9]*");
    }    
    
    
    
    // Charlotte
    public boolean regexSocialSecurityNr(String socialSecurityNr)
    {
        return socialSecurityNr.matches("\\d{11}");
    }    
    
    
    
    // Charlotte
    public boolean regexPassword(String passwd)
    {
        return passwd.matches("[A-ZÆØÅa-zæøå1-9]+");
    }
    
    
    
    // Charlotte
    public boolean regexPrice(String price)
    {
        return price.matches("\\d{1,3}");
    }
    
    
    
    /* Anette og Nanna
     * Metoden mottar en tekststreng med en dato på formatet dd.mm.yy.
     * Deretter sjekker den om dette er en gyldig dato i form av månedsnummer og om 
     * det eventuelt er skuddår. Til slutt konverteres datoen til en Date, og returnerer denne.*/
    public Date convertDate(String date)
    {
        String regEx = "^([0][1-9]|[12][0-9]|3[01])[.]([0-1][0-9]|[1][12])[.]\\d{2}$";
        
        if (!date.matches(regEx))
            return null;
        
        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, 8));

        if (day == 31 && (month == 4 || month == 6 || month == 9 || month == 11))
            return null;
        else if (day >= 30 && month == 2)
            return null;
        else if (month == 2 && day == 29 && !(year % 4 == 0 && (year % 100 != 0 || year % 4 == 0)))
            return null;
        
        try
        {
            DateFormat formatter = new SimpleDateFormat("dd.MM.yy");
            Date formatDate = (Date)formatter.parse(date);
            return formatDate;
        }
        catch (ParseException pe)
        {
            return null;
        }
    } // End of method convertDate(...)
    
    
    
    /* Anette
     * Metoden kaller opp klassen MyDialogWindowPrint, som lager et dialogvindu
     * for valg av utskrift.*/
    public void choosePrint()
    {
        MyDialogWindow choosePrint = new MyDialogWindowPrint(this, "Velg utskrift");
        choosePrint.setLocationRelativeTo(this);
        choosePrint.setVisible(true);
    }// End of choosePrint()
    
    
    
    /* Anette
     * Metoden blir kalt opp i klassen MyDialogWindowPrint. 
     * Kaller opp printConfirmation()-metoden i klassen BookingTab*/
    public void printConfirmation()
    {
        ((BookingTab)booking).printConfirmation();
    }
    
    
    
    /* Anette
     * Metoden blir kalt opp i klassen MyDialogWindowPrint.
     * Kaller opp printInvoice()-metoden i klassen CheckInCheckOutTab*/
    public void printInvoice()
    {
        ((CheckInCheckOutTab)checkInCheckOut).printInvoice();
    }
    
    
    
    // Privat indre lytterklasse
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == menuItemLogout)
            {
                setVisible(false);
                loginWindow.setVisible(true);
            }
            else if (e.getSource() == menuItemAbout)
                loginWindow.about(EmployeeWindow.this);
            else if (e.getSource() == menuItemSave)
                loginWindow.writeToFile();
            else if (e.getSource() == menuItemPrint)
                choosePrint();
            else if (e.getSource() == menuItemClose)
            {
                loginWindow.writeToFile();
                System.exit(0);
            }
        }
    } // End of class Listener
} // End of class EmployeeWindow