/*
 * Nanna Mjørud s180477, 13.05.12
 * 
 * Klassen er subklasse av ConferenceRoom og representerer et auditorium i programmet.
 */

package room.conferenceroom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Auditorium extends ConferenceRoom
{
    private boolean projector;
    private static int price;
    
    public Auditorium(String roomType, int capacity, int roomNr, boolean proj, int pri)
    {
        super(roomType, capacity, roomNr);
        projector = proj;
        price = pri;
    }
    
    
    
    public boolean getProjector()
    {
        return projector;
    }
    
    
    
    public static void setPrice(int newPrice)
    {
        price = newPrice;
    }
    
    
    
    @Override
    public int getPrice()
    {
        return price;
    }
    
    
    
    // Metoden henter den lagrede static variabelen pris fra filen som mottas som parameter
    @Override
    public void getSavedStaticPrice(ObjectInputStream file) throws IOException
    {
        price = file.readInt();
    }
    
    
    
    // Metoden lagrer static variabelen pris på filen som mottas som parameter
    @Override
    public void saveStaticPrice(ObjectOutputStream file) throws IOException
    {
        file.writeInt(price);
    }
    
    
    
    // Metoden viser alle detaljer romtypen
    @Override
    public String showRoomTypeDetails()
    {
        return super.showRoomTypeDetails() + "\nProjektor: " + (projector ? "Ja" : "Nei") + "\nPris: " + 
                                                                price + " NOK";
    }
    
    
    
    @Override
    public String toString()
    {
        return super.toString() + "\nProjektor: " + (projector ? "Ja" : "Nei") + "\nPris: " + price + " NOK";
    }
} // End of class Auditorium