/*
 * Nanna Mjørud s180477, 13.05.12
 * 
 * Klassen er superklasse for SingleRoom, DoubleRoom, FamilyRoom og Suite, som representerer hotellrom i 
 * programmet.
 */

package room.hotelroom;

import java.io.*;

public abstract class HotelRoom implements Serializable
{
    private static final long serialVersionUID = 
            ObjectStreamClass.lookup(HotelRoom.class).getSerialVersionUID();
       
    private int roomNr, nrOfBedspaces;
    private String typeOfBeds, typeOfRoom;
    
    public HotelRoom(String roomType, int nr, int nrOfBedsp, String bedTypes)
    {
        typeOfRoom = roomType;
        roomNr = nr;
        nrOfBedspaces = nrOfBedsp;
        typeOfBeds = bedTypes;
    }
    
    
    
    public String getTypeOfRoom()
    {
        return typeOfRoom;
    }    
    
    
    public int getRoomNr()
    {
        return roomNr;
    }    
    
    
    public int getNrOfBedspaces()
    {
        return nrOfBedspaces;
    }    
    
    
    public String getTypeOfBeds()
    {
        return typeOfBeds;
    }    
    
    public abstract void getSavedStaticPrice(ObjectInputStream file) throws IOException;
    
    public abstract void saveStaticPrice(ObjectOutputStream file) throws IOException;
    
    
    // Metoden viser all informasjon om en romtype
    public String showRoomType()
    {
        return typeOfRoom + "\nAntall sengeplasser: " + nrOfBedspaces + "\nSenger: " + typeOfBeds;
    }    
    
    
    @Override
    public String toString()
    {
        return typeOfRoom + "\nRomnummer: " + roomNr + "\nAntall sengeplasser: " + nrOfBedspaces + 
                            "\nSenger: " + typeOfBeds;
        
    }    
} // End of abstract class HotelRoom