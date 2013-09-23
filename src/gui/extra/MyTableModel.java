/*
 * Charlotte Sjøthun s180495, 17.04.2012
 * 
 * Klassen MyTableModel oppretter modellen for en tabell
 */

package gui.extra;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel
{
    private String[] columnName;
    private Object[][] columnValue;

    public MyTableModel(String[] name, Object[][] value)
    {
        columnName = name;
        columnValue = value;
    }

    
    
    @Override
    public int getRowCount() 
    {
        return columnValue.length;
    }

    
    
    @Override
    public int getColumnCount() 
    {
        return columnValue[0].length;
    }

    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        return columnValue[rowIndex][columnIndex];
    }

    
    
    @Override
    public String getColumnName(int columnIndex)
    {
        return columnName[columnIndex];
    }
    

    
    @Override
    public Class getColumnClass(int columnIndex)
    {
        return columnValue[0][columnIndex].getClass();
    }
} // End of class MyTableModel