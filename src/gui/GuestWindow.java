/*
 * Anette Molund, s181083, 07.05.12.
 * 
 * Denne klassen oppretter vinduet som vises hvis man velger å åpne gjestevinduet i 
 * innloggingsvinduet. Klassen har metoder for å vise informasjon om rom, fasiliteter
 * og meny. Klassen har en egen indre lytteklasse.
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import restaurant.Restaurant;
import room.conferenceroom.ConferenceRoomList;
import room.hotelroom.HotelRoomList;
import service.ServiceList;

public class GuestWindow extends JFrame
{
    private JButton facilities, menu, hotelRoomsButton, conferenceRoomsButton;
    private JTextArea output, extraOutput1, extraOutput2;
    private JLabel logo;
    private JPanel right, rightExtra, center;
    private JMenuItem menuItemClose, menuItemLogout, menuItemAbout, menuItemSave, menuItemPrint;
    private Restaurant restaurant;
    private HotelRoomList hotelRoom;
    private ConferenceRoomList conferenceRoom;
    private ServiceList service;
    private LoginWindow loginWindow;
    private Listener listener;
    
    public GuestWindow(LoginWindow login, Restaurant restaurantList, HotelRoomList hotelRoomList, 
                       ConferenceRoomList conferenceRoomList, ServiceList serviceList)
    {
        super("OSLO ICEHOTEL");
        
        listener = new Listener();
        hotelRoom = hotelRoomList;
        conferenceRoom = conferenceRoomList;
        restaurant = restaurantList;
        service = serviceList;
        loginWindow = login;
        
        JMenuBar menuBar = createMenuBar();
        
        JPanel pic = new JPanel();
        Icon hotellogo = new ImageIcon(getClass().getClassLoader().getResource("pictures/hotellogoNy2.png"));
        logo = new JLabel(hotellogo);
        pic.add(logo);
        pic.setBackground(Color.WHITE);
        
        JPanel guestPanel = new JPanel(new GridLayout(1,1));
        
        JPanel left = new JPanel(new GridLayout(4,1));
        
        JPanel button1 = new JPanel(new GridLayout(1,4));
        facilities = new JButton("Vis fasiliteter");
        facilities.addActionListener(listener);
        facilities.setMnemonic( KeyEvent.VK_F);
        button1.add(facilities);
        left.add(button1);
        
        JPanel button2 = new JPanel(new GridLayout(1,4));
        menu = new JButton("Vis meny");
        menu.addActionListener(listener);
        menu.setMnemonic( KeyEvent.VK_M );
        button2.add(menu);
        left.add(button2);
        
        JPanel button3 = new JPanel(new GridLayout(1,4));
        hotelRoomsButton = new JButton("Vis hotellrom");
        hotelRoomsButton.addActionListener(listener);
        hotelRoomsButton.setMnemonic( KeyEvent.VK_H );
        button3.add(hotelRoomsButton);
        left.add(button3); 
        
        JPanel button4 = new JPanel(new GridLayout(1,4));
        conferenceRoomsButton = new JButton("Vis seminarrom");
        conferenceRoomsButton.addActionListener(listener);
        conferenceRoomsButton.setMnemonic( KeyEvent.VK_S );
        button4.add(conferenceRoomsButton);
        left.add(button4);                
        
        right = new JPanel(new GridLayout(1,2));
        output = new JTextArea(25,50);
        output.setEditable(false);
        output.setText("Velkommen til Oslo IceHotel");
        right.add(new JScrollPane(output, 
                  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        
        rightExtra = new JPanel(new BorderLayout());
        extraOutput1 = new JTextArea(25,25);
        extraOutput1.setEditable(false);
        extraOutput2 = new JTextArea(25,25);
        extraOutput2.setEditable(false);
        rightExtra.add(new JScrollPane(extraOutput1, 
                       JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                       JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), 
                       BorderLayout.LINE_START);
        rightExtra.add(new JScrollPane(extraOutput2, 
                       JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                       JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), 
                       BorderLayout.LINE_END);
        
        
        center = new JPanel();
        center.add(left, BorderLayout.LINE_START);
        center.add(right, BorderLayout.LINE_END);
        center.setBackground(Color.WHITE);
        guestPanel.add(center);
        
        JPanel info = new JPanel();
        JLabel infoText = new JLabel("Adresse: Stortingsgata 27                "
                                     + "Telefon: 452 68 691                "
                                     + "www.osloicehotel.com                "
                                     + "post@osloicehotel.com");
        info.add(infoText);
        
        
        add(pic, BorderLayout.PAGE_START);
        add(guestPanel, BorderLayout.CENTER);
        add(info, BorderLayout.PAGE_END);
        
        
        URL kilde = LoginWindow.class.getClassLoader().getResource("pictures/hotellogoIcon.png");

    	if (kilde != null)
    	{
            ImageIcon bilde = new ImageIcon(kilde);
            Image ikon = bilde.getImage();
            setIconImage(ikon);
    	}
        
        
        
        setJMenuBar(menuBar);
        pack();
        setSize(920, 680);
        setLocationRelativeTo(null);
        setVisible(true);
        
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                loginWindow.writeToFile();
                System.exit(0);
            }
        });
    }//End of constructor
    
    
    
    // Metoden oppretter en menylinje
    private JMenuBar createMenuBar()
    {
        UIManager.put("Menu.selectionBackground", new Color(0xDA,0xDD,0xED));
        UIManager.put("MenuItem.selectionForeground", Color.LIGHT_GRAY);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("Fil");
        UIManager.put("MenuItem.selectionBackground", menu1.getBackground());
        menuItemSave = new JMenuItem("Lagre");
        UIManager.put("MenuItem.selectionBackground", menuItemSave.getBackground());
        menuItemSave.setForeground(Color.LIGHT_GRAY);
        menuItemSave.setBorder(BorderFactory.createEmptyBorder());
        menuItemSave.setAccelerator(KeyStroke.getKeyStroke('S', Event.CTRL_MASK));
        menuItemSave.addActionListener(listener);
        menu1.add(menuItemSave);
        menuItemPrint = new JMenuItem("Skriv ut");
        menuItemPrint.setForeground(Color.LIGHT_GRAY);
        menuItemPrint.setBorder(BorderFactory.createEmptyBorder());
        menuItemPrint.setAccelerator(KeyStroke.getKeyStroke('P', Event.CTRL_MASK));
        menuItemPrint.addActionListener(listener);
        menu1.add(menuItemPrint);
        menu1.addSeparator();
        UIManager.put("MenuItem.selectionBackground", new Color(0xDA,0xDD,0xED));
        UIManager.put("MenuItem.selectionForeground", Color.BLACK);
        menuItemLogout = new JMenuItem("Logg av");
        menuItemLogout.setAccelerator(KeyStroke.getKeyStroke('L', Event.CTRL_MASK));
        menuItemLogout.addActionListener(listener);
        menu1.add(menuItemLogout);
        menuItemClose = new JMenuItem("Avslutt");
        menuItemClose.setAccelerator(KeyStroke.getKeyStroke('A', Event.CTRL_MASK));
        menuItemClose.addActionListener(listener);
        menu1.add(menuItemClose);
        menuBar.add(menu1);
        JMenu menu2 = new JMenu("Om");
        menuItemAbout = new JMenuItem("Om");
        menuItemAbout.setAccelerator(KeyStroke.getKeyStroke('O', Event.CTRL_MASK));
        menuItemAbout.addActionListener(listener);
        menu2.add(menuItemAbout);
        menuBar.add(menu2);
        
        return menuBar;
    } // End of method createMenuBar()
    
    
    
    /* Metoden skriver ut informasjon om fasiliteter og ekstra tjenester
     * i et JTextArea når bruker trykker på knappen "Vis fasiliteter".*/
    public void showFacilities()
    {
        output.setText("Fasiliteter\n\n");
        output.append("Hotellet har en flott lobby med store sofagrupper foran peisen."
                + "\nI underetasjen finner du vår spaavdeling som er gratis for våre gjester."
                + "\nDer kan du svømme i vårt 100m2 store basseng og slappe av i boblebad.\n\n");
        output.append("\nOversikt over ekstra tjenester:\n\n" + service.showServices());
        output.setCaretPosition(0);
    }// End of method showFacilities()
    
    
    
    /* Metoden skriver ut i to JTextArea oversikt over drikke- og matmeny når bruker
     * klikker på knappen "Vis meny".*/
    public void showRestaurant()
    {
        center.remove(right);
        center.validate();
        center.add(rightExtra);
        center.validate();
        
        restaurant.showDishList(extraOutput1);
        extraOutput1.setCaretPosition(0);
        
        restaurant.showBeverageList(extraOutput2);
        extraOutput2.setCaretPosition(0);
    }// End og method showRestaurant()
    
    
    
    /* En hjelpemetode for å fjerne den ekstra JTextArea som trengs for å skrive
     * ut drikke- og matmeny.*/
    private void removeExtraOutput()
    {
        center.remove(rightExtra);
        center.repaint();
        center.add(right);
        center.repaint();
    }// End of method removeExtraOutput()
    
    
    
    /* Metoden skriver ut informasjon i JTextArea om hotellrommene når man trykker
     * "Vis hotellrom".*/
    public void showHotelRooms()
    {
        hotelRoom.showHotelRoomTypes(output);
        output.setCaretPosition(0);
    }// End of method showHotelRooms()
    
    
    
    /* Metoden skriver ut informasjon i JTextArea om seminarrommene når man trykker
     * "Vis seminarrom".*/
    public void showConferenceRooms()
    {
        conferenceRoom.showConferenceRoomList(output);
        output.setCaretPosition(0);     
    }// End of method showConferenceRooms()
    
    
    
    //Privat indre lytteklasse
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == facilities)
            {
                removeExtraOutput();
                showFacilities();
            }   
            else if(e.getSource()== menu)
            {
                removeExtraOutput();
                showRestaurant();
            }
            else if(e.getSource() == hotelRoomsButton)
            {
                removeExtraOutput();
                showHotelRooms();
            }
            else if(e.getSource() == conferenceRoomsButton)
            {
                removeExtraOutput();
                showConferenceRooms();
            }
            else if(e.getSource() == menuItemLogout)
            {
                loginWindow.setVisible(true);
                setVisible(false);
            }
            else if (e.getSource() == menuItemAbout)
                loginWindow.about(GuestWindow.this);
            else if (e.getSource() == menuItemClose)
            {
                loginWindow.writeToFile();
                System.exit(0);
            }
        }
    } // end of class Listener
}// end of class GuestWindow