/*
 * Charlotte Sjøthun s180495, 04.05.2012
 * 
 * Klassen LoginWindow er GUI for loginvinduet og oppretter vindusklassene EmployeeWindow og GuestWindow. 
 * Klassen har også skriv og les fra fil metodene.
 */

package gui;

import booking.BookingList;
import gui.extra.MyDialogWindowAbout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;
import javax.swing.*;
import person.Person;
import person.employee.EmployeeList;
import restaurant.Restaurant;
import room.conferenceroom.ConferenceRoomList;
import room.hotelroom.HotelRoomList;
import service.ServiceList;

public class LoginWindow extends JFrame
{
    private JMenuItem menuItemClose, menuItemLogout, menuItemAbout, menuItemSave, menuItemPrint;
    private JLabel picture;
    private JButton guest, login;
    private JTextField username;
    private JPasswordField password;
    private Listener listener;
    private EmployeeWindow employeeWindow;
    private GuestWindow guestWindow;
    private Restaurant restaurant;
    private HotelRoomList hotelRoomList;
    private ConferenceRoomList conferenceRoomList;
    private BookingList bookingList;
    private EmployeeList employeeList;
    private ServiceList serviceList;
    
    public LoginWindow()
    {
        super("OSLO ICEHOTEL");
        readFromFile();
        listener = new Listener();
        
        employeeWindow = new EmployeeWindow(this, restaurant, hotelRoomList, conferenceRoomList, bookingList, 
                                            employeeList, serviceList);
        employeeWindow.setVisible(false);
        guestWindow = new GuestWindow(this, restaurant, hotelRoomList, conferenceRoomList, serviceList);
        guestWindow.setVisible(false);
        
        JMenuBar menuBar = createMenuBar();
        
        JPanel pic = new JPanel();
        Icon hotel = new ImageIcon(getClass().getClassLoader().getResource("pictures/hotellogoNy.png"));
        picture = new JLabel(hotel);
        pic.add(picture);
        pic.setBackground(Color.WHITE);
        
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        loginPanel.setBackground(Color.WHITE);
        
        JPanel login1 = new JPanel(new GridLayout(1,2));
        login1.add(new JLabel("Brukernavn: "));
        username = new JTextField(12);
        username.addActionListener(listener);
        login1.add(username);
        login1.setBackground(Color.WHITE);
        loginPanel.add(login1, gBC);
        
        JPanel login2 = new JPanel(new GridLayout(1,2));
        login2.add(new JLabel("Passord: "));
        password = new JPasswordField(12);
        password.setEchoChar('*');
        password.addActionListener(listener);
        login2.add(password);
        login2.setBackground(Color.WHITE);
        gBC.gridx = 0;
        gBC.gridy = 2;
        gBC.insets = new Insets(3,0,5,0);
        loginPanel.add(login2, gBC);
        
        login = new JButton("Login");
        login.setMnemonic(KeyEvent.VK_L);
        login.addActionListener(listener);
        gBC.gridx = 0;
        gBC.gridy = 3;
        loginPanel.add(login, gBC);
        
        JPanel guestPanel = new JPanel();
        Icon guestPicture = new ImageIcon(getClass().getClassLoader().getResource("pictures/guest.gif"));
        guest = new JButton("For gjester", guestPicture);
        guest.setVerticalTextPosition(AbstractButton.BOTTOM);
      	guest.setHorizontalTextPosition(AbstractButton.CENTER);
        guest.setMnemonic(KeyEvent.VK_G);
        guest.addActionListener(listener);
        guest.setPreferredSize(new Dimension(101, 109));
        guestPanel.add(guest);
        guestPanel.setBackground(Color.WHITE);
        
        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gBC2 = new GridBagConstraints();
        gBC2.gridx = 0;
        gBC2.gridy = 0;
        gBC2.insets = new Insets(0,0,0,120);
        center.add(loginPanel, gBC2);
        gBC2.gridx = 1;
        gBC2.gridy = 0;
        gBC2.insets = new Insets(0,0,0,0);
        center.add(guestPanel, gBC2);
        center.setBackground(Color.WHITE);
        
        JPanel info = new JPanel();
        JLabel infoText = new JLabel("Adresse: Stortingsgata 27                "
                                     + "Telefon: 452 68 691                "
                                     + "www.osloicehotel.com                "
                                     + "post@osloicehotel.com");
        info.add(infoText);
        
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(pic, BorderLayout.PAGE_START);
        c.add(center, BorderLayout.CENTER);
        c.add(info, BorderLayout.PAGE_END);
        
    	URL kilde = LoginWindow.class.getClassLoader().getResource("pictures/hotellogoIcon.png");

    	if (kilde != null)
    	{
            ImageIcon bilde = new ImageIcon(kilde);
            Image ikon = bilde.getImage();
            setIconImage(ikon);
    	}

        setJMenuBar(menuBar);
    	setSize(920, 680);
        setVisible(true);
    	setLocationRelativeTo(null);
    }  // End of constructor
    
    
    
