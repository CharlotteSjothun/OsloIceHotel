/*
 * Anette Molund, s181083, 08.05.12
 * 
 * Denne klassen er en subklasse av den abstrakte klassen Booking, og representerer
 * en seminarrombooking i programmet.
 */

package booking;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import person.Person;
import room.conferenceroom.Auditorium;
import room.conferenceroom.ConferenceRoom;
import room.conferenceroom.GroupRoom;
import room.conferenceroom.MeetingRoom;

public class ConferenceRoomBooking extends Booking
{
    private List<ConferenceRoom> conferenceRooms;
    
    public ConferenceRoomBooking(Person g, Date fDate, Date tDate, List<ConferenceRoom> cRoom)
    {
        super(g, fDate, tDate);
        conferenceRooms = cRoom;
    }
    
    
    
    @Override
    public List<Integer> getRoomNr()
    {
        List<Integer> roomNr = new LinkedList<>();
        
        Iterator<ConferenceRoom> iter = conferenceRooms.iterator();
        
        while(iter.hasNext())
        {
            ConferenceRoom cRoom = iter.next();
            
            roomNr.add(cRoom.getRoomNr());
        }
        
        return roomNr;
    }// End of method getRoomNr()
    
    
    
    //Metoden legger til de nye rommene i bookingen.
    public boolean addRoom(List<ConferenceRoom> newRooms)
    {
        return conferenceRooms.addAll(newRooms);
    }
    
    
    
    //Metoden fjerner rommet med romnummer lik parameteren i bookingen.
    @Override
    public boolean removeRoom(int room)
    {
        Iterator<ConferenceRoom> iter = conferenceRooms.iterator();
        
        while(iter.hasNext())
        {
            ConferenceRoom conferenceRoom = iter.next();
            int cRoom = conferenceRoom.getRoomNr();
            
            if(cRoom == room)
            {
                iter.remove();
                return true;
            }
        }
        
        return false;
    }// End of method removeRoom(...)
    
    
    
    @Override
    //Metoden sletter alle hotellrommene som ligger i hotellromlista.
    public void removeAllRooms()
    {
        conferenceRooms.clear();
    }
    
    
    
    //Metoden bygger opp en tekst som viser pris mht. rom og ekstra tjenester.
    @Override
    public String getStringBuilder()
    {
        int price = 0;
        
        StringBuilder auditorium = new StringBuilder("\nAuditorum");
        StringBuilder groupRoom = new StringBuilder("\nGrupperom");
        StringBuilder meetingRoom = new StringBuilder("\nMøterom");
        
        boolean hasAuditorium = false;
        boolean hasGroupRoom = false;
        boolean hasMeetingRoom = false;
        int days = super.getDays();
        
        Iterator<ConferenceRoom> iter = conferenceRooms.iterator();
        
        while(iter.hasNext())
        {
            ConferenceRoom cRoom = iter.next();
            
            if(cRoom instanceof Auditorium)
            {
                hasAuditorium = true;
                price += cRoom.getPrice()*days;
                auditorium.append("\n");
                auditorium.append(cRoom.getRoomNr());
                auditorium.append("\t");
                auditorium.append(cRoom.getPrice()*days);
                auditorium.append(" NOK");
            }
            else if(cRoom instanceof GroupRoom)
            {
                hasGroupRoom = true;
                price += cRoom.getPrice()*days;
                groupRoom.append("\n");
                groupRoom.append(cRoom.getRoomNr());
                groupRoom.append("\t");
                groupRoom.append(cRoom.getPrice()*days);
                groupRoom.append(" NOK");
            }
            else if(cRoom instanceof MeetingRoom)
            {
                hasMeetingRoom = true;
                price += cRoom.getPrice()*days;
                meetingRoom.append("\n");
                meetingRoom.append(cRoom.getRoomNr());
                meetingRoom.append("\t");
                meetingRoom.append(cRoom.getPrice()*days);
                meetingRoom.append(" NOK");
            } 
        }// End of while
        
        String output = (hasAuditorium ? "\n" + auditorium : "")
                        + (hasGroupRoom ? "\n" + groupRoom : "")
                        + (hasMeetingRoom ? "\n" + meetingRoom : "")
                        + "\n\nTotalsum:\t" + price + " NOK";
        
        super.setPrice(price);
        return output;
    }// End of method getStringBuilder()
    
    
    
    @Override
    public String toString()
    {
        return super.toString() + getStringBuilder();
    }
}// End of class ConferenceRoomBooking