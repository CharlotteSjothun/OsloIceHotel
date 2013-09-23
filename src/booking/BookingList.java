/*
 * Anette Molund, s181083, 13.05.12
 * 
 * Denne klassen oppretter en liste av bookinger, og utfører diverse operasjoner
 * på bookingene.
 */

package booking;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTextArea;
import person.Person;
import person.guest.CompanyGuest;
import person.guest.PrivateGuest;

public class BookingList implements Serializable
{
    private static final long serialVersionUID = 
            ObjectStreamClass.lookup(BookingList.class).getSerialVersionUID();

    private List<Booking> bookings;
    
    public BookingList()
    {
        bookings = new LinkedList<>();
    }
    
    
    
    // Metoden legger bookingen den mottar i parameter inn i bookinglisten.
    public void addBooking(Booking b)
    {
        bookings.add(b);
    }
    
    
    
    // Metoden returnerer bookingen med bookingnr lik parameteren.
    public Booking getBooking(int bookingNr)
    {
        Iterator<Booking> iter = bookings.iterator();
        
        while(iter.hasNext())
        {
            Booking booking = iter.next();
            
            if(booking.getBookingNr() == bookingNr)
            {
                return booking;
            }
        }
        
        return null;
    }// End of method getBooking(...)
    
    
    
    
    /* Metoden returnerer bookingnr til booking gjort av hovedperson med navn
     * lik parameter på spesifikk fradato.*/
    public int getBookingNr(String firstName, String lastName, Date fromDate)
    {
        Iterator<Booking> iter = bookings.iterator();
        
        while(iter.hasNext())
        {
            Booking booking = iter.next();
            
            if(booking.getGuest().getFirstname().equals(firstName) && 
                booking.getGuest().getLastname().equals(lastName) &&
                booking.getFromDate().compareTo(fromDate) == 0 )
            {
                return booking.getBookingNr();
            }
        }// End of while
        
        return -1;
    }// End of method getBookingNr(...)
    
    
    
    /* Metoden sjekker inn bookingen som kommer med i parameter hvis denne finnes, 
     * og returnerer true. Hvis den ikke finnes returnerer den false.*/
    public boolean setCheckedIn(Booking b)
    {
        if(b == null)
            return false;
        
        b.setCheckedIn();
        return true;
    }// End of method setCheckedIn(...)
    
    
    
    /* Metoden returnerer true hvis bookingen er sjekket inn, false hvis den ikke
     * finnes eller ikke er sjekket inn.*/
    public boolean getCheckedIn(Booking b)
    {
        if(b == null)
            return false;
        
        return b.getCheckedIn();
    }// End of method getCheckedIn(...)
    
    
    
    /* Metoden sjekker ut bookingen som kommer med i parameter hvis denne finnes, 
     * og returnerer true. Hvis den ikke finnes returnerer den false.*/
    public boolean setCheckedOut(Booking b)
    {
        if(b == null)
            return false;
        
        b.setCheckedOut();
        return true;
    }// End of method setCheckedOut(...)
    
    
    
    /* Metoden returnerer true hvis bookingen er sjekket ut, false hvis den ikke
     * finnes eller ikke er sjekket ut.*/
    public boolean getCheckedOut(Booking b)
    {
        if(b == null)
            return false;
        
        return b.getCheckedOut();
    }// End of method getCheckedOut(...)
    
    
    
    // Metoden kansellerer bookingen som kommer med parameter.
    public void cancelBooking(Booking b)
    {
        b.setCancelled(true);
    }
    
    
    
    /* Metoden returnerer om true hvis bookingen er kansellert, false hvis den
     * ikke finnes eller er kansellert.*/
    public boolean getCancelled(Booking b)
    {
        if(b == null)
            return false;
        
        return b.getCancelled();
    }// End of method getCancelled(...)
    
    
    
    /* Metoden returnerer true hvis navnet på kontaktpersonen blir endret, false
     * hvis bookingen ikke finnes.*/
    public boolean changeName(String fName, String lName, Person g, Booking b)
    {
        if(b == null)
            return false;
        
        b.changeName(fName, lName, g);
        return true;
    }// End of method changeName(...)
    
    
    
    /* Metoden returnerer true hvis adressen til kontaktpersonen blir endret, 
     * false hvis bookingen ikke finnes.*/
    public boolean changeAddress(String address, Person g, Booking b)
    {
        if(b == null)
            return false;
        
        b.changeAddress(address, g);
        return true;
    }// End of method changeAddress(...)
    
    
    
    /* Metoden returnerer true hvis emailen til kontaktpersonen blir endret, 
     * false hvis bookingen ikke finnes.*/
    public boolean changeEmail(String email, Person g, Booking b)
    {
        if(b == null)
            return false;
        
        b.changeEmail(email, g);
        return true;
    }// End of method changeEmail(...)
    
    
    
    /* Metoden returnerer true hvis telefonnummeret til kontaktpersonen blir endret, 
     * false hvis bookingen ikke finnes.*/
    public boolean changePhone(String phone, Person g, Booking b)
    {
        if(b == null)
            return false;
        
        b.changePhoneNr(phone, g);
        return true;
    }// End of method changePhone(...)
    
    
    
    /*Metoden endrer fra- og tildato på bookingen som kommer inn som parameter, 
     * og returnerer true hvis bookingen finnes. Ellers false.*/
    public boolean changeDates(Date fDate, Date tDate, Booking b)
    {
        if(b == null)
            return false;
        
        b.changeDates(fDate, tDate);
        return true;
    }// End of method changeDates(...)
    
    
    
