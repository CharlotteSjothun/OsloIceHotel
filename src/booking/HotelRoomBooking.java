/*
 * Anette Molund, s181083, 08.05.12
 * 
 * Denne klassen er en subklasse av den abstrakte klassen Booking, og representerer
 * en hotellrombooking i programmet.
 */

package booking;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import person.Person;
import person.guest.CompanyGuest;
import person.guest.PrivateGuest;
import room.hotelroom.*;
import service.*;

public class HotelRoomBooking extends Booking
{
    private List<HotelRoom> hotelRooms;
    private List<Service> service;
    
    public HotelRoomBooking(Person g, Date fDate, Date tDate, List<HotelRoom> hRoomsNr, 
                            List<Service> serviceList)
    {
        super(g, fDate, tDate);
        hotelRooms = new LinkedList<>(hRoomsNr);
        service = new LinkedList<>(serviceList);
    }
    
    
    
    //Metoden skriver ut en liste med romnummerene som ligger i denne bookingen.
    @Override
    public List<Integer> getRoomNr()
    {
        List<Integer> roomNr = new LinkedList<>();
        
        Iterator<HotelRoom> iter = hotelRooms.iterator();
        
        while(iter.hasNext())
        {
            HotelRoom hRoom = iter.next();
            
            roomNr.add(hRoom.getRoomNr());
        }
        
        return roomNr;
    }// End of method getRoomNr()
    
    
    
    //Metoden legger til de nye tjenestene i bookingen.
    public boolean addService(List<Service> newService)
    {
        return service.addAll(newService);
    }
    
    
    
    //Metoden skriver ut en liste med tjenestene som ligger i denne bookingen.
    public List<String> getServices()
    {
        List<String> services = new LinkedList<>();
        
        Iterator<Service> iter = service.iterator();
        
        while(iter.hasNext())
        {
            Service serv = iter.next();
            
            services.add(serv.toString());
        }
        return services;
    }// End of method getServices()
    
    
    
    //Metoden fjerner tjenesten med navn lik parameteren i bookingen.
    public boolean removeService(String serviceIn)
    {        
        Iterator<Service> iter = service.iterator();
        
        while(iter.hasNext())
        {
            Service services = iter.next();
            
            if(services.toString().equals(serviceIn))
            {
                iter.remove();
                return true;
            }  
        }
        return false;
    }// End of method removeService(...)
    
    
    
    //Metoden legger til en ny medgjest i bookingen.
    public boolean addGuestName(String newName)
    {
        Person person = super.getGuest();
        
        if(person instanceof PrivateGuest)
            return ((PrivateGuest)person).addGuestName(newName);
        else if(person instanceof CompanyGuest)
            return ((CompanyGuest)person).addGuestName(newName);
        
        return false;
    }// End of method addGuestName(...)
    
    
    
    //Metoden fjerner medgjesten med navn lik parameteren i bookingen.
    public boolean removeGuestName(String oldName)
    {
        Person person = super.getGuest();
        
        if(person instanceof PrivateGuest)
            return ((PrivateGuest)person).removeGuestName(oldName);
        else if(person instanceof CompanyGuest)
            return ((CompanyGuest)person).removeGuestName(oldName);
        
        return false;
    }// End of method removeGuestName()
    
    
    
    //Metoden legger til de nye rommene i bookingen.
    public boolean addRoom(List<HotelRoom> newRooms)
    {
        return hotelRooms.addAll(newRooms);
    }
    
    
    
    //Metoden fjerner rommet med romnummer lik parameteren i bookingen.
    @Override
    public boolean removeRoom(int room)
    {
        Iterator<HotelRoom> iter = hotelRooms.iterator();
        
        while(iter.hasNext())
        {
            HotelRoom hotelRoom = iter.next();
            int hRoom = hotelRoom.getRoomNr();
            
            if(hRoom == room)
            {
                iter.remove();
                return true;
            }
        }
        return false;
    }// End of method removeRoom(...)
    
    
    
    //Metoden sletter alle hotellrommene som ligger i hotellromlista.
    @Override
    public void removeAllRooms()
    {
        hotelRooms.clear();
    }
    
    
    
