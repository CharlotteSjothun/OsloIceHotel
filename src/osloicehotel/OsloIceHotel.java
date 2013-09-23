/*
 * Charlotte Sjøthun s180495, Anette Molund s181083, 27.04.12
 * 
 * Denne klassen inneholder main-metoden.
 */

package osloicehotel;

import gui.LoginWindow;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OsloIceHotel 
{
    public static void main (String [] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                final LoginWindow window = new LoginWindow();
                
                window.addWindowListener(new WindowAdapter()
                {
                    @Override
                    public void windowClosing(WindowEvent e)
                    {
                        window.writeToFile();
                        System.exit(0);
                    }
                });
            }
        });
    }
}


