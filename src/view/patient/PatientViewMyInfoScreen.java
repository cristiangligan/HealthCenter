package view.patient;

import controller.Controller;
import model.Patient;

import javax.swing.*;
import java.awt.*;

public class PatientViewMyInfoScreen extends JFrame {

    private Controller controller;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("My info");
    private JLabel firstNameLbl = new JLabel("First name");
    private JLabel lastNameLbl = new JLabel("Last name");
    private JLabel addressLbl = new JLabel("Address");
    private JLabel phoneLbl = new JLabel("Phone");
    private JLabel birthDateLbl = new JLabel("Birth date");
    private JLabel genderLbl = new JLabel("Gender");
    //obs medical number och registration date ska vara fixed - ska ej gå att ändra!
    private JLabel medicalIdLbl = new JLabel("Medical ID");
    private JLabel registerDateLbl = new JLabel("Register date");


    private JTextField firstNameField = new JTextField();
    private JTextField lastNameField = new JTextField();
    private JTextField addressField = new JTextField();
    private JTextField phoneField = new JTextField();
    private JTextField birthDateField = new JTextField();
    private JTextField genderField = new JTextField();
    private JTextField medicalIdField = new JTextField();
    private JTextField registerDateField = new JTextField();

    private JButton backBtn = new JButton("Back");
    private JButton editBtn = new JButton("Edit info");

