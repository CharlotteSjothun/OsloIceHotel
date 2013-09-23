/*
 * Anette Molund, s181083, 22.04.12
 * 
 * Denne klassen er en abstrakt superklasse for tjenester.
 */

package service;

import java.io.ObjectStreamClass;
import java.io.Serializable;

public abstract class Service implements Serializable
{
    private static final long serialVersionUID = 
            ObjectStreamClass.lookup(Service.class).getSerialVersionUID();
    
    private int price;
    private String type;
    
    public Service(int p, String serviceType)
    {
        price = p;
        type = serviceType;
    }
    
    
    
    //Metoden returnerer prisen på tjenesten.
    public int getPrice()
    {
        return price;
    }
    
    
    
    //Metoden returnerer typen tjeneste.
    public String getType()
    {
        return type;
    }
    
    
    
    @Override
    public abstract String toString();
}// End of class Service