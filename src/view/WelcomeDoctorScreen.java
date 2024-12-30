package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class WelcomeDoctorScreen extends JFrame {
    private Controller controller;
    private JPanel pnlMain = new JPanel();
    private JLabel welcomeDoctor;
    private JButton btnMyPatients;
    private JButton btnMySchedule;
    private JButton btnMyAppointments;
    private JButton logOut;

    public WelcomeDoctorScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(pnlMain);
        SpringLayout springLayout = new SpringLayout();
        pnlMain.setLayout(springLayout);

        welcomeDoctor = new JLabel("Welcome [doctor]");
        welcomeDoctor.setVerticalAlignment(SwingConstants.CENTER);
        welcomeDoctor.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeDoctor.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        //welcomeUsername.setBackground(Color.red);
        welcomeDoctor.setOpaque(true); //ej genomskinligt
        pnlMain.add(welcomeDoctor);

        //layout för var texten ska hamna
        springLayout.putConstraint(SpringLayout.NORTH, welcomeDoctor, 40, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.WEST, welcomeDoctor, 10, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, welcomeDoctor, -10, SpringLayout.EAST, pnlMain);

        btnMyPatients = new JButton("My patients");
        btnMyPatients.setVerticalAlignment(SwingConstants.CENTER);
        btnMyPatients.setHorizontalAlignment(SwingConstants.CENTER);
        btnMyPatients.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        //btnAdmin.setOpaque(true);
        pnlMain.add(btnMyPatients);
        btnMyPatients.addActionListener(e -> controller.viewMyPatients());

        //sätt layout för admin-knapp
        springLayout.putConstraint(SpringLayout.NORTH, btnMyPatients, 120, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.WEST, btnMyPatients, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnMyPatients, -150, SpringLayout.EAST, pnlMain);

        btnMySchedule = new JButton("My schedule");
        btnMySchedule.setVerticalAlignment(SwingConstants.CENTER);
        btnMySchedule.setHorizontalAlignment(SwingConstants.CENTER);
        btnMySchedule.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(btnMySchedule);

        //sätt layout för doctor-knapp
        springLayout.putConstraint(SpringLayout.NORTH, btnMySchedule, 40, SpringLayout.SOUTH, btnMyPatients);
        springLayout.putConstraint(SpringLayout.WEST, btnMySchedule, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnMySchedule, -150, SpringLayout.EAST, pnlMain);

        btnMyAppointments = new JButton("My appointments");
        btnMyAppointments.setVerticalAlignment(SwingConstants.CENTER);
        btnMyAppointments.setHorizontalAlignment(SwingConstants.CENTER);
        btnMyAppointments.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(btnMyAppointments);

        //sätt layout för doctor-knapp
        springLayout.putConstraint(SpringLayout.NORTH, btnMyAppointments, 40, SpringLayout.SOUTH, btnMySchedule);
        springLayout.putConstraint(SpringLayout.WEST, btnMyAppointments, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnMyAppointments, -150, SpringLayout.EAST, pnlMain);

        logOut = new JButton("Log out");
        logOut.setVerticalAlignment(SwingConstants.CENTER);
        logOut.setHorizontalAlignment(SwingConstants.CENTER);
        logOut.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(logOut);
        logOut.addActionListener(e -> controller.logOutDoctor());

        springLayout.putConstraint(SpringLayout.NORTH, logOut, 40, SpringLayout.SOUTH, btnMyAppointments);
        springLayout.putConstraint(SpringLayout.WEST, logOut, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, logOut, -150, SpringLayout.EAST, pnlMain);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setWelcomeDoctorLabel(String doctorLabel) {
        welcomeDoctor.setText("Welcome doctor " + doctorLabel);
    }
}