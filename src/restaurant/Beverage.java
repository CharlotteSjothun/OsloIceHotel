/*
 * Charlotte Sjøthun s180495, 04.05.2012
 * 
 * Klassen Beverage representerer en drikke i programmet. 
 */

package restaurant;

import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.text.DecimalFormat;

public class Beverage implements Serializable
{
    private static final long serialVersionUID = 
            ObjectStreamClass.lookup(Beverage.class).getSerialVersionUID();
    
    private String beverageName;
    private int price;
    boolean alcohol;
    
    public Beverage(String bevName, int bevPrice, boolean bevAlcohol)
    {
        beverageName = bevName;
        price = bevPrice;
        alcohol = bevAlcohol;
    }
    
    
    
    public String getBeverage()
    {
        return beverageName;
    }
    
    
    
    public void setPrice(int bevPrice)
    {
        price = bevPrice;
    }
    
    
    
    public int getPrice()
    {
        return price;
    }
    
    
    
    public boolean getAlcohol()
    {
        return alcohol;
    }
    
    
    
    @Override
    public String toString()
    {
        return beverageName + "\t" + new DecimalFormat("0.00").format(price)
               + " kr";
    }
} // End of class Beverage