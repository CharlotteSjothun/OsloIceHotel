/*
 * Anette Molund, s181083, 22.04.12.
 * 
 * Denne klassen er subklasse av Service og representerer en ekstra pute i programmet.
 */

package service;

public class ExtraPillow extends Service
{
    public ExtraPillow()
    {
        super(50, "Ekstra pute");
    }
    
    
    
    @Override
    public String toString()
    {
        return "Ekstra pute: \t" + super.getPrice() + " NOK";
    }
}// End of class ExtraPillow