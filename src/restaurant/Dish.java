/*
 * Charlotte Sjøthun s180495, 04.05.2012
 * 
 * Klassen Dish representerer en matrett i programmet. 
 */

package restaurant;

import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.text.DecimalFormat;

public class Dish implements Serializable
{
    private static final long serialVersionUID = 
            ObjectStreamClass.lookup(Dish.class).getSerialVersionUID();
    
    private String dishName, typeOfDish;
    private int price;
    
    public Dish(String dishname, int dishPrice, String typeofdish)
    {
       dishName = dishname;
       price = dishPrice;
       typeOfDish = typeofdish;
    }
    
    
    
    public String getDish()
    {
        return dishName;
    }
    
    
    
    public void setPrice(int dishPrice)
    {
        price = dishPrice;
    }
    
    
    
    public int getPrice()
    {
        return price;
    }
    
    
    
    public String getTypeOfDish()
    {
        return typeOfDish;
    }
    
    
    
    @Override
    public String toString()
    {
        return dishName + "\t" + new DecimalFormat("0.00").format(price) + " kr";
    }    
} // End of class Dish
