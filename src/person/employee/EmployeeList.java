/*
 * Charlotte Sjøthun s180495, 04.05.2012
 * 
 * Klassen EmployeeList oppretter en liste av person objekter som er ansatte og utføre div 
 * operasjoner på dem.
 */

package person.employee;

import java.io.*;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import person.Person;

public class EmployeeList implements Serializable
{
    private static final long serialVersionUID = 
            ObjectStreamClass.lookup(EmployeeList.class).getSerialVersionUID();
    
    private List<Person> list;
    
    public EmployeeList()
    {
        list = new LinkedList<>();
        addAdmin();
    }
    
    
    
    /* Hjelpemetoden brukes til å opprette en administrator første gang lista
     * blir opprettet.*/
    private void addAdmin()
    {
        list.add(new Employee("Admin", "", "", "", "", "", new  Date(), encryptPassword("passord"), true));
    }
    
    
    
    public boolean addEmployee(Person employee)
    {
        return list.add(employee);
    }
    
    
    
    // Metoden returnerer første forekomst av person objekter med ansattnr lik parameteren.
    public Person getEmployeeByEmpNr(int empNr)
    {
        Iterator<Person> iterator = list.iterator();

    	while (iterator.hasNext())
    	{
            Employee person = (Employee) iterator.next();
            
            if(person.getEmpNr() == empNr)
            {
                return person;
            }
    	}
        
        return null;
    } // End of method getEmployeeByEmpNr(...)
    
    
    
    /* Metoden kontrollerer om passordet matcher passordet til (første forekomst av) 
     * objektet (som ikke har en sluttdato) med ansattnr lik parameteren og 
     * returnerer det objektet. Finner den ingen som matcher returnerer den null.*/
    public Person checkPassword(int employeeNr, String password)
    {
        Employee employee = (Employee)getEmployeeByEmpNr(employeeNr);
        
        if (employee != null && employee.getEndDate() == null
            && employee.getPassword().equals(encryptPassword(password)))
                return employee;
        
        return null;
    } // End of method checkPassword(...)
    
    
    
    // Metoden legger til salt og krypterer Stringen den får inn som parameter med SHA1.
    public String encryptPassword(String password)
    {
        try 
        {
            password += "*^?¤#%653";
            byte[] passwordByte = password.getBytes();
            return new String(MessageDigest.getInstance("SHA1").digest(passwordByte));          
        }
        catch (java.security.NoSuchAlgorithmException e) 
        {
            JOptionPane.showMessageDialog(null, "Feil ved kryptering av passord!\n"
                                          + e, "Feilmelding", JOptionPane.WARNING_MESSAGE);
        }
        return null;
    } // End of method encryptPassword(...)
    
    
    
    // Hjelpemetode som legger alle personobjektene uten sluttdato i en liste og returnerer denne.
    private List<Person> getCurrentEmployees()
    {
        Iterator<Person> listIterator = list.iterator();
        List<Person> currentEmployees = new LinkedList<>();
           
        if (listIterator.hasNext())
        {
            do
            {
                Employee listEmp = (Employee) listIterator.next();
                
                if (listEmp.getEndDate() == null)
                {
                    currentEmployees.add(listEmp);
                }
            }while (listIterator.hasNext());
        }
        
        return currentEmployees;
    } // End of method getCurrentEmployees()
    
    
    
    // Metoden returnerer ansattnr til de objektene i lista som ikke har sluttdato.
    public Integer[] getCurrentEmployeeNr()
    {
        List<Person> currentEmployees = getCurrentEmployees();
        Integer[] employeeNumbers = new Integer[currentEmployees.size()+1];
        employeeNumbers[0] = 0;
        int index = 1;
        
        Iterator<Person> iterator = currentEmployees.iterator();
           
        if (iterator.hasNext())
        {
            do
            {
                Employee person = (Employee) iterator.next();
                
                if(person.getEndDate() == null)
                {
                    employeeNumbers[index++] = (Integer)person.getEmpNr();
                }
            }while (iterator.hasNext());
        } // End of if
        
        return employeeNumbers;
    } // End of method getCurrentEmployeeNr(...)
    
    
    
