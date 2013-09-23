/*
 * Anette Molund, s181083, 22.04.12.
 * 
 * Denne klassen er subklasse av Service og representerer en velkomstgave i programmet.
 */

package service;

public class WelcomeGift extends Service
{
    public WelcomeGift()
    {
        super(400, "Velkomstgave");
    }
    
    
    
    @Override
    public String toString()
    {
        return "Velkomstgave: \t" + super.getPrice() + " NOK";
    }
}
