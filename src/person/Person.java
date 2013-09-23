/*
 * Charlotte Sjøthun s180495, 04.05.2012
 * 
 * Klassen Person er superklasse til Employee, PrivateGuest og CompanyGuest. 
 * Som representerer ansatte og gjester i programmet.
 */

package person;

import java.io.ObjectStreamClass;
import java.io.Serializable;

public abstract class Person implements Serializable
{
    private static final long serialVersionUID = 
            ObjectStreamClass.lookup(Person.class).getSerialVersionUID();
    
    String firstname, lastname, address, phoneNr, email;
    
    public Person(String fName, String lName, String adr, String phone, String mail)
    {
        firstname = fName;
        lastname = lName;
        address = adr;
        phoneNr = phone;
        email = mail;
    }
    
    
    
    public void setName(String fName, String lName)
    {
        firstname = fName;
        lastname = lName;
    }
    
    
    
    public String getFirstname()
    {
        return firstname;
    }
    
    
    
    public String getLastname()
    {
        return lastname;
    }
    
    
    
    public void setEmail(String mail)
    {
        email = mail;
    }
    
    
    
    public String getEmail()
    {
        return email;
    }
    
    
    
    public void setAddress(String adr)
    {
        address = adr;
    }
    
    
    
    public String getAddress()
    {
        return address;
    }
    
    
    
    public void setPhoneNr(String phone)
    {
        phoneNr = phone;
    }
    
    
    
    public String getPhoneNr()
    {
        return phoneNr;
    }
   
    
    
    @Override
    public String toString()
    {
        return "Fornavn: \t" + firstname + "\nEtternavn: \t" + lastname + "\nAdresse: \t"
               + address + "\nTelefonnr: \t" + phoneNr + "\nE-mail: \t" + email;
    }
} // End of abstract class Person