/*
 * Charlotte Sjøthun s180495, 07.05.2012
 * 
 * Klassen ManageEmployeeTab oppretter et JPanel som skal brukes til å legge i
 * tabbPanelet, har metoder som utfører forskjellige operasjoner og har en egen
 * indre lytterklasse.
 */

package gui.tabs;

import gui.EmployeeWindow;
import gui.extra.MyDialogWindow;
import gui.extra.MyDialogWindowTable;
import gui.extra.MyTable;
import gui.extra.MyTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;
import person.Person;
import person.employee.Employee;
import person.employee.EmployeeList;

public class ManageEmployeeTab extends JPanel
{
    private JTextField empFirstName, empLastName, empAddress, empPhoneNr, empEmail, sosialSecurityNr;
    private JButton regEmployee, updateEmployee, endEmployment, showEmployeeList, showAllEmployees;
    private JPasswordField password;
    private JCheckBox isAdmin;
    private JComboBox<Integer> employeeNr;
    private MyTable table;
    private EmployeeWindow empWindow;
    private EmployeeList employeeList;
    private Listener listener;
    
    public ManageEmployeeTab(EmployeeWindow window, EmployeeList employeeL)
    {
        empWindow = window;
        employeeList = employeeL;
        listener = new Listener();
        
        JPanel manageEmployee = new JPanel(new BorderLayout());
        manageEmployee.setBackground(Color.WHITE);
        
        JPanel manageEmployeeInputPanel = new JPanel(new GridBagLayout());
        manageEmployeeInputPanel.setBackground(Color.WHITE);
        GridBagConstraints gBC = new GridBagConstraints();
        gBC.insets = new Insets(20,0,10,0);
        
        JPanel employeeChange = new JPanel(new GridLayout(1,2));
        employeeChange.setBackground(Color.WHITE);
        employeeChange.add(new JLabel("Ved endring: (velg ansattnr.)     "));
        Integer[] typeSelection = employeeList.getCurrentEmployeeNr();
        employeeNr = new JComboBox<>(typeSelection);
        employeeNr.setSelectedIndex(0);
        employeeNr.setBackground(Color.WHITE);
        employeeNr.addActionListener(listener);
        employeeChange.add(employeeNr);
        gBC.gridx = 0;
        gBC.gridy = 0;
        manageEmployeeInputPanel.add(employeeChange, gBC);
        
        JPanel employeeInfo = new JPanel(new GridLayout(4,4,30,2));
        employeeInfo.setBorder(BorderFactory.createTitledBorder("Ansattes personalia"));
        employeeInfo.setBackground(Color.WHITE);
        employeeInfo.add(new JLabel("Fornavn: "));
        empFirstName = new JTextField(14);
        empFirstName.addActionListener(listener);
        employeeInfo.add(empFirstName);
        employeeInfo.add(new JLabel("Etternavn: "));
        empLastName = new JTextField(14);
        empLastName.addActionListener(listener);
        employeeInfo.add(empLastName);
        employeeInfo.setBackground(Color.WHITE);
        employeeInfo.add(new JLabel("Adresse: "));
        empAddress = new JTextField(14);
        empAddress.addActionListener(listener);
        employeeInfo.add(empAddress);
        employeeInfo.add(new JLabel("Telefonnr: "));
        empPhoneNr = new JTextField(14);
        empPhoneNr.addActionListener(listener);
        employeeInfo.add(empPhoneNr);
        employeeInfo.setBackground(Color.WHITE);
        employeeInfo.add(new JLabel("Mail: "));
        empEmail = new JTextField(14);
        empEmail.addActionListener(listener);
        employeeInfo.add(empEmail);
        employeeInfo.add(new JLabel("Personnr: "));
        sosialSecurityNr = new JTextField(14);
        sosialSecurityNr.addActionListener(listener);
        employeeInfo.add(sosialSecurityNr);
        employeeInfo.setBackground(Color.WHITE);
        employeeInfo.add(new JLabel("Passord: "));
        password = new JPasswordField(14);
        password.setEchoChar('*');
        password.addActionListener(listener);
        employeeInfo.add(password);
        isAdmin = new JCheckBox("Administrator");
        isAdmin.setBackground(Color.WHITE);
        isAdmin.addActionListener(listener);
        employeeInfo.add(isAdmin);
        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.insets = new Insets(0,0,20,0);
        manageEmployeeInputPanel.add(employeeInfo, gBC);
        
        JPanel employeeInfo9 = new JPanel();
        employeeInfo9.setBackground(Color.WHITE);
        regEmployee = new JButton("Registrer ansatt");
        regEmployee.addActionListener(listener);
        employeeInfo9.add(regEmployee);
        updateEmployee = new JButton("Endre ansatt");
        updateEmployee.addActionListener(listener);
        employeeInfo9.add(updateEmployee);
        endEmployment = new JButton("Avslutt ansettelsesforholdet");
        endEmployment.addActionListener(listener);
        employeeInfo9.add(endEmployment);
        showEmployeeList = new JButton("Vis ansatte");
        showEmployeeList.addActionListener(listener);
        employeeInfo9.add(showEmployeeList);
        showAllEmployees = new JButton("Vis fullt ansattregister");
        showAllEmployees.addActionListener(listener);
        employeeInfo9.add(showAllEmployees);
        gBC.gridx = 0;
        gBC.gridy = 2;
        manageEmployeeInputPanel.add(employeeInfo9, gBC);
        
        manageEmployee.add(manageEmployeeInputPanel, BorderLayout.PAGE_START);
        
        JPanel manageEmployeeOutputPanel = new JPanel();
        manageEmployeeOutputPanel.setBackground(Color.WHITE);
        table = new MyTable();
        table.setAutoCreateRowSorter(true);
        table.setPreferredScrollableViewportSize(new Dimension(800,262));
        manageEmployeeOutputPanel.add(new JScrollPane(table,
                                                      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                                                      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), 
                                                      BorderLayout.CENTER);
        
        manageEmployee.add(manageEmployeeOutputPanel, BorderLayout.CENTER);  
        
        add(manageEmployee);
        setBackground(Color.WHITE);
    } // End of constructor
    
    
    
