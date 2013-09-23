/*
 * Anette, s181083, 13.05.12
 * 
 * Denne klassen oppretter  innholdet i Booking-taben, og inneholder metoder for å booke
 * rom på hotellet. Klassen har en egen indre lytteklasse.
 */

package gui.tabs;



import booking.Booking;
import booking.BookingList;
import booking.ConferenceRoomBooking;
import booking.HotelRoomBooking;
import gui.EmployeeWindow;
import gui.extra.MyDialogWindow;
import gui.extra.MyDialogWindowRoomChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import person.guest.CompanyGuest;
import person.guest.PrivateGuest;
import room.conferenceroom.ConferenceRoom;
import room.conferenceroom.ConferenceRoomList;
import room.hotelroom.*;
import service.*;

public class BookingTab extends JPanel
{
    private JComboBox<String> bookingHotelConference;
    private JTextArea bookingOutput;
    private List<String> bookingGuests, bookingService;
    private List<Integer> bookingRooms;
    private JButton bookingAddGuest, bookingSelectRoom, bookingSelectService;
    private JButton bookingDeleteRoom, bookingDeleteGuest, bookingDeleteService;
    private JButton bookingAddBooking, bookingGetPrice, getConfirmation;
    private JList<String> bookingGuestList, bookingTypeOfRoom;
    private JList bookingSelectedRooms;
    private JList<String> bookingTypeOfService, bookingSelectedServices;
    private DefaultListModel<String> bookingGuestModel, bookingRoomModel;
    private DefaultListModel<String> bookingServiceModel, hTypeOfRoom, cTypeOfRoom;
    private JTextField bookingFirstName, bookingLastName, bookingAddress;
    private JTextField bookingPhoneNr, bookingEmail, bookingGuestInput;
    private JTextField bookingCompany, bookingFromDate, bookingToDate;
    private JPanel left3;
    
    private Listener listener;
    private EmployeeWindow employeeWindow;
    private BookingList booking;
    private HotelRoomList hotelRoomList;
    private ConferenceRoomList conferenceRoomList;
    private ServiceList serviceList;
    
