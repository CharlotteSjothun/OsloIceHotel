/*
 * Nanna Mjørud s180477, 13.05.12
 * 
 * Klassen oppretter en array av alle hotellrom-objekter, og inneholder metoder som gjør operasjoner på 
 * hotellrommene.
 */

package room.hotelroom;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTextArea;

public class HotelRoomList implements Serializable
{
    private static final long serialVersionUID = 
            ObjectStreamClass.lookup(HotelRoomList.class).getSerialVersionUID();
    
    private HotelRoom[] hotelRooms;
    
    public HotelRoomList()
    {
        hotelRooms = new HotelRoom[160];
        setHotelRooms();
    }
    
    
    
    // Oppretter objekter av HotelRoom og legger dem inn i hotellrom-arrayen
    private void setHotelRooms()
    {
        int roomNr = 1;
        int floor = 200;
        
        for (int i = 0; i < hotelRooms.length; i ++)
        {
            if (roomNr >= 1 && roomNr <= 8)
            {              
                hotelRooms[i] = new SingleRoom("Enkeltrom", roomNr + floor, 1, "Enkeltseng", 800);
                roomNr++;
            }            
            else if (roomNr >= 9 && roomNr <= 12)
            {
                hotelRooms[i] = new DoubleRoom("Dobbeltrom", roomNr + floor, 2, "Dobbeltseng", 1000);
                roomNr++;
            }
            else if (roomNr >= 13 && roomNr <= 16)
            {
                hotelRooms[i] = new DoubleRoom("Dobbeltrom", roomNr + floor, 2, "To enkeltsenger", 1000);
                roomNr++;
            }
            else if (floor != 900 && roomNr >= 17 && roomNr <= 20)
            {
                hotelRooms[i] = new FamilyRoom("Familierom", roomNr + floor, 4, "Dobbeltseng og to " + 
                                               "enkeltsenger", 1200);
                roomNr++;
            }            
            else if (floor == 900 && roomNr >= 11 && roomNr <= 20)
            {
                hotelRooms[i] = new Suite("Suite", roomNr + floor, 2, "Dobbeltseng", 2000);
                roomNr++;
            }            
            if (roomNr == 21)
            {
                roomNr = 1;
                floor += 100;               
            }
        }
    } // End of method setHotelRooms()
    
    
    
    // Metoden mottar et romnummer og returnerer et objekt av hotellrommet med det romnummeret.
    public HotelRoom getHotelRoom(int roomNr)
    {
        for (int i = 0; i < hotelRooms.length; i++)
            if (hotelRooms[i].getRoomNr() == roomNr)
                return hotelRooms[i];
        
        return null;
    }
    
    
    
    // Metoden returnerer en liste med alle hotellromnummerene til rom av den mottatte romtypen
    public List<Integer> getRoomNrListByType(String roomType)
    {        
        List<Integer> hotelRoomList = new LinkedList<>();
        
        for (int i = 0; i < hotelRooms.length; i++)
            if (roomType.equals(hotelRooms[i].getTypeOfRoom()))
                hotelRoomList.add(hotelRooms[i].getRoomNr());
        
        return hotelRoomList;
    }  
    
    
    
    // Metoden mottar en liste av romnummere og returnerer objekter av rommene som har disse romnummerne
    public List<HotelRoom> getRoomsByRoomNrs(List<Integer> roomNrsIn)
    {        
        List<HotelRoom> rooms = new LinkedList<>();
        
        Iterator<Integer> iter = roomNrsIn.iterator();
        
        while (iter.hasNext())
            rooms.add(getHotelRoom(iter.next()));
        
        return rooms;
    }
    
    
    
    // Metoden returnerer en array av alle hotellromtypene
    public String[] getRoomTypes()
    {
        String[] roomTypes = new String[4];
        
        for (int i = 0; i < hotelRooms.length; i++)
        {
            if (hotelRooms[i] instanceof SingleRoom)
                roomTypes[0] = hotelRooms[i].getTypeOfRoom();
            else if (hotelRooms[i] instanceof DoubleRoom)
                roomTypes[1] = hotelRooms[i].getTypeOfRoom();
            else if (hotelRooms[i] instanceof FamilyRoom)
                roomTypes[2] = hotelRooms[i].getTypeOfRoom();
            else if (hotelRooms[i] instanceof Suite)
                roomTypes[3] = hotelRooms[i].getTypeOfRoom();
        }
        return roomTypes;
    }
    
    
    
    // Metoden endrer prisen på alle hotellrom av den mottatte romtypen, til den mottatte prisen
    public boolean changePrice(int price, String roomType)
    {
        switch (roomType)
        {
            case "Enkeltrom" : SingleRoom.setPrice(price);
                return true;
            case "Dobbeltrom" : DoubleRoom.setPrice(price);
                return true;
            case "Familierom" : FamilyRoom.setPrice(price);
                return true;
            case "Suite" : Suite.setPrice(price);
                return true;
        }
        return false;
    }
    
    
    
    /* Metoden løper gjennom hotellrom-arrayen og henter den lagrede static variabelen pris, fra den mottatte
     * filen. */
    public void getSavedStaticPrice(ObjectInputStream file) throws IOException
    {
        for (int i = 0; i < hotelRooms.length; i++)
            hotelRooms[i].getSavedStaticPrice(file);
    }
    
    // Metoden løper gjennom hotellrom-arrayen og lagrer static variabelen pris, på den mottatte filen
    public void saveStaticPrice(ObjectOutputStream file) throws IOException
    {
        for (int i = 0; i < hotelRooms.length; i++)
            hotelRooms[i].saveStaticPrice(file);
    }
    
    
    // Metoden viser all informasjon om de ulike hotellromtypene i det mottatte tekstområdet
    public void showHotelRoomTypes(JTextArea output)
    {
        output.setText("Hotellrom\n\n");
        
        String singleRoom = null;
        String doubleRoomTwinBeds = null; 
        String doubleRoomDoubleBed = null;
        String familyRoom = null;
        String suite = null;
        
        for (int i = 0; i < hotelRooms.length; i++)
        {
            if (hotelRooms[i] instanceof SingleRoom)
                singleRoom = hotelRooms[i].showRoomType();
            else if (hotelRooms[i] instanceof DoubleRoom)
            {
                if (hotelRooms[i].getTypeOfBeds().equals("Dobbeltseng"))
                    doubleRoomTwinBeds = hotelRooms[i].showRoomType();
                else
                    doubleRoomDoubleBed = hotelRooms[i].showRoomType();
            }
            else if (hotelRooms[i] instanceof FamilyRoom)
                familyRoom = hotelRooms[i].showRoomType();
            else if (hotelRooms[i] instanceof Suite)
                suite = hotelRooms[i].showRoomType();
        }
        
        output.append(singleRoom + "\n\n" + doubleRoomTwinBeds + "\n\n" + doubleRoomDoubleBed + "\n\n" + 
                familyRoom + "\n\n" + suite);
        
    } // End of method showHotelRoomTypes(...)
    
} // End of class HotelRoomList