    /* Charlotte 20.04.2012
     * Når man velger "Registrer ansatt" blir denne metoden kalt, den oppretter
     * et ansattobjekt og legger det i ansattlista.*/
    public void employeeAddEmployee()
    {
        String fName = empFirstName.getText().trim();
        String lName = empLastName.getText().trim();
        String adr = empAddress.getText().trim();
        String phoneNr = empPhoneNr.getText().trim();
        String mail = empEmail.getText().trim();
        String sSecurityNr = sosialSecurityNr.getText().trim();
        char[] pass = password.getPassword();
        boolean admin = isAdmin.isSelected();
        String passwd = new String(pass);
        
        String errorMessage = employeeInputControl(fName, lName, adr, phoneNr, mail, sSecurityNr, 
                                                   passwd);
        
        if(!errorMessage.equals(""))
        {
            empWindow.showMessage(errorMessage, "Feilmelding", JOptionPane.WARNING_MESSAGE);
            return;
        }
            
        passwd = employeeList.encryptPassword(passwd);
        
        Person newEmployee = new Employee(fName, lName, adr, phoneNr, mail,
                                          sSecurityNr, new Date(), passwd, admin);
        
        if (employeeList.addEmployee(newEmployee))
        {
            empWindow.showMessage("Ny ansatt ble registrert!\n\n" + newEmployee.toString(), 
                                  "Ansettelse", JOptionPane.PLAIN_MESSAGE);
            
            employeeNr.addItem(((Employee)newEmployee).getEmpNr());
            employeeShowEmployee(newEmployee);
            employeeDeleteInfoField();
        }
        else
            empWindow.showMessage("Kunne ikke registrere ny ansatt!", "Feilmelding",
                                  JOptionPane.WARNING_MESSAGE);
    } // End of method employeeAddEmployee()
    
    
    
    /* Charlotte 20.04.2012
     * Metoden blir brukt til å kontrollere input fra brukeren i ansatttabben.*/
    private String employeeInputControl(String fName, String lName, String adr, String phoneNr, 
                                        String mail, String sSecurityNr, String passwd)
    {
        boolean error = false;
        String errorMessage = "";

        if(!empWindow.regexName(fName))
        {
            errorMessage = "Mangler fornavn eller feil i format.";
            error = true;
        }
        if(!empWindow.regexName(lName))
        {
            if(error)
                errorMessage += "\nMangler etternavn eller feil i format.";
            else
                errorMessage = "Mangler etternavn eller feil i format.";

            error = true;
        }
        if(!empWindow.regexAdr(adr))
        {
            if(error)
                errorMessage += 
                        "\nMangler adresse eller feil i format. Eksempel: Storgata 1b, 0182 Oslo";
            else
                errorMessage = 
                        "Mangler adresse eller feil i format. Eksempel: Storgata 1b, 0182 Oslo";

            error = true;
        }
        if(!empWindow.regexPhoneNr(phoneNr))
        {
            if(error)
                errorMessage += "\nMangler telefonnr eller feil i format.";
            else
                errorMessage = "Mangler telefonnr eller feil i format.";

            error = true;
        }
        if(!empWindow.regexEmail(mail))
        {
            if(error)
                errorMessage += "\nMangler mail eller feil i format.";
            else
                errorMessage = "Mangler mail eller feil i format.";

            error = true;
        }
        if(!empWindow.regexSocialSecurityNr(sSecurityNr))
        {
            if(error)
                errorMessage += "\nMangler personnr eller feil i format.";
            else
                errorMessage = "Mangler personnr eller feil i format.";

            error = true;
        }
        if(!empWindow.regexPassword(passwd))
        {
            if(error)
                errorMessage += "\nMangler passord eller feil i format.";
            else
                errorMessage = "Mangler passord eller feil i format.";
        }
        
        return errorMessage;
    } // End of method employeeInputControl(...)
    
    
    
