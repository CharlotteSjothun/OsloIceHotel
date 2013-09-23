/*
 * Charlotte Sjøthun s180495, 17.04.2012
 * 
 * Klassen MyTable oppretter en tabell.
 */

package gui.extra;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class MyTable extends JTable
{
    // Metoden setter hva slags farge som skal være på de forskjellige radene.
    @Override
    public Component prepareRenderer(TableCellRenderer rendrer, int row, int column)
    {
        Component c = super.prepareRenderer(rendrer, row, column);

        if (row % 2 == 0 && !isCellSelected(row, column))
        {
            c.setBackground(getBackground());
        }
        else if (isCellSelected(row, column))
        {
            c.setBackground(getSelectionBackground());
        }
        else
        {
            c.setBackground(new Color(0xDA,0xDD,0xED));
        }
        return c;
    } // End of method prepareRenderer(...)
    
    
    
    // Metoden setter størrelsen på kolonnene i tabellen
    public void initColumnSizes() 
    {
        TableColumn column;
        int columnCount = this.getColumnCount();
        
        for (int i = 0; i < columnCount; i++) 
        {
            column = this.getColumnModel().getColumn(i);
            
            switch (i)
            {
                case 0 : column.setPreferredWidth(5);
                            break;
                case 1 : column.setPreferredWidth(50);
                            break;
                case 2 : column.setPreferredWidth(50);
                            break;
                case 3 : column.setPreferredWidth(130);
                            break;
                case 4 : column.setPreferredWidth(25);
                            break;
                case 5 : column.setPreferredWidth(140);
                            break;
                case 6 : if (columnCount < 9)
                            column.setPreferredWidth(35);
                         else
                            column.setPreferredWidth(50);
                            break;
                case 7 : if (columnCount < 9)
                            column.setPreferredWidth(10);
                         else
                            column.setPreferredWidth(40);
                            break;
                case 8 : column.setPreferredWidth(40);
                            break;
                case 9 : column.setPreferredWidth(10);
            } // End of switch
        } // End of for
    } // End of method initColumnSizes()
} // End of class MyTable