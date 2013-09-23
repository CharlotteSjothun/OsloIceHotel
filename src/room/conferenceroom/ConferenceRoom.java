/*
 * Nanna Mjørud s180477, 13.05.12
 * 
 * Klassen er superklasse til Auditorium, MeetingRoom og GroupRoom, som representerer seminarrom i programmet.
 */

package room.conferenceroom;

import java.io.*;

public abstract class ConferenceRoom implements Serializable
{
    private static final long serialVersionUID = 
            ObjectStreamClass.lookup(ConferenceRoom.class).getSerialVersionUID();
    
    private String typeOfRoom;
    private int roomNr, capacity;
    
    public ConferenceRoom(String roomType, int nr, int cap)
    {
        typeOfRoom = roomType;
        roomNr = nr;
        capacity = cap;
    }
    
    
    
    public String getTypeOfRoom()
    {
        return typeOfRoom;
    }
    
    
    
    public int getRoomNr()
    {
        return roomNr;
    }
    
    
    public int getCapacity()
    {
        return capacity;
    }
    
    
    public abstract int getPrice();
    
    public abstract void getSavedStaticPrice(ObjectInputStream file) throws IOException;    
    
    public abstract void saveStaticPrice(ObjectOutputStream file) throws IOException;
    
    
    public String showRoomTypeDetails()
    {
        return typeOfRoom + "\nKapasitet: " + capacity;
    }
    
    
    
    @Override
    public String toString()
    {
        return typeOfRoom + "\nRomnummer: " + roomNr + "\nKapasitet: " + capacity;
    }    
} // End of abstract class ConferenceRoom
