/*
 * Nanna Mjørud s180477, 13.05.12
 * 
 * Klassen oppretter en array av seminarrom-objekter, og inneholder metoder som gjør operasjoner på 
 * seminarrommene.
 */

package room.conferenceroom;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTextArea;

public class ConferenceRoomList implements Serializable
{
    private static final long serialVersionUID = 
            ObjectStreamClass.lookup(ConferenceRoomList.class).getSerialVersionUID();
    
    private ConferenceRoom[] conferenceRooms;
    
    public ConferenceRoomList()
    {
        conferenceRooms = new ConferenceRoom[10];
        setConferenceRooms();
    }    
    
    
    
    // Metoden oppretter objekter av seminarrom og legger dem inn i seminarrom-arrayen
    private void setConferenceRooms()
    {
        int roomNr = 1;
        
        for (int i = 0; i < conferenceRooms.length; i++)
        {
            switch (i)
            {
                case 0:
                case 1: conferenceRooms[i] = new Auditorium("Auditorium", roomNr++, 100, true, 20000);
                    break;
                case 2:                     
                case 3: conferenceRooms[i] = new MeetingRoom("Møterom", roomNr++, 40, true, false, 15000);
                    break;
                case 4: conferenceRooms[i] = new MeetingRoom("Møterom", roomNr++, 30, false, true, 15000);
                    break;
                case 5:                     
                case 6: conferenceRooms[i] = new GroupRoom("Grupperom", roomNr++, 8, 2500);
                    break;
                case 7:                     
                case 8:
                case 9: conferenceRooms[i] = new GroupRoom("Grupperom", roomNr++, 5, 2500);
            }
        }
    } // End of method setConferenceRooms()
    
    
    
    // Metoden mottar et romnummer som paramter og retunrer et objekt av rommet med det romnummeret.
    public ConferenceRoom getConferenceRoom(int roomNr)
    {
        for (int i = 0; i < conferenceRooms.length; i++)
            if (conferenceRooms[i].getRoomNr() == roomNr)
                return conferenceRooms[i];
        
        return null;
    }
    
    
    
    // Metoden mottar en romtype og returnerer en liste med alle seminarromnummerene til rom av denne typen.
    public List<Integer> getRoomNrListByType(String roomType)
    {
        List<Integer> conferenceRoomList = new LinkedList<>();
        
        for (int i = 0; i < conferenceRooms.length; i++)
            if (roomType.equals(conferenceRooms[i].getTypeOfRoom()))
                conferenceRoomList.add(conferenceRooms[i].getRoomNr());
        
        return conferenceRoomList;        
    }
    
    
    
    // Metoden mottar en liste av romnummere og returnerer objekter av rommene som har disse romnummerne
    public List<ConferenceRoom> getRoomsByRoomNrs(List<Integer> roomNrsIn)
    {        
        List<ConferenceRoom> rooms = new LinkedList<>();
        
        Iterator<Integer> iter = roomNrsIn.iterator();
        
        while (iter.hasNext())
            rooms.add(getConferenceRoom(iter.next()));
        
        return rooms;
    }
    
    
    
    // Metoden returnerer en array med alle seminarromtypene
    public String[] getRoomTypes()
    {
        String[] roomTypes = new String[3];
        
        for (int i = 0; i < conferenceRooms.length; i++)
        {
            if (conferenceRooms[i] instanceof Auditorium)
                roomTypes[0] = conferenceRooms[i].getTypeOfRoom();
            else if (conferenceRooms[i] instanceof MeetingRoom)
                roomTypes[1] = conferenceRooms[i].getTypeOfRoom();
            else if (conferenceRooms[i] instanceof GroupRoom)
                roomTypes[2] = conferenceRooms[i].getTypeOfRoom();
        }
        return roomTypes;
    }
    
    
    // Metoden endrer prisen på alle seminarrom av den mottatte romtypen, til den mottatte prisen.
    public boolean changePrice(int price, String roomType)
    {
        switch (roomType)
        {
            case "Auditorium" : Auditorium.setPrice(price);
                return true;
            case "Møterom" : MeetingRoom.setPrice(price);
                return true;
            case "Grupperom" : GroupRoom.setPrice(price);
                return true;
        }
        return false;
    }
    
    
    /* Metoden løper gjennom seminarrom-arrayen og leser den lagrede static variabelen pris, fra filen som
     * mottas som parameter. */
    public void getSavedStaticPrice(ObjectInputStream file) throws IOException
    {
        for (int i = 0; i < conferenceRooms.length; i++)
            conferenceRooms[i].getSavedStaticPrice(file);
    }
    
    
    /* Metoden løper gjennom seminarrom-arrayen og lagrer static variabelen pris, på filen som mottas som 
     * parameter. */
    public void saveStaticPrice(ObjectOutputStream file) throws IOException
    {
        for (int i = 0; i < conferenceRooms.length; i++)
            conferenceRooms[i].saveStaticPrice(file);
    }
    
    
    // Metoden viser all informasjon om alle seminarrom i det mottatte tekstområdet.
    public void showConferenceRoomList(JTextArea output)
    {
        output.setText("Seminarrom\n\n");
        
        for (int i = 1; i < conferenceRooms.length; i++)
            output.append(conferenceRooms[i] + "\n\n");
    }
    
    
    // Metoden viser alle informasjon om de ulike seminarromtypene i det mottatte tekstområdet
    public void showConferenceRoomTypes(JTextArea output)
    {
        output.setText("Seminarrom\n\n");
        
        ConferenceRoom auditorium = null;
        ConferenceRoom meetingRoomVideoC = null;
        ConferenceRoom meetingRoomProject = null;
        ConferenceRoom groupRoomBig = null;
        ConferenceRoom groupRoomSmall = null;
        
        for (int i = 0; i < conferenceRooms.length; i++)
        {
            if (conferenceRooms[i] instanceof Auditorium)
                auditorium = conferenceRooms[i];
            else if (conferenceRooms[i] instanceof MeetingRoom)
            {
                MeetingRoom room = (MeetingRoom)conferenceRooms[i];
                
                if (room.getVideoConference())
                    meetingRoomVideoC = room;
                else if (room.getProjector())
                    meetingRoomProject = room;
            }
            else if (conferenceRooms[i] instanceof GroupRoom)
            {
                GroupRoom room = (GroupRoom)conferenceRooms[i];
                
                if (room.getCapacity() > 5)
                    groupRoomBig = room;
                else
                    groupRoomSmall = room;
            }
        }
        output.append(auditorium.showRoomTypeDetails() + "\n\n" + meetingRoomVideoC.showRoomTypeDetails() + 
                      "\n\n" + meetingRoomProject.showRoomTypeDetails() + "\n\n" + 
                      groupRoomBig.showRoomTypeDetails() + "\n\n" + groupRoomSmall.showRoomTypeDetails());
        
    } // End of method showConferenceRoomTypes(...)
    
} // End of class ConferenceRoomList
