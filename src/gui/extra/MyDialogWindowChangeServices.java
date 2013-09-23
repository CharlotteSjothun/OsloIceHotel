/*
 * Anette Molund, s181083, 23.04.12
 * 
 * Klassen er en subklasse av MyDialogWindow.
 * Denne klassen oppretter et dialogvindu hvor man kan legge til og fjerne tjenester.
 */

package gui.extra;

import gui.tabs.CheckInCheckOutTab;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import service.Service;
import service.ServiceList;

public class MyDialogWindowChangeServices extends MyDialogWindow
{
    private JList<String> serviceList, list;
    private JButton add, delete, cancel;
    private Listener listener;
    private ServiceList serv;
    CheckInCheckOutTab checkInCheckOut;
    
    public MyDialogWindowChangeServices(Window window, String title, 
            JList<String> sList, JList<String> jList, ServiceList s, CheckInCheckOutTab checkInOut)
    {
        super(window, title, Dialog.ModalityType.APPLICATION_MODAL);
        listener = new Listener();
        checkInCheckOut = checkInOut;
        serviceList = sList;
        list = jList;
        serv = s;
        
        serviceList.setFixedCellHeight(15);
        serviceList.setFixedCellWidth(160);
        serviceList.setVisibleRowCount(7);
        list.setFixedCellHeight(15);
        list.setFixedCellWidth(160);
        list.setVisibleRowCount(7);
        
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.setBackground(Color.WHITE);
        
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        
        panel1.add(new JScrollPane(list));
        
        JPanel panel2 = new JPanel(new GridLayout(2,1));
        panel2.setBackground(Color.WHITE);
        add = new JButton("Legg til");
        add.addActionListener(listener);
        panel2.add(add, BorderLayout.CENTER);
        delete = new JButton("Slett");
        delete.addActionListener(listener);
        panel2.add(delete);
        panel1.add(panel2);
        panel1.add(new JScrollPane(serviceList));
        c.add(panel1);
        
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.WHITE);
        cancel = new JButton("Bekreft");
        cancel.addActionListener(listener);
        panel3.add(cancel);
        c.add(panel3);
        setSize(425, 200);
    }// End of constructor
    
    
    
    /* Metoden sletter valgt tjeneste fra visningen og kaller opp metode i CheckInCheckOutTab 
     * for å slette tjenesten fra bookinglista.*/
    public void deleteService()
    {
        String service  = serviceList.getSelectedValue();
        int serviceIndex = serviceList.getSelectedIndex();
        
        DefaultListModel model = (DefaultListModel)serviceList.getModel();
        model.remove(serviceIndex);
        checkInCheckOut.checkRemoveService(service);
    }// End of method deleteService()
    
    
    
    /* Metoden legger til valgt tjeneste i visningen og kaller opp metode i CheckInCheckOutTab
     * for å legge til tjenesten i bookinglista.*/
    public void addService()
    {
        List<String> serviceString = list.getSelectedValuesList();
        List<Service> service = serv.getServicesByString(serviceString);
        
        checkInCheckOut.checkAddService(service);
    }// End of method addService
    
    
    
    // Privat indre lytteklasse
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == add)
                addService();
            else if(e.getSource() == delete)
                deleteService();
            else if(e.getSource() == cancel)
                setVisible(false);
        }
    }// End of class Listener
}// End of class MyDialogWindowChangeServices