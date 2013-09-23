/*
 * Anette Molund, s181083, 22.04.12.
 * 
 * Denne klassen er subklasse av Service og representerer fullpensjon i programmet.
 */

package service;

public class AllInclusive extends Service
{
    
    public AllInclusive()
    {
        super(200, "Fullpensjon");
    }
    
    
    
    @Override
    public String toString()
    {
        return "Fullpensjon: \t" + super.getPrice() + " NOK";
    }
}//End of class AllInclusive