    //Metoden bygger opp en tekst som viser pris mht. rom og ekstra tjenester.
    @Override
    public String getStringBuilder()
    {
        int price = 0;
        
        StringBuilder singleRoom = new StringBuilder("\nEnkeltrom");
        StringBuilder doubleRoom = new StringBuilder("\nDobbeltrom");
        StringBuilder familyRoom = new StringBuilder("\nFamilierom");
        StringBuilder suite = new StringBuilder("\nSuite");
        StringBuilder serviceString = new StringBuilder("\nEkstra tjenester");
        StringBuilder food = new StringBuilder();
        StringBuilder extraBed = new StringBuilder();
        StringBuilder crib = new StringBuilder();
        StringBuilder extraPillow = new StringBuilder();
        StringBuilder extraQuilt = new StringBuilder();
        StringBuilder welcomeGift = new StringBuilder();
        boolean hasSingleRoom = false;
        boolean hasDoubleRoom = false;
        boolean hasFamilyRoom = false;
        boolean hasSuite = false;
        boolean hasServices = false;
        boolean hasFood = false;
        boolean hasExtraBed = false;
        boolean hasCrib = false;
        boolean hasExtraPillow = false;
        boolean hasExtraQuilt = false;
        boolean hasWelcomeGift = false;
        int days = super.getDays();
        int extraBeds = 0;
        int cribs = 0;
        int extraPillows = 0;
        int extraQuilts = 0;
        int welcomeGifts = 0;
        
        Iterator<HotelRoom> iter = hotelRooms.iterator();
        
        while(iter.hasNext())
        {
            HotelRoom hRoom = iter.next();
            if(hRoom instanceof SingleRoom)
            {
                hasSingleRoom = true;
                price += SingleRoom.getPrice()*days;
                singleRoom.append("\n");
                singleRoom.append(hRoom.getRoomNr());
                singleRoom.append("\t");
                singleRoom.append(SingleRoom.getPrice()*days);
                singleRoom.append(" NOK");
            }
            else if(hRoom instanceof DoubleRoom)
            {
                hasDoubleRoom = true;
                price += DoubleRoom.getPrice()*days;
                doubleRoom.append("\n");
                doubleRoom.append(hRoom.getRoomNr());
                doubleRoom.append("\t");
                doubleRoom.append(DoubleRoom.getPrice()*days);
                doubleRoom.append(" NOK");
            }
            else if(hRoom instanceof FamilyRoom)
            {
                hasFamilyRoom = true;
                price += FamilyRoom.getPrice()*days;
                familyRoom.append("\n");
                familyRoom.append(hRoom.getRoomNr());
                familyRoom.append("\t");
                familyRoom.append(FamilyRoom.getPrice()*days);
                familyRoom.append(" NOK");
            }
            else if(hRoom instanceof Suite)
            {
                hasSuite = true;
                price += Suite.getPrice()*days;
                suite.append("\n");
                suite.append(hRoom.getRoomNr());
                suite.append("\t");
                suite.append(Suite.getPrice()*days);
                suite.append(" NOK");
            }
        }// End of while
        
        Iterator<Service> serviceIter = service.iterator();
        
        
        
        while(serviceIter.hasNext())
        {
            Service services = serviceIter.next();
            
            if (services instanceof AllInclusive || services instanceof Breakfast)
            {
                hasServices = true;
                hasFood = true;
                Person person = super.getGuest();
                int count = 1;
                
                if (person instanceof PrivateGuest)
                    count += ((PrivateGuest)person).getGuestNames().size();
                else if (person instanceof CompanyGuest)
                    count += ((CompanyGuest)person).getGuestNames().size();
                
                price += services.getPrice()*count;
                food.append(services.getType());
                food.append(":\t");
                food.append(services.getPrice()*count);
                food.append(" NOK");
            }// End of if
            
            if (services instanceof ExtraBed)
            {
                if (hasExtraBed)
                {
                    ++extraBeds;
                    price += services.getPrice();
                    extraBed.delete(0, extraBed.length());
                    extraBed.append(services.getType());
                    extraBed.append("\t");
                    extraBed.append(services.getPrice()*extraBeds);
                    extraBed.append(" NOK");
                    extraBed.append("\tAntall: ");
                    extraBed.append(extraBeds);
                }
                else
                {
                    hasServices = true;
                    hasExtraBed = true;
                    ++extraBeds;

                    price += services.getPrice();
                    extraBed.append(services.toString());
                }
            }// End of if
            
            if (services instanceof Crib)
            {
                if (hasCrib)
                {
                    ++cribs;
                    price += services.getPrice();
                    crib.delete(0, crib.length());
                    crib.append(services.getType());
                    crib.append("\t");
                    crib.append(services.getPrice()*cribs);
                    crib.append(" NOK");
                    crib.append("\tAntall: ");
                    crib.append(cribs);
                    
                }
                else
                {
                    hasServices = true;
                    hasCrib = true;
                    ++cribs;

                    price += services.getPrice();
                    crib.append(services.toString());
                }
            }// End of if
            
            if (services instanceof ExtraPillow)
            {
                if (hasExtraPillow)
                {
                    ++extraPillows;
                    price += services.getPrice();
                    extraPillow.delete(0, extraPillow.length());
                    extraPillow.append(services.getType());
                    extraPillow.append("\t");
                    extraPillow.append(services.getPrice()*extraPillows);
                    extraPillow.append(" NOK");
                    extraPillow.append("\tAntall: ");
                    extraPillow.append(extraPillows);
                    
                }
                else
                {
                    hasServices = true;
                    hasExtraPillow = true;
                    ++extraPillows;

                    price += services.getPrice();
                    extraPillow.append(services.toString());
                }
            }// End of if
            
            if (services instanceof ExtraQuilt)
            {
                if (hasExtraQuilt)
                {
                    ++extraQuilts;
                    price += services.getPrice();
                    extraQuilt.delete(0, extraQuilt.length());
                    extraQuilt.append(services.getType());
                    extraQuilt.append("\t");
                    extraQuilt.append(services.getPrice()*extraQuilts);
                    extraQuilt.append(" NOK");
                    extraQuilt.append("\tAntall: ");
                    extraQuilt.append(extraQuilts);
                    
                }
                else
                {
                    hasServices = true;
                    hasExtraQuilt = true;
                    ++extraQuilts;

                    price += services.getPrice();
                    extraQuilt.append(services.toString());
                }
            }// End of if
            
            if (services instanceof WelcomeGift)
            {
                if (hasWelcomeGift)
                {
                    ++welcomeGifts;
                    price += services.getPrice();
                    welcomeGift.delete(0, welcomeGift.length());
                    welcomeGift.append(services.getType());
                    welcomeGift.append("\t");
                    welcomeGift.append(services.getPrice()*welcomeGifts);
                    welcomeGift.append(" NOK");
                    welcomeGift.append("\tAntall: ");
                    welcomeGift.append(welcomeGifts);
                    
                }
                else
                {
                    hasServices = true;
                    hasWelcomeGift = true;
                    ++welcomeGifts;

                    price += services.getPrice();
                    welcomeGift.append(services.toString());
                }
            }// End of if
        }// End of while
        
        String output = (hasSingleRoom ? "\n" + singleRoom : "")
                        + (hasDoubleRoom ? "\n" + doubleRoom : "")
                        + (hasFamilyRoom ? "\n" + familyRoom : "")
                        + (hasSuite ? "\n" + suite : "")
                        + (hasServices ? "\n" + serviceString : "")
                        + (hasFood ? "\n" + food : "")
                        + (hasExtraBed ? "\n" + extraBed : "")
                        + (hasCrib ? "\n" + crib : "")
                        + (hasExtraPillow ? "\n" + extraPillow : "")
                        + (hasExtraQuilt ? "\n" + extraQuilt : "")
                        + (hasWelcomeGift ? "\n" + welcomeGift : "")
                        + "\n\nTotalsum:\t" + price + " NOK";
        
        super.setPrice(price);
        return output;
    }// End of method getStringBuilder()
    
    
    
    @Override
    public String toString()
    {
        return super.toString() + getStringBuilder();
    }
}// end of class BookHotelRoom