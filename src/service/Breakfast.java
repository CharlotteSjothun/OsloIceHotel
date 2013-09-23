/*
 * Anette Molund, s181083, 22.04.12.
 * 
 * Denne klassen er subklasse av Service og representerer halvpensjon/frokost i 
 * programmet.
 */

package service;

public class Breakfast extends Service
{    
    public Breakfast()
    {
        super(50, "Halvpensjon");
    }
    
    
    
    @Override
    public String toString()
    {
        return "Halvpensjon: \t" + super.getPrice() + " NOK";
    }
}// End of class Breakfast
