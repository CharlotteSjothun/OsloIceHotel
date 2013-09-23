/*
 * Charlotte Sjøthun s180495, 07.05.2012
 * 
 * Klassen ManageEmployeeTab oppretter et JPanel som skal brukes til å legge i
 * tabbPanelet, har metoder som utfører forskjellige operasjoner og har en egen
 * indre lytterklasse.
 */

package gui.tabs;

import gui.EmployeeWindow;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import restaurant.Beverage;
import restaurant.Dish;
import restaurant.Restaurant;

public class RestaurantMenuTab extends JPanel
{
    private JTextField dishName, dishPrice, bevName, bevPrice;
    private JCheckBox alcohol;
    private JComboBox<String> type;
    private JButton regDish, regBev, showMenu, deleteDish, deleteBev, changePriceDish, changePriceBev;
    private JTextArea dishOutput, bevOutput;
    private EmployeeWindow empWindow;
    private Restaurant restaurant;
    private Listener listener;
    
    public RestaurantMenuTab(EmployeeWindow window, Restaurant rest)
    {
        empWindow = window;
        restaurant = rest;
        listener = new Listener();
        
        JPanel restaurantMenu = new JPanel(new BorderLayout());
        restaurantMenu.setBackground(Color.WHITE);
        
        JPanel addDishAndBevPanel = new JPanel(new GridBagLayout());
        addDishAndBevPanel.setBackground(Color.WHITE);
        GridBagConstraints gBC = new GridBagConstraints();
        gBC.insets = new Insets(10,0,40,0);
        
        JPanel addShowMenuButton = new JPanel();
        addShowMenuButton.setBackground(Color.WHITE);
        showMenu = new JButton("Vis mat- og drikke-meny");
        showMenu.addActionListener(listener);
        addShowMenuButton.add(showMenu);
        gBC.gridx = 0;
        gBC.gridy = 0;
        addDishAndBevPanel.add(addShowMenuButton, gBC);
        
        JPanel addDishPanel = new JPanel(new GridBagLayout());
        addDishPanel.setBorder(BorderFactory.createTitledBorder("Administrer matmeny"));
        GridBagConstraints gBC2 = new GridBagConstraints();
        gBC2.insets = new Insets(0,0,2,0);
        addDishPanel.setBackground(Color.WHITE);
        
        JPanel addDishPanel1 = new JPanel(new GridLayout(1,2));
        addDishPanel1.setBackground(Color.WHITE);
        dishName = new JTextField(12);
        dishName.addActionListener(listener);
        addDishPanel1.add(new JLabel("Matrettens navn: "));
        addDishPanel1.add(dishName);
        gBC2.gridx = 0;
        gBC2.gridy = 0;
        addDishPanel.add(addDishPanel1, gBC2);
        
        JPanel addDishPanel2 = new JPanel(new GridLayout(1,2));
        addDishPanel2.setBackground(Color.WHITE);
        dishPrice = new JTextField(12);
        dishPrice.addActionListener(listener);
        addDishPanel2.add(new JLabel("Pris: "));
        addDishPanel2.add(dishPrice);
        gBC2.gridx = 0;
        gBC2.gridy = 1;
        addDishPanel.add(addDishPanel2, gBC2);
        
        JPanel addDishPanel3 = new JPanel(new GridLayout(1,2));
        addDishPanel3.setBackground(Color.WHITE);
        String[] typeSelection = {"Forrett", "Hovedrett", "Dessert"};
        type = new JComboBox<>(typeSelection);
        type.setSelectedIndex(1);
        type.setBackground(Color.WHITE);
        type.addActionListener(listener);
        addDishPanel3.add(new JLabel("Type matrett: "));
        addDishPanel3.add(type);
        gBC2.gridx = 0;
        gBC2.gridy = 2;
        addDishPanel.add(addDishPanel3, gBC2);
        
        JPanel addDishPanel4 = new JPanel(new BorderLayout(2,2));
        addDishPanel4.setBackground(Color.WHITE);
        regDish = new JButton("Registrer matrett");
        regDish.addActionListener(listener);
        addDishPanel4.add(regDish, BorderLayout.LINE_START);
        deleteDish = new JButton("Slett matrett");
        deleteDish.addActionListener(listener);
        addDishPanel4.add(deleteDish, BorderLayout.LINE_END);
        changePriceDish = new JButton("Endre pris");
        changePriceDish.addActionListener(listener);
        addDishPanel4.add(changePriceDish, BorderLayout.PAGE_END);
        gBC2.gridx = 0;
        gBC2.gridy = 3;
        addDishPanel.add(addDishPanel4, gBC2);
        
        gBC.gridx = 0;
        gBC.gridy = 1;
        addDishAndBevPanel.add(addDishPanel, gBC);
        
        JPanel addBevPanel = new JPanel(new GridBagLayout());
        addBevPanel.setBorder(BorderFactory.createTitledBorder("Administer drikkemeny"));
        GridBagConstraints gBC3 = new GridBagConstraints();
        gBC3.insets = new Insets(0,0,2,0);
        addBevPanel.setBackground(Color.WHITE);
        
        JPanel addBevPanel1 = new JPanel(new GridLayout(1,2));
        addBevPanel1.setBackground(Color.WHITE);
        bevName = new JTextField(12);
        bevName.addActionListener(listener);
        addBevPanel1.add(new JLabel("Drikkens navn: "));
        addBevPanel1.add(bevName);
        gBC3.gridx = 0;
        gBC3.gridy = 0;
        addBevPanel.add(addBevPanel1, gBC3);
        
        JPanel addBevPanel2 = new JPanel(new GridLayout(1,2));
        addBevPanel2.setBackground(Color.WHITE);
        bevPrice = new JTextField(12);
        bevPrice.addActionListener(listener);
        addBevPanel2.add(new JLabel("Pris: "));
        addBevPanel2.add(bevPrice);
        gBC3.gridx = 0;
        gBC3.gridy = 1;
        addBevPanel.add(addBevPanel2, gBC3);
        
        JPanel addBevPanel3 = new JPanel();
        addBevPanel3.setBackground(Color.WHITE);
        alcohol = new JCheckBox("Alkohol");
        alcohol.setBackground(Color.WHITE);
        alcohol.addActionListener(listener);
        addBevPanel3.add(alcohol);
        gBC3.gridx = 0;
        gBC3.gridy = 2;
        addBevPanel.add(addBevPanel3, gBC3);
        
        JPanel addBevPanel4 = new JPanel(new BorderLayout(2,2));
        addBevPanel4.setBackground(Color.WHITE);
        regBev = new JButton("Registrer drikken");
        regBev.addActionListener(listener);
        addBevPanel4.add(regBev, BorderLayout.LINE_START);
        deleteBev = new JButton("Slett drikken");
        deleteBev.addActionListener(listener);
        addBevPanel4.add(deleteBev, BorderLayout.LINE_END);
        changePriceBev = new JButton("Endre pris");
        changePriceBev.addActionListener(listener);
        addBevPanel4.add(changePriceBev, BorderLayout.PAGE_END);
        gBC3.gridx = 0;
        gBC3.gridy = 3;
        addBevPanel.add(addBevPanel4, gBC3);
        
        gBC.gridx = 0;
        gBC.gridy = 2;
        addDishAndBevPanel.add(addBevPanel, gBC);
        
        restaurantMenu.add(addDishAndBevPanel, BorderLayout.LINE_START);
        
        JPanel restaurantOutputPanel = new JPanel();
        restaurantOutputPanel.setBackground(Color.WHITE);
        dishOutput = new JTextArea(34,24);
        dishOutput.setEditable(false);
        restaurantOutputPanel.add(new JScrollPane(dishOutput, 
                                                  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                                                  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), 
                                                  BorderLayout.LINE_START);
        
        bevOutput = new JTextArea(34,24);
        bevOutput.setEditable(false);
        dishOutput.setMinimumSize(new Dimension(32,24));
        restaurantOutputPanel.add(new JScrollPane(bevOutput, 
                                                  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                                                  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), 
                                                  BorderLayout.CENTER);
        
        restaurantMenu.add(restaurantOutputPanel, BorderLayout.CENTER);
        
        add(restaurantMenu);
        setBackground(Color.WHITE);
    } // End of constructor
    
    
    
