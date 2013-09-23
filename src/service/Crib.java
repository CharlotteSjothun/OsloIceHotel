/*
 * Anette Molund, s181083, 22.04.12.
 * 
 * Denne klassen er subklasse av Service og representerer en barneseng i programmet.
 */

package service;

public class Crib extends Service
{
    public Crib()
    {
        super(200, "Barneseng");
    }
    
    
    
    @Override
    public String toString()
    {
        return "Barneseng: \t" + super.getPrice() + " NOK";
    }
}// End of class Crib