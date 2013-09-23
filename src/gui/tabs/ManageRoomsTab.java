/*
 * Nanna Mjørud s180477, 13.05.12
 * 
 * Klassen er en av fanene i ansatt-delen av programmet. Den lar brukeren endre pris på enten hotellrom eller
 * seminarrom.
 */

package gui.tabs;

import gui.EmployeeWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import room.conferenceroom.ConferenceRoomList;
import room.hotelroom.HotelRoomList;

public class ManageRoomsTab extends JPanel
{
    private JTextField newRoomPrice;
    private JButton changeRoomPrice, showRoomList;
    private JTextArea hotelRoomListArea, conferenceRoomListArea;
    private JComboBox<String> typeOfRoom1, typeOfRoom2;
    private JPanel top1, manageRooms;
    private EmployeeWindow empWindow;
    private Listener listener;
    private HotelRoomList hotelRoomList;
    private ConferenceRoomList conferenceRoomList;
    private String[] confOrHotelRoom = {"Seminarrom", "Hotellrom"};
    private String[] hotelRoomTypes, conferenceRoomTypes;
    
    public ManageRoomsTab(EmployeeWindow window, HotelRoomList hRoomList, ConferenceRoomList cRoomList)
    {
        empWindow = window;
        listener = new Listener();
        hotelRoomList = hRoomList;
        conferenceRoomList = cRoomList;
        hotelRoomTypes = hotelRoomList.getRoomTypes();
        conferenceRoomTypes = conferenceRoomList.getRoomTypes();
        
        manageRooms = new JPanel(new BorderLayout());
        manageRooms.setBackground(Color.WHITE);
        
        JPanel topPanel = new JPanel(new GridLayout(3,1));
        topPanel.setBackground(Color.WHITE);
        
        top1 = new JPanel();
        top1.setBackground(Color.WHITE);
        
        top1.add(new JLabel("Velg type rom"));
        typeOfRoom1 = new JComboBox<>(confOrHotelRoom);
        typeOfRoom1.addActionListener(listener);
        typeOfRoom1.setBackground(Color.WHITE);
        top1.add(typeOfRoom1);

        typeOfRoom2 = new JComboBox<>(conferenceRoomTypes);
        typeOfRoom2.setBackground(Color.WHITE);
        top1.add(typeOfRoom2);
        
        JPanel top2 = new JPanel();
        top2.setBackground(Color.WHITE);
        top2.add(new JLabel("Ny pris:"));
        newRoomPrice = new JTextField(11);
        top2.add(newRoomPrice);
        
        JPanel top3 = new JPanel();
        top3.setBackground(Color.WHITE);
        changeRoomPrice = new JButton("Endre pris");
        changeRoomPrice.addActionListener(listener);
        top3.add(changeRoomPrice);
        showRoomList = new JButton("Vis romlister");
        showRoomList.addActionListener(listener);
        top3.add(showRoomList);
        
        topPanel.add(top1);
        topPanel.add(top2);
        topPanel.add(top3);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        hotelRoomListArea = new JTextArea(26, 25);
        hotelRoomListArea.setEditable(false);
        bottomPanel.add(new JScrollPane(hotelRoomListArea));        
        conferenceRoomListArea = new JTextArea(26, 25);
        bottomPanel.add(new JScrollPane(conferenceRoomListArea));
        conferenceRoomListArea.setEditable(false);
        
        manageRooms.add(topPanel, BorderLayout.NORTH);
        manageRooms.add(bottomPanel, BorderLayout.CENTER);  
        
        updateRoomTypeChooser();
        
        add(manageRooms);
        setBackground(Color.WHITE);
        
    } // End of constructor
    
    /* Metoden oppdaterer romtype-velger nr 2 til hotellrom-typer eller seminarrom-typer ettersom hva som 
     * velges i første romtype-velger */
    private void updateRoomTypeChooser()
    {        
        top1.remove(typeOfRoom2);
        
        String chosenRoomType = (String)typeOfRoom1.getSelectedItem();
        
        if (chosenRoomType.equals(confOrHotelRoom[0]))
            typeOfRoom2 = new JComboBox<>(conferenceRoomTypes);
        else
        {
            typeOfRoom2 = new JComboBox<>(hotelRoomTypes);
        }
        
        typeOfRoom2.setBackground(Color.WHITE);
        top1.add(typeOfRoom2);
        top1.validate();
        
    } // End of method updateRoomTypeChooser()
    
    
    // Metoden setter valgt romtype til den innskrevne prisen. Prisen må være et tall mellom 1 og 99999.
    private void changeRoomPrice()
    {
        String roomType1 = (String)typeOfRoom1.getSelectedItem();
        
        String newPrice = newRoomPrice.getText().trim();

        if (!newPrice.matches("\\d{1,6}"))
        {
            empWindow.showMessage("Du må skrive inn et tall mellom 1 og 99999!", "Advarsel", 
                                  JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String roomType2 = (String)typeOfRoom2.getSelectedItem();

        Object[] options = {"Ja", "Avbryt"};
        int confirmed = JOptionPane.showOptionDialog(null, "Vil du sette prisen for " + roomType2 + " til " + 
                                                     Integer.parseInt(newPrice) + " NOK?", "Endre pris", 
                                                     JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE, 
                                                     null, options, options[0]);

        if (confirmed == 0)
        {
            if (roomType1.equals(confOrHotelRoom[0]))
            {
                if (!conferenceRoomList.changePrice(Integer.parseInt(newPrice), roomType2))
                {
                    empWindow.showMessage("Feil ved endring av pris! Pris ble ikke endret.", "Feil", 
                                          JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            else
            {
                if (!hotelRoomList.changePrice(Integer.parseInt(newPrice), roomType2))
                {
                    empWindow.showMessage("Feil ved endring av pris! Pris ble ikke endret.", "Feil", 
                                          JOptionPane.ERROR_MESSAGE);
                    return;
                }                    
            }

            if (!hotelRoomListArea.getText().equals(""))
                    showRoomLists();

            empWindow.showMessage("Prisen på " + roomType2 + " er satt til " + newPrice + " NOK", 
                                  "Pris endret", JOptionPane.INFORMATION_MESSAGE);
        }      
    } // End of method changeRoomPrice()
    
    
    // Metoden viser oversikt over de ulike romtypene av hotellrom og seminarrom
    private void showRoomLists()
    {
        hotelRoomList.showHotelRoomTypes(hotelRoomListArea);
        conferenceRoomList.showConferenceRoomTypes(conferenceRoomListArea);
    }
    
    // Privat indre lytterklasse
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == showRoomList)
                showRoomLists();
            else if (e.getSource() == changeRoomPrice)
                changeRoomPrice();
            else if (e.getSource() == typeOfRoom1)
                updateRoomTypeChooser();
        }
    } // End of class Listener
    
} // End of class ManageRoomsTab