    public BookingTab(EmployeeWindow window, BookingList bookingList, 
                      HotelRoomList hRoomList, ConferenceRoomList cRoomList,
                      ServiceList sList)
    {
        employeeWindow = window;
        booking = bookingList;
        hotelRoomList = hRoomList;
        conferenceRoomList = cRoomList;
        serviceList = sList;
        listener = new Listener();
        
        //Panelet addbooking inneholder alle panelene i denne taben.
        JPanel addbooking = new JPanel(new BorderLayout());
        addbooking.setBackground(Color.WHITE);
        
        bookingRooms = new LinkedList<>();
        bookingService = new LinkedList<>();
        
        //Panelet left inneholder alle paneler på venstre side i denne taben.
        JPanel left = new JPanel(new GridLayout(5,1));
        
        //Panelet left0 inneholder valg av type booking, og ligger inne panelet left1.
        JPanel left0 = new JPanel();
        left0.setBackground(Color.WHITE);
        
        //Panelet left1 inneholder panelene left0 og left2.
        JPanel left1 = new JPanel(new GridBagLayout());
        GridBagConstraints gBC = new GridBagConstraints();
        left1.setBackground(Color.WHITE);
        left0.add(new JLabel("Velg type booking:"));
        bookingHotelConference = new JComboBox<>();
        bookingHotelConference.addItem("Hotellrom");
        bookingHotelConference.addItem("Seminarrom");
        bookingHotelConference.setBackground(Color.WHITE);
        bookingHotelConference.addActionListener(listener);
        left0.add(bookingHotelConference);
        gBC.gridx = 0;
        gBC.gridy = 0;
        left1.add(left0, gBC);
        left.add(left1);
        
        //Panelet left2 inneholder valg av datoer og ligger i panelet left1.
        JPanel left2 = new JPanel();
        left2.setBackground(Color.WHITE);
        left2.setBorder(BorderFactory.createTitledBorder("Velg datoer"));
        left2.add(new JLabel("Fra dato(dd.mm.åå)"));
        bookingFromDate = new JTextField(8);
        left2.add(bookingFromDate);
        left2.add(new JLabel("Til dato(dd.mm.åå)"));
        bookingToDate = new JTextField(8);
        left2.add(bookingToDate);
        gBC.gridx = 0;
        gBC.gridy = 1;
        left1.add(left2, gBC);
        
        //Panelet left3 inneholder valg av type rom.
        left3 = new JPanel();
        left3.setBackground(Color.WHITE);
        left3.setBorder(BorderFactory.createTitledBorder("Romtyper"));
        hTypeOfRoom = new DefaultListModel<>();
        hTypeOfRoom.addElement("Enkeltrom");
        hTypeOfRoom.addElement("Dobbeltrom");
        hTypeOfRoom.addElement("Familierom");
        hTypeOfRoom.addElement("Suite");
        
        cTypeOfRoom = new DefaultListModel<>();
        cTypeOfRoom.addElement("Auditorium");
        cTypeOfRoom.addElement("Møterom");
        cTypeOfRoom.addElement("Grupperom");
        
        bookingTypeOfRoom = new JList<>(hTypeOfRoom);
        bookingTypeOfRoom.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingTypeOfRoom.setVisibleRowCount( 5 );
        bookingTypeOfRoom.setFixedCellWidth( 100 );
        bookingTypeOfRoom.setFixedCellHeight( 15 );
        left3.add(new JScrollPane(bookingTypeOfRoom));
        
        bookingSelectRoom = new JButton("Velg rom");
        bookingSelectRoom.addActionListener(listener);
        bookingDeleteRoom = new JButton("Fjern");
        bookingDeleteRoom.addActionListener(listener);
        
        bookingRoomModel = new DefaultListModel<>();
        bookingSelectedRooms = new JList(bookingRoomModel);
        bookingSelectedRooms.setVisibleRowCount( 5 );
        bookingSelectedRooms.setFixedCellWidth( 100 );
        bookingSelectedRooms.setFixedCellHeight( 15 );
        JPanel roomButtons = new JPanel(new GridLayout(2,1,0,5));
        roomButtons.setBackground(Color.WHITE);
        roomButtons.add(bookingSelectRoom);
        roomButtons.add(bookingDeleteRoom);
        left3.add(roomButtons);
        left3.add(new JScrollPane(bookingSelectedRooms));
        left.add(left3);
        //Her slutter left3
        
        //Panelet bookingInput inneholder left4, left5 og left6.
        JPanel bookingInput = new JPanel(new GridLayout(3,1));
        bookingInput.setBackground(Color.WHITE);
        bookingInput.setBorder(BorderFactory.createTitledBorder("Kontaktinformasjon"));
        
        JPanel left4 = new JPanel(new GridLayout(1,4));
        left4.setBackground(Color.WHITE);
        left4.add(new JLabel("Fornavn"));
        bookingFirstName = new JTextField(12);
        bookingFirstName.setBounds(0, 0, 12, 10);
        left4.add(bookingFirstName);
        left4.add(new JLabel("Etternavn"));
        bookingLastName = new JTextField(12);
        left4.add(bookingLastName);
        bookingInput.add(left4);
        
        JPanel left5 = new JPanel(new GridLayout(1,4));
        left5.setBackground(Color.WHITE);
        left5.add(new JLabel("Adresse"));
        bookingAddress = new JTextField(12);
        left5.add(bookingAddress);
        left5.add(new JLabel("Telefonnummer"));
        bookingPhoneNr = new JTextField(12);
        left5.add(bookingPhoneNr);
        bookingInput.add(left5);
        
        JPanel left6 = new JPanel(new GridLayout(1,4));
        left6.setBackground(Color.WHITE);
        left6.add(new JLabel("E-mail"));
        bookingEmail = new JTextField(12);
        left6.add(bookingEmail);
        left6.add(new JLabel("Firmanavn"));
        bookingCompany = new JTextField(12);
        left6.add(bookingCompany);
        bookingInput.add(left6);
        left.add(bookingInput);
        
        //Panelet left7 inneholder medgjesters navn.
        JPanel left7 = new JPanel();
        left7.setBackground(Color.WHITE);
        left7.setBorder(BorderFactory.createTitledBorder("Medgjesters navn"));
        bookingGuests = new LinkedList<>();
        bookingGuestInput = new JTextField(14);
        left7.add(bookingGuestInput);
        JPanel guestButtons = new JPanel(new GridLayout(2,1,0,5));
        guestButtons.setBackground(Color.WHITE);
        bookingAddGuest = new JButton("Legg til");
        bookingAddGuest.addActionListener(listener);
        guestButtons.add(bookingAddGuest);
        bookingDeleteGuest = new JButton("Fjern");
        bookingDeleteGuest.addActionListener(listener);
        guestButtons.add(bookingDeleteGuest);
        left7.add(guestButtons);
        bookingGuestModel = new DefaultListModel<>();
        bookingGuestList = new JList<>(bookingGuestModel);
        bookingGuestList.setVisibleRowCount( 5 );
        bookingGuestList.setFixedCellWidth( 170 );
        bookingGuestList.setFixedCellHeight( 15 );
        bookingGuestList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION );
        left7.add(new JScrollPane(bookingGuestList));
        left.add(left7);
        
