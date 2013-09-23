/*
 * Anette Molund s181083, 08.05.12
 * 
 * Denne klassen oppretter innholdet i SjekkInn/SjekkUt-taben, og inneholder metoder for å
 * sjekke gjesten inn og ut, kansellere bookingen og endre diverse informasjon
 * om bookingen. Klassen har en egen indre lytteklasse.
 */ 

package gui.tabs;


import booking.Booking;
import booking.BookingList;
import booking.ConferenceRoomBooking;
import booking.HotelRoomBooking;
import gui.EmployeeWindow;
import gui.extra.MyDialogWindow;
import gui.extra.MyDialogWindowChangeGuests;
import gui.extra.MyDialogWindowChangeRooms;
import gui.extra.MyDialogWindowChangeServices;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import person.Person;
import person.guest.CompanyGuest;
import person.guest.PrivateGuest;
import room.conferenceroom.ConferenceRoom;
import room.conferenceroom.ConferenceRoomList;
import room.hotelroom.HotelRoom;
import room.hotelroom.HotelRoomList;
import service.Service;
import service.ServiceList;

public class CheckInCheckOutTab extends JPanel
{
    private JTextField checkBookingnr, checkFirstName, checkLastName, checkFromDate;
    private JTextField checkInfoFirstName, checkInfoLastName, checkInfoAddress;
    private JTextField checkInfoPhoneNr, checkInfoEmail, checkInfoCompany;
    private JTextField checkInfoFromDate, checkInfoToDate, checkPrice, checkTypeOfBooking;
    private JButton checkGetBooking, checkChangeInfo, checkIn, checkOut, checkCancel;
    private JButton checkChangeRooms, checkChangeGuests, checkChangeServices, invoiceButton;
    private JCheckBox checkChangeFirstName, changeChangeLastName, checkChangeAddress;
    private JCheckBox checkChangePhoneNr, checkChangeEmail, checkChangeCompany;
    private JCheckBox checkChangeFromDate, checkChangeToDate, checkCancelled;
    private JCheckBox checkCheckedIn, checkCheckedOut;
    private JList<String> checkGuests, checkServices;
    private JList<Integer> checkRooms;
    private JTextArea invoice = new JTextArea();    //Denne brukes kun til å skrive ut faktura.
    private Booking checkBooking;
    
    private Listener listener;
    private EmployeeWindow employeeWindow;
    private BookingList booking;
    private HotelRoomList hotelRoomList;
    private ConferenceRoomList conferenceRoomList;
    private ServiceList serviceList;
    private JPanel bookingTab;
    