    /* Metoden returnerer en liste med romnummerene til rommene som er booket i
     * i tidsrommet som kommer inn som parameter.*/
    public List<Integer> getBookedRooms(Date fromDate, Date toDate)
    {
        String fDate = DateFormat.getDateInstance(DateFormat.SHORT).format(fromDate);
        String tDate = DateFormat.getDateInstance(DateFormat.SHORT).format(toDate);
        
        List<Integer> bookedRooms = new LinkedList<>();
        
        Iterator<Booking> iter = bookings.iterator();
        
        while(iter.hasNext())
        {
            Booking booking = iter.next();
            String bookingFromDate = DateFormat.getDateInstance(DateFormat.SHORT).format
                                    (booking.getFromDate());
            
            String bookingToDate = DateFormat.getDateInstance(DateFormat.SHORT).format
                                    (booking.getToDate());
            
            if(!booking.getCancelled() && !(fDate.compareTo(bookingToDate) == 0) 
                    && fDate.compareTo(bookingToDate) <= 0 &&
               bookingFromDate.compareTo(fDate) <= 0 && bookingFromDate.compareTo(tDate) <= 0)
            {    
                List<Integer> b = booking.getRoomNr();
                bookedRooms.addAll(b);
            }
        }// End of while
        
        return bookedRooms;
    }// End of method getBookedRooms(...)
    
    
    
    /* Metoden løper gjennom lista og gjør kall på setSavedStaticBookingRunNr(...) metoden til de 
     * forskjellige objektene. (Dette er for å hente ut de lagrede static variablene).*/
    public void setSavedStaticBookingRunNr(ObjectInputStream file) throws IOException
    {
        Iterator<Booking> iter = bookings.iterator();
        
        if(iter.hasNext())
        {
            do
            {
                Booking book = iter.next();
                book.setSavedStaticBookingRunNr(file);
            }while(iter.hasNext());
        }
    }// End of method setSavedStaticBookingRunNr(...)
    
    
    
    /* Metoden løper gjennom lista og gjør kall på saveStaticBookingRunNr(...) metoden til de forskjellige 
     * objektene. (Dette er for å lagre de forskjellige static variablene).*/
    public void saveStaticBookingRunNr(ObjectOutputStream file) throws IOException
    {
        Iterator<Booking> iter = bookings.iterator();
        
        if(iter.hasNext())
        {
            do
            {
                Booking book = iter.next();
                book.saveStaticBookingRunNr(file);
            }while(iter.hasNext());
        }
    }// End of method saveStaticBookingRunNr(...)
    
    
    
    // Metoden skriver ut hvilke gjester/firmaer som er innlosjert.
    public void showAccommodatedGuests(JTextArea output)
    {
        output.setText("Innlosjerte gjester\n");
        
        String company = "\n\nFirmagjester\n";
        String noCompany = "\n\nPrivatgjester\n";
        
        Iterator<Booking> iter = bookings.iterator();
        
        while(iter.hasNext())
        {
            Booking booking = iter.next();
            
            if(booking.getCheckedIn() && !booking.getCheckedOut())
            {
                Person guest = booking.getGuest();
                
                if(guest instanceof PrivateGuest)
                    noCompany += "   " + guest.getFirstname() + " " + guest.getLastname() + 
                                 "\t\tBookingnr: \t" + booking.getBookingNr() + "\n";
                else if(guest instanceof CompanyGuest)
                {
                    CompanyGuest companyGuest = (CompanyGuest)guest;
                    company += "   " + companyGuest.getCompanyName() + " ved " + 
                                  companyGuest.getFirstname() + " " + 
                                  companyGuest.getLastname() + "\tBookingnr: \t" + booking.getBookingNr() 
                                  + "\n";
                }
            }// End of if
        }// End of while
        
        output.append(noCompany);
        output.append(company);
    }// End of method showGuests(...)
    
    
    
    //Metoden skriver ut informasjon om bookingen som kommer inn som parameter.
    public void showBooking(JTextArea output, Booking b)
    {
        output.setText(b.toString());
    }
    
    
    
    /* Metoden skriver ut informasjon om bookinger som inneholder enten fornavnet 
     * eller etternavnet som kommer inn som parameter.*/
    public void showBookingsByName(JTextArea output, String firstName, String lastName)
    {
        output.setText("Bookinger på " + firstName + " " + lastName + "\n\n");
        
        Iterator<Booking> iter = bookings.iterator();
        
        while(iter.hasNext())
        {
            Booking booking = iter.next();
            
            if(booking.getGuest().getFirstname().equals(firstName) || 
               booking.getGuest().getLastname().equals(lastName))
            {
                output.append(booking.toString() 
                + "\n*******************************************************\n");
            }//End of if
        }// End of while
    }// End of method showBookingsByName(...)
    
    
    
    /* Metoden skriver ut informasjon om bookinger i tidsrommet som kommer inn 
     * som parameter.*/
    public void showBookingsByDates(JTextArea output, Date fromDate, Date toDate)
    {
        output.setText("Bookinger mellom " 
                + (DateFormat.getDateInstance(DateFormat.MEDIUM).format(fromDate)) 
                + " og " + (DateFormat.getDateInstance(DateFormat.MEDIUM).format(toDate)) + "\n\n");
        
        Iterator<Booking> iter = bookings.iterator();
        
        while(iter.hasNext())
        {
            Booking booking = iter.next();
            
            if((booking.getToDate().compareTo(toDate) >= 0 &&
                booking.getFromDate().compareTo(fromDate) <= 0) ||
               (booking.getToDate().compareTo(toDate) <= 0) && 
                booking.getFromDate().compareTo(fromDate) >= 0)
            {
                    output.append(booking.toString() 
                    + "\n*******************************************************\n");
            }//End of if
        }// End of while
    }//End of method showBookingsByDates()
}// End of class BookingList
