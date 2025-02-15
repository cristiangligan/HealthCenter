package view.doctor;

import controller.Controller;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

public class DoctorPatientsScreen extends JFrame {
    private final Controller controller;
    private final JList patientsLst = new JList();

    public DoctorPatientsScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        JPanel mainPnl = new JPanel();
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        JLabel titleLbl = new JLabel("Patients");
        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 40, SpringLayout.WEST, mainPnl);

        JScrollPane scrollPane = new JScrollPane(patientsLst);
        mainPnl.add(scrollPane);
        patientsLst.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewMedicalRecords();
                }
            }
        });
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -60, SpringLayout.SOUTH, mainPnl);

        JButton backBtn = new JButton("Back");
        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 10, SpringLayout.SOUTH, scrollPane);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, scrollPane);
        //back from patient screen to welcome username
        backBtn.addActionListener(e -> controller.handleBackFromPatientScreenInDoctor());

        JButton viewMedicalRecordsBtn = new JButton("View medical records");
        mainPnl.add(viewMedicalRecordsBtn);
        springLayout.putConstraint(SpringLayout.NORTH, viewMedicalRecordsBtn, 10, SpringLayout.SOUTH, scrollPane);
        springLayout.putConstraint(SpringLayout.EAST, viewMedicalRecordsBtn, 0, SpringLayout.EAST, scrollPane);
        viewMedicalRecordsBtn.addActionListener(e -> viewMedicalRecords());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void displayPatients(ArrayList<Patient> patients) {
        patientsLst.setListData(patients.toArray());
    }

    private void viewMedicalRecords() {
        Patient patient = getSelectedPatient();
        if (patient != null) {
            controller.handleViewMedicalRecords(patient);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a patient.", "No selection", WARNING_MESSAGE);
        }
    }

    private Patient getSelectedPatient() {
        Patient patient = null;
        int selectedIndex = patientsLst.getSelectedIndex();
        if (selectedIndex != -1) {
            patient = (Patient) patientsLst.getSelectedValue();
        }
        return patient;
    }
}