    public CheckInCheckOutTab(EmployeeWindow window, BookingList bookingList, 
                      HotelRoomList hRoomList, ConferenceRoomList cRoomList,
                      ServiceList sList, JPanel bTab)
    {
        employeeWindow = window;
        booking = bookingList;
        hotelRoomList = hRoomList;
        conferenceRoomList = cRoomList;
        serviceList = sList;
        bookingTab = bTab;
        listener = new Listener();
        
        //Panelet panel inneholder alle paneler i denne taben.
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gBC = new GridBagConstraints();
        gBC.insets = new Insets(1,1,1,1);
        
        //Panelet input inneholder panelene input1, input2, input3 og input4.
        JPanel input = new JPanel(new GridLayout(4,1));
        input.setBorder(BorderFactory.createTitledBorder("Hent booking"));
        input.setBackground(Color.WHITE);
        
        
        JPanel input1 = new JPanel(new GridLayout(1,2));
        input1.setBackground(Color.WHITE);
        input1.add(new JLabel("Bookingnr"));
        checkBookingnr = new JTextField(10);
        input1.add(checkBookingnr);
        input.add(input1);
        
        JPanel input2 = new JPanel(new GridLayout(1,2));
        input2.setBackground(Color.WHITE);
        input2.add(new JLabel("Fornavn"));
        checkFirstName = new JTextField(10);
        input2.add(checkFirstName);
        input.add(input2);
        
        JPanel input3 = new JPanel(new GridLayout(1,2));
        input3.setBackground(Color.WHITE);
        input3.add(new JLabel("Etternavn"));
        checkLastName = new JTextField(10);
        input3.add(checkLastName);
        input.add(input3);
        
        JPanel input4 = new JPanel(new GridLayout(1,2));
        input4.setBackground(Color.WHITE);
        input4.add(new JLabel("Fra dato (dd.mm.åå)"));
        checkFromDate = new JTextField(10);
        input4.add(checkFromDate);
        input.add(input4);
        
        //Panelet getBooking inneholder knapp for å hente booking.
        JPanel getBooking = new JPanel();
        getBooking.setBackground(Color.WHITE);
        checkGetBooking = new JButton("Hent booking");
        checkGetBooking.addActionListener(listener);
        getBooking.add(checkGetBooking);
        
        //Panelet infoPanel inneholder panelene bookingAndPrice, info0, info1,
        //info2, info3, listInfo, buttonChange og checkBoxPanel.
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gBC2 = new GridBagConstraints();
        infoPanel.setBorder(BorderFactory.createTitledBorder("Bookingopplysninger"));
        infoPanel.setBackground(Color.WHITE);
        
        //Panelet bookingAndPrice inneholder to JTextField for type booking og pris.
        JPanel bookingAndPrice = new JPanel();
        bookingAndPrice.setBackground(Color.WHITE);
        checkTypeOfBooking = new JTextField(12);
        checkTypeOfBooking.setEditable(false);
        checkTypeOfBooking.setText("Bookingtype");
        checkTypeOfBooking.setBackground(Color.WHITE);
        bookingAndPrice.add(checkTypeOfBooking);
        checkPrice = new JTextField(12);
        checkPrice.setEditable(false);
        checkPrice.setText("Totalpris");
        checkPrice.setBackground(Color.WHITE);
        bookingAndPrice.add(checkPrice);
        gBC2.gridx = 0;
        gBC2.gridy = 0;
        infoPanel.add(bookingAndPrice, gBC2);
        
        //Panelet info0 inneholder felter for dato.
        JPanel info0 = new JPanel(new GridLayout(1,6));
        info0.setBackground(Color.WHITE);
        info0.add(new JLabel("Fra dato"));
        checkInfoFromDate = new JTextField(12);
        info0.add(checkInfoFromDate);
        checkChangeFromDate = new JCheckBox("Endre");
        checkChangeFromDate.setBackground(Color.WHITE);
        info0.add(checkChangeFromDate);
        info0.add(new JLabel("Til dato"));
        checkInfoToDate = new JTextField(12);
        info0.add(checkInfoToDate);
        checkChangeToDate = new JCheckBox("Endre");
        checkChangeToDate.setBackground(Color.WHITE);
        info0.add(checkChangeToDate);
        gBC2.gridx = 0;
        gBC2.gridy = 1;
        infoPanel.add(info0, gBC2);
        
        //Panelet info1 inneholder felter for navn.
        JPanel info1 = new JPanel(new GridLayout(1,6));
        info1.setBackground(Color.WHITE);
        info1.add(new JLabel("Fornavn"));
        checkInfoFirstName = new JTextField(12);
        info1.add(checkInfoFirstName);
        checkChangeFirstName = new JCheckBox("Endre");
        checkChangeFirstName.setBackground(Color.WHITE);
        info1.add(checkChangeFirstName);
        info1.add(new JLabel("Etternavn"));
        checkInfoLastName = new JTextField(12);
        info1.add(checkInfoLastName);
        changeChangeLastName = new JCheckBox("Endre");
        changeChangeLastName.setBackground(Color.WHITE);
        info1.add(changeChangeLastName);
        gBC2.gridx = 0;
        gBC2.gridy = 2;
        infoPanel.add(info1, gBC2);        
        
        //Panelet info2 inneholder felter for adresse og telefonnummer.
        JPanel info2 = new JPanel(new GridLayout(1,6));
        info2.setBackground(Color.WHITE);
        info2.add(new JLabel("Adresse"));
        checkInfoAddress = new JTextField(12);
        info2.add(checkInfoAddress);
        checkChangeAddress = new JCheckBox("Endre");
        checkChangeAddress.setBackground(Color.WHITE);
        info2.add(checkChangeAddress);
        info2.add(new JLabel("Telefonnummer"));
        checkInfoPhoneNr = new JTextField(12);
        info2.add(checkInfoPhoneNr);
        checkChangePhoneNr = new JCheckBox("Endre");
        checkChangePhoneNr.setBackground(Color.WHITE);
        info2.add(checkChangePhoneNr);
        gBC2.gridx = 0;
        gBC2.gridy = 3;
        infoPanel.add(info2, gBC2);
        
        //Panelet info3 inneholder felter for email og firmanavn.
        JPanel info3 = new JPanel(new GridLayout(1,6));
        info3.setBackground(Color.WHITE);
        info3.add(new JLabel("Email"));
        checkInfoEmail = new JTextField(12);
        info3.add(checkInfoEmail);
        checkChangeEmail = new JCheckBox("Endre");
        checkChangeEmail.setBackground(Color.WHITE);
        info3.add(checkChangeEmail);
        info3.add(new JLabel("Firmanavn"));
        checkInfoCompany = new JTextField(12);
        info3.add(checkInfoCompany);
        checkChangeCompany = new JCheckBox("Endre");
        checkChangeCompany.setBackground(Color.WHITE);
        info3.add(checkChangeCompany);
        gBC2.gridx = 0;
        gBC2.gridy = 4;
        infoPanel.add(info3, gBC2);
       
        //Panelet listInfo inneholder JList-er som viser rom, medgjester og tjenester.
        JPanel listInfo = new JPanel();
        listInfo.setBackground(Color.WHITE);
        checkRooms = new JList<>();
        checkRooms.setVisibleRowCount(5);
        checkRooms.setFixedCellWidth(140);
        checkRooms.setFixedCellHeight(15);
        listInfo.add(new JScrollPane(checkRooms));
        checkChangeRooms = new JButton("Endre rom");
        checkChangeRooms.addActionListener(listener);
        
        checkGuests = new JList<>();
        checkGuests.setVisibleRowCount(5);
        checkGuests.setFixedCellWidth(140);
        checkGuests.setFixedCellHeight(15);
        listInfo.add(new JScrollPane(checkGuests));
        checkChangeGuests = new JButton("Endre gjester");
        checkChangeGuests.addActionListener(listener);       
        
        checkServices = new JList<>();
        checkServices.setVisibleRowCount(5);
        checkServices.setFixedCellWidth(140);
        checkServices.setFixedCellHeight(15);
        listInfo.add(new JScrollPane(checkServices));
        checkChangeServices = new JButton("Endre tjenester");
        checkChangeServices.addActionListener(listener);        
        
        gBC2.gridx = 0;
        gBC2.gridy = 5;
        infoPanel.add(listInfo, gBC2);
        
        //Panelet buttonChange inneholder knapper for å endre rom, medgjester og
        //tjenester.
        JPanel buttonChange = new JPanel(new GridBagLayout());
        GridBagConstraints gBC3 = new GridBagConstraints();
        gBC3.insets = new Insets(0,25,5,20);
        buttonChange.setBackground(Color.WHITE);
        buttonChange.add(checkChangeRooms, gBC3);
        buttonChange.add(checkChangeGuests, gBC3);
        buttonChange.add(checkChangeServices, gBC3);
        gBC2.gridx = 0;
        gBC2.gridy = 6;
        infoPanel.add(buttonChange, gBC2);
        
        //Panelet checkBoxPanel inneholder sjekkbokser som viser om bookingen er
        //sjekket inn, ut og/eller kansellert.
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setBackground(Color.WHITE);
        checkCheckedIn = new JCheckBox("Innsjekket");
        checkCheckedIn.setBackground(Color.WHITE);
        checkCheckedIn.setEnabled(false);
        checkBoxPanel.add(checkCheckedIn);
        checkCheckedOut = new JCheckBox("Utsjekket");
        checkCheckedOut.setBackground(Color.WHITE);
        checkCheckedOut.setEnabled(false);
        checkBoxPanel.add(checkCheckedOut);
        checkCancelled = new JCheckBox("Kansellert");
        checkCancelled.setBackground(Color.WHITE);
        checkCancelled.setEnabled(false);
        checkBoxPanel.add(checkCancelled);
        gBC2.gridx = 0;
        gBC2.gridy = 7;
        infoPanel.add(checkBoxPanel, gBC2);
        
        //Panelet changeInfo inneholder knapp for å endre opplysninger.
        JPanel changeInfo = new JPanel();
        changeInfo.setBackground(Color.WHITE);
        checkChangeInfo = new JButton("Endre opplsyninger");
        checkChangeInfo.addActionListener(listener);
        changeInfo.add(checkChangeInfo);
        
        //Panelet checkInOutPanel inneholder knapper for å sjekke inn, ut og kansellere.
        JPanel checkInOutPanel = new JPanel(new GridLayout(1,3));
        checkInOutPanel.setBackground(Color.WHITE);
        checkIn = new JButton("Sjekk inn");
        checkIn.addActionListener(listener);
        checkOut = new JButton("Sjekk ut");
        checkOut.addActionListener(listener);
        checkCancel = new JButton("Avbesill");
        checkCancel.addActionListener(listener);
        checkInOutPanel.add(checkIn);
        checkInOutPanel.add(checkOut);
        checkInOutPanel.add(checkCancel);
        
        invoiceButton = new JButton("Skriv ut faktura");
        invoiceButton.addActionListener(listener);
        
        gBC.gridx = 0;
        gBC.gridy = 0;
        panel.add(input, gBC);
        gBC.gridx = 0;
        gBC.gridy = 1;
        panel.add(getBooking, gBC);
        gBC.gridx = 0;
        gBC.gridy = 2;
        panel.add(infoPanel, gBC);
        gBC.gridx = 0;
        gBC.gridy = 3;
        panel.add(changeInfo, gBC);
        gBC.gridx = 0;
        gBC.gridy = 4;
        panel.add(checkInOutPanel, gBC);
        gBC.gridx = 0;
        gBC.gridy = 5;
        gBC.insets = new Insets(5,0,0,0);
        panel.add(invoiceButton, gBC);
        add(panel);
        setBackground(Color.WHITE);
    }// End of constructor
    
    
    
