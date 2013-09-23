/*
 * Anette Molund s181083, Charlotte Sjøthun s180495, 23.04.2012
 * 
 * Klassen MyDialogWindow er superklasse for flere dialogvinduer.
 */

package gui.extra;

import java.awt.Dialog;
import java.awt.Window;
import javax.swing.JDialog;

public class MyDialogWindow extends JDialog
{
    private Window window;
    
    public MyDialogWindow(Window parentWindow, String title, Dialog.ModalityType modalt)
    {
        super(parentWindow, title, modalt);
        window = parentWindow;
    }
    
    public Window getWindow()
    {
        return window;
    }
} // End of class MyDialogWindow