/*
 * Nanan Mjørud s180477, 13.05.12
 * 
 * Klassen er subklasse av HotelRoom, og representerer et familierom i programmet.
 */

package room.hotelroom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FamilyRoom extends HotelRoom
{
    private static int price;
    
    public FamilyRoom(String roomType, int nr, int nrOfBedsp, String bedTypes, int pri)
    {
        super (roomType, nr, nrOfBedsp, bedTypes);
        price = pri;
    }
    
    
    
    public static void setPrice(int newPrice)
    {
        price = newPrice;
    }
    
    
    
    public static int getPrice()
    {
        return price;
    }
    
    
    // Metoden henter den lagrede static variabelen pris, fra filen som mottas som parameter
    @Override
    public void getSavedStaticPrice(ObjectInputStream file) throws IOException
    {
        price = file.readInt();
    }
    
    
    // Metoden lagrer static variabelen pris på den mottatte filen
    @Override
    public void saveStaticPrice(ObjectOutputStream file) throws IOException
    {
        file.writeInt(price);
    }
    
    
    // Metoden viser all informasjon om romtypen
    @Override
    public String showRoomType()
    {
        return super.showRoomType() + "\nPris: " + price + " NOK";
    }
    
    
    
    @Override
    public String toString()
    {
        return super.toString() + "\nPris: " + price + " NOK";
    }
} // End of class FamilyRoom