    /* Metoden henter inn alle opplysninger om bookingen som velges når bruker
     * trykker på "Hent booking".*/
    public void checkGetBooking()
    {
        String bookingNrString = checkBookingnr.getText().trim();
        int bookingNr;
        String firstName = checkFirstName.getText().trim();
        String lastName = checkLastName.getText().trim();
        String date = checkFromDate.getText().trim();
        Date fromDate = null;
        if(date.length() != 0)
            fromDate = employeeWindow.convertDate(date);
        
        checkDeleteInfoField();
        
        
        if(bookingNrString.length() > 0)
            bookingNr = Integer.parseInt(bookingNrString);
        else if(fromDate != null && firstName.length() > 0 && lastName.length() > 0)
            bookingNr = booking.getBookingNr(firstName, lastName, fromDate);
        else
        {
            employeeWindow.showMessage("Du må enten fylle ut: \n- Bookingnr\n- "
                                        + "Fornavn, etternavn, fra dato", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
            
        checkBooking = booking.getBooking(bookingNr);
        if(checkBooking == null)
        {
            employeeWindow.showMessage("Finner ikke booking med bookingnr: " 
                                        + bookingNr, "Feil", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Person guest = checkBooking.getGuest();
        fromDate = checkBooking.getFromDate();
        Date toDate = checkBooking.getToDate();
        
        checkBookingnr.setText(checkBooking.getBookingNr() + "");
        checkInfoFromDate.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(fromDate));
        checkInfoToDate.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(toDate));
        checkInfoFirstName.setText(guest.getFirstname());
        checkInfoLastName.setText(guest.getLastname());
        checkInfoAddress.setText(guest.getAddress());
        checkInfoPhoneNr.setText(guest.getPhoneNr());
        checkInfoEmail.setText(guest.getEmail());
        
        if(checkBooking.getCheckedIn())
            checkCheckedIn.setSelected(true);
        
        if(checkBooking.getCheckedOut())
            checkCheckedOut.setSelected(true);
        
        if(checkBooking.getCancelled())
            checkCancelled.setSelected(true);
        
        checkPrice.setText("Totalpris: " + checkBooking.getPrice() + " NOK");
        
        if(guest instanceof CompanyGuest)
        {
            CompanyGuest c = (CompanyGuest) guest;
            checkInfoCompany.setText(c.getCompanyName());
        }
        
        if(checkBooking instanceof HotelRoomBooking)
        {
            checkTypeOfBooking.setText("Hotellrom");
            
            HotelRoomBooking h = (HotelRoomBooking) checkBooking;
            
            DefaultListModel checkServiceModel = new DefaultListModel<>();
            List<String> serviceElements = h.getServices();
            
            for(String s : serviceElements)
                checkServiceModel.addElement(s);
            
            checkServices.setModel(checkServiceModel);
            
            List<Integer> roomElements = h.getRoomNr();
            DefaultListModel checkRoomModel = new DefaultListModel<>();
            
            for(int s : roomElements)
                checkRoomModel.addElement(s);
            
            checkRooms.setModel(checkRoomModel);
            
            DefaultListModel checkGuestModel = new DefaultListModel<>();
            List<String> guestElements = null;
            Person g = h.getGuest();
            
            if(g instanceof PrivateGuest)
                guestElements = ((PrivateGuest)g).getGuestNames();
            else if(g instanceof CompanyGuest)
                guestElements = ((CompanyGuest)g).getGuestNames();
            
            if(guestElements != null)
                for(String s : guestElements)
                    checkGuestModel.addElement(s);
            
            checkGuests.setModel(checkGuestModel);
        }// End of if
        else if(checkBooking instanceof ConferenceRoomBooking)
        {
            checkTypeOfBooking.setText("Seminarrom");
            
            ConferenceRoomBooking h = (ConferenceRoomBooking) checkBooking;
            
            
            List<Integer> roomElements = h.getRoomNr();
            DefaultListModel checkRoomModel = new DefaultListModel<>();
            
            for(int s : roomElements)
                checkRoomModel.addElement(s);
            
            checkRooms.setModel(checkRoomModel);
        }// End of else if
        
        invoice.setText(checkBooking.getStringBuilder());
    } // End of method checkGetBooking()
    
    
    
    /* Denne metoden blir kalt opp når man trykker på "Endre opplysninger", og
     * kaller opp metoder for å endre ønskede felter.*/
    public void checkChangeInfo()
    {
        if(checkBooking == null)
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
        else if(checkBooking != null && !checkBooking.getCheckedOut() && !checkBooking.getCancelled())
        {
            if(checkChangeFromDate.isSelected() || checkChangeToDate.isSelected())
                changeDates();
            if(checkChangeFirstName.isSelected() || changeChangeLastName.isSelected())
                changeGuestName();
            if(checkChangeAddress.isSelected())
                changeGuestEmail();
            if(checkChangePhoneNr.isSelected())
                changeGuestPhone();
            if(checkChangeEmail.isSelected())
                changeGuestEmail();
            if(checkChangeCompany.isSelected())
                changeGuestCompany();
        }// End of else if
        else if(checkBooking.getCheckedIn())
            employeeWindow.showMessage("Gjesten kan ikke endre opplysninger når "
                                        + "den er innsjekket", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
        else if(checkBooking.getCheckedOut())
            employeeWindow.showMessage("Gjesten kan ikke endre opplysninger når "
                                        + "den er utsjekket", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
        else if(checkBooking.getCancelled())
            employeeWindow.showMessage("Gjesten kan ikke endre opplysninger når "
                                        + "den har kansellert", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
    }// End of method checkChangeInfo()
    
    
    
    //Metoden endrer fra og tildato i en valgt booking.
    public void changeDates()
    {
        int answer = JOptionPane.showOptionDialog(null, "Er du sikker på at du "
                + "vil endre dato?\nDu må da velge rom på nytt!", "Endre dato?", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        
        if(answer == JOptionPane.YES_OPTION)
        {
            String fromDate = checkInfoFromDate.getText().trim();
            String toDate = checkInfoToDate.getText().trim();
            Date fDate = employeeWindow.convertDate(fromDate);
            Date tDate = employeeWindow.convertDate(toDate);
            checkBooking.changeDates(fDate, tDate);
            checkChangeFromDate.setSelected(false);
            checkChangeToDate.setSelected(false);
            
            checkBooking.removeAllRooms();
            checkRooms.removeAll();
            DefaultListModel roomModel = (DefaultListModel)checkRooms.getModel();
            roomModel.removeAllElements();
            checkChangeRooms();
        }// End of if
        else
        {
            Date fromDate = checkBooking.getFromDate();
            Date toDate = checkBooking.getToDate();
            checkInfoFromDate.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(fromDate));
            checkInfoToDate.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(toDate));
            checkChangeFromDate.setSelected(false);
            checkChangeToDate.setSelected(false);
        }// End of else
    }// End of method changeDates()
    
    
    
    //Metoden endrer navnet til hovedgjesten.
    public void changeGuestName()
    {
        String firstName = checkInfoFirstName.getText().trim();
        String lastName = checkInfoLastName.getText().trim();
        
        if(!employeeWindow.regexName(firstName) || !employeeWindow.regexName(lastName))
        {
            employeeWindow.showMessage("Du må skrive inn fornavn og etternavn riktig\nNye "
                                        + "opplysninger vil ikke bli endret", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Person g = checkBooking.getGuest();
        if(!booking.changeName(firstName, lastName, g, checkBooking))
            employeeWindow.showMessage("Kan ikke endre navn", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
        else
        {
            checkChangeFirstName.setSelected(false);
            changeChangeLastName.setSelected(false);
            employeeWindow.showMessage("Endring av navn vellykket", "Vellykket", 
                                        JOptionPane.INFORMATION_MESSAGE);
        }
    }// End of method changeGuestName()
    
    
    
    //Metoden endrer adressen til hovedgjesten.
    public void changeGuestAddress()
    {
        String address = checkInfoAddress.getText().trim();
        
        if(!employeeWindow.regexAdr(address))
        {
            employeeWindow.showMessage("Du må skrive inn adresse riktig\nNye "
                                        + "opplysninger vil ikke bli endret", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Person g = checkBooking.getGuest();
        if(!booking.changeAddress(address, g, checkBooking))
            employeeWindow.showMessage("Kan ikke endre adresse", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
        else
        {
            checkChangeAddress.setSelected(false);
            employeeWindow.showMessage("Endring av adresse vellykket", "Vellykket", 
                                        JOptionPane.INFORMATION_MESSAGE);
        }
    }// End of method changeGuestAddress()
    
    
    //Metoden endrer emailen til hovedgjesten.
    public void changeGuestEmail()
    {
        String email = checkInfoEmail.getText().trim();
        
        if(!employeeWindow.regexEmail(email))
        {
            employeeWindow.showMessage("Du må skrive inn email riktig\nNye "
                                        + "opplysninger vil ikke bli endret", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Person g = checkBooking.getGuest();
        if(!booking.changeEmail(email, g, checkBooking))
            employeeWindow.showMessage("Kan ikke endre email", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
        else
        {
            checkChangeEmail.setSelected(false);
            employeeWindow.showMessage("Endring av email vellykket", "Vellykket", 
                                        JOptionPane.INFORMATION_MESSAGE);
        }
    }// End of method changeGuestEmail()
    
    
    
    //Metoden endrer telefonnummeret til hovedgjesten.
    public void changeGuestPhone()
    {
        String phoneNr = checkInfoPhoneNr.getText().trim();
        
        if(!employeeWindow.regexPhoneNr(phoneNr))
        {
            employeeWindow.showMessage("Du må skrive inn telefonnummer riktig\nNye "
                                        + "opplysninger vil ikke bli endret", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Person g = checkBooking.getGuest();
        if(!booking.changePhone(phoneNr, g, checkBooking))
            employeeWindow.showMessage("Kan ikke endre telefonnummer", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
        else
        {
            checkChangePhoneNr.setSelected(false);
            employeeWindow.showMessage("Endring av telefonnummer vellykket", 
                                        "Vellykket", JOptionPane.INFORMATION_MESSAGE);
        }
    }// End of method changeGuestPhone()
    
    
    
    //Metoden endrer firmanavn.
    public void changeGuestCompany()
    {
        String company = checkInfoCompany.getText().trim();
        
        if(!employeeWindow.regexCompany(company))
        {
            employeeWindow.showMessage("Du må skrive inn firmanavn riktig\nNye "
                                        + "opplysninger vil ikke bli endret", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Person g = checkBooking.getGuest();
        if(!checkBooking.changeCompanyName(company, g))
        {
            employeeWindow.showMessage("Kan ikke endre firmanavn fordi gjesten ikke er en firmakunde", 
                                        "Advarsel", JOptionPane.ERROR_MESSAGE);
            checkChangeCompany.setSelected(false);
            checkInfoCompany.setText("");
        }
        else
        {
            checkChangePhoneNr.setSelected(false);
            employeeWindow.showMessage("Endring av firmanavn vellykket", 
                                        "Vellykket", JOptionPane.INFORMATION_MESSAGE);
        }
    }// End of method changeGuestCompany()
    
    
    
    /* Metoden blir kalt opp hvis man trykker på knappen "Endre", 
     * og gjør at man kan endre medgjesters navn.*/
    public void checkChangeGuests()
    {
        if(checkBooking != null)
        {
            if(checkBooking instanceof ConferenceRoomBooking)
            {
                employeeWindow.showMessage("Dette valget er ikke tilgjengelig "
                                            + "for seminarrombooking", "Advarsel", 
                                            JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            DefaultListModel model = (DefaultListModel)checkGuests.getModel();
            JList<String> checkGuestsCopy = new JList<>(model);


            MyDialogWindow changeGuestWindow = new MyDialogWindowChangeGuests(employeeWindow, 
                                                "Endre medgjesters navn", checkGuestsCopy, this);
            
            changeGuestWindow.setLocationRelativeTo(employeeWindow);
            changeGuestWindow.setVisible(true);
                
            invoice.setText(checkBooking.getStringBuilder());
            checkPrice.setText("Totalpris: " + checkBooking.getPrice() + " NOK");
        }// End of if
        else
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
    }// End of method checkChangeGuests()
    
    
    
    /* Metoden blir kalt opp i MyDialogWindowChangeGuests i metoden addGuest().
     * Metoden legger til medgjestens navn som ligger i gjesten.*/
    public void checkAddGuestName(String guestName)
    {
        if(checkBooking != null)
        {
            if(checkBooking instanceof HotelRoomBooking)
            {
                HotelRoomBooking hotelRoomBooking = (HotelRoomBooking) checkBooking;
                hotelRoomBooking.addGuestName(guestName);
                DefaultListModel model = (DefaultListModel)checkGuests.getModel();
                model.addElement(guestName);
            }
            else
                employeeWindow.showMessage("Dette valget er ikke tilgjengelig "
                                            + "for seminarrombooking", "Advarsel", 
                                            JOptionPane.ERROR_MESSAGE);
        }// End of if
        else
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
    }// End of method checkAddGuestName()
    
    
    
    /* Metoden blir kalt opp i MyDialogWindowChangeGuests i metoden deleteGuest().
     * Metoden sletter medgjestens navn som ligger i gjesten.*/
    public void checkRemoveGuests(String guest)
    {
        if(checkBooking != null)
        {
            if(checkBooking instanceof HotelRoomBooking)
            {
                HotelRoomBooking hotelRoomBooking = (HotelRoomBooking) checkBooking;
                hotelRoomBooking.removeGuestName(guest);
            }
            else
                employeeWindow.showMessage("Dette valget er ikke tilgjengelig "
                                            + "for seminarrombooking", "Advarsel", 
                                            JOptionPane.ERROR_MESSAGE);
        }// End of if
        else
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
    }// End of method checkRemoveGuests
    
    
    
    /* Metoden blir kalt opp hvis man trykker på knappen "Endre" under tjenester,
     * og gjør at man kan slette og legge til tjenester i bookingen.*/
    public void checkChangeServices()
    {
        if(checkBooking != null)
        {
            if(checkBooking instanceof ConferenceRoomBooking)
            {
                employeeWindow.showMessage("Dette valget er ikke tilgjengelig "
                                            + "for seminarrombooking", "Advarsel", 
                                            JOptionPane.ERROR_MESSAGE);
                return;
            }
            DefaultListModel model = (DefaultListModel)checkServices.getModel();
            JList<String> checkServicesCopy = new JList<>(model);

            JList<String> typeOfService = serviceList.getServicesInfo();


            MyDialogWindow checkServiceWindow = new MyDialogWindowChangeServices(
                                                employeeWindow, "Endre tjenester", 
                                                checkServicesCopy, typeOfService, 
                                                serviceList, this);
            checkServiceWindow.setLocationRelativeTo(employeeWindow);
            checkServiceWindow.setVisible(true);
            
            invoice.setText(checkBooking.getStringBuilder());
            checkPrice.setText("Totalpris: " + checkBooking.getPrice() + " NOK");
        }// End of if
        else
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
    }// End of method checkChangeServices()
    
    
    
    /* Metoden blir kalt opp i MyDialogWindowChangeServices i metoden addService().
     * Metoden legger til valgt service i bookingen.*/
    public void checkAddService(List<Service> service)
    {
        if(checkBooking != null)
        {
            if(checkBooking instanceof HotelRoomBooking)
            {
                HotelRoomBooking hotelRoomBooking = (HotelRoomBooking) checkBooking;
                hotelRoomBooking.addService(service);
                DefaultListModel model = (DefaultListModel) checkServices.getModel();
                model.addElement(service);
            }
            else
                employeeWindow.showMessage("Dette valget er ikke tilgjengelig "
                                            + "for seminarrombooking", "Advarsel", 
                                            JOptionPane.ERROR_MESSAGE);
        }// End of if
        else
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
    }// End of method checkAddService(...)
    
    
    
    /* Metoden blir kalt opp i MyDialogWindowChangeServices i metoden deleteService().
     * Metoden sletter valgt service fra bookingen.*/
    public void checkRemoveService(String service)
    {
        if(checkBooking != null)
        {
            if(checkBooking instanceof HotelRoomBooking)
            {
                HotelRoomBooking hotelRoomBooking = (HotelRoomBooking) checkBooking;
                hotelRoomBooking.removeService(service);
            }
            else
                employeeWindow.showMessage("Dette valget er ikke tilgjengelig "
                                            + "for seminarrombooking", "Advarsel", 
                                            JOptionPane.ERROR_MESSAGE);
        }// End of if
        else
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
    }// End of method checkRemoveService()
    
    
    
    /* Metoden blir kalt opp når man trykker på knappen "Endre" under rommene,
     * og gjør at man kan slette og legge til rom i bookingen.*/
    public void checkChangeRooms()
    {
        if(checkBooking != null)
        {
            DefaultListModel model = (DefaultListModel)checkRooms.getModel();
            JList<Integer> checkRoomsCopy = new JList<>(model);

            JList<String> typeOfRoom;

            if(checkBooking instanceof HotelRoomBooking)
            {
                DefaultListModel hTypeOfRoom = new DefaultListModel<>();
                hTypeOfRoom.addElement("Enkeltrom");
                hTypeOfRoom.addElement("Dobbeltrom");
                hTypeOfRoom.addElement("Familierom");
                hTypeOfRoom.addElement("Suite");
                typeOfRoom = new JList<>(hTypeOfRoom);
            }// End of if
            else
            {
                DefaultListModel cTypeOfRoom = new DefaultListModel<>();
                cTypeOfRoom.addElement("Auditorium");
                cTypeOfRoom.addElement("Møterom");
                cTypeOfRoom.addElement("Grupperom");
                typeOfRoom = new JList<>(cTypeOfRoom);
            }// End of else

            DefaultListModel model2 = (DefaultListModel) typeOfRoom.getModel();
            JList<String> checkTypeOfRoom = new JList<>(model2);

            BookingTab bookingTabb = (BookingTab) bookingTab;
            MyDialogWindow checkRoomWindow = new MyDialogWindowChangeRooms(employeeWindow, 
                                             "Endre rom", checkRoomsCopy, checkTypeOfRoom, 
                                             hotelRoomList, conferenceRoomList, checkBooking, 
                                             this, bookingTabb);
            
            checkRoomWindow.setLocationRelativeTo(employeeWindow);
            checkRoomWindow.setVisible(true);
            
            invoice.setText(checkBooking.getStringBuilder());
            checkPrice.setText("Totalpris: " + checkBooking.getPrice() + " NOK");
        }// End of if
        else
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
    }// End of method checkChangeRooms()
    
    
    
    /* Metoden blir kalt opp i MyDialogWindowChangeRooms i metoden addRoom()
     * Metoden legger til valgt rom i bookingen.*/
    public void checkAddRoom(List<HotelRoom> hroom, List<ConferenceRoom> croom)
    {
        if(checkBooking != null)
        {
            if(checkBooking instanceof HotelRoomBooking)
            {
                HotelRoomBooking hotelRoomBooking = (HotelRoomBooking) checkBooking;
                hotelRoomBooking.addRoom(hroom);
            }
            else
            {
                ConferenceRoomBooking conferenceRoomBooking = (ConferenceRoomBooking) checkBooking;
                conferenceRoomBooking.addRoom(croom);
            }
        }// End of if
        else
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
    }// End of method checkAddRoom(...)
    
    
    
    /* Metoden blir kalt opp i MyDialogWindowChangeRooms i metoden deleteRoom().
     * Metoden sletter valgt rom fra bookingen.*/
    public void checkRemoveRoom(int room)
    {
        if(checkBooking != null)
        {
            checkBooking.removeRoom(room);
        }
        else
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
    }// End of method checkRemoveRoom(...)
    
    
    
    /* Metoden sjekker inn gjesten, hvis den ikke er kansellert, sjekket inn
     * eller sjekket ut.*/
    public void checkIn()
    {
        if(checkBooking != null)
        {
            if(booking.getCancelled(checkBooking))
            {
                employeeWindow.showMessage("Kan ikke sjekke inn fordi gjesten "
                                            + "har kansellert bookingen", "Informasjon", 
                                            JOptionPane.INFORMATION_MESSAGE);
            }
            else if(booking.getCheckedOut(checkBooking))
                employeeWindow.showMessage("Kan ikke sjekke inn fordi gjesten er "
                                            + "allerede utsjekket", "Informasjon", 
                                            JOptionPane.INFORMATION_MESSAGE);
            else if(booking.getCheckedIn(checkBooking))
                employeeWindow.showMessage("Kan ikke sjekke inn fordi gjesten er "
                                            + "allerede innsjekket", "Informasjon", 
                                            JOptionPane.INFORMATION_MESSAGE);
            else if(!booking.getCheckedIn(checkBooking))
            {
                booking.setCheckedIn(checkBooking);
                employeeWindow.showMessage("Gjesten har nå sjekket inn", "Vellykket", 
                                            JOptionPane.INFORMATION_MESSAGE);
            }
        }// End of if
        else
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
        
        checkDeleteInfoField();
    }// End of method checkIn()
    
    
    
    /* Metoden sjekker gjesten ut hvis den ikke er sjekket inn, har sjekket ut
     * eller er kansellert.*/
    public void checkOut()
    {
        if(checkBooking != null)
        {
            if(!booking.getCheckedIn(checkBooking))
                employeeWindow.showMessage("Kan ikke sjekke ut fordi gjesten har "
                                            + "ikke sjekket inn", "Informasjon", 
                                            JOptionPane.INFORMATION_MESSAGE);    
            else if(booking.getCheckedOut(checkBooking))
                employeeWindow.showMessage("Kan ikke sjekke ut fordi gjesten "
                                            + "allerede er sjekket ut", "Informasjon", 
                                            JOptionPane.INFORMATION_MESSAGE);
            else if(booking.setCheckedOut(checkBooking))
                employeeWindow.showMessage("Gjesten har nå sjekket ut", "Vellykket", 
                                            JOptionPane.INFORMATION_MESSAGE);
        }// End of if
        else
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
        
        checkDeleteInfoField();
    }// End of method checkOut()
    
    
    
    /* Metoden kansellerer bookingen, hvis den ikke allerede er kansellert, ikke
     * er sjekket inn og ikke er sjekket ut.*/
    public void checkCancel()
    {
        if(checkBooking != null)
        {
            if(booking.getCancelled(checkBooking))
                employeeWindow.showMessage("Bookingen er allerede kansellert", 
                                            "Informasjon", JOptionPane.INFORMATION_MESSAGE);
            else if(booking.getCheckedIn(checkBooking))
                employeeWindow.showMessage("Kan ikke kansellere bookingen fordi "
                                            + "gjesten har sjekket inn", "Informasjon", 
                                            JOptionPane.INFORMATION_MESSAGE);
            else if(booking.getCheckedOut(checkBooking))
                employeeWindow.showMessage("Kan ikke kansellere bookingen fordi "
                                            + "gjesten har sjekket ut", "Informasjon", 
                                            JOptionPane.INFORMATION_MESSAGE);
            else
            {
                employeeWindow.showMessage("Bookingen er nå kansellert", "Vellykket", 
                                            JOptionPane.INFORMATION_MESSAGE);
                booking.cancelBooking(checkBooking);
            }
        }// End of if
        else
            employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
        
        checkDeleteInfoField();
    }// End of method checkCancel()
    
    
    
    //Metoden skriver ut en JTextArea med faktura over bookingen.
    public void printInvoice()
    {
        try
        {
            if(checkBooking == null)
            {
                employeeWindow.showMessage("Ingen booking er hentet opp", "Advarsel", 
                                           JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            booking.showBooking(invoice, checkBooking);
            String output = invoice.getText();
            invoice.setText("FAKTURA\n\n");
            invoice.append(output);
            invoice.print();
        }
        catch (PrinterException pe)
        {
            Logger.getLogger(EmployeeWindow.class.getName()).log(Level.SEVERE, null, pe);
        }
    }// End of method printInvoice()
    
    
    
    //Metoden er en hjelpemetode for å slette alle felter i taben.
    private void checkDeleteInfoField()
    {
        checkBookingnr.setText("");
        checkFirstName.setText("");
        checkLastName.setText("");
        checkFromDate.setText("");
        checkInfoFirstName.setText("");
        checkInfoLastName.setText("");
        checkInfoAddress.setText("");
        checkInfoPhoneNr.setText("");
        checkInfoEmail.setText("");
        checkInfoCompany.setText("");
        checkInfoFromDate.setText("");
        checkInfoToDate.setText("");
        if(checkGuests.getModel().getSize() > 0)
            ((DefaultListModel)checkGuests.getModel()).removeAllElements();
        if(checkRooms.getModel().getSize() > 0)
            ((DefaultListModel)checkRooms.getModel()).removeAllElements();
        if(checkServices.getModel().getSize() > 0)
            ((DefaultListModel)checkServices.getModel()).removeAllElements();
        checkCheckedIn.setSelected(false);
        checkCheckedOut.setSelected(false);
        checkCancelled.setSelected(false);
        checkBooking = null;
        checkTypeOfBooking.setText("Bookingtype");
        checkPrice.setText("Totalpris");
    }// End of method checkDeleteInfoField()
    
    
    
    //Privat indre lytteklasse
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == checkGetBooking)
                checkGetBooking();
            else if(e.getSource() == checkChangeInfo)
                checkChangeInfo();
            else if(e.getSource() == checkIn)
                checkIn();
            else if(e.getSource() == checkOut)
                checkOut();
            else if(e.getSource() == checkCancel)
                checkCancel();
            else if(e.getSource() == checkChangeGuests)
                checkChangeGuests();
            else if(e.getSource() == checkChangeRooms)
                checkChangeRooms();
            else if(e.getSource() == checkChangeServices)
                checkChangeServices();
            else if(e.getSource() == invoiceButton)
                printInvoice();
        }
    } // End of class Listener
}// End of class CheckInCheckOutTab