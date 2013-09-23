/*
 * Anette Molund s181083, 13.05.12
 * 
 * Denne klassen oppretter innholdet i Vis Bookinger-taben, og inneholder metoder for å 
 * vise bookinger på forskjellige måter, og vise innlosjerte gjester. 
 * Klassen har en egen indre lytteklasse.
 */ 

package gui.tabs;

import booking.BookingList;
import gui.EmployeeWindow;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;


public class ShowBookingsTab extends JPanel
{
    private JTextArea output;
    private JTextField fromDate, toDate, firstName, lastName;
    private JButton getBookingsByDates, getBookingsByName, getAccommodatedGuests;
    
    private EmployeeWindow employeeWindow;
    private BookingList bookingList;
    private Listener listener;
    
    public ShowBookingsTab(EmployeeWindow window, BookingList bookingL)
    {
        employeeWindow = window;
        bookingList = bookingL;
        listener = new Listener();
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gBC = new GridBagConstraints();
        panel.setBackground(Color.WHITE);
        
        JPanel input = new JPanel(new GridLayout(2,1));
        
        JPanel dateInput = new JPanel(new GridLayout(1,4));
        dateInput.setBorder(BorderFactory.createTitledBorder("Hent booking på dato"));
        dateInput.setBackground(Color.WHITE);
        dateInput.add(new JLabel("Fra dato"));
        fromDate = new JTextField(10);
        dateInput.add(fromDate);
        dateInput.add(new JLabel("Til dato"));
        toDate = new JTextField(10);
        dateInput.add(toDate);
        input.add(dateInput);
        
        JPanel nameInput = new JPanel(new GridLayout(1,4));
        nameInput.setBorder(BorderFactory.createTitledBorder("Hent booking på navn"));
        nameInput.setBackground(Color.WHITE);
        nameInput.add(new JLabel("Fornavn"));
        firstName = new JTextField(10);
        nameInput.add(firstName);
        nameInput.add(new JLabel("Etternavn"));
        lastName = new JTextField(10);
        nameInput.add(lastName);
        dateInput.add(toDate);
        input.add(nameInput);
        
        gBC.gridx = 0;
        gBC.gridy = 0;
        panel.add(input, gBC);
        
        JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        getBookingsByDates = new JButton("Hent bookinger på dato");
        getBookingsByDates.addActionListener(listener);
        buttons.add(getBookingsByDates);
        getBookingsByName = new JButton("Hent bookinger på navn");
        getBookingsByName.addActionListener(listener);
        buttons.add(getBookingsByName);
        getAccommodatedGuests = new JButton("Vis innlosjerte gjester");
        getAccommodatedGuests.addActionListener(listener);
        buttons.add(getAccommodatedGuests);
        gBC.gridx = 0;
        gBC.gridy = 1;
        panel.add(buttons, gBC);
        
        output = new JTextArea(27,42);
        output.setEditable(false);
        gBC.gridx = 0;
        gBC.gridy = 2;
        panel.add(new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                       JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), gBC);
        add(panel);
        setBackground(Color.WHITE);
    }// End of constructor
    
    
    
    //Metoden skriver ut bookinger med datoer i intervallet brukeren skriver inn.
    public void showBookingsByDate()
    {
        String fDate = fromDate.getText().trim();
        String tDate = toDate.getText().trim();
        
        if(fDate.length() == 0 || tDate.length() == 0)
        {
            employeeWindow.showMessage("Du må skrive inn både fra og til dato",
                                        "Informasjon", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Date from = employeeWindow.convertDate(fDate);
        Date to = employeeWindow.convertDate(tDate);
        
        bookingList.showBookingsByDates(output, from, to);
        output.setCaretPosition(0);
    }// End of method showBookingsByDate()
    
    
    
    /* Metoden skriver ut bookinger som har kontaktperson med fornavnet og/eller
     * etternavnet brukeren har skrevet inn.*/
    public void showBookingsByName()
    {
        String first = firstName.getText().trim();
        String last = lastName.getText().trim();
        
        bookingList.showBookingsByName(output, first, last);
        output.setCaretPosition(0);
    }// End of method showBookingsByName()
    
    
    
    // Metoden skriver ut alle innlosjerte gjester og firmaer.
    public void showAccommodatedGuests()
    {
        bookingList.showAccommodatedGuests(output);
    }
    
    
    
    //Privat indre lytteklasse
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == getBookingsByDates)
                showBookingsByDate();
            else if(e.getSource() == getBookingsByName)
                showBookingsByName();
            else if(e.getSource() == getAccommodatedGuests)
                showAccommodatedGuests();
        }
    } // End of class Listener
}// End of class ShowBookingsTab