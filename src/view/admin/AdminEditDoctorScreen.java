package view.admin;

import controller.Controller;
import model.Doctor;
import model.Specialization;

import javax.swing.*;
import java.awt.*;

public class AdminEditDoctorScreen extends JFrame {
    private Controller controller;
    private Doctor doctor;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("Doctor");
    private JLabel employerNrLbl = new JLabel("Employer nr.");
    private JLabel firstNameLbl = new JLabel("First name");
    private JLabel lastNameLbl = new JLabel("Last name");
    private JLabel phoneLbl = new JLabel("Phone");
    private JLabel specializationLbl = new JLabel("Specialization");

    private JTextField employerNrField = new JTextField();
    private JTextField firstNameField = new JTextField();
    private JTextField lastNameField = new JTextField();
    private JTextField phoneField = new JTextField();
    private JComboBox<Specialization> specializationField = new JComboBox<>();

    private JButton cancelBtn = new JButton("Cancel");
    private JButton saveBtn = new JButton("Save");

    public AdminEditDoctorScreen(Controller controller, Doctor doctor) {
        this.controller = controller;
        this.doctor = doctor;
        this.setTitle("Health Center");
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(employerNrField);
        employerNrField.setPreferredSize(new Dimension(0, 40));
        springLayout.putConstraint(SpringLayout.NORTH, employerNrField, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, employerNrField, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, employerNrField, -310, SpringLayout.EAST, mainPnl);

        mainPnl.add(phoneField);
        springLayout.putConstraint(SpringLayout.NORTH, phoneField, 0, SpringLayout.NORTH, employerNrField);
        springLayout.putConstraint(SpringLayout.WEST, phoneField, 20, SpringLayout.EAST, employerNrField);
        springLayout.putConstraint(SpringLayout.EAST, phoneField, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, phoneField, 0, SpringLayout.SOUTH, employerNrField);

        mainPnl.add(firstNameField);
        firstNameField.setPreferredSize(new Dimension(0, 40));
        springLayout.putConstraint(SpringLayout.NORTH, firstNameField, 40, SpringLayout.SOUTH, employerNrField);
        springLayout.putConstraint(SpringLayout.WEST, firstNameField, 0, SpringLayout.WEST, employerNrField);
        springLayout.putConstraint(SpringLayout.EAST, firstNameField, 0, SpringLayout.EAST, employerNrField);

        mainPnl.add(specializationField);
        springLayout.putConstraint(SpringLayout.NORTH, specializationField, 0, SpringLayout.NORTH, firstNameField);
        springLayout.putConstraint(SpringLayout.WEST, specializationField, 0, SpringLayout.WEST, phoneField);
        springLayout.putConstraint(SpringLayout.EAST, specializationField, 0, SpringLayout.EAST, phoneField);
        springLayout.putConstraint(SpringLayout.SOUTH, specializationField, 0, SpringLayout.SOUTH, firstNameField);
        specializationField.setModel(new DefaultComboBoxModel<>(controller.getSpecializationArray()));
        setSpecializationSelectedDoctor(doctor.getSpecialization());
        //specializationField.setSelectedIndex(0);

        mainPnl.add(lastNameField);
        lastNameField.setPreferredSize(new Dimension(0, 40));
        springLayout.putConstraint(SpringLayout.NORTH, lastNameField, 40, SpringLayout.SOUTH, firstNameField);
        springLayout.putConstraint(SpringLayout.WEST, lastNameField, 0, SpringLayout.WEST, firstNameField);
        springLayout.putConstraint(SpringLayout.EAST, lastNameField, 0, SpringLayout.EAST, firstNameField);

        mainPnl.add(employerNrLbl);
        springLayout.putConstraint(SpringLayout.SOUTH, employerNrLbl, -5, SpringLayout.NORTH, employerNrField);
        springLayout.putConstraint(SpringLayout.WEST, employerNrLbl, 0, SpringLayout.WEST, employerNrField);

        mainPnl.add(phoneLbl);
        springLayout.putConstraint(SpringLayout.SOUTH, phoneLbl, -5, SpringLayout.NORTH, phoneField);
        springLayout.putConstraint(SpringLayout.WEST, phoneLbl, 0, SpringLayout.WEST, phoneField);

        mainPnl.add(firstNameLbl);
        springLayout.putConstraint(SpringLayout.SOUTH, firstNameLbl, -5, SpringLayout.NORTH, firstNameField);
        springLayout.putConstraint(SpringLayout.WEST, firstNameLbl, 0, SpringLayout.WEST, firstNameField);

        mainPnl.add(specializationLbl);
        springLayout.putConstraint(SpringLayout.SOUTH, specializationLbl, -5, SpringLayout.NORTH, specializationField);
        springLayout.putConstraint(SpringLayout.WEST, specializationLbl, 0, SpringLayout.WEST, specializationField);

        mainPnl.add(lastNameLbl);
        springLayout.putConstraint(SpringLayout.SOUTH, lastNameLbl, -5, SpringLayout.NORTH, lastNameField);
        springLayout.putConstraint(SpringLayout.WEST, lastNameLbl, 0, SpringLayout.WEST, lastNameField);

        mainPnl.add(cancelBtn);
        springLayout.putConstraint(SpringLayout.NORTH, cancelBtn, 10, SpringLayout.SOUTH, lastNameField);
        springLayout.putConstraint(SpringLayout.WEST, cancelBtn, 0, SpringLayout.WEST, lastNameField);
        cancelBtn.addActionListener(e -> controller.handleCancelEditDoctor());

        mainPnl.add(saveBtn);
        springLayout.putConstraint(SpringLayout.NORTH, saveBtn, 0, SpringLayout.NORTH, cancelBtn);
        springLayout.putConstraint(SpringLayout.EAST, saveBtn, 0, SpringLayout.EAST, specializationField);
        saveBtn.addActionListener(e -> controller.handleSaveEditedDoctor());

        employerNrField.setText(String.valueOf(doctor.getEmployerNr()));
        firstNameField.setText(String.valueOf(doctor.getFirstName()));
        lastNameField.setText(String.valueOf(doctor.getLastName()));
        phoneField.setText(String.valueOf(doctor.getPhone()));
        specializationField.setSelectedItem(doctor.getSpecialization());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String getEmployerNumberText() {
        return employerNrField.getText();
    }
/*
    public Doctor getDoctorInfo() {
        int employerNr = controller.getEmployerNrChecked();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNr = phoneField.getText();
        Specialization specialization = (Specialization) specializationField.getSelectedItem();
        return new Doctor(employerNr, firstName, lastName, phoneNr, specialization);
    }
 */

    public Doctor getDoctorInfo() {
        //int employerNr = Integer.parseInt(employerNrField.getText());
        int employerNr = controller.getEmployerNrChecked();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNr = phoneField.getText();
        Specialization specialization = (Specialization) specializationField.getSelectedItem();
        return new Doctor(employerNr, firstName, lastName, phoneNr, specialization);
    }

    public void displayDoctorInfo(Doctor doctor) {
        employerNrField.setText(String.valueOf(doctor.getEmployerNr()));
        firstNameField.setText(String.valueOf(doctor.getFirstName()));
        lastNameField.setText(String.valueOf(doctor.getLastName()));
        phoneField.setText(String.valueOf(doctor.getPhone()));
        specializationField.setSelectedItem(doctor.getSpecialization());
    }

    public void setSpecializationSelectedDoctor(Specialization specialization) {
        for (int i = 0; i < specializationField.getItemCount(); i++) {
            Specialization specializationFromList = specializationField.getItemAt(i);
            if (specializationFromList != null && specializationFromList.getId() == specialization.getId()) {
                specializationField.setSelectedIndex(i);
                return;
            }
        }
    }
}