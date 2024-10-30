package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class LogInScreen extends JFrame {
    private Controller controller;
    private JPanel pnlMain = new JPanel();
    private JLabel lblLogInAs;
    private JButton btnAdmin;
    private JButton btnDoctor;
    private JButton btnPatient;

    public LogInScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(pnlMain);
        SpringLayout springLayout = new SpringLayout();
        pnlMain.setLayout(springLayout);

        lblLogInAs = new JLabel("Log in as:");
        lblLogInAs.setVerticalAlignment(SwingConstants.CENTER);
        lblLogInAs.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogInAs.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        lblLogInAs.setBackground(Color.red);
        lblLogInAs.setOpaque(true); //ej genomskinligt
        pnlMain.add(lblLogInAs);

        //layout för var texten ska hamna
        springLayout.putConstraint(SpringLayout.NORTH, lblLogInAs, 40, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.WEST, lblLogInAs, 10, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, lblLogInAs, -10, SpringLayout.EAST, pnlMain);

        btnAdmin = new JButton("Admin");
        btnAdmin.setVerticalAlignment(SwingConstants.CENTER);
        btnAdmin.setHorizontalAlignment(SwingConstants.CENTER);
        btnAdmin.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        //btnAdmin.setOpaque(true);
        pnlMain.add(btnAdmin);

        //sätt layout för admin-knapp
        springLayout.putConstraint(SpringLayout.NORTH, btnAdmin, 120, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.WEST, btnAdmin, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnAdmin, -150, SpringLayout.EAST, pnlMain);

        btnDoctor = new JButton("Doctor");
        btnDoctor.setVerticalAlignment(SwingConstants.CENTER);
        btnDoctor.setHorizontalAlignment(SwingConstants.CENTER);
        btnDoctor.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(btnDoctor);

        //sätt layout för doctor-knapp
        springLayout.putConstraint(SpringLayout.NORTH, btnDoctor, 40, SpringLayout.SOUTH, btnAdmin);
        springLayout.putConstraint(SpringLayout.WEST, btnDoctor, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnDoctor, -150, SpringLayout.EAST, pnlMain);

        btnPatient = new JButton("Patient");
        btnPatient.setVerticalAlignment(SwingConstants.CENTER);
        btnPatient.setHorizontalAlignment(SwingConstants.CENTER);
        btnPatient.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(btnPatient);

        //sätt layout för doctor-knapp
        springLayout.putConstraint(SpringLayout.NORTH, btnPatient, 40, SpringLayout.SOUTH, btnDoctor);
        springLayout.putConstraint(SpringLayout.WEST, btnPatient, 150, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, btnPatient, -150, SpringLayout.EAST, pnlMain);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
