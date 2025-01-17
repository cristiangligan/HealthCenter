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
    private final JTextArea diagnosisArea = new JTextArea();
    private final MedicalRecord medicalRecord;

    public DiagnosisScreen(MedicalRecord medicalRecord, Controller controller) {
        this.medicalRecord = medicalRecord;
        this.controller = controller;
        this.setTitle("Health Center");
        JPanel mainPnl = new JPanel();
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        JLabel diagnosisLbl = new JLabel("Diagnosis: ");
        diagnosisLbl.setFont(new Font(null, Font.BOLD, 20));
        mainPnl.add(diagnosisLbl);
        springLayout.putConstraint(SpringLayout.NORTH, diagnosisLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, diagnosisLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(diagnosisArea);
        springLayout.putConstraint(SpringLayout.SOUTH, diagnosisArea, 0, SpringLayout.SOUTH, diagnosisLbl);
        springLayout.putConstraint(SpringLayout.WEST, diagnosisArea, 5, SpringLayout.EAST, diagnosisLbl);
        springLayout.putConstraint(SpringLayout.EAST, diagnosisArea, -40, SpringLayout.WEST, doctorDateLbl);

        mainPnl.add(doctorDateLbl);
        springLayout.putConstraint(SpringLayout.EAST, doctorDateLbl, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, doctorDateLbl, 0, SpringLayout.SOUTH, diagnosisLbl);

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
        //backBtn.addActionListener(e -> controller.handleBackFromDiagnosis(medicalRecord.getPatientId()));

        JButton saveBtn = new JButton("Save");
        mainPnl.add(saveBtn);
        springLayout.putConstraint(SpringLayout.NORTH, saveBtn, 10, SpringLayout.SOUTH, descriptionArea);
        springLayout.putConstraint(SpringLayout.WEST, saveBtn, 0, SpringLayout.WEST, descriptionArea);
        //saveBtn.addActionListener(e -> controller.handleSaveNewMedicalRecord();

        mainPnl.add(costLbl);
        springLayout.putConstraint(SpringLayout.NORTH, costLbl, 0, SpringLayout.NORTH, backBtn);
        springLayout.putConstraint(SpringLayout.EAST, costLbl, 0, SpringLayout.EAST, prescriptionArea);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if(medicalRecord != null) {
            setMedicalRecord();
        }
    }

    private void setMedicalRecord() {
        Doctor doctor = controller.getDoctor(medicalRecord.getDoctorId());
        doctorDateLbl.setText(doctor.getFirstName() + " " + doctor.getLastName() + " | Date");
        costLbl.setText(doctor.getSpecialization().getCost() + "$");
        diagnosisArea.setText(medicalRecord.getDiagnosis());
        descriptionArea.setText(medicalRecord.getDescription());
        prescriptionArea.setText(medicalRecord.getPrescription());
    }

    /*private MedicalRecord getMedicalFromView() {

        String diagnosisText = diagnosisArea.getText();
        String descriptionText = descriptionArea.getText();
        String prescriptionText = prescriptionArea.getText();
    }*/
}
