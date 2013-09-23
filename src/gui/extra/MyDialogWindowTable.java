/*
 * Charlotte Sjøthun s180495, 04.05.2012
 * 
 * Klassen MyDialogWindowTable oppretter et vindu med en tabell
 */

package gui.extra;

import java.awt.Dialog;
import java.awt.Window;
import javax.swing.JScrollPane;

public class MyDialogWindowTable extends MyDialogWindow
{
    private MyTable table;
    
    public MyDialogWindowTable(Window window, String title, MyTable myTable)
    {
        super(window, title, Dialog.ModalityType.APPLICATION_MODAL);
        table = myTable;
        add(new JScrollPane(table));
        setSize(950,300);
    }
} // End of class MyDialogWindowTable