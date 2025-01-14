package view;

import controller.Controller;
import model.Patient;

import javax.swing.*;
import java.awt.*;

public class WelcomePatientScreen extends JFrame {
    private final Controller controller;
    private final JLabel welcomePatient;

    public WelcomePatientScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        JPanel pnlMain = new JPanel();
        this.setContentPane(pnlMain);
        SpringLayout springLayout = new SpringLayout();
        pnlMain.setLayout(springLayout);

        welcomePatient = new JLabel("Welcome [patient]");
        welcomePatient.setVerticalAlignment(SwingConstants.CENTER);
        welcomePatient.setHorizontalAlignment(SwingConstants.CENTER);
        welcomePatient.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        welcomePatient.setOpaque(true); //ej genomskinligt
        pnlMain.add(welcomePatient);

        //layout för var texten ska hamna
        springLayout.putConstraint(SpringLayout.NORTH, welcomePatient, 40, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.WEST, welcomePatient, 10, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, welcomePatient, -10, SpringLayout.EAST, pnlMain);

        JButton btnMyInfo = new JButton("My info");
        btnMyInfo.setVerticalAlignment(SwingConstants.CENTER);
        btnMyInfo.setHorizontalAlignment(SwingConstants.CENTER);
        btnMyInfo.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(btnMyInfo);
        btnMyInfo.addActionListener(e -> controller.handleViewMyInfoPatient());

        //sätt layout för admin-knapp
        springLayout.putConstraint(SpringLayout.NORTH, btnMyInfo, 120, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.WEST, btnMyInfo, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnMyInfo, -150, SpringLayout.EAST, pnlMain);

        JButton btnBookAnAppointment = new JButton("Book an appointment");
        btnBookAnAppointment.setVerticalAlignment(SwingConstants.CENTER);
        btnBookAnAppointment.setHorizontalAlignment(SwingConstants.CENTER);
        btnBookAnAppointment.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(btnBookAnAppointment);
        btnBookAnAppointment.addActionListener(e -> controller.bookAnAppointmentPatient());

        //sätt layout för doctor-knapp
        springLayout.putConstraint(SpringLayout.NORTH, btnBookAnAppointment, 40, SpringLayout.SOUTH, btnMyInfo);
        springLayout.putConstraint(SpringLayout.WEST, btnBookAnAppointment, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnBookAnAppointment, -150, SpringLayout.EAST, pnlMain);

        JButton btnViewMedicalRecord = new JButton("View medical records");
        btnViewMedicalRecord.setVerticalAlignment(SwingConstants.CENTER);
        btnViewMedicalRecord.setHorizontalAlignment(SwingConstants.CENTER);
        btnViewMedicalRecord.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(btnViewMedicalRecord);
        btnViewMedicalRecord.addActionListener(e -> {
            Patient patient = (Patient)controller.getCurrentUser();
            controller.handleViewMedicalRecords(patient);
        });

        //sätt layout för doctor-knapp
        springLayout.putConstraint(SpringLayout.NORTH, btnViewMedicalRecord, 40, SpringLayout.SOUTH, btnBookAnAppointment);
        springLayout.putConstraint(SpringLayout.WEST, btnViewMedicalRecord, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnViewMedicalRecord, -150, SpringLayout.EAST, pnlMain);

        JButton btnLogOut = new JButton("Log out");
        btnLogOut.setVerticalAlignment(SwingConstants.CENTER);
        btnLogOut.setHorizontalAlignment(SwingConstants.CENTER);
        btnLogOut.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(btnLogOut);
        btnLogOut.addActionListener(e -> controller.logOut());

        springLayout.putConstraint(SpringLayout.NORTH, btnLogOut, 40, SpringLayout.SOUTH, btnViewMedicalRecord);
        springLayout.putConstraint(SpringLayout.WEST, btnLogOut, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnLogOut, -150, SpringLayout.EAST, pnlMain);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 480));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setWelcomePatient(String patientLabel){
        welcomePatient.setText("Welcome " + patientLabel);
    }
}