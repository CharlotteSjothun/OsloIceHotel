/*
 * Charlotte Sjøthun s180495, 04.05.2012
 * 
 * Klassen CompanyGuest er subklasse av Person og representerer en firmagjest i programmet
 */

package person.guest;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import person.Person;

public class CompanyGuest extends Person
{
    private String companyName;
    private List<String> travelCompanionsNames;
    
    public CompanyGuest(String firstname, String lastname, String address, 
                        String phone, String email, String company, 
                        List<String> guests)
    {
        super(firstname, lastname, address, phone, email);
        companyName = company;
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
    
    
    public void setCompanyName(String cName)
    {
        companyName = cName;
    }
    
    
    
    public String getCompanyName()
    {
        return companyName;
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
        
        return "Kontaktperson:\n" + super.toString() + "\nFirma:\t" + companyName
               + (hasCompanions ? "\nReisefølge:" + travelCompanions : "");
    }
} // end of class CompanyGuest