    /* Charlotte 20.04.2012
     * Når man velger "Endre ansatt" blir denne metoden kalt, den endrer informasjonen
     * til den valgte ansatte.*/
    public void employeeChangeEmployeeData()
    {
        String fName = empFirstName.getText().trim();
        String lName = empLastName.getText().trim();
        String adr = empAddress.getText().trim();
        String phoneNr = empPhoneNr.getText().trim();
        String mail = empEmail.getText().trim();
        String sSecurityNr = sosialSecurityNr.getText().trim();
        char[] pass = password.getPassword();
        boolean admin = isAdmin.isSelected();
        String passwd = new String(pass);
        
        String errorMessage = employeeInputControl(fName, lName, adr, phoneNr, mail, sSecurityNr, 
                                                   passwd);
        
        if(!errorMessage.equals(""))
        {
            empWindow.showMessage(errorMessage, "Feilmelding", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int empNrIndex = employeeNr.getSelectedIndex();
        int empNr = employeeNr.getItemAt(empNrIndex);
        Employee emp = (Employee)employeeList.getEmployeeByEmpNr(empNr);
        
        if (emp != null)
        {
            emp.setName(fName, lName);
            emp.setAddress(adr);
            emp.setPhoneNr(phoneNr);
            emp.setEmail(mail);
            emp.setSocialSecurityNr(sSecurityNr);
            emp.setPassword(employeeList.encryptPassword(passwd));
            emp.setIsAdmin(admin);
            
            empWindow.showMessage("Ansatt nr " + empNr + " har blitt endret\n\n" + emp.toString(),
                                  "Endret", JOptionPane.PLAIN_MESSAGE);
            
            employeeShowEmployee(emp);
            employeeDeleteInfoField();
        }
        else
            empWindow.showMessage("Kunne ikke endre informasjonen til den ansatte.\nMangler "
                                + "ansattnr", "Feilmelding", JOptionPane.WARNING_MESSAGE);
    } // End of method employeeChangeEmployeeData()
    
    
    
    /* Charlotte 20.04.2012
     * Når man velger "Avslutt ansettelsesforholdet" blir denne metoden kalt, 
     * den setter en sluttdato på den valgte ansatte.*/
    public void employeeSetEndDate()
    {
        int empNrIndex = employeeNr.getSelectedIndex();
        int empNr = employeeNr.getItemAt(empNrIndex);
        Employee emp = (Employee)employeeList.getEmployeeByEmpNr(empNr);
        
        if (emp != null)
        {
            String[] option = {"Ja", "Avbryt"};
            int confirm = JOptionPane.showOptionDialog(this, "Er du sikker på at du vil avslutte "
                                                     + "ansettelsesforholdet med ansattnr. "
                                                     + empNr, "Bekreftelse", 
                                                       JOptionPane.YES_NO_OPTION,
                                                       JOptionPane.QUESTION_MESSAGE, null, option, 
                                                       option[0]);
            
            if (confirm == JOptionPane.OK_OPTION)
            {
                emp.setEndDate(new Date());
            
                empWindow.showMessage("Ansettelsesforholdet for ansatt nr " + empNr 
                                    + " er avsluttet!\n\n" + emp.toString(),
                                      "Ansettelsesforhold", JOptionPane.PLAIN_MESSAGE);
            
                employeeNr.removeItemAt(empNrIndex);
                employeeDeleteInfoField();
            }
        }
        else
            empWindow.showMessage("Kunne ikke avslutte ansettelsesforholdet til den ansatte."
                                + "\nMangler ansattnr", "Feilmelding", JOptionPane.WARNING_MESSAGE);
    } // End of method employeeSetEndDate()
    
    
    
    /* Charlotte 24.04.2012
     * Metoden viser informasjonen om den ansatte (som kommer inn som parameter)
     * i tabellvisingen.*/
    public void employeeShowEmployee(Person emp)
    {
        String[] columnName = {"Ansnr", "Fornavn", "Etternavn", "Adresse", "Telefonnr", 
                               "Mail", "Ansatt dato", "Admin."};
        
        Object[][] tableData = employeeList.showEmployee(emp);
        
        table.setModel(new MyTableModel(columnName, tableData));
        table.initColumnSizes();
        table.getTableHeader().setReorderingAllowed(false);
    } // End of method employeeShowEmployee(...)
    
    
    
    /* Charlotte 24.04.2012
     * Når man velger "Vis ansatte" blir denne metoden kalt, den viser 
     * informasjonen om de nåværende ansatte i tabellvisningen.*/
    public void employeeShowEmployees()
    {
        String[] columnName = {"Ansnr", "Fornavn", "Etternavn", "Adresse", "Telefonnr", 
                               "Mail", "Ansatt dato", "Admin."};
        Object[][] tableData = employeeList.showCurrentEmployeeList();
        
        if (tableData != null)
        {
            table.setModel(new MyTableModel(columnName, tableData));
            table.setPreferredScrollableViewportSize(new Dimension(800,262));
            table.initColumnSizes();
            table.getTableHeader().setReorderingAllowed(false);
        }
        else
            empWindow.showMessage("Ingen ansatte i lista", "Merknad", 
                                  JOptionPane.INFORMATION_MESSAGE);
    } // End of method employeeShowEmployees()
    
    
    
    /* Charlotte 24.04.2012
     * Når man velger "Vis fullt ansattregister" blir denne metoden kalt, den viser 
     * informasjonen om alle som har vært ansatt i tabellvisningen.*/
    public void employeeShowAllEmployeesInList()
    {
        String[] columnName = {"Ansnr", "Fornavn", "Etternavn", "Adresse", "Telefonnr", 
                               "Mail", "Personnr", "Ansatt dato", "Slutt dato", "Admin."};
        
        Object[][] tableData = employeeList.showAllEmployeeInList();
        
        MyTable allEmployeeTable = new MyTable();
        
        allEmployeeTable.setModel(new MyTableModel(columnName, tableData));
        allEmployeeTable.setPreferredScrollableViewportSize(new Dimension(800,262));
        allEmployeeTable.initColumnSizes();
        allEmployeeTable.setAutoCreateRowSorter(true);
        allEmployeeTable.getTableHeader().setReorderingAllowed(false);
        
        MyDialogWindow window = new MyDialogWindowTable(empWindow, "Ansatt register", 
                                                        allEmployeeTable);
        window.setLocationRelativeTo(empWindow);
        window.setVisible(true);
    } // End of method employeeShowAllEmployeesInList()
    
    
    
    /* Charlotte 20.04.2012
     * Når man velger ansattnr blir denne metoden kalt, den fyller ut info feltene
     * om den ansatte med valgt ansattnr.*/
    public void employeeAutoFillInfo()
    {
        int empNrIndex = employeeNr.getSelectedIndex();
        int empNr = employeeNr.getItemAt(empNrIndex);
        Employee emp = (Employee)employeeList.getEmployeeByEmpNr(empNr);
        
        if (emp != null)
        {
            empFirstName.setText(emp.getFirstname());
            empLastName.setText(emp.getLastname());
            empAddress.setText(emp.getAddress());
            empPhoneNr.setText(emp.getPhoneNr());
            empEmail.setText(emp.getEmail()); 
            sosialSecurityNr.setText(emp.getSocialSecurityNr());
            password.setText(emp.getPassword());
            isAdmin.setSelected(emp.getIsAdmin());
        
            employeeShowEmployee(emp);
        }
        else
            employeeDeleteInfoField();
    } // End of method employeeAutoFillInfo()
    
    
    
    /* Charlotte 20.04.2012
     * Denne hjelpemetoden sletter det som står skrevet i informasjonsfeltene i 
     * ansatttabben.*/
    private void employeeDeleteInfoField()
    {
        empFirstName.setText("");
        empLastName.setText("");
        empAddress.setText("");
        empPhoneNr.setText("");
        empEmail.setText(""); 
        sosialSecurityNr.setText("");
        password.setText("");
        isAdmin.setSelected(false);
        employeeNr.setSelectedIndex(0);
    } // End of method employeeDeleteInfoField()
    
    
    
    // Privat indre lytterklasse.
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == employeeNr)
                employeeAutoFillInfo();
            else if (e.getSource() == regEmployee)
                employeeAddEmployee();
            else if (e.getSource() == updateEmployee)
                employeeChangeEmployeeData();
            else if (e.getSource() == endEmployment)
                employeeSetEndDate();
            else if (e.getSource() == showEmployeeList)
                employeeShowEmployees();
            else if (e.getSource() == showAllEmployees)
                employeeShowAllEmployeesInList();
        }
    } // End of class Listener
} // End of class ManageEmployeeTab
