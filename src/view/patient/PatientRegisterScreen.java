package view.patient;
import controller.Controller;
import util.MedicalIdDocumentFilter;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class PatientRegisterScreen extends JFrame {
    private Controller controller;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("Register new patient");
    private JLabel medicalIdLbl = new JLabel("Medical ID  (9 digits)");
    private JLabel firstNameLbl = new JLabel("First name");
    private JLabel lastNameLbl = new JLabel("Last name");
    private JLabel addressLbl = new JLabel("Address");
    private JLabel phoneLbl = new JLabel("Phone");
    private JLabel birthDateLbl = new JLabel("Birth date");
    //ska vi kanske göra om gender till JComboBox?
    private JLabel genderLbl = new JLabel("Gender");
    private JTextField medicalIdField = new JTextField();
    private JTextField firstNameField = new JTextField();
    private JTextField lastNameField = new JTextField();
    private JTextField addressField = new JTextField();
    private JTextField phoneField = new JTextField();
    private JTextField birthDateField = new JTextField();
    private JTextField genderField = new JTextField();

    private JButton backBtn = new JButton("Back");
    private JButton registerBtn = new JButton("Register");

    public PatientRegisterScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 20, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLbl, 0, SpringLayout.HORIZONTAL_CENTER, mainPnl);

        mainPnl.add(medicalIdLbl);
        springLayout.putConstraint(SpringLayout.NORTH, medicalIdLbl, 40, SpringLayout.SOUTH, titleLbl);
        springLayout.putConstraint(SpringLayout.WEST, medicalIdLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(medicalIdField);
        medicalIdField.setPreferredSize(new Dimension(400, 30));
        springLayout.putConstraint(SpringLayout.NORTH, medicalIdField, 5, SpringLayout.SOUTH, medicalIdLbl);
        springLayout.putConstraint(SpringLayout.WEST, medicalIdField, 40, SpringLayout.WEST, mainPnl);
        ((AbstractDocument) medicalIdField.getDocument()).setDocumentFilter(new MedicalIdDocumentFilter());

        mainPnl.add(firstNameLbl);
        springLayout.putConstraint(SpringLayout.NORTH, firstNameLbl, 20, SpringLayout.SOUTH, medicalIdField);
        springLayout.putConstraint(SpringLayout.WEST, firstNameLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(firstNameField);
        firstNameField.setPreferredSize(new Dimension(400, 30));
        springLayout.putConstraint(SpringLayout.NORTH, firstNameField, 5, SpringLayout.SOUTH, firstNameLbl);
        springLayout.putConstraint(SpringLayout.WEST, firstNameField, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(lastNameLbl);
        springLayout.putConstraint(SpringLayout.NORTH, lastNameLbl, 20, SpringLayout.SOUTH, firstNameField);
        springLayout.putConstraint(SpringLayout.WEST, lastNameLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(lastNameField);
        lastNameField.setPreferredSize(new Dimension(400,30));
        springLayout.putConstraint(SpringLayout.NORTH, lastNameField, 5, SpringLayout.SOUTH, lastNameLbl);
        springLayout.putConstraint(SpringLayout.WEST, lastNameField, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(addressLbl);
        springLayout.putConstraint(SpringLayout.NORTH, addressLbl, 20, SpringLayout.SOUTH, lastNameField);
        springLayout.putConstraint(SpringLayout.WEST, addressLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(addressField);
        addressField.setPreferredSize(new Dimension(400, 30));
        springLayout.putConstraint(SpringLayout.NORTH, addressField, 5, SpringLayout.SOUTH, addressLbl);
        springLayout.putConstraint(SpringLayout.WEST, addressField, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(phoneLbl);
        springLayout.putConstraint(SpringLayout.NORTH, phoneLbl, 20, SpringLayout.SOUTH, addressField);
        springLayout.putConstraint(SpringLayout.WEST, phoneLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(phoneField);
        phoneField.setPreferredSize((new Dimension(400, 30)));
        springLayout.putConstraint(SpringLayout.NORTH, phoneField, 5, SpringLayout.SOUTH, phoneLbl);
        springLayout.putConstraint(SpringLayout.WEST, phoneField, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(birthDateLbl);
        springLayout.putConstraint(SpringLayout.NORTH, birthDateLbl, 20, SpringLayout.SOUTH, phoneField);
        springLayout.putConstraint(SpringLayout.WEST, birthDateLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(birthDateField);
        birthDateField.setPreferredSize(new Dimension(400, 30));
        springLayout.putConstraint(SpringLayout.NORTH, birthDateField, 5, SpringLayout.SOUTH, birthDateLbl);
        springLayout.putConstraint(SpringLayout.WEST, birthDateField, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(genderLbl);
        springLayout.putConstraint(SpringLayout.NORTH, genderLbl, 20, SpringLayout.SOUTH, birthDateField);
        springLayout.putConstraint(SpringLayout.WEST, genderLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(genderField);
        genderField.setPreferredSize(new Dimension(400,30));
        springLayout.putConstraint(SpringLayout.NORTH, genderField, 5, SpringLayout.SOUTH, genderLbl);
        springLayout.putConstraint(SpringLayout.WEST, genderField, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(backBtn);
        backBtn.addActionListener(e -> controller.handleBackFromRegisterNewPatientScreen());

        mainPnl.add(registerBtn);
        registerBtn.addActionListener(e -> controller.handleRegisterNewPatient());

        springLayout.putConstraint(SpringLayout.SOUTH, backBtn, -20, SpringLayout.SOUTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 40, SpringLayout.WEST, mainPnl);

        springLayout.putConstraint(SpringLayout.SOUTH, registerBtn, -20, SpringLayout.SOUTH, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, registerBtn, -40, SpringLayout.EAST, mainPnl);

        this.pack();
        this.setSize(new Dimension(700, 700));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    } //end of constructor OBS! put getters outside

    public String getMedicalId() {
        return medicalIdField.getText();
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastname() {
        return lastNameField.getText();
    }

    public String getGender() {
        return genderField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }
    public String getPhone() {
        return phoneField.getText();
    }
    public String getBirthDate() {
        return birthDateField.getText();
    }
}