    public PatientViewMyInfoScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 20, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 20, SpringLayout.NORTH, mainPnl);

        mainPnl.add(firstNameField);
        springLayout.putConstraint(SpringLayout.EAST, firstNameField, -320, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, firstNameField, 20, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.NORTH, firstNameField, 40, SpringLayout.SOUTH, titleLbl);
        //firstNameField.setText();
        firstNameField.setEditable(false);

        mainPnl.add(lastNameField);
        springLayout.putConstraint(SpringLayout.NORTH, lastNameField, 40, SpringLayout.SOUTH, firstNameField);
        springLayout.putConstraint(SpringLayout.WEST, lastNameField, 0, SpringLayout.WEST, firstNameField);
        springLayout.putConstraint(SpringLayout.EAST, lastNameField, 0, SpringLayout.EAST, firstNameField);
        lastNameField.setEditable(false);

        mainPnl.add(addressField);
        springLayout.putConstraint(SpringLayout.NORTH, addressField, 40, SpringLayout.SOUTH, lastNameField);
        springLayout.putConstraint(SpringLayout.WEST, addressField, 0, SpringLayout.WEST, lastNameField);
        springLayout.putConstraint(SpringLayout.EAST, addressField, 0, SpringLayout.EAST, lastNameField);
        addressField.setEditable(false);

        mainPnl.add(birthDateField);
        springLayout.putConstraint(SpringLayout.NORTH, birthDateField, 40, SpringLayout.SOUTH, addressField);
        springLayout.putConstraint(SpringLayout.WEST, birthDateField, 0, SpringLayout.WEST, addressField);
        springLayout.putConstraint(SpringLayout.EAST, birthDateField, 0, SpringLayout.EAST, addressField);
        birthDateField.setEditable(false);

        mainPnl.add(phoneField);
        springLayout.putConstraint(SpringLayout.NORTH, phoneField, 40, SpringLayout.SOUTH, birthDateField);
        springLayout.putConstraint(SpringLayout.WEST, phoneField, 0, SpringLayout.WEST, birthDateField);
        springLayout.putConstraint(SpringLayout.EAST, phoneField, 0, SpringLayout.EAST, birthDateField);
        phoneField.setEditable(false);

        mainPnl.add(genderField);
        springLayout.putConstraint(SpringLayout.NORTH, genderField, 40, SpringLayout.SOUTH, phoneField);
        springLayout.putConstraint(SpringLayout.WEST, genderField, 0, SpringLayout.WEST, phoneField);
        springLayout.putConstraint(SpringLayout.EAST, genderField, 0, SpringLayout.EAST, phoneField);
        genderField.setEditable(false);

        mainPnl.add(medicalIdField);
        springLayout.putConstraint(SpringLayout.WEST, medicalIdField, 50, SpringLayout.EAST, firstNameField);
        springLayout.putConstraint(SpringLayout.EAST, medicalIdField, -20, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.NORTH, medicalIdField, 0, SpringLayout.NORTH, firstNameField);
        medicalIdField.setEditable(false);

        mainPnl.add(registerDateField);
        springLayout.putConstraint(SpringLayout.WEST, registerDateField, 0, SpringLayout.WEST, medicalIdField);
        springLayout.putConstraint(SpringLayout.EAST, registerDateField, 0, SpringLayout.EAST, medicalIdField);
        springLayout.putConstraint(SpringLayout.NORTH, registerDateField, 40, SpringLayout.SOUTH, medicalIdField);
        registerDateField.setEditable(false);

        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, titleLbl);
        springLayout.putConstraint(SpringLayout.SOUTH, backBtn, -20, SpringLayout.SOUTH, mainPnl);
        backBtn.addActionListener(e -> controller.handleBackFromMyInfo());

        mainPnl.add(editBtn);
        springLayout.putConstraint(SpringLayout.EAST, editBtn, -20, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, editBtn, -20, SpringLayout.SOUTH, mainPnl);
        editBtn.addActionListener(e -> controller.handleEditMyInfoButton());

        mainPnl.add(firstNameLbl);
        springLayout.putConstraint(SpringLayout.WEST, firstNameLbl, 0, SpringLayout.WEST, firstNameField);
        springLayout.putConstraint(SpringLayout.SOUTH, firstNameLbl, 0, SpringLayout.NORTH, firstNameField);

        mainPnl.add(lastNameLbl);
        springLayout.putConstraint(SpringLayout.WEST, lastNameLbl, 0, SpringLayout.WEST, lastNameField);
        springLayout.putConstraint(SpringLayout.SOUTH, lastNameLbl, 0, SpringLayout.NORTH, lastNameField);

        mainPnl.add(addressLbl);
        springLayout.putConstraint(SpringLayout.WEST, addressLbl, 0, SpringLayout.WEST, addressField);
        springLayout.putConstraint(SpringLayout.SOUTH, addressLbl, 0, SpringLayout.NORTH, addressField);

        mainPnl.add(phoneLbl);
        springLayout.putConstraint(SpringLayout.WEST, phoneLbl, 0, SpringLayout.WEST, phoneField);
        springLayout.putConstraint(SpringLayout.SOUTH, phoneLbl, 0, SpringLayout.NORTH, phoneField);

        mainPnl.add(birthDateLbl);
        springLayout.putConstraint(SpringLayout.WEST, birthDateLbl, 0, SpringLayout.WEST, birthDateField);
        springLayout.putConstraint(SpringLayout.SOUTH, birthDateLbl, 0, SpringLayout.NORTH, birthDateField);

        mainPnl.add(genderLbl);
        springLayout.putConstraint(SpringLayout.WEST, genderLbl, 0, SpringLayout.WEST, genderField);
        springLayout.putConstraint(SpringLayout.SOUTH, genderLbl, 0, SpringLayout.NORTH, genderField);

        mainPnl.add(medicalIdLbl);
        springLayout.putConstraint(SpringLayout.WEST, medicalIdLbl, 0, SpringLayout.WEST, medicalIdField);
        springLayout.putConstraint(SpringLayout.SOUTH, medicalIdLbl, 0, SpringLayout.NORTH, medicalIdField);

        mainPnl.add(registerDateLbl);
        springLayout.putConstraint(SpringLayout.WEST, registerDateLbl, 0, SpringLayout.WEST, registerDateField);
        springLayout.putConstraint(SpringLayout.SOUTH, registerDateLbl, 0, SpringLayout.NORTH, registerDateField);


        this.pack();
        this.setSize(new Dimension(600, 700));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void displayMyInfoPatient(Patient patient) {
        medicalIdField.setText(Integer.toString(patient.getMedicalId()));
        firstNameField.setText(patient.getFirstName());
        lastNameField.setText(patient.getLastName());
        addressField.setText(patient.getAddress());
        phoneField.setText(patient.getPhone());
        birthDateField.setText(patient.getBirthDate());
        genderField.setText(patient.getGender());
        registerDateField.setText(patient.getRegistrationDate());
        medicalIdField.setText(Integer.toString(patient.getMedicalId()));
    }
}
