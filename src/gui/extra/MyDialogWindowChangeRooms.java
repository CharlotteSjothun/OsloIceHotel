/*
 * Anette Molund, s181083, 13.05.12
 * 
 * Klassen er en subklasse av MyDialogWindow.
 * Denne klassen oppretter et dialogvindu hvor man kan legge til og fjerne rom.
 */

package gui.extra;

import booking.Booking;
import gui.EmployeeWindow;
import gui.tabs.BookingTab;
import gui.tabs.CheckInCheckOutTab;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import room.conferenceroom.ConferenceRoom;
import room.conferenceroom.ConferenceRoomList;
import room.hotelroom.HotelRoom;
import room.hotelroom.HotelRoomList;


public class MyDialogWindowChangeRooms extends MyDialogWindow
{
    private JList<Integer> bookedRooms;
    private JList<String> typeOfRooms;
    private JButton add, delete, cancel;
    private Listener listener;
    private HotelRoomList hotelRoomList;
    private ConferenceRoomList conferenceRoomList;
    private Booking booking;
    private CheckInCheckOutTab checkInCheckOut;
    private BookingTab bookingTab;
    
    public MyDialogWindowChangeRooms(Window window, String title, 
            JList<Integer> bRooms, JList<String> tOfRooms, HotelRoomList hRoomList, 
            ConferenceRoomList cRoomList, Booking b, CheckInCheckOutTab checkInOut, 
            BookingTab bTab)
    {
        super(window, title, Dialog.ModalityType.APPLICATION_MODAL);
        listener = new Listener();
        bookedRooms = bRooms;
        typeOfRooms = tOfRooms;
        hotelRoomList = hRoomList;
        conferenceRoomList = cRoomList;
        checkInCheckOut = checkInOut;
        bookingTab = bTab;
        booking = b;
        
        bookedRooms.setFixedCellHeight(15);
        bookedRooms.setFixedCellWidth(140);
        bookedRooms.setVisibleRowCount(5);
        typeOfRooms.setFixedCellHeight(15);
        typeOfRooms.setFixedCellWidth(140);
        typeOfRooms.setVisibleRowCount(5);
        typeOfRooms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.setBackground(Color.WHITE);
        
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        
        panel1.add(new JScrollPane(typeOfRooms));
        
        JPanel panel2 = new JPanel(new GridLayout(2,1));
        panel2.setBackground(Color.WHITE);
        add = new JButton("Legg til");
        add.addActionListener(listener);
        panel2.add(add, BorderLayout.CENTER);
        delete = new JButton("Slett");
        delete.addActionListener(listener);
        panel2.add(delete);
        panel1.add(panel2);
        panel1.add(new JScrollPane(bookedRooms));
        c.add(panel1);
        
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.WHITE);
        cancel = new JButton("Bekreft");
        cancel.addActionListener(listener);
        panel3.add(cancel);
        c.add(panel3);
        setSize(425, 175);
    }// End of constructor
    
    
    
    /* Metoden sletter valgt romnummer fra visningen og kaller opp checkRemoveRoom i 
     * CheckInCheckOutTab for å slette rommet fra bookinglista.*/
    public void deleteRoom()
    {
        int room  = bookedRooms.getSelectedValue();
        int roomIndex = bookedRooms.getSelectedIndex();
        
        DefaultListModel model = (DefaultListModel)bookedRooms.getModel();
        model.remove(roomIndex);
        checkInCheckOut.checkRemoveRoom(room);
    }// End of method deleteRoom()
    
    
    
    /* Metoden leser inn valgt romtype, og kaller opp dialogvinduet MyDialogWindowRoomChooser for
     * at brukeren skal velge hvilke(t) romnummer som skal legges inn i lista.*/
    public void addRoom()
    {
        Date fromDate = booking.getFromDate();
        Date toDate = booking.getToDate();
        String roomType = typeOfRooms.getSelectedValue();
        
        JList<Integer> roomNrList = ((EmployeeWindow)super.getWindow()).getVacancy(fromDate, toDate, 
                                                                                   roomType);
        
        MyDialogWindow roomChooser = new MyDialogWindowRoomChooser(this, "Velg rom", roomNrList, 
                                                                   bookingTab);
        roomChooser.setLocationRelativeTo(this);
        roomChooser.setVisible(true);
    }// End of method addRoom()
    
    
    
    /* Metoden blir kalt opp i dialogvinduet MyDialogWindowRoomChooser, og kaller opp metoden
     * checkAddRoom i CheckInCheckOutTab for å legge til rommet i bookinglista.*/
    public void bookingAddRoomToBookingList(List<Integer> elements)
    {
        List<HotelRoom> hroom = hotelRoomList.getRoomsByRoomNrs(elements);
        List<ConferenceRoom> croom = conferenceRoomList.getRoomsByRoomNrs(elements);
        
        checkInCheckOut.checkAddRoom(hroom, croom);
    }// End of method bookingAddRoomToBookingList(...)
    
    
    
    /* Metoden blir kalt opp i dialogvinduet MyDialogWindowRoomChooser, og legger til valgte rom
     * i visningen.*/
    public void bookingAddElementInteger(List<Integer> hotelRoom)
    {
        DefaultListModel modelList = (DefaultListModel) bookedRooms.getModel();
        for(int s : hotelRoom)
            modelList.addElement(s);
    }// End of method bookingAddElementInteger(...)
    
    
    
    //Privat indre lytteklasse.
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == add)
                addRoom();
            else if(e.getSource() == delete)
                deleteRoom();
            else if(e.getSource() == cancel)
                setVisible(false);
        }
    }// End of class Listener
}// End of class MyDialogWindowChangeRooms
