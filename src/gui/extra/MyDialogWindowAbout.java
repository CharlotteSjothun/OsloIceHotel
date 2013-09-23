/*
 * Charlotte Sjøthun s180495, 04.05.2012
 * 
 * Klassen MyWindowAbout oppretter et vindu som viser info om hvem som har laget programmet.
 */

package gui.extra;

import java.awt.Dialog;
import java.awt.Window;
import javax.swing.JTextArea;

public class MyDialogWindowAbout extends MyDialogWindow
{
    private JTextArea output;
    
    public MyDialogWindowAbout(Window window, String title)
    {
        super(window, title, Dialog.ModalityType.APPLICATION_MODAL);
        
        output = new JTextArea();
        output.setEditable(false);
        output.setText("Programmet er laget av Anette Molund, Nanna Mjørud og Charlotte Sjøthun."
                     + "\nStuderer dataingeniør og informasjonsteknologi ved HiOA. Våren 2012");
        add(output);
        setSize(500,300);
    }
} // End of class MyDialogWindowAbout