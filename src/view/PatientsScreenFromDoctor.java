package view;

import controller.Controller;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PatientsScreenFromDoctor extends JFrame {
    private Controller controller;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("Patients");
    private JButton viewMedicalRecordBtn = new JButton("View medical record");
    private JButton backBtn = new JButton("Back");
    private JList patientsLst = new JList();
    //scroll-funktion för lista med patienter
    private JScrollPane scrollPane = new JScrollPane(patientsLst);

    public PatientsScreenFromDoctor(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(patientsLst);
        springLayout.putConstraint(SpringLayout.NORTH, patientsLst, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, patientsLst, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, patientsLst, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, patientsLst, -60, SpringLayout.SOUTH, mainPnl);

        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 10, SpringLayout.SOUTH, patientsLst);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, patientsLst);
        //back from patient screen to welcome username
        backBtn.addActionListener(e -> controller.handleBackFromPatientScreenInDoctor());

        mainPnl.add(viewMedicalRecordBtn);
        springLayout.putConstraint(SpringLayout.NORTH, viewMedicalRecordBtn, 10, SpringLayout.SOUTH, patientsLst);
        springLayout.putConstraint(SpringLayout.EAST, viewMedicalRecordBtn, 0, SpringLayout.EAST, patientsLst);
        viewMedicalRecordBtn.addActionListener(e -> controller.handleViewMedicalRecord());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void displayPatients(List<Patient> patients) {
        patientsLst.setListData(patients.toArray());
    }
}