        //Panelet left8 inneholder valg av tjenester.
        JPanel left8 = new JPanel();
        left8.setBorder(BorderFactory.createTitledBorder("Tjenester"));
        left8.setBackground(Color.WHITE);
        bookingTypeOfService = serviceList.getServicesInfo();
        bookingTypeOfService.setVisibleRowCount( 5 );
        bookingTypeOfService.setFixedCellWidth( 140 );
        bookingTypeOfService.setFixedCellHeight( 15 );
        left8.add(new JScrollPane(bookingTypeOfService));
        JPanel serviceButtons = new JPanel(new GridLayout(2,1,0,5));
        serviceButtons.setBackground(Color.WHITE);
        bookingSelectService = new JButton("Velg tjeneste");
        bookingSelectService.addActionListener(listener);
        serviceButtons.add(bookingSelectService);
        bookingDeleteService = new JButton("Fjern");
        bookingDeleteService.addActionListener(listener);
        serviceButtons.add(bookingDeleteService);
        left8.add(serviceButtons);
        bookingServiceModel = new DefaultListModel<>();
        bookingSelectedServices = new JList<>(bookingServiceModel);
        bookingSelectedServices.setVisibleRowCount( 5 );
        bookingSelectedServices.setFixedCellWidth( 140 );
        bookingSelectedServices.setFixedCellHeight( 15 );
        left8.add(new JScrollPane(bookingSelectedServices));
        left.add(left8);
        
        //Panelet right inneholder alt på høyresiden i denne taben.
        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(Color.WHITE);
        
        
        JPanel book = new JPanel();
        book.setBackground(Color.WHITE);
        bookingGetPrice = new JButton("Estimer pris");
        bookingGetPrice.addActionListener(listener);
        book.add(bookingGetPrice);
        bookingAddBooking = new JButton("Bekreft booking");
        bookingAddBooking.addActionListener(listener);
        book.add(bookingAddBooking);
        right.add(book, BorderLayout.PAGE_START);
        
        JPanel confirmation = new JPanel();
        confirmation.setBackground(Color.WHITE);
        getConfirmation = new JButton("Skriv ut bekreftelse");
        getConfirmation.addActionListener(listener);
        confirmation.add(getConfirmation);
        right.add(confirmation);
        
        bookingOutput = new JTextArea(30,30);
        bookingOutput.setEditable(false);
        right.add(new JScrollPane(bookingOutput), BorderLayout.PAGE_END);
        
