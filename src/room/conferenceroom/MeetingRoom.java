/*
 * Nanna Mjørud s180477, 13.05.12
 * 
 * Klassen er subklasse av ConferenceRoom, og representerer et møterom i programmet.
 */

package room.conferenceroom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MeetingRoom extends ConferenceRoom
{
    private boolean projector, videoConference;
    private static int price;
    
    public MeetingRoom(String roomType, int roomNr, int capacity, boolean proj, boolean videoConf, int pri)
    {
        super(roomType, roomNr, capacity);
        projector = proj;
        videoConference = videoConf;
        price = pri;
    }
    
    
    public boolean getProjector()
    {
        return projector;
    }
    
    
    
    public boolean getVideoConference()
    {
        return videoConference;
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
    
    
    // Metoden henter den lagrede static variabelen pris, fra den mottatte filen
    @Override
    public void getSavedStaticPrice(ObjectInputStream file) throws IOException
    {
        price = file.readInt();
    }
    
    
    // Metoden lagrer static variabelen pris, på den mottatte filen
    @Override
    public void saveStaticPrice(ObjectOutputStream file) throws IOException
    {
        file.writeInt(price);
    }
    
    
    // Metoden viser all informasjon om romtypen
    @Override
    public String showRoomTypeDetails()
    {
        return super.showRoomTypeDetails() + "\nProjektor: " + (projector ? "Ja" : "Nei") + 
                "\nVideokonferanse: " + (videoConference ? "Ja" : "Nei") + "\nPris: " + price + " NOK";
    }
    
    
    
    @Override
    public String toString()
    {
        return super.toString() + "\nProjektor: " + (projector ? "Ja" : "Nei") + "\nVideokonferanse: " + 
                (videoConference ? "Ja" : "Nei") + "\nPris: " + price + " NOK";
    }
} // End of class MeetingRoom
