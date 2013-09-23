/*
 * Anette Molund, s181083, 22.04.12.
 * 
 * Denne klassen er subklasse av Service og representerer en ekstra dyne i programmet.
 */

package service;

public class ExtraQuilt extends Service
{
    public ExtraQuilt()
    {
        super(50, "Ekstra dyne");
    }
    
    
    
    @Override
    public String toString()
    {
        return "Ekstra dyne: \t" + super.getPrice() + " NOK";
    }
}// End of class ExtraQuilt