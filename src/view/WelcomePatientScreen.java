package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class WelcomePatientScreen extends JFrame {
    private Controller controller;
    private JPanel pnlMain = new JPanel();
    private JLabel welcomePatient;
    private JButton btnMyInfo;
    private JButton btnBookAnAppointment;
    private JButton btnViewMedicalRecord;
    private JButton logOut;

    public WelcomePatientScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
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

        btnMyInfo = new JButton("My info");
        btnMyInfo.setVerticalAlignment(SwingConstants.CENTER);
        btnMyInfo.setHorizontalAlignment(SwingConstants.CENTER);
        btnMyInfo.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(btnMyInfo);

        //sätt layout för admin-knapp
        springLayout.putConstraint(SpringLayout.NORTH, btnMyInfo, 120, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.WEST, btnMyInfo, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnMyInfo, -150, SpringLayout.EAST, pnlMain);

        btnBookAnAppointment = new JButton("Book an appointment");
        btnBookAnAppointment.setVerticalAlignment(SwingConstants.CENTER);
        btnBookAnAppointment.setHorizontalAlignment(SwingConstants.CENTER);
        btnBookAnAppointment.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(btnBookAnAppointment);

        //sätt layout för doctor-knapp
        springLayout.putConstraint(SpringLayout.NORTH, btnBookAnAppointment, 40, SpringLayout.SOUTH, btnMyInfo);
        springLayout.putConstraint(SpringLayout.WEST, btnBookAnAppointment, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnBookAnAppointment, -150, SpringLayout.EAST, pnlMain);

        btnViewMedicalRecord = new JButton("View medical records");
        btnViewMedicalRecord.setVerticalAlignment(SwingConstants.CENTER);
        btnViewMedicalRecord.setHorizontalAlignment(SwingConstants.CENTER);
        btnViewMedicalRecord.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(btnViewMedicalRecord);

        //sätt layout för doctor-knapp
        springLayout.putConstraint(SpringLayout.NORTH, btnViewMedicalRecord, 40, SpringLayout.SOUTH, btnBookAnAppointment);
        springLayout.putConstraint(SpringLayout.WEST, btnViewMedicalRecord, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnViewMedicalRecord, -150, SpringLayout.EAST, pnlMain);

        logOut = new JButton("Log out");
        logOut.setVerticalAlignment(SwingConstants.CENTER);
        logOut.setHorizontalAlignment(SwingConstants.CENTER);
        logOut.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(logOut);

        springLayout.putConstraint(SpringLayout.NORTH, logOut, 40, SpringLayout.SOUTH, btnViewMedicalRecord);
        springLayout.putConstraint(SpringLayout.WEST, logOut, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, logOut, -150, SpringLayout.EAST, pnlMain);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}