    // Metoden oppretter menybaren
    private JMenuBar createMenuBar()
    {
        UIManager.put("Menu.selectionBackground", new Color(0xDA,0xDD,0xED));
        UIManager.put("MenuItem.selectionForeground", Color.LIGHT_GRAY);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Fil");
        UIManager.put("MenuItem.selectionBackground", menu.getBackground());
        menuItemSave = new JMenuItem("Lagre");
        UIManager.put("MenuItem.selectionBackground", menuItemSave.getBackground());
        menuItemSave.setForeground(Color.LIGHT_GRAY);
        menuItemSave.setBorder(BorderFactory.createEmptyBorder());
        menuItemSave.setAccelerator(KeyStroke.getKeyStroke('S', Event.CTRL_MASK));
        menuItemSave.addActionListener(listener);
        menu.add(menuItemSave);
        menuItemPrint = new JMenuItem("Skriv ut");
        menuItemPrint.setForeground(Color.LIGHT_GRAY);
        menuItemPrint.setBorder(BorderFactory.createEmptyBorder());
        menuItemPrint.setAccelerator(KeyStroke.getKeyStroke('P', Event.CTRL_MASK));
        menuItemPrint.addActionListener(listener);
        menu.add(menuItemPrint);
        menu.addSeparator();
        menuItemLogout = new JMenuItem("Logg av");
        menuItemLogout.setForeground(Color.LIGHT_GRAY);
        menuItemLogout.setBorder(BorderFactory.createEmptyBorder());
        menuItemLogout.addActionListener(listener);
        menuItemLogout.setAccelerator(KeyStroke.getKeyStroke('L', Event.CTRL_MASK));
        menu.add(menuItemLogout);
        UIManager.put("MenuItem.selectionBackground", new Color(0xDA,0xDD,0xED));
        UIManager.put("MenuItem.selectionForeground", Color.BLACK);
        menuItemClose = new JMenuItem("Avslutt");
        menuItemClose.setAccelerator(KeyStroke.getKeyStroke('A', Event.CTRL_MASK));
        menuItemClose.addActionListener(listener);
        menuBar.add(menu);
        menu.add(menuItemClose);
        JMenu menu2 = new JMenu("Om");
        menuItemAbout = new JMenuItem("Om");
        menuItemAbout.setAccelerator(KeyStroke.getKeyStroke('O', Event.CTRL_MASK));
        menuItemAbout.addActionListener(listener);
        menuItemAbout.setBorder(BorderFactory.createEmptyBorder());
        menu2.add(menuItemAbout);
        menuBar.add(menu2);
        
        return menuBar;
    } // End of method createMenuBar()
    
    
    
