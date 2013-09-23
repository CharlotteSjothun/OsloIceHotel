/*
 * Anette Molund, s181083, 23.04.12
 * 
 * Klassen er en subklasse av MyDialogWindow.
 * Denne klassen oppretter et dialogvindu hvor man kan legge til og fjerne medgjester.
 */

package gui.extra;

import gui.tabs.CheckInCheckOutTab;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MyDialogWindowChangeGuests extends MyDialogWindow
{
    private JList<String> guestList;
    private JTextField input;
    private JButton add, delete, cancel;
    private CheckInCheckOutTab checkInCheckOut;
    private Listener listener;
    
    public MyDialogWindowChangeGuests(Window window, String title, JList<String> guests, 
                                      CheckInCheckOutTab checkInOut)
    {
        super(window, title, Dialog.ModalityType.APPLICATION_MODAL);
        listener = new Listener();
        checkInCheckOut = checkInOut;
        guestList = guests;
        guestList.setFixedCellHeight(15);
        guestList.setFixedCellWidth(160);
        guestList.setVisibleRowCount(5);
        
        
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.setBackground(Color.WHITE);
        
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        
        input = new JTextField(12);
        input.addActionListener(listener);
        panel1.add(input);
        
        JPanel panel2 = new JPanel(new GridLayout(2,1));
        panel2.setBackground(Color.WHITE);
        add = new JButton("Legg til");
        add.addActionListener(listener);
        panel2.add(add, BorderLayout.CENTER);
        delete = new JButton("Slett");
        delete.addActionListener(listener);
        panel2.add(delete);
        panel1.add(panel2);
        panel1.add(new JScrollPane(guestList));
        c.add(panel1);
        
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.WHITE);
        cancel = new JButton("Bekreft");
        cancel.addActionListener(listener);
        panel3.add(cancel);
        c.add(panel3);
        setSize(435, 175);
    }// End of constructor
    
    
    
    /* Metoden sletter medgjestens navn fra visningen og kaller opp metode i CheckInCheckOutTab
     * som fjerner gjesten fra bookinglista.*/
    public void deleteGuest()
    {
        String guestName = guestList.getSelectedValue();
        int guestIndex = guestList.getSelectedIndex();
        
        DefaultListModel list = (DefaultListModel)guestList.getModel();
        list.remove(guestIndex);
        checkInCheckOut.checkRemoveGuests(guestName);
    }// End of method deleteGuest()
    
    
    
    /* Metoden legger medgjestens navn i visningen og kaller opp metode i CheckInCheckOutTab
     * som legger gjesten til i bookinglista.*/
    public void addGuest()
    {
        String guestName = input.getText().trim();
        
        checkInCheckOut.checkAddGuestName(guestName);
        input.setText("");
    }// End of method addGuest()
    
    
    
    // Privat indre lytteklasse
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == add)
                addGuest();
            else if(e.getSource() == delete)
                deleteGuest();
            else if(e.getSource() == cancel)
                setVisible(false);
        }
    }// End of class Listener
}// End of class MyDialogWindowChangeGuests