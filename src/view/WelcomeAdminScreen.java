package view;
import controller.Controller;
import javax.swing.*;
import java.awt.*;

public class WelcomeAdminScreen extends JFrame {
        private Controller controller;
        private JPanel pnlMain = new JPanel();
        private JLabel welcomeUsername;
        private JButton btnDoctors;
        private JButton btnPatients;
        private JButton btnAppointments;
        private JButton btnSpecializations;
        private JButton btnLogOut = new JButton("Log out");

        public WelcomeAdminScreen(Controller controller) {
            this.controller = controller;
            this.setTitle("Health Center");
            this.setContentPane(pnlMain);
            SpringLayout springLayout = new SpringLayout();
            pnlMain.setLayout(springLayout);

            welcomeUsername = new JLabel("Welcome [username]");
            welcomeUsername.setVerticalAlignment(SwingConstants.CENTER);
            welcomeUsername.setHorizontalAlignment(SwingConstants.CENTER);
            welcomeUsername.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            //welcomeUsername.setBackground(Color.red);
            welcomeUsername.setOpaque(true); //ej genomskinligt
            pnlMain.add(welcomeUsername);

            //layout för var texten ska hamna
            springLayout.putConstraint(SpringLayout.NORTH, welcomeUsername, 40, SpringLayout.NORTH, pnlMain);
            springLayout.putConstraint(SpringLayout.WEST, welcomeUsername, 10, SpringLayout.WEST, pnlMain);
            springLayout.putConstraint(SpringLayout.EAST, welcomeUsername, -10, SpringLayout.EAST, pnlMain);

            btnDoctors = new JButton("Doctors");
            btnDoctors.setVerticalAlignment(SwingConstants.CENTER);
            btnDoctors.setHorizontalAlignment(SwingConstants.CENTER);
            btnDoctors.setFont(new Font(Font.SERIF, Font.BOLD, 15));
            pnlMain.add(btnDoctors);
            btnDoctors.addActionListener(e -> controller.handleDoctors());

            //sätt layout för admin-knapp
            springLayout.putConstraint(SpringLayout.NORTH, btnDoctors, 120, SpringLayout.NORTH, pnlMain);
            springLayout.putConstraint(SpringLayout.WEST, btnDoctors, 150, SpringLayout.WEST, pnlMain);
            springLayout.putConstraint(SpringLayout.EAST, btnDoctors, -150, SpringLayout.EAST, pnlMain);

            btnPatients = new JButton("Patients");
            btnPatients.setVerticalAlignment(SwingConstants.CENTER);
            btnPatients.setHorizontalAlignment(SwingConstants.CENTER);
            btnPatients.setFont(new Font(Font.SERIF, Font.BOLD, 15));
            pnlMain.add(btnPatients);
            btnPatients.addActionListener(e -> controller.handlePatients());


            //sätt layout för doctor-knapp
            springLayout.putConstraint(SpringLayout.NORTH, btnPatients, 40, SpringLayout.SOUTH, btnDoctors);
            springLayout.putConstraint(SpringLayout.WEST, btnPatients, 150, SpringLayout.WEST, pnlMain);
            springLayout.putConstraint(SpringLayout.EAST, btnPatients, -150, SpringLayout.EAST, pnlMain);

            btnAppointments = new JButton("Appointments");
            btnAppointments.setVerticalAlignment(SwingConstants.CENTER);
            btnAppointments.setHorizontalAlignment(SwingConstants.CENTER);
            btnAppointments.setFont(new Font(Font.SERIF, Font.BOLD, 15));
            pnlMain.add(btnAppointments);

            //sätt layout för doctor-knapp
            springLayout.putConstraint(SpringLayout.NORTH, btnAppointments, 40, SpringLayout.SOUTH, btnPatients);
            springLayout.putConstraint(SpringLayout.WEST, btnAppointments, 150, SpringLayout.WEST, pnlMain);
            springLayout.putConstraint(SpringLayout.EAST, btnAppointments, -150, SpringLayout.EAST, pnlMain);

            btnSpecializations = new JButton("Specializations");
            btnSpecializations.setVerticalAlignment(SwingConstants.CENTER);
            btnSpecializations.setHorizontalAlignment(SwingConstants.CENTER);
            btnSpecializations.setFont(new Font(Font.SERIF, Font.BOLD, 15));
            pnlMain.add(btnSpecializations);
            btnSpecializations.addActionListener(e -> controller.handleSpecializations());

            springLayout.putConstraint(SpringLayout.NORTH, btnSpecializations, 40, SpringLayout.SOUTH, btnAppointments);
            springLayout.putConstraint(SpringLayout.WEST, btnSpecializations, 150, SpringLayout.WEST, pnlMain);
            springLayout.putConstraint(SpringLayout.EAST, btnSpecializations, -150, SpringLayout.EAST, pnlMain);

            pnlMain.add(btnLogOut);
            springLayout.putConstraint(SpringLayout.WEST, btnLogOut, 10, SpringLayout.WEST, pnlMain);
            springLayout.putConstraint(SpringLayout.NORTH, btnLogOut, 0, SpringLayout.NORTH, btnSpecializations);
            springLayout.putConstraint(SpringLayout.SOUTH, btnLogOut, 0, SpringLayout.SOUTH, btnSpecializations);
            btnLogOut.addActionListener(e -> controller.handleLogOut());

            this.pack();
            this.setVisible(true);
            this.setSize(new Dimension(600, 400));
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        public void setUsernameLabel(String username) {
            welcomeUsername.setText("Welcome admin " + username);
        }
    }
