/*
 * Anette Molund, s181083, 22.04.12.
 * 
 * Denne klassen er subklasse av Service og representerer en ekstra seng i programmet.
 */

package service;

public class ExtraBed extends Service
{
    public ExtraBed()
    {
        super(200, "Ekstra seng");
    }
    
    
    
    @Override
    public String toString()
    {
        return "Ekstra seng: \t" + super.getPrice() + " NOK";
    }
}// End of class ExtraBed