    /* Charlotte 20.04.2012
     * Når man velger "Registrer matrett" blir denne metoden kalt, den oppretter
     * et dish-objekt og legger det i dishlista i restaurantklassen.*/
    public void restaurantAddDish()
    {
        String name = dishName.getText().trim();
        String p = dishPrice.getText().trim();
        int typeIndex = type.getSelectedIndex();
        String t = type.getItemAt(typeIndex);
        
        String errorMessage = restaurantInputControl(name, p);
        
        if(!errorMessage.equals(""))
        {
            empWindow.showMessage(errorMessage, "Feilmelding", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int price = Integer.parseInt(dishPrice.getText().trim());
        
        Dish d = new Dish(name, price, t);
        
        if (restaurant.addDish(d))
        {
            empWindow.showMessage("Matretten ble registrert!", "Bekreftelse", 
                                  JOptionPane.PLAIN_MESSAGE);
            
            restaurantShowMenu();
        }
        else
            empWindow.showMessage("Kunne ikke registrere matretten!", "Feilmelding", 
                                  JOptionPane.WARNING_MESSAGE);
    } // End of method restaurantAddDish()
    
    
    
    /* Charlotte 20.04.2012
     * Når man velger "Registrer drikken" blir denne metoden kalt, den oppretter
     * et beverage-objekt og legger det i beveragelista i restaurantklassen.*/
    public void restaurantAddBeverage()
    {
        String name = bevName.getText().trim();
        String p = bevPrice.getText().trim();
        boolean al = alcohol.isSelected();
        
        String errorMessage = restaurantInputControl(name, p);
        
        if(!errorMessage.equals(""))
        {
            empWindow.showMessage(errorMessage, "Feilmelding", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int price = Integer.parseInt(bevPrice.getText().trim());
        
        Beverage b = new Beverage(name, price, al);
        
        if (restaurant.addBeverage(b))
        {
            empWindow.showMessage("Drikken ble registrert!", "Bekreftelse", 
                                  JOptionPane.PLAIN_MESSAGE);
            
            restaurantShowMenu();
        }
        else
            empWindow.showMessage("Kunne ikke registrere drikken!", "Feilmelding", 
                                  JOptionPane.WARNING_MESSAGE);
    } // End of method restaurantAddBeverage()
    
    
    
    /* Charlotte 20.04.2012
     * Hjelpemetoden blir brukt til å kontrollere input fra brukeren i restauranttabben.*/
    private String restaurantInputControl(String name, String price)
    {
        boolean error = false;
        String  errorMessage = "";
        
        if (!empWindow.regexName(name))
        {
            errorMessage = "Mangler navn på drikken eller feil i format.";
            error = true;
        }
        if(!empWindow.regexPrice(price))
        {
            if(error)
                errorMessage += "\nMangler pris eller feil i format.";
            else
                errorMessage = "Mangler pris eller feil i format.";
        }
        
        return errorMessage;
    } // End of method restaurantInputControl(...)
    
    
    
    /* Charlotte 20.04.2012
     * Når man velger "Slett matrett" blir denne metoden kalt, den sletter første
     * forekomst av dish-objektet (fra dishlista i restaurantklassen) som matcher 
     * inputen fra brukeren.*/
    public void restaurantRemoveDish()
    {
        String name = dishName.getText().trim();
        
        if (!empWindow.regexName(name))
        {
            empWindow.showMessage("Mangler navn på matretten eller feil i format.",
                                  "Feilmelding", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            if(restaurant.removeDish(name))
            {
                empWindow.showMessage(name + " ble slettet fra menyen.", "Bekreftelse",
                                      JOptionPane.PLAIN_MESSAGE);
                
                restaurantShowMenu();
            }
            else
                empWindow.showMessage("Fant ingen matrett med navn \"" + name + "\" i menyen",
                                      "Feilmelding", JOptionPane.WARNING_MESSAGE);
        }
    } // End of method restaurantRemoveDish()
    
    
    
    /* Charlotte 20.04.2012
     * Når man velger "Slett drikken" blir denne metoden kalt, den sletter første
     * forekomst av beverage-objektet (fra beveragelista i restaurantklassen) som matcher 
     * inputen fra brukeren.*/
    public void restaurantRemoveBeverage()
    {
        String name = bevName.getText().trim();
        
        if (!empWindow.regexName(name))
        {
            empWindow.showMessage("Mangler navn på drikken eller feil i format.",
                                  "Feilmelding", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            if(restaurant.removeBeverage(name))
            {
                empWindow.showMessage(name + " ble slettet fra menyen.", "Bekreftelse",
                                      JOptionPane.PLAIN_MESSAGE);
                
                restaurantShowMenu();
            }
            else
                empWindow.showMessage("Fant ingen drikke med navn \"" + name + "\" i menyen",
                                      "Feilmelding", JOptionPane.WARNING_MESSAGE);
        }
    } // End of method restaurantRemoveBeverage()
    
    
    
    /* Charlotte 20.04.2012
     * Når man velger "Endre pris" (i administrer matmeny tabben) blir denne 
     * metoden kalt, den endrer prisen til den første forekomst av dish-objektet 
     * (fra dishlista i restaurantklassen) som matcher inputen fra brukeren.*/
    public void restaurantChangePriceDish()
    {
        String name = dishName.getText().trim();
        String p = dishPrice.getText().trim();
        
        String errorMessage = restaurantInputControl(name, p);
        
        if(!errorMessage.equals(""))
        {
            empWindow.showMessage(errorMessage, "Feilmelding", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int price = Integer.parseInt(dishPrice.getText().trim());
        
        if (restaurant.setDishPrice(price, name))
        {
            empWindow.showMessage("Prisen på " + name + " ble endret!", "Bekreftelse", 
                                  JOptionPane.PLAIN_MESSAGE);
            
            restaurantShowMenu();
        }
        else
            empWindow.showMessage("Fant ingen matrett med navn \"" + name + "\" i menyen", 
                                  "Feilmelding", JOptionPane.WARNING_MESSAGE);
    } // End of method restaurantChangePriceDish()
    
    
    
    /* Charlotte 20.04.2012
     * Når man velger "Endre pris" (i administrer drikkemeny tabben) blir denne 
     * metoden kalt, den endrer prisen til den første forekomst av beverage-objektet 
     * (fra beveragelista i restaurantklassen) som matcher inputen fra brukeren.*/
    public void restaurantChangePriceBeverage()
    {
        String name = bevName.getText().trim();
        String p = bevPrice.getText().trim();
        
        String errorMessage = restaurantInputControl(name, p);
        
        if(!errorMessage.equals(""))
        {
            empWindow.showMessage(errorMessage, "Feilmelding", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int price = Integer.parseInt(bevPrice.getText().trim());
        
        if (restaurant.setBeveragePrice(price, name))
        {
            empWindow.showMessage("Prisen på " + name + " ble endret!", "Bekreftelse", 
                                  JOptionPane.PLAIN_MESSAGE);
            
            restaurantShowMenu();
        }
        else
            empWindow.showMessage("Fant ingen drikke med navn \"" + name + "\" i menyen", 
                                  "Feilmelding", JOptionPane.WARNING_MESSAGE);
    } // End of method restaurantChangePriceBeverage()
    
    
    
    /* Charlotte 16.04.2012
     * Når man velger "Vis mat og drikke meny" blir denne metoden kalt, den viser 
     * informasjonen om dish og beverage-objektene som ligger i de to listene i 
     * restaurantklassen*/
    public void restaurantShowMenu()
    {
        restaurant.showDishList(dishOutput);
        restaurant.showBeverageList(bevOutput);
    } // End of method restaurantShowMenu()
    
    
    
    // Privat indre lytterklasse.
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == showMenu)
                restaurantShowMenu();
            else if (e.getSource() == regDish)
                restaurantAddDish();
            else if (e.getSource() == regBev)
                restaurantAddBeverage();
            else if (e.getSource() == deleteDish)
                restaurantRemoveDish();
            else if (e.getSource() == deleteBev)
                restaurantRemoveBeverage();
            else if (e.getSource() == changePriceDish)
                restaurantChangePriceDish();
            else if (e.getSource() == changePriceBev)
                restaurantChangePriceBeverage();
        }
    } // End of class Listener
} // End of class RestaurantMenuTab
