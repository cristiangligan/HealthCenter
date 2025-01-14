package view;

import controller.Controller;
import model.Doctor;
import model.MedicalRecord;

import javax.swing.*;
import java.awt.*;

public class DiagnosisScreen extends JFrame {
    private final Controller controller;
    private final JLabel doctorDateLbl = new JLabel("Doctor | Date");
    private final JLabel costLbl = new JLabel("Cost: 000$");
    private final JTextArea descriptionArea = new JTextArea();
    private final JTextArea prescriptionArea = new JTextArea();
    private final MedicalRecord medicalRecord;

    public DiagnosisScreen(MedicalRecord medicalRecord, Controller controller) {
        this.medicalRecord = medicalRecord;
        this.controller = controller;
        this.setTitle("Health Center");
        JPanel mainPnl = new JPanel();
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        JLabel titleLbl = new JLabel("Diagnosis");
        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(doctorDateLbl);
        springLayout.putConstraint(SpringLayout.EAST, doctorDateLbl, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, doctorDateLbl, 0, SpringLayout.SOUTH, titleLbl);

        mainPnl.add(descriptionArea);
        springLayout.putConstraint(SpringLayout.NORTH, descriptionArea, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, descriptionArea, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, descriptionArea, -310, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, descriptionArea, -60, SpringLayout.SOUTH, mainPnl);

        mainPnl.add(prescriptionArea);
        springLayout.putConstraint(SpringLayout.NORTH, prescriptionArea, 0, SpringLayout.NORTH, descriptionArea);
        springLayout.putConstraint(SpringLayout.WEST, prescriptionArea, 20, SpringLayout.EAST, descriptionArea);
        springLayout.putConstraint(SpringLayout.EAST, prescriptionArea, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, prescriptionArea, 0, SpringLayout.SOUTH, descriptionArea);

        JLabel descriptionLbl = new JLabel("Description");
        mainPnl.add(descriptionLbl);
        springLayout.putConstraint(SpringLayout.SOUTH, descriptionLbl, -10, SpringLayout.NORTH, descriptionArea);
        springLayout.putConstraint(SpringLayout.WEST, descriptionLbl, 0, SpringLayout.WEST, descriptionArea);

        JLabel prescriptionLbl = new JLabel("Prescription");
        mainPnl.add(prescriptionLbl);
        springLayout.putConstraint(SpringLayout.SOUTH, prescriptionLbl, -10, SpringLayout.NORTH, prescriptionArea);
        springLayout.putConstraint(SpringLayout.WEST, prescriptionLbl, 0, SpringLayout.WEST, prescriptionArea);

        JButton backBtn = new JButton("Back");
        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 10, SpringLayout.SOUTH, descriptionArea);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, descriptionArea);
        backBtn.addActionListener(e -> controller.handleBackFromDiagnosis(medicalRecord.getPatientId()));

        mainPnl.add(costLbl);
        springLayout.putConstraint(SpringLayout.NORTH, costLbl, 0, SpringLayout.NORTH, backBtn);
        springLayout.putConstraint(SpringLayout.EAST, costLbl, 0, SpringLayout.EAST, prescriptionArea);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setMedicalRecord();
    }

    private void setMedicalRecord() {
        Doctor doctor = controller.getDoctor(medicalRecord.getDoctorId());
        doctorDateLbl.setText(doctor.getFirstName() + " " + doctor.getLastName() + " | Date");
        //costLbl.setText("");
        descriptionArea.setText(medicalRecord.getDescription());
        prescriptionArea.setText(medicalRecord.getPrescription());
    }
}
