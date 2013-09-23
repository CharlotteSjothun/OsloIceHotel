/*
 * Charlotte Sjøthun s180495, 04.05.2012
 * 
 * Klassen PrivateGuest er subklasse av Person og representerer en privatgjest i programmet.
 */

package person.guest;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import person.Person;

public class PrivateGuest extends Person
{
    private List<String> travelCompanionsNames;
    
    public PrivateGuest(String firstname, String lastname, String address, String phone, 
                        String email, List<String> guests)
    {
        super(firstname, lastname, address, phone, email);
        travelCompanionsNames = new LinkedList<>(guests);
    }
    
    
    
    public List<String> getGuestNames()
    {
        return travelCompanionsNames;
    }
    
    
    
    // Metoden legger til navnet som kommer inn som parameter i guestNames lista
    public boolean addGuestName(String newName)
    {
        return travelCompanionsNames.add(newName);
    }
    
    
    
    /* Metoden finner første forekomst av navnet som kommer inn som parameter og
     * sletter den fra guestNames lista. Returnerer true om den fikk slettet navnet
     * ellers returnerer den false.*/
    public boolean removeGuestName(String oldName)
    {
        Iterator<String> iterator = travelCompanionsNames.iterator();

    	while (iterator.hasNext())
    	{
            String name = iterator.next();
            
            if(name.compareToIgnoreCase(oldName) == 0)
            {
                iterator.remove();
                return true;
            }
    	}
        
        return false;
    }
    
    
    
    @Override
    public String toString()
    {
        String travelCompanions = "";
        boolean hasCompanions = false;
        Iterator<String> iterator = travelCompanionsNames.iterator();

    	while (iterator.hasNext())
    	{
            travelCompanions += "\n   " + iterator.next();
            hasCompanions = true;
    	}
        
        return "Kontaktperson:\n" + super.toString()
               + (hasCompanions ? "\nReisefølge: " + travelCompanions : "");
    }
} // End of class PrivateGuest