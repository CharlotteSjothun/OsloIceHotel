/*
 * Anette Molund, s181083, 23.04.12
 * 
 * Klassen er en subklasse av MyDialogWindow.
 * Denne klassen oppretter et dialogvindu hvor man kan velge hvilke romnummer man ønsker.
 */

package gui.extra;

import gui.EmployeeWindow;
import gui.tabs.BookingTab;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class MyDialogWindowRoomChooser extends MyDialogWindow
{
    private JList<Integer> list;
    private JButton choose, cancel;
    private BookingTab bookingTab;
    private MyDialogWindowRoomChooser.Listener listener;
    
    public MyDialogWindowRoomChooser(Window window, String title, JList<Integer> jList, BookingTab bTab)
    {
        super(window, title, Dialog.ModalityType.APPLICATION_MODAL);
        
        listener = new MyDialogWindowRoomChooser.Listener();
        bookingTab = bTab;
        list = jList;
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollList = new JScrollPane(list);
        
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(scrollList, BorderLayout.CENTER);
        
        JPanel buttons = new JPanel();
        
        choose = new JButton("Velg rom");
        choose.addActionListener(listener);
        buttons.add(choose);
        cancel = new JButton("Avbryt");
        cancel.addActionListener(listener);
        buttons.add(cancel);
        c.add(buttons, BorderLayout.PAGE_END);
        setSize(200,250);
    }// End of constructor
    
    
    
    /* Metoden kaller opp metoder i klassene BookingTab eller MyDialogWindowChangeRooms
     * avhengig av hvor denne metoden kalles opp.*/
    public void chooseRooms()
    { 
        List<Integer> selectedRooms = list.getSelectedValuesList();
        
        Window parentWindow = super.getWindow();
        
        if(parentWindow instanceof EmployeeWindow)
        {
            bookingTab.bookingAddRoomToList(selectedRooms);
            bookingTab.bookingAddElementInteger(selectedRooms);
        }
        else if(parentWindow instanceof MyDialogWindowChangeRooms)
        {
            ((MyDialogWindowChangeRooms)parentWindow).bookingAddElementInteger(selectedRooms);
            ((MyDialogWindowChangeRooms)parentWindow).bookingAddRoomToBookingList(selectedRooms);
            
        }
        setVisible(false);
    }
    
    
    
    // Privat indre lytteklasse
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == choose)
                chooseRooms();
            else if(e.getSource() == cancel)
                setVisible(false);
        }
    }// End of class Listener
} // End of class MyDialogWindowRoomChooser