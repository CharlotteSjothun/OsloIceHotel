/*
 * Charlotte Sjøthun s180495, 04.05.2012
 * 
 * Klassen Restaurant oppretter to lister (en liste av dish objekter 
 * og en liste av beverageobjekter) og utføre div operasjoner på dem.
 */

package restaurant;

import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTextArea;

public class Restaurant implements Serializable
{
    private static final long serialVersionUID = 
            ObjectStreamClass.lookup(Restaurant.class).getSerialVersionUID();
    
    private List<Dish> dishList;
    private List<Beverage> beverageList;
    
    public Restaurant()
    {
        dishList = new LinkedList<>();
        beverageList = new LinkedList<>();
    }
    
    
    
    // Metoden legger til dish objektet som kommer inn som parameter i dishList lista
    public boolean addDish(Dish dish)
    {
        return dishList.add(dish);
    }
    
    
    
    /* Metoden finner første forekomst av objektet med navn lik navnet som kommer inn som 
     * parameter og sletter den fra dishList lista. Returner true hvis den fikk slettet objektet
     * ellers false.*/
    public boolean removeDish(String dishName)
    {
        return dishList.remove(getDish(dishName));
    }
    
    
    
    // Metoden endrer prisen til første forekomst av objektet med navn lik parameteren.
    public boolean setDishPrice(int price, String dishName)
    {
        Dish dish = getDish(dishName);
        
        if (dish != null)
        {
            dish.setPrice(price);
            return true;
        }
        
        return false;
    } // End of method setDishPrice(...)
    
    
    
    /* Metoden finner første forekomst av dish objektet med navn lik parameteren og
     * returnerer objektet, finner den ingen returnerer den null*/
    public Dish getDish(String dishName)
    {
        Iterator<Dish> iterator = dishList.iterator();

    	while (iterator.hasNext())
    	{
            Dish dish = iterator.next();
            
            if(dish.getDish().compareToIgnoreCase(dishName) == 0)
            {
                return dish;
            }
    	}
        
        return null;
    } // End of method getDish(...)
    
    
    
    // Metoden legger til beverage objektet som kommer inn som parameter i beverageList lista
    public boolean addBeverage(Beverage beverage)
    {
        return beverageList.add(beverage);
    }
    
    
    
    /* Metoden finner første forekomst av objektet med navn lik navnet som kommer inn som 
     * parameter og sletter den fra beverageList lista. Returner true hvis den fikk slettet 
     * objektet ellers false.*/
    public boolean removeBeverage(String beverageName)
    {
        return beverageList.remove(getBeverage(beverageName));
    }
    
    
    
    // Metoden endrer prisen til første forekomst av objektet med navn lik parameteren.
    public boolean setBeveragePrice(int price, String beverageName)
    {
        Beverage bev = getBeverage(beverageName);
        
        if (bev != null)
        {
            bev.setPrice(price);
            return true;
        }
        
        return false;
    } // End of method setBeveragePrice(...)
    
    
    
    /* Metoden finner første forekomst av beverage objektet med navn lik parameteren og
     * returnerer objektet, finner den ingen returnerer den null*/
    public Beverage getBeverage(String beverageName)
    {
        Iterator<Beverage> iterator = beverageList.iterator();

    	while (iterator.hasNext())
    	{
            Beverage bev = iterator.next();
            
            if(bev.getBeverage().compareToIgnoreCase(beverageName) == 0)
            {
                return bev;
            }
    	}
        
        return null;
    } // End of method getBeverage(...)
    
    
    
    /* Metoden mottar et JTextArea og bygger opp en String med overskrifter og 
     * toStringene til alle dish objektene i dishList som den til slutt viser 
     * i JTextArea*/
    public void showDishList(JTextArea output)
    {
        output.setText("Meny:");
        StringBuilder appetizer = new StringBuilder("Forrett\n");
        StringBuilder mainCourse = new StringBuilder("Hovedrett\n");
        StringBuilder dessert = new StringBuilder("Dessert\n");
        boolean hasAppetizer = false;
        boolean hasMainCourse = false;
        boolean hasDessert = false;
        
        Iterator<Dish> iterator = dishList.iterator();
           
        if (iterator.hasNext())
        {
            do
            {
                Dish dish = iterator.next();
                
                if (dish.getTypeOfDish().compareToIgnoreCase("forrett") == 0)
                {
                    hasAppetizer = true;
                    appetizer.append("\n    ");
                    appetizer.append(dish.toString());
                }
                else if(dish.getTypeOfDish().compareToIgnoreCase("hovedrett") == 0)
                {
                    hasMainCourse = true;
                    mainCourse.append("\n    ");
                    mainCourse.append(dish.toString());
                }
                else if(dish.getTypeOfDish().compareToIgnoreCase("dessert") == 0)
                {
                    hasDessert = true;
                    dessert.append("\n    ");
                    dessert.append(dish.toString());
                }
            }while (iterator.hasNext());
            
            output.append((hasAppetizer ? "\n\n" + appetizer : "")  
                        + (hasMainCourse ? "\n\n" + mainCourse : "") 
                        + (hasDessert ? "\n\n" + dessert : ""));
        } // End of if
        else
            output.append("\n\nDet er ikke registrert noen matretter i menyen.");
    } // End of method showDishList(...)
    
    
    
    /* Metoden mottar et JTextArea og bygger opp en String med overskrifter og 
     * toStringene til alle beverage objektene i beverageList som den til slutt 
     * viser i JTextArea*/
    public void showBeverageList(JTextArea output)
    {
        output.setText("Drikkemeny:\n");
        StringBuilder alcohol = new StringBuilder("Alkohol\n");
        boolean hasAlcohol = false;
        
        Iterator<Beverage> iterator = beverageList.iterator();
           
        if (iterator.hasNext())
        {
            do
            {
                Beverage bev = iterator.next();
                
                if (bev.getAlcohol())
                {
                    hasAlcohol = true;
                    alcohol.append("\n    ");
                    alcohol.append(bev.toString());
                }
                else
                    output.append("\n    " + bev.toString());
               
            }while (iterator.hasNext());
            
            output.append("\n\n" + (hasAlcohol ? alcohol : ""));
        } // End of if
        else
            output.append("\nDet er ikke registrert noen drikker i menyen.");
    } // End of method showBeverageList(...)
} // End of class Restaurant