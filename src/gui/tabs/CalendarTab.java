/*
 * Nanna Mjørud s180477, 13.05.12
 * 
 * Klassen er en av fanene som vises i ansatt-delen av programmet. Den viser de hotellrommene/seminarrommene 
 * som er ledige mellom de innskrevne datoene.
 */

package gui.tabs;

import gui.EmployeeWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;
import room.conferenceroom.ConferenceRoom;
import room.conferenceroom.ConferenceRoomList;
import room.hotelroom.*;

public class CalendarTab extends JPanel
{
    private JTextField fromDate, toDate;
    private JButton showRooms;
    private JTextArea roomList;
    private JComboBox<String> typeOfRoom;
    private EmployeeWindow empWindow;
    private Listener listener;
    private HotelRoomList hotelRoomList;
    private ConferenceRoomList conferenceRoomList;
    private JPanel calendar;
    private String[] confOrHotelRoom = {"Seminarrom", "Hotellrom"};
    private String[] hotelRoomTypes, conferenceRoomTypes;
    
    public CalendarTab(EmployeeWindow window, HotelRoomList hRoomList, ConferenceRoomList cRoomList)
    {
        empWindow = window;
        listener = new Listener();
        hotelRoomList = hRoomList;
        conferenceRoomList = cRoomList;
        hotelRoomTypes = hotelRoomList.getRoomTypes();
        conferenceRoomTypes = conferenceRoomList.getRoomTypes();
        
        calendar = new JPanel(new BorderLayout());
        calendar.setBackground(Color.WHITE);
        
        JPanel topPanel = new JPanel(new GridLayout(4,1));
        topPanel.setBackground(Color.WHITE);
        
        JPanel top1 = new JPanel();
        top1.setBackground(Color.WHITE);
        top1.add(new JLabel("Velg type rom"));
        typeOfRoom = new JComboBox<>(confOrHotelRoom);
        typeOfRoom.setBackground(Color.WHITE);
        top1.add(typeOfRoom);
        
        JPanel top2 = new JPanel();
        top2.setBackground(Color.WHITE);
        top2.add(new JLabel("Fra dato (dd.mm.yy):"));
        fromDate = new JTextField(11);
        top2.add(fromDate);
        
        JPanel top3 = new JPanel();
        top3.setBackground(Color.WHITE);
        top3.add(new JLabel("Til dato (dd.mm.yy):"));
        toDate = new JTextField(11);
        top3.add(toDate);
        
        JPanel top4 = new JPanel();
        top4.setBackground(Color.WHITE);
        showRooms = new JButton("Vis ledige rom");
        showRooms.addActionListener(listener);
        top4.add(showRooms);
        
        topPanel.add(top1);
        topPanel.add(top2);
        topPanel.add(top3);
        topPanel.add(top4);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        roomList = new JTextArea(26, 50);
        roomList.setEditable(false);
        bottomPanel.add(new JScrollPane(roomList));
        
        calendar.add(topPanel, BorderLayout.NORTH);
        calendar.add(bottomPanel, BorderLayout.CENTER);
        
        add(calendar);
        setBackground(Color.WHITE);
        
    } // End of constructor
    
    
    
    /* Metoden viser ledige rom av valgt kategori (hotellrom eller seminarrom) mellom de innskrevne datoene.
     * Datoene må være skrevet riktig og være fremover i tid. */
    private void showRoomsFromDates()
    {
        String selectedRoomType = (String)typeOfRoom.getSelectedItem();
        
        Date dateFrom = empWindow.convertDate(fromDate.getText());        
        Date dateTo = empWindow.convertDate(toDate.getText());
        Date now = new Date();
                
        if (dateFrom == null || dateTo == null || dateFrom.compareTo(now) < 0 ||
            dateFrom.compareTo(dateTo) > 0)
        {
            empWindow.showMessage("Feil i innskrevne datoer", "Feil", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String roomType;

        JList<Integer> roomNrList;
        
        if (selectedRoomType.equals(confOrHotelRoom[0]))
            roomList.setText("Ledige seminarrom for perioden: ");
        else
            roomList.setText("Ledige hotellrom for perioden: ");

        roomList.append(DateFormat.getDateInstance(DateFormat.MEDIUM).format(dateFrom) + " - " + 
                DateFormat.getDateInstance(DateFormat.MEDIUM).format(dateTo) + "\n");
        
        if (selectedRoomType.equals(confOrHotelRoom[0]))
        {
            for (int i = 0; i < 3; i++)
            {
                roomType = conferenceRoomTypes[i];

                roomNrList = empWindow.getVacancy(dateFrom, dateTo, roomType);

                ConferenceRoom room = conferenceRoomList.getConferenceRoom
                        (roomNrList.getModel().getElementAt(1));

                int price = room.getPrice();

                roomList.append("\n\n" + roomType + ", " + price + " NOK, antall ledige: " + 
                                        roomNrList.getModel().getSize() + ", ledige romnr: ");

                for (int j = 0; j < roomNrList.getModel().getSize(); j++)
                {
                    roomList.append(roomNrList.getModel().getElementAt(j) + "");

                    if (j != roomNrList.getModel().getSize() - 1)
                        roomList.append(", ");
                }
            }
        }
        else
        {   
            int price = 0;
            for (int i = 0; i < 4; i++)
            {
                roomType = hotelRoomTypes[i];

                roomNrList = empWindow.getVacancy(dateFrom, dateTo, roomType);

                HotelRoom room = hotelRoomList.getHotelRoom
                        (roomNrList.getModel().getElementAt(1));

                if (room instanceof SingleRoom)
                    price = SingleRoom.getPrice();
                else if (room instanceof DoubleRoom)
                    price = DoubleRoom.getPrice();
                else if (room instanceof FamilyRoom)
                    price = FamilyRoom.getPrice();
                else if (room instanceof Suite)
                    price = Suite.getPrice();

                roomList.append("\n\n" + roomType + ", " + price + 
                        " NOK, antall ledige: " + roomNrList.getModel().getSize() + 
                        ", ledige romnr:\n\t");

                for (int j = 0; j < roomNrList.getModel().getSize(); j++)
                {
                    roomList.append(roomNrList.getModel().getElementAt(j) 
                            + "");

                    if (j % 16 == 15)
                        roomList.append("\n\t");
                    else if (j != roomNrList.getModel().getSize() - 1)
                        roomList.append(", ");
                }
            }
        }        
    } // End of method showRoomsFromDates()
    
    
    
    // Privat indre lytterklasse
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == showRooms)
                showRoomsFromDates();
        }
    } // End of class Listener
    
} // End of class CalendarTab