    // Metoden returneren en todimensjonal array med infomasjon om person objektet den får inn som parameter.
    public Object[][] showEmployee(Person employee)
    {
        Employee emp = (Employee)employee;
        String hireDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(emp.getHireDate());
        
        
        Object[][] tableData = {{emp.getEmpNr(), emp.getFirstname(), emp.getLastname(), emp.getAddress(), 
                                 emp.getPhoneNr(), emp.getEmail(), hireDate, emp.getIsAdmin()}};
        
        return tableData;
    } // End of method showEmployee(...)
    
    
    
    /* Metoden returnerer en todimensjonal array med informasjon om alle person objektene i lista som har
     * endDate lik null. (objekter som fortsatt er "ansatt")*/
    public Object[][] showCurrentEmployeeList()
    {
        List<Person> currentEmployee = getCurrentEmployees();
        Object[][] tableData;
            
        Iterator<Person> iter = currentEmployee.iterator();

        if (iter.hasNext())
        {
            tableData = new Object[currentEmployee.size()][8];
            int row = 0;

            do
            {
                Employee emp = (Employee) iter.next();

                String hireDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(emp.getHireDate());

                int coloumn = 0;

                tableData[row][coloumn++] = emp.getEmpNr();
                tableData[row][coloumn++] = emp.getFirstname();
                tableData[row][coloumn++] = emp.getLastname();
                tableData[row][coloumn++] = emp.getAddress();
                tableData[row][coloumn++] = emp.getPhoneNr();
                tableData[row][coloumn++] = emp.getEmail();
                tableData[row][coloumn++] = hireDate;
                tableData[row++][coloumn] = emp.getIsAdmin();

            }while (iter.hasNext());
        } // End of if
        else
            tableData = null;
        
        return tableData;
    } // End of method showCurrentEmployeeList(...)
   
    
    
    // Metoden returnerer en todimensjonal array med informasjon om alle person objektene i lista
    public Object[][] showAllEmployeeInList()
    {
        Iterator<Person> iterator = list.iterator();
        Object[][] tableData = new Object[list.size()][10];
        int row = 0;
           
        if (iterator.hasNext())
        {
            do
            {
                Employee emp = (Employee) iterator.next();
                
                String hireDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(emp.getHireDate());
                
                Date endD = emp.getEndDate();
                String endDate;
                
                if (endD != null)
                    endDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(endD);
                else
                    endDate = "";
                
                int coloumn = 0;

                tableData[row][coloumn++] = emp.getEmpNr();
                tableData[row][coloumn++] = emp.getFirstname();
                tableData[row][coloumn++] = emp.getLastname();
                tableData[row][coloumn++] = emp.getAddress();
                tableData[row][coloumn++] = emp.getPhoneNr();
                tableData[row][coloumn++] = emp.getEmail();
                tableData[row][coloumn++] = emp.getSocialSecurityNr();
                tableData[row][coloumn++] = hireDate;
                tableData[row][coloumn++] = endDate;
                tableData[row++][coloumn] = emp.getIsAdmin();
                
            }while (iterator.hasNext());
        } // End of if
        return tableData;
    } // End of method showAllEmployeeInList(...)
    
    
    
    /* Metoden løper gjennom lista og gjør kall på setSavedStaticEmpRunNR(...) metoden til de forskjellige 
     * objektene. (Dette er for å hente ut de lagrede static variablene).*/
    public void setSavedStaticEmpRunNR(ObjectInputStream file) throws IOException
    {
        Iterator<Person> iterator = list.iterator();
        
        if (iterator.hasNext())
        {
            do
            {
                Employee emp = (Employee) iterator.next();
                emp.setSavedStaticEmpRunNR(file);
            }while (iterator.hasNext());
        }
    } // End of method setSavedStaticEmpRunNR(...)
    
    
    
    /* Metoden løper gjennom lista og gjør kall på saveStaticEmpRunNr(...) metoden til de forskjellige 
     * objektene. (Dette er for å lagre de forskjellige static variablene).*/
    public void saveStaticEmpRunNr(ObjectOutputStream file) throws IOException
    {
        Iterator<Person> iterator = list.iterator();
           
        if (iterator.hasNext())
        {
            do
            {
                Employee emp = (Employee) iterator.next();
                emp.saveStaticEmpRunNr(file);
            }while (iterator.hasNext());
        }
    } // End of method saveStaticEmpRunNr(...)
} // End of class EmployeeList