        left.setBackground(Color.WHITE);
        addbooking.add(left, BorderLayout.CENTER);
        addbooking.add(right, BorderLayout.LINE_END);
        add(addbooking);
        setBackground(Color.WHITE);
    }// End of constructor
    
    
    
    /* Når man trykker på knappen "Bekreft booking" blir denne metoden kalt opp.
     * Metoden leser inn all informasjon som er skrevet inn, tester om verdiene er gyldig, 
     * oppretter en gjest og en booking og skriver ut bookingbekreftelse.*/
    public void makeBooking()
    {
        String firstName = bookingFirstName.getText().trim();
        String lastName = bookingLastName.getText().trim();
        String address = bookingAddress.getText().trim();
        String phoneNr = bookingPhoneNr.getText().trim();
        String email = bookingEmail.getText().trim();
        String company = bookingCompany.getText().trim();
        String fDate = bookingFromDate.getText().trim();
        String tDate = bookingToDate.getText().trim();
        Date now = new Date();
        Date fromDate = employeeWindow.convertDate(fDate);
        Date toDate = employeeWindow.convertDate(tDate);
        
        if(fromDate == null || toDate == null)
        {
            employeeWindow.showMessage("Valgt dato er ikke gyldig", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(fromDate.compareTo(now) <= 0 || toDate.compareTo(fromDate) < 0 || fromDate.equals(toDate))
        {
            employeeWindow.showMessage("Valgt dato er ikke gyldig", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }

        PrivateGuest privateGuest;
        CompanyGuest companyGuest;
        
        if(!employeeWindow.regexName(firstName) || !employeeWindow.regexName(lastName))
        {
            employeeWindow.showMessage("Du må skrive inn fornavn og etternavn", 
                                        "Advarsel", JOptionPane.WARNING_MESSAGE);
        }
        else if(!employeeWindow.regexAdr(address))
            employeeWindow.showMessage("Du må skrive inn adresse riktig"
                                        + "\nGatenavn nr, postadresse Poststed\n"
                                        + "Eksempel: Storgata 1A, 0182 Oslo", 
                                        "Advarsel", JOptionPane.WARNING_MESSAGE);
        else if(!employeeWindow.regexPhoneNr(phoneNr))
            employeeWindow.showMessage("Du må skrive inn telefonnummer riktig", 
                                        "Advarsel", JOptionPane.WARNING_MESSAGE);
        else if(!employeeWindow.regexEmail(email))
            employeeWindow.showMessage("Du må skrive inn email riktig", 
                                        "Advarsel", JOptionPane.WARNING_MESSAGE);
        else
        {
            List<HotelRoom> hotelRoom = hotelRoomList.getRoomsByRoomNrs(bookingRooms);
            List<Service> service = serviceList.getServicesByString(bookingService);
            
            List<ConferenceRoom> conferenceRoom = conferenceRoomList.getRoomsByRoomNrs(bookingRooms);
            
            if(company.length() != 0 && !employeeWindow.regexCompany(company))
                employeeWindow.showMessage("Du må skrive inn firmanavn riktig", 
                                            "Advarsel", JOptionPane.WARNING_MESSAGE);
            else if(company.length() != 0 && employeeWindow.regexCompany(company))
            {
                companyGuest = new CompanyGuest(firstName, lastName, address, phoneNr, email, company, 
                                                bookingGuests);
                Booking b;
                
                if(bookingHotelConference.getSelectedItem().equals("Hotellrom"))
                {
                    b = new HotelRoomBooking(companyGuest, fromDate, toDate, hotelRoom, service);
                }
                else
                    b = new ConferenceRoomBooking(companyGuest, fromDate, toDate, conferenceRoom);
                
                booking.addBooking(b);
                booking.showBooking(bookingOutput, b);
            }// End of else if
            else
            {
                
                Booking b;
                
                if(bookingHotelConference.getSelectedItem().equals("Hotellrom"))
                {
                    int space = bookingCompareBedSpacesWithGuests();
                    
                    if(space < 0)
                    {
                        int answer = JOptionPane.showOptionDialog(null, "Du har lagt inn færre "
                        + "sengeplasser enn gjester. Er du sikker på at du vil fullføre?", 
                        "For få sengeplasser", JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                        
                        if(answer == JOptionPane.NO_OPTION)
                            return;
                    }
                    privateGuest = new PrivateGuest(firstName, lastName, address, phoneNr, email, 
                                                    bookingGuests);
                    b = new HotelRoomBooking(privateGuest, fromDate, toDate, hotelRoom, service);
                }
                else
                {
                    privateGuest = new PrivateGuest(firstName, lastName, address, phoneNr, email, 
                                                    bookingGuests);
                    b = new ConferenceRoomBooking(privateGuest, fromDate, toDate, conferenceRoom);
                }
                
                booking.addBooking(b);
                booking.showBooking(bookingOutput, b);
            }// End of else

            bookingDeleteInfoFields();
        }// End of else
    } // End of method makeBooking()
    
    
    
    // Metoden estimerer pris på valgte rom og tjenester.
    public void bookingEstimatePrice()
    {
        int days = bookingGetDays();
        
        if(days == -1)
            return;
        
        bookingOutput.setText("Prisoversikt\n\n");
        
        int price = 0;
        
        if(!bookingRooms.isEmpty())
        {
            bookingOutput.append("Rom\n");
            Iterator<Integer> roomIter = bookingRooms.iterator();

            while(roomIter.hasNext())
            {
                int roomNr = roomIter.next();
                if(bookingHotelConference.getSelectedItem().equals("Hotellrom"))
                {
                    HotelRoom hotelRoom = hotelRoomList.getHotelRoom(roomNr);
                    String roomType = hotelRoom.getTypeOfRoom();
                    if(hotelRoom instanceof SingleRoom)
                    {
                        bookingOutput.append(roomType + ": Romnummer " + roomNr 
                                + "\t" + SingleRoom.getPrice()*days + " NOK\n");
                        price += SingleRoom.getPrice()*days;
                    }
                    else if(hotelRoom instanceof DoubleRoom)
                    {
                        bookingOutput.append(roomType + ": Romnummer " + roomNr 
                                + "\t" + DoubleRoom.getPrice()*days + " NOK\n");
                        price += DoubleRoom.getPrice()*days;
                    }
                    else if(hotelRoom instanceof FamilyRoom)
                    {
                        bookingOutput.append(roomType + ": Romnummer " + roomNr 
                                + "\t" + FamilyRoom.getPrice()*days + " NOK\n");
                        price += FamilyRoom.getPrice()*days;
                    }
                    else if(hotelRoom instanceof Suite)
                    {
                        bookingOutput.append(roomType + ": Romnummer " + roomNr 
                                + "\t" + Suite.getPrice()*days + " NOK\n");
                        price += Suite.getPrice()*days;
                    }
                }// End of if
                else if (bookingHotelConference.getSelectedItem().equals("Seminarrom"))
                {
                    ConferenceRoom conferenceRoom = conferenceRoomList.getConferenceRoom(roomNr);
                    String roomType = conferenceRoom.getTypeOfRoom();
                    bookingOutput.append(roomType + ": Romnummer " + roomNr + "\t" + 
                                        conferenceRoom.getPrice() + " NOK\n");
                    price += conferenceRoom.getPrice();
                }
            }// End of while
        }// End of if
        
        if(!bookingService.isEmpty())
        {
            bookingOutput.append("\n\nEkstra tjenester\n");
            Iterator<String> serviceIter = bookingService.iterator();
            
            while(serviceIter.hasNext())
            {
                String serviceType = serviceIter.next();
                Service service = serviceList.getServiceByString(serviceType);
                
                if(service instanceof AllInclusive || service instanceof Breakfast)
                {
                    int guests = bookingGuests.size() + 1;
                    
                    bookingOutput.append("\n" + service.getType() + ":\t" 
                            + service.getPrice()*days*guests + " NOK");
                    price += service.getPrice()*days*guests;
                }
                else
                {
                    bookingOutput.append("\n" + service.getType() + ":\t" 
                            + service.getPrice() + " NOK");
                    price += service.getPrice();
                }
            }// End of while
        }// End of if
        
        bookingOutput.append("\n\nTotalpris:\t" + price + " NOK");
    }// End of method bookingEstimatePrice()
    
    
    
    // Metoden legger navnet til en medgjest inn i medgjestelista og i visningen.
    public void bookingAddGuest()
    {
        if(bookingHotelConference.getSelectedItem().equals("Seminarrom"))
        {
            employeeWindow.showMessage("Dette valget er ikke tilgjengelig for "
                                        + "seminarrombooking", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            bookingGuestInput.setText("");
            return;
        }
        
        String guestName = bookingGuestInput.getText().trim();
        
        if(!employeeWindow.regexFirstLastName(guestName))
        {
            employeeWindow.showMessage("Du må skrive inn fornavn og etternavn riktig.", 
                                        "Advarsel", JOptionPane.ERROR_MESSAGE);
            return;
        }
            
        if(guestName.length() == 0)
            return;
        bookingGuests.add(guestName);
        List<String> bookingGuest = new LinkedList<>();
        bookingGuest.add(guestName);
        bookingAddElement(bookingGuest, bookingGuestList);
        bookingGuestInput.setText("");
    }// End of method bookingAddGuest()
    
    
    
    // Metoden sletter valgt medgjestenavn både fra medgjesterlista og fra visningen.
    public void bookingDeleteGuestName()
    {
        if(bookingHotelConference.getSelectedItem().equals("Seminarrom"))
        {
            employeeWindow.showMessage("Dette valget er ikke tilgjengelig for "
                                        + "seminarrombooking", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String guest = bookingGuestList.getSelectedValue();
        int guestIndex = bookingGuestList.getSelectedIndex();
        
        if(guestIndex < 0)
            return;
        
        bookingGuestModel.remove(guestIndex);
        
        Iterator<String> iter = bookingGuests.iterator();
        
        while(iter.hasNext())
        {
            String name = iter.next();
            
            if(name.equals(guest))
                iter.remove();
        }
    } // End of method bookingGetDeleteGuestName()
    
    
    
    /* Metoden blir kalt opp når man trykker knappen "Velg rom".
     * Formaterer datoene, tester om de er gyldige, og kaller opp metodene bookingAddElement()
     * og bookingChooseRoomNr().*/
    public void bookingSelectRoom()
    {
        String fDate = bookingFromDate.getText().trim();
        String tDate = bookingToDate.getText().trim();
        Date nowDate = new Date();
        Date fromDate = employeeWindow.convertDate(fDate);
        Date toDate = employeeWindow.convertDate(tDate);
        
        if(fromDate == null || toDate == null)
        {
            employeeWindow.showMessage("Valgt dato er ikke gyldig", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(fromDate.compareTo(nowDate) <= 0 || toDate.compareTo(fromDate) < 0 || fromDate.equals(toDate))
        {
            employeeWindow.showMessage("Valgt dato er ikke gyldig", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(bookingTypeOfRoom.getSelectedValue() == null)
        {
            employeeWindow.showMessage("Du har ikke valgt romtype", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        List<String> bookingRoomsList = bookingTypeOfRoom.getSelectedValuesList();
        bookingAddElement(bookingRoomsList,bookingSelectedRooms);
        bookingChooseRoomNr(bookingTypeOfRoom.getSelectedValue());
    }// End of method bookingSelectRoom()
    
    
    
    // Metoden leser inn valgte datoer og kaller opp klassen MyDialogWindowRoomChooser.
    public void bookingChooseRoomNr(String roomType)
    {
        String fDate = bookingFromDate.getText().trim();
        String tDate = bookingToDate.getText().trim();
        Date fromDate = employeeWindow.convertDate(fDate);
        Date toDate = employeeWindow.convertDate(tDate);
        
        JList<Integer> roomList = employeeWindow.getVacancy(fromDate, toDate, roomType);
        DefaultListModel listModel = (DefaultListModel) roomList.getModel();
        JList<Integer> avalibleRoomList = new JList<>();
        avalibleRoomList.setModel(new DefaultListModel());
        DefaultListModel avalibleRoomListModel = (DefaultListModel) avalibleRoomList.getModel();
        
        
        if(!bookingRooms.isEmpty())
        {
            for(int i = 0; i < listModel.getSize(); i++)
            {
                Iterator<Integer> roomIter = bookingRooms.iterator();

                boolean noMatch = true;
                Integer listRoom = (Integer) listModel.getElementAt(i);

                while(roomIter.hasNext())
                {
                    Integer room = roomIter.next();

                    if(room.compareTo(listRoom) == 0)
                    {
                        noMatch = false;
                    }
                }// End of while
                if (noMatch)
                {
                    avalibleRoomListModel.addElement(listRoom);
                }
            }// End of for

            MyDialogWindow chooseRoomsWindow = new MyDialogWindowRoomChooser(employeeWindow, 
                                                "Velg rom", avalibleRoomList, this);
            chooseRoomsWindow.setLocationRelativeTo(employeeWindow);
            chooseRoomsWindow.setVisible(true);
        }// End of if
        else
        {
            MyDialogWindow chooseRoomsWindow = new MyDialogWindowRoomChooser(employeeWindow, 
                                                "Velg rom", roomList, this);
            chooseRoomsWindow.setLocationRelativeTo(employeeWindow);
            chooseRoomsWindow.setVisible(true);
        }
    }// End of method bookingChooseRoomNr(...)
    
    
    
    /* Metoden blir kalt opp i klassen MyDialogWindowRoomChooser, og legger en liste 
     * av rom inn i romlista.*/
    public void bookingAddRoomToList(List<Integer> room)
    {
        bookingRooms.addAll(room);
    }
    
    
    
    /* Metoden blir kalt opp i klassen MyDialogWindowRoomChooser, og legger inn elementene 
     * i en List av Integer inn i JList-en med valgte rom.*/
    public void bookingAddElementInteger(List<Integer> elements)
    {
        DefaultListModel bookingModelList = (DefaultListModel) bookingSelectedRooms.getModel();
        for(int s : elements)
            bookingModelList.addElement(s);
    }// End of method bookingAddElementInteger(...)
    
    
    
    // Metoden sletter valgt rom både fra romlista og fra visningen.
    public void bookingDeleteRoom()
    {
        Object room = bookingSelectedRooms.getSelectedValue();
        if(room instanceof Integer)
        {
            Integer intRoom = (Integer) room;

            Iterator<Integer> iter = bookingRooms.iterator();

            while(iter.hasNext())
            {
                int rooms = iter.next();

                if(rooms == intRoom)
                    iter.remove();
            }
        }// End of if
        
        int roomIndex = bookingSelectedRooms.getSelectedIndex();
        
        if(roomIndex < 0)
            return;
        
        bookingRoomModel.remove(roomIndex);
    }// End of method bookingDeleteRoom()
    
    
    
    // Metoden legger tjenesten inn i visningen.
    public void bookingSelectService()
    {
        if(bookingHotelConference.getSelectedItem().equals("Seminarrom"))
        {
            employeeWindow.showMessage("Dette valget er ikke tilgjengelig for "
                                        + "seminarrombooking", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        List<String> bookingServices = bookingTypeOfService.getSelectedValuesList();
        bookingAddElement(bookingServices, bookingSelectedServices);
        bookingAddServiceToList(bookingServices);
    }// End of method bookingSelectService()
    
    
    
    // Metoden legger en liste av tjenester inn i tjenestelista.
    public void bookingAddServiceToList(List<String> service)
    {
        bookingService.addAll(service);
    }
    
    
    
    // Metoden sletter valgt tjenesten både fra tjenestelista og fra visningen.
    public void bookingDeleteServices()
    {
        if(bookingHotelConference.getSelectedItem().equals("Seminarrom"))
        {
            employeeWindow.showMessage("Dette valget er ikke tilgjengelig for "
                                        + "seminarrombooking", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String service = bookingSelectedServices.getSelectedValue();
        int serviceIndex = bookingSelectedServices.getSelectedIndex();
        
        if(serviceIndex < 0)
            return;
        
        bookingServiceModel.remove(serviceIndex);
        
        Iterator<String> iter = bookingService.iterator();
        
        boolean match = false;
        
        while(iter.hasNext())
        {
            String services = iter.next();
            
            if(services.equals(service))
            {
                iter.remove();
                match = true;
            }
            
            if(match)
                return;
        }
    } // End of method bookingDeleteServices()
    
    
    
    // Metoden legger inn elementene i en List av String inn i en JList av String.
    public void bookingAddElement(List<String> elements, JList<String> list)
    {
        DefaultListModel bookingModelList = (DefaultListModel) list.getModel();
        for(String s : elements)
            bookingModelList.addElement(s);
    }// End of method bookingAddElement(...)
    
    
    
    // Metoden skriver ut JTextArea med bekreftelse på bookingen.
    public void printConfirmation()
    {
        try
        {
            String output = bookingOutput.getText();
            bookingOutput.setText("BEKREFTELSE\n\n");
            bookingOutput.append(output);
            bookingOutput.print();
        }
        catch (PrinterException pe)
        {
            Logger.getLogger(EmployeeWindow.class.getName()).log(Level.SEVERE, null, pe);
        }
    }// End of method printConfirmation()
    
    
    
    /* Metoden regner ut antall senger og antall gjester, og returnerer 0 om det akkurat er plass,
     * og antall for mye eller for lite ellers.*/
    private int bookingCompareBedSpacesWithGuests()
    {
        int guests = bookingGuests.size() + 1;
        int bedSpaces = 0;
        
        Iterator<Integer> roomIter = bookingRooms.iterator();
        
        while(roomIter.hasNext())
        {
            int roomNr = roomIter.next();
            HotelRoom room = hotelRoomList.getHotelRoom(roomNr);
            bedSpaces += room.getNrOfBedspaces();
        }
        
        Iterator<String> serviceIter = bookingService.iterator();
        
        while(serviceIter.hasNext())
        {
            String serviceString = serviceIter.next();
            Service service = serviceList.getServiceByString(serviceString);
            if(service instanceof ExtraBed)
                bedSpaces++;
        }
        
        return bedSpaces - guests;
    }// End of method bookingCompareBedSpacesWithGuests()
    
    
    
    /* Metoden bytter visning av type rom utfra om det er valgt hotellrom eller 
     * seminarrom i comboBox.*/
    private void bookingUpdateRoomTypes()
    {
        if(bookingHotelConference.getSelectedItem().equals("Hotellrom"))
        {
            bookingTypeOfRoom.setModel(hTypeOfRoom);
        }
        else
            bookingTypeOfRoom.setModel(cTypeOfRoom);
    } // End of method bookingUpdateRoomTypes()
    
    
    
    // Metoden beregner og returnerer antall dager som er valgt for bookingen.
    private int bookingGetDays()
    {
        String fDate = bookingFromDate.getText().trim();
        String tDate = bookingToDate.getText().trim();
        
        if(fDate.length() == 0 || tDate.length() == 0)
        {
            employeeWindow.showMessage("Du må velge dato før du kan estimere pris", "Advarsel", 
                                        JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        
        Date fromDate = employeeWindow.convertDate(fDate);
        Date toDate = employeeWindow.convertDate(tDate);
        
        long milisecound = toDate.getTime() - fromDate.getTime();
        int days = (int) Math.ceil(milisecound/86400000L);
        
        return days;
    }// End of method bookingGetDays()
    
    
    
    // Metoden er en hjelpemetode for å slette alle felter i taben.
    private void bookingDeleteInfoFields()
    {
        bookingFromDate.setText("");
        bookingToDate.setText("");
        bookingFirstName.setText("");
        bookingLastName.setText("");
        bookingAddress.setText("");
        bookingPhoneNr.setText("");
        bookingEmail.setText("");
        bookingCompany.setText("");
        bookingGuestInput.setText("");
        bookingRoomModel.removeAllElements();
        bookingServiceModel.removeAllElements();
        bookingGuestModel.removeAllElements();
        bookingRooms.clear();
        bookingGuests.clear();
        bookingService.clear();
    }// End of method bookingDeleteInfoFields()
    
    
    
    // Privat indre lytteklasse.
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == bookingAddGuest)
                bookingAddGuest();
            else if(e.getSource() == bookingSelectService)
                bookingSelectService();
            else if(e.getSource() == bookingSelectRoom)
                bookingSelectRoom();
            else if(e.getSource() == bookingAddBooking)
                makeBooking();
            else if(e.getSource() == bookingHotelConference)
                bookingUpdateRoomTypes();
            else if(e.getSource() == bookingDeleteGuest)
                bookingDeleteGuestName();
            else if(e.getSource() == bookingDeleteService)
                bookingDeleteServices();
            else if(e.getSource() == bookingDeleteRoom)
                bookingDeleteRoom();
            else if (e.getSource() == bookingGetPrice)
                bookingEstimatePrice();
            else if(e.getSource() == getConfirmation)
                printConfirmation();
        }
    } // End of class Listener
}// End of class BookingTab
