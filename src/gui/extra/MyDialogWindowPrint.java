/*
 * Anette Molund s181083, Charlotte Sjøthun s180495, 11.05.12
 * 
 * Klassen oppretter et vindu som lar brukeren velge om det skal skrives ut faktura eller 
 * bekreftelse på bookingen.
 */

package gui.extra;

import gui.EmployeeWindow;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class MyDialogWindowPrint extends MyDialogWindow
{
    private JComboBox<String> choosePrint;
    private JButton print;
    private Listener listener;
    
    public MyDialogWindowPrint(Window window, String title)
    {
        super(window, title, Dialog.ModalityType.APPLICATION_MODAL);
        listener = new Listener();
        
        JPanel c = new JPanel();
        choosePrint = new JComboBox<>();
        choosePrint.addItem("Bekreftelse");
        choosePrint.addItem("Faktura");
        choosePrint.addActionListener(listener);
        c.add(choosePrint);
        print = new JButton("Print");
        print.addActionListener(listener);
        c.add(print);
        add(c);
        
        c.setBackground(Color.WHITE);
        setSize(150,125);
    }// End of constructor
    
    
    
    /* Metoden kaller opp print-metoder klassen EmplopyeeWindow 
     * avhengig av hva som er valgt i comboboksen.*/
    public void print()
    {
        Window parentWindow = super.getWindow();
        if(choosePrint.getSelectedItem().equals("Bekreftelse"))
            ((EmployeeWindow)parentWindow).printConfirmation();
        else if(choosePrint.getSelectedItem().equals("Faktura"))
            ((EmployeeWindow)parentWindow).printInvoice();
        
        setVisible(false);
    }// End of method print()
    
    
    
    // Privat indre lytteklasse
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == print)
                print();
        }
    }// End of class Listener
}// End of class MyDialogWindowPrint
