/*
 * Anette Molund, s181083, 08.05.12.
 * 
 * Denne klassen er en abstrakt klasse, og er subklasse for de to bookingklassene
 * HotelRoomBooking og ConferenceRoomBooking og utfører diverse operasjoner på en booking.
 */

package booking;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import person.Person;
import person.guest.CompanyGuest;
import person.guest.PrivateGuest;

public abstract class Booking implements Serializable
{        
    private static final long serialVersionUID = 
            ObjectStreamClass.lookup(Booking.class).getSerialVersionUID();

    private static int bookingRunningNr = 1;
    private int bookingNr, price;
    private Person guest;
    private boolean checkedIn, checkedOut, cancelled;
    private Date toDate, fromDate;
    
    public Booking(Person g, Date fDate, Date tDate)
    {
        bookingNr = bookingRunningNr++;
        guest = g;
        price = 0;
        toDate = tDate;
        fromDate = fDate;
        checkedIn = false;
        checkedOut = false;
        cancelled = false;
    }
    
    
    
    //Metoden returnerer kontaktpersonen til denne bookingen.
    public Person getGuest()
    {
        return guest;
    }
    
    
    
    //Metoden returnerer en liste med navnene på medgjestene
    public List<String> getGuestList()
    {
        if(guest == null)
            return null;
        
        if(guest instanceof PrivateGuest)
            return ((PrivateGuest)guest).getGuestNames();
        
        if(guest instanceof CompanyGuest)
            return ((CompanyGuest)guest).getGuestNames();
        
        return null;
    }// End of method getGuestList()
    
    
    
    //Metoden beregner og returnerer antall dager som er booket.
    public int getDays()
    {
        long milisecound = getToDate().getTime() - getFromDate().getTime();
        int days = (int) Math.ceil(milisecound/86400000L);
        
        return days;
    }// End of method getDays()
    
    
    
    //Metoden setter prisen på denne bookingen lik parameteren.
    public void setPrice(int pri)
    {
        price = pri;
    }
    
    
    
    //Metoden returnerer prisen som er lagres på denne bookingen.
    public int getPrice()
    {
        return price;
    }
    
    
    
    /* Metoden setter denne bookingen til true, det vil si at bookingen er 
     * sjekket inn.*/
    public void setCheckedIn()
    {
        checkedIn = true;
    }
    
    
    
    //Hvis bookingen er sjekket inn, returnerer metoden true, ellers false.
    public boolean getCheckedIn()
    {
        return checkedIn;
    }
    
    
    
    /* Metoden setter denne bookingen til true, det vil si at bookingen er 
     * sjekket ut.*/
    public void setCheckedOut()
    {
        checkedOut = true;
    }
    
    
    
    //Hvis bookingen er sjekket ut, returnerer metoden true, ellers false.
    public boolean getCheckedOut()
    {
        return checkedOut;
    }
    
    
    
    //Metoden mottar parameter som tilsier om bookingen skal kanselleres.
    public void setCancelled(boolean cancel)
    {
        cancelled = cancel;
    }
    
    
    
    //Metoden returnerer true hvis bookingen er kansellert, ellers false.
    public boolean getCancelled()
    {
        return cancelled;
    }
    
    
    
    /* Metoden endrer kontaktpersonens navn hvis personen finnes, og returnerer
     * true. Hvis personen ikke finnes returnerer metoden false.*/
    public boolean changeName(String fName, String lName, Person g)
    {
        if(g == null)
            return false;
        
        
        g.setName(fName, lName);
        return true;
    }// End of method changeName(...)
    
    
    
    /* Metoden endrer kontaktpersonens adresse hvis personen finnes, og
     * returnerer true. Hvis personen ikke finnes returnerer metoden false.*/
    public boolean changeAddress(String address, Person g)
    {
        if(g == null)
            return false;
        
        g.setAddress(address);
        return true;
    }// End of method changeAddress(...)
    
    
    
    /* Metoden endrer kontaktpersonens email hvis personen finnes, og
     * returnerer true. Hvis personen ikke finnes returnerer metoden false.*/
    public boolean changeEmail(String email, Person g)
    {
        if(g == null)
            return false;
        
        
        g.setEmail(email);
        return true;
    }// End of method changeEmail(...)
    
    
    
    /* Metoden endrer kontaktpersonens telefonnummeret hvis personen finnes, og
     * returnerer true. Hvis personen ikke finnes returnerer metoden false.*/
    public boolean changePhoneNr(String phone, Person g)
    {
        if(g == null)
            return false;
        
        
        g.setPhoneNr(phone);
        return true;
    }// End of method changePhoneNr(...)
    
    
    
    /* Metoden endrer firmanavn hvis personen finnes og den er registrert som firmagjest, og
     * returnerer dermed true. Hvis personen ikke finnes, eller ikke er firmagjest returnerer 
     * metoden false.*/
    public boolean changeCompanyName(String companyName, Person g)
    {
        if(g == null)
            return false;
        
        if(g instanceof CompanyGuest)
        {
            CompanyGuest cGuest = (CompanyGuest) g;
            cGuest.setCompanyName(companyName);
            return true;
        }
        else
            return false;
    }// End of method changeCompanyName(...)
    
    
    
    // Metoden endrer datoene på bookingen.
    public void changeDates(Date fDate, Date tDate)
    {
        fromDate = fDate;
        toDate = tDate;
    }// End of method changeDates(...)
    
    
    
    public int getBookingNr()
    {
        return bookingNr;
    }
    
    
    
    public Date getFromDate()
    {
        return fromDate;
    }
    
    
    
    public Date getToDate()
    {
        return toDate;
    }
    
    
    
    // Metoden leser den lagrede static variablen fra fil
    public void setSavedStaticBookingRunNr(ObjectInputStream file) throws IOException
    {
        bookingRunningNr = file.readInt();
    }
    
    
    
    // Metoden lagrer static variablen til fil.
    public void saveStaticBookingRunNr(ObjectOutputStream file) throws IOException
    {
        file.writeInt(bookingRunningNr);
    }
    
    
    
    public abstract List<Integer> getRoomNr();
    
    public abstract boolean removeRoom(int room);
    
    public abstract void removeAllRooms();
    
    public abstract String getStringBuilder();
    
    
    
    @Override
    public String toString()
    {
        return guest.toString() + "\n\nBookingnummer: " + bookingNr + "\nFra:\t" 
                + DateFormat.getDateInstance(DateFormat.MEDIUM).format(fromDate) 
                + "\nTil:\t" + DateFormat.getDateInstance(DateFormat.MEDIUM).format(toDate); 
    }// End of method toString()
}// End of abstract class Booking