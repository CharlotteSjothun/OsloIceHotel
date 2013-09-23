/*
 * Charlotte Sjøthun s180495, 04.05.2012
 * 
 * Klassen Employee er subklasse av Person og representerer en ansatt i programmet.
 */

package person.employee;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.Date;
import person.Person;

public class Employee extends Person
{
    private String socialSecurityNr, password;
    private Date hireDate, endDate;
    private boolean isAdmin;
    private int empNr;
    private static int empRunningNr = 1;
    
    public Employee(String firstname, String lastname, String address, String phone, String email, 
                    String sSecurityNr, Date hiredate, String passwd, boolean admin)
    {
        super(firstname, lastname, address, phone, email);
        socialSecurityNr = sSecurityNr;
        hireDate = hiredate;
        endDate = null;
        password = passwd;
        isAdmin = admin;
        empNr = empRunningNr++;
    }
    
    
    
    public void setSocialSecurityNr(String sSecurityNr)
    {
        socialSecurityNr = sSecurityNr;
    }
    
    
    
    public String getSocialSecurityNr()
    {
        return socialSecurityNr;
    }
    
    
    
    public int getEmpNr()
    {
        return empNr;
    }
    
    
    
    public Date getHireDate()
    {
        return hireDate;
    }
    
    
    
    public void setEndDate(Date date)
    {
        endDate = date;
    }
    
    
    
    public Date getEndDate()
    {
        return endDate;
    }
    
    
    
    public void setPassword(String newPassword)
    {
        password = newPassword;
    }
    
    
    
    public String getPassword()
    {
        return password;
    }
    
    
    
    public void setIsAdmin(boolean admin)
    {
        isAdmin = admin;
    }
    
    
    
    public boolean getIsAdmin()
    {
        return isAdmin;
    }
    
    
    
    /* Metoden returnerer en String med fornavn, etternavn og ansattnr for
     * å vise hvilken ansatt som er innlogget.*/
    public String getEmployeeNameAndNr()
    {
        return super.getFirstname() + " " + super.getLastname() + ", ansattnr. " + empNr;
    }
    
    
    
    // Metoden leser den lagrede static variablen fra fil.
    public void setSavedStaticEmpRunNR(ObjectInputStream file) throws IOException
    {
        empRunningNr = file.readInt();
    }
    
    
    
    // Metoden lagrer static variablen til fil.
    public void saveStaticEmpRunNr(ObjectOutputStream file) throws IOException
    {
        file.writeInt(empRunningNr);
    }
    
    
    
    @Override
    public String toString()
    {
        return super.toString() + "\nPersonnr: " + socialSecurityNr 
               + "\nAnsattnr: " + empNr + "\nPassord: " + password
               + "\nAnsettelse dato: "
               + DateFormat.getDateInstance(DateFormat.MEDIUM).format(hireDate)
               + (endDate != null ? "\nSluttdato: " 
               + DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate) : "")
               + "\nAdministrator: " + (isAdmin ? "Ja." : "Nei.");
    }
} // End of class Employee