    /* Når man velger login (eller trykker enter mens man står i JTextField for
     * brukernavn og passord) blir denne metoden kalt. Den kontrollerer om 
     * passordet til brukeren med brukernavn lik input stemmer.*/
    public void checkPassword()
    {
        String employeeNr = username.getText().trim(); 
        char[] pass = password.getPassword();
        String passwd = new String(pass);
        
        if (!employeeNr.matches("\\d+") || !employeeWindow.regexPassword(passwd))
        {
            JOptionPane.showMessageDialog(null, "Må skrive inn brukernavn og passord!", 
                                          "Feilmelding", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        
        int empNr = Integer.parseInt(employeeNr);
        Person employee = employeeWindow.checkPassword(empNr, passwd);
        
        if (employee != null)
        {
            employeeWindow.setInfoEmployee(employee);
            employeeWindow.ifAdmin(employee);
            employeeWindow.setVisible(true);
            setVisible(false);
            username.setText("");
            password.setText("");
        }
        else
            JOptionPane.showMessageDialog(null, "Feil brukernavn eller passord!", 
                                          "Feilmelding", JOptionPane.WARNING_MESSAGE);       
    } // End og method checkPassword()
    
    
    
    /* Når man velger "om" fra menyen blir denne metoden kalt, den viser et 
     * dialogvindu. Metoden tar imot et vindu som parameter fordi den kan 
     * kalles opp fra de andre vinduene også.*/
    public void about(Window window)
    {
        MyDialogWindowAbout aboutWindow = new MyDialogWindowAbout(window, "Om");
        aboutWindow.setLocationRelativeTo(window);
        aboutWindow.setVisible(true);
    } // End of method about(...)
    
    
    
    // Metoden leser restaurant fra fil
    public void readFromRestaurantFile()
    {
        try(ObjectInputStream fromRestaurantFile = 
                new ObjectInputStream(new FileInputStream("listfiles/restaurant.dta")))
        {
            restaurant = (Restaurant) fromRestaurantFile.readObject();
        }
        catch(ClassNotFoundException cnfe)
        {
            restaurant = new Restaurant();
            JOptionPane.showMessageDialog(null, "Fant ikke definisjon av mat og "
                                        + "drikke objektene.\nOppretter tomt "
                                        + "menyregister.\n" + cnfe.getMessage(), 
                                          "Feilmelding", JOptionPane.ERROR_MESSAGE);
        }
        catch(FileNotFoundException fnfe)
        {
            restaurant = new Restaurant();
            JOptionPane.showMessageDialog(null, "Finner ikke angitt fil.\n"
                                        + "Oppretter tomt menyregister.\n" 
                                        + fnfe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException ioe)
	{
            restaurant = new Restaurant();
            JOptionPane.showMessageDialog(null, "Fikk ikke lest fra filen.\n"
                                        + "Oppretter tomt menyregister.\n"
                                        + ioe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
	}
    } // End of method readFromRestaurantFile()
    
    
    
    // Metoden leser hotelRoomList fra fil
    public void readFromHotelRoomListFile()
    {
        try(ObjectInputStream fromHotelRoomListFile = 
                new ObjectInputStream(new FileInputStream("listfiles/hotelRoomlist.dta")))
        {
            hotelRoomList = (HotelRoomList) fromHotelRoomListFile.readObject();
            hotelRoomList.getSavedStaticPrice(fromHotelRoomListFile);
        }
        catch(ClassNotFoundException cnfe)
        {
            hotelRoomList = new HotelRoomList();
            JOptionPane.showMessageDialog(null, "Fant ikke definisjon av hotelrom "
                                        + "objektene.\nOppretter ny liste for "
                                        + "hotelrommene.\n" + cnfe.getMessage(), 
                                          "Feilmelding", JOptionPane.ERROR_MESSAGE);
        }
        catch(FileNotFoundException fnfe)
        {
            hotelRoomList = new HotelRoomList();
            JOptionPane.showMessageDialog(null, "Finner ikke angitt fil.\n"
                                        + "Oppretter ny liste for hotelrommene.\n" 
                                        + fnfe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException ioe)
	{
            hotelRoomList = new HotelRoomList();
            JOptionPane.showMessageDialog(null, "Fikk ikke lest fra filen.\n"
                                        + "Oppretter ny liste for hotelrommene.\n"
                                        + ioe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
	}
    } // End of method readFromHotelRoomListFile()
    
    
    
    // Metoden leser conferenceRoomList fra fil
    public void readFromConferenceRoomListFile()
    {
        try(ObjectInputStream fromConferenceRoomListFile = 
                new ObjectInputStream(new FileInputStream("listfiles/conferenceroomlist.dta")))
        {
            conferenceRoomList = (ConferenceRoomList) fromConferenceRoomListFile.readObject();
            conferenceRoomList.getSavedStaticPrice(fromConferenceRoomListFile);
        }
        catch(ClassNotFoundException cnfe)
        {
            conferenceRoomList = new ConferenceRoomList();
            JOptionPane.showMessageDialog(null, "Fant ikke definisjon av konferanserom "
                                        + "objektene.\nOppretter ny liste for "
                                        + "konferanserommene.\n" + cnfe.getMessage(), 
                                          "Feilmelding", JOptionPane.ERROR_MESSAGE);
        }
        catch(FileNotFoundException fnfe)
        {
            conferenceRoomList = new ConferenceRoomList();
            JOptionPane.showMessageDialog(null, "Finner ikke angitt fil.\n"
                                        + "Oppretter ny liste for konferanserommene.\n" 
                                        + fnfe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException ioe)
	{
            conferenceRoomList = new ConferenceRoomList();
            JOptionPane.showMessageDialog(null, "Fikk ikke lest fra filen.\n"
                                        + "Oppretter ny liste for konferanserommene.\n"
                                        + ioe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
	}
    } // End of method readFromConferenceRoomListFile()
    
    
    
    // Metoden leser bookingList fra fil
    public void readFromBookingListFile()
    {
        try(ObjectInputStream fromBookingListFile = 
                new ObjectInputStream(new FileInputStream("listfiles/bookinglist.dta")))
        {
            bookingList = (BookingList) fromBookingListFile.readObject();
            bookingList.setSavedStaticBookingRunNr(fromBookingListFile);
        }
        catch(ClassNotFoundException cnfe)
        {
            bookingList = new BookingList();
            JOptionPane.showMessageDialog(null, "Fant ikke definisjon av booking "
                                        + "objektene.\nOppretter tomt bookingregister.\n" 
                                        + cnfe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
        }
        catch(FileNotFoundException fnfe)
        {
            bookingList = new BookingList();
            JOptionPane.showMessageDialog(null, "Finner ikke angitt fil.\n"
                                        + "Oppretter tomt bookingregister.\n" 
                                        + fnfe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException ioe)
	{
            bookingList = new BookingList();
            JOptionPane.showMessageDialog(null, "Fikk ikke lest fra filen.\n"
                                        + "Oppretter tomt bookingregister.\n"
                                        + ioe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
	}
    } // End of method readFromBookingListFile()
    
    
    
    // Metoden leser employeeList fra fil
    public void readFromEmployeeListFile()
    {
        try(ObjectInputStream fromEmployeeListFile = 
                new ObjectInputStream(new FileInputStream("listfiles/employeelist.dta")))
        {
            employeeList = (EmployeeList) fromEmployeeListFile.readObject();
            employeeList.setSavedStaticEmpRunNR(fromEmployeeListFile);
        }
        catch(ClassNotFoundException cnfe)
        {
            employeeList = new EmployeeList();
            JOptionPane.showMessageDialog(null, "Fant ikke definisjon av ansatt "
                                        + "objektene.\nOppretter tomt ansattregister.\n"
                                        + cnfe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
        }
        catch(FileNotFoundException fnfe)
        {
            employeeList = new EmployeeList();
            JOptionPane.showMessageDialog(null, "Finner ikke angitt fil.\n"
                                        + "Oppretter tomt ansattregister.\n" 
                                        + fnfe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException ioe)
	{
            employeeList = new EmployeeList();
            JOptionPane.showMessageDialog(null, "Fikk ikke lest fra filen.\n"
                                        + "Oppretter tomt ansattregister.\n"
                                        + ioe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
	}
    } // End of method readFromEmployeeListFile()
    
    
    
    // Metoden leser serviceList fra fil
    public void readFromServiceFile()
    {
        try(ObjectInputStream fromServiceFile = 
                new ObjectInputStream(new FileInputStream("listfiles/servicelist.dta")))
        {
            serviceList = (ServiceList) fromServiceFile.readObject();
        }
        catch(ClassNotFoundException cnfe)
        {
            serviceList = new ServiceList();
            JOptionPane.showMessageDialog(null, "Fant ikke definisjon av service"
                                        + " objektene.\nOppretter nytt "
                                        + "serviceregister.\n" + cnfe.getMessage(), 
                                          "Feilmelding", JOptionPane.ERROR_MESSAGE);
        }
        catch(FileNotFoundException fnfe)
        {
            serviceList = new ServiceList();
            JOptionPane.showMessageDialog(null, "Finner ikke angitt fil.\n"
                                        + "Oppretter nytt serviceregister.\n" 
                                        + fnfe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException ioe)
	{
            serviceList = new ServiceList();
            JOptionPane.showMessageDialog(null, "Fikk ikke lest fra filen.\n"
                                        + "Oppretter nytt serviceregister.\n"
                                        + ioe.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
	}
    }  // End of method readFromServiceFile()
    
    
    
    // Metoden gjør kall på alle de forskjellige les-fra-fil metodene.
    private void readFromFile()
    {
        readFromRestaurantFile();
        readFromHotelRoomListFile();
        readFromConferenceRoomListFile();
        readFromBookingListFile();
        readFromEmployeeListFile();
        readFromServiceFile();
    } // End of method readFromFile()
    
    
    
    // Metoden skriver restaurant til fil.
    public void writeToRestaurantFile()
    {
	try(ObjectOutputStream toRestaurantFile = 
                new ObjectOutputStream(new FileOutputStream("listfiles/restaurant.dta")))
	{
            toRestaurantFile.writeObject(restaurant);
	}
	catch(NotSerializableException nse)
	{
            JOptionPane.showMessageDialog(null, "Restaurant objektene er ikke "
                                        + "serialiserbare.\nIngen registrering på fil!"
                                        + nse.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
	}
	catch(IOException ioe)
	{
            JOptionPane.showMessageDialog(null, "Det oppsto en feil ved skriving "
                                        + "til fil.\n" + ioe.getMessage());
	}
    } // End of method writeToRestaurantFile()
    
    
    
    // Metoden skriver hotelRoomList til fil.
    public void writeToHotelRoomListFile()
    {
	try(ObjectOutputStream toHotelRoomListFile = 
                new ObjectOutputStream(new FileOutputStream("listfiles/hotelroomlist.dta")))
	{
            toHotelRoomListFile.writeObject(hotelRoomList);
            hotelRoomList.saveStaticPrice(toHotelRoomListFile);
	}
	catch(NotSerializableException nse)
	{
            JOptionPane.showMessageDialog(null, "Hotelrom objektene er ikke "
                                        + "serialiserbare.\nIngen registrering på fil!"
                                        + nse.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
	}
	catch(IOException ioe)
	{
            JOptionPane.showMessageDialog(null, "Det oppsto en feil ved skriving "
                                        + "til fil.\n" + ioe.getMessage());
	}
    } // End of method writeToHotelRoomListFile()
    
    
    
    // Metoden skriver conferenceRoomList til fil.
    public void writeToConferenceRoomListFile()
    {
	try(ObjectOutputStream toConferenceRoomListFile = 
                new ObjectOutputStream(new FileOutputStream("listfiles/conferenceroomlist.dta")))
	{
            toConferenceRoomListFile.writeObject(conferenceRoomList);
            conferenceRoomList.saveStaticPrice(toConferenceRoomListFile);
	}
	catch(NotSerializableException nse)
	{
            JOptionPane.showMessageDialog(null, "Konferanserom objektene er ikke "
                                        + "serialiserbare.\nIngen registrering på fil!"
                                        + nse.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
	}
	catch(IOException ioe)
	{
            JOptionPane.showMessageDialog(null, "Det oppsto en feil ved skriving "
                                        + "til fil.\n" + ioe.getMessage());
	}
    } // End of method writeToConferenceRoomListFile()
    
    
    
    // Metoden skriver bookingList til fil.
    public void writeToBookingListFile()
    {
	try(ObjectOutputStream toBookingListFile = 
                new ObjectOutputStream(new FileOutputStream("listfiles/bookinglist.dta")))
	{
            toBookingListFile.writeObject(bookingList);
            bookingList.saveStaticBookingRunNr(toBookingListFile);
	}
	catch(NotSerializableException nse)
	{
            JOptionPane.showMessageDialog(null, "Booking objektene er ikke "
                                        + "serialiserbare.\nIngen registrering på fil!"
                                        + nse.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
	}
	catch(IOException ioe)
	{
            JOptionPane.showMessageDialog(null, "Det oppsto en feil ved skriving "
                                        + "til fil.\n" + ioe.getMessage());
	}
    } // End of method writeToBookingListFile()
    
    
    
    // Metoden skriver employeeList til fil.
    public void writeToEmployeeListFile()
    {
	try(ObjectOutputStream toEmployeeListFile = 
                new ObjectOutputStream(new FileOutputStream("listfiles/employeelist.dta")))
	{
            toEmployeeListFile.writeObject(employeeList);
            employeeList.saveStaticEmpRunNr(toEmployeeListFile);
	}
	catch(NotSerializableException nse)
	{
            JOptionPane.showMessageDialog(null, "Ansatt objektene er ikke "
                                        + "serialiserbare.\nIngen registrering på fil!"
                                        + nse.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
	}
	catch(IOException ioe)
	{
            JOptionPane.showMessageDialog(null, "Det oppsto en feil ved skriving "
                                        + "til fil.\n" + ioe.getMessage());
	}
    } // End of method writeToEmployeeListFile()
    
    
    
    // Metoden skriver serviceList til fil.
    public void writeToServiceFile()
    {
	try(ObjectOutputStream toServiceFile = 
                new ObjectOutputStream(new FileOutputStream("listfiles/servicelist.dta")))
	{
            toServiceFile.writeObject(serviceList);
	}
	catch(NotSerializableException nse)
	{
            JOptionPane.showMessageDialog(null, "Service objektene er ikke "
                                        + "serialiserbare.\nIngen registrering på fil!"
                                        + nse.getMessage(), "Feilmelding", 
                                          JOptionPane.ERROR_MESSAGE);
	}
	catch(IOException ioe)
	{
            JOptionPane.showMessageDialog(null, "Det oppsto en feil ved skriving "
                                        + "til fil.\n" + ioe.getMessage());
	}
    } // End of method writeToServiceFile()
    
    
    
    // Metoden gjør kall på de forskjellige skriv-til-fil metodene.
    public void writeToFile()
    {
        writeToRestaurantFile();
        writeToHotelRoomListFile();
        writeToConferenceRoomListFile();
        writeToBookingListFile();
        writeToEmployeeListFile();
        writeToServiceFile();
    } // End of method writeToFile()
    
    
    
    // Privat indre lytterklasse.
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == login || e.getSource() == password || e.getSource() == username)
                 checkPassword();
            else if (e.getSource() == guest)
            {
                guestWindow.setVisible(true);
                setVisible(false);
            }
            else if (e.getSource() == menuItemAbout)
                about(LoginWindow.this);
            else if (e.getSource() == menuItemClose)
            {
                writeToFile();
                System.exit(0);
            }
        }
    } // End of class Listener
} // End of class LoginWindow