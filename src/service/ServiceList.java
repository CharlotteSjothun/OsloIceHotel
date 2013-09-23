/*
 * Anette Molund, s181083, 10.05.12
 * 
 * Klassen oppretter en liste med tjenester og utfører 
 * diverse operasjoner på dem.
 */

package service;


import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;


public class ServiceList implements Serializable
{
    private static final long serialVersionUID 
            = ObjectStreamClass.lookup(ServiceList.class).getSerialVersionUID();
    
    private List<Service> service;
    
    public ServiceList()
    {
        service = new LinkedList<>();
        addServices();
    }
    
    
    
    // Metoden legger alle de forskjellige tjenestene inn i tjenestelista.
    private void addServices()
    {
        service.add(new AllInclusive());
        service.add(new Breakfast());
        service.add(new ExtraBed());
        service.add(new Crib());
        service.add(new ExtraPillow());
        service.add(new ExtraQuilt());
        service.add(new WelcomeGift());        
    }// End of method addServices()
    
    
    
    // Metoden mottar en tekststreng, og returnerer tjenesten som er lik denne.
    public Service getServiceByString(String serv)
    {
        Iterator<Service> iter = service.iterator();
        
        while(iter.hasNext())
        {
            Service servi = iter.next();
            
            if(serv.equals(servi.toString()))
                return servi;
        }
        
        return null;
    }// End of method getServiceByString(...)
    
    
    
    /* Metoden mottar en liste med tekststrenger, og returnerer en liste med tjenester 
     * lik tekststrengene.*/
    public List<Service> getServicesByString(List<String> serviceList)
    {
        List<Service> services = new LinkedList<>();
        Iterator<String> iter = serviceList.iterator();
        
        while(iter.hasNext())
        {
            String serv = iter.next();
            Iterator<Service> serviceIter = service.iterator();
            boolean hasService = false;
            
            while(serviceIter.hasNext())
            {
                Service s = serviceIter.next();
                if(!hasService && s.toString().equals(serv))
                {
                    services.add(s);
                    hasService = true;
                }
            }// End of while
        }// End of while
        
        return services;
    }// End of method getServicesByString(...)
    
    
    
    // Metoden returnerer en jList med tekststrenger med informasjon om tjenestene.
    public JList<String> getServicesInfo()
    {
        Iterator<Service> iter = service.iterator();
        
        JList<String> toStringList = new JList<>();
        toStringList.setModel(new DefaultListModel());
        DefaultListModel toStringListModel = (DefaultListModel) toStringList.getModel();
        
        while(iter.hasNext())
        {
            toStringListModel.addElement(iter.next().toString());
        }
        
        return toStringList;
    }// End of method getServicesInfo()
    
    
    
    // Metoden returnerer en tekststreng med informasjon om alle tjenestene.
    public String showServices()
    {
        Iterator<Service> iter = service.iterator();
        String output = "";
        
        if(iter.hasNext())
        {
            do
            {
                String serviceString = iter.next().toString();
                output += serviceString + "\n";
            }while(iter.hasNext());
        }
        
        return output;
    }// End of method showServices()
}// End of class ServiceList