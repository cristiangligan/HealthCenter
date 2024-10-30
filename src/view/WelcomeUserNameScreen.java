package view;
import controller.Controller;
import javax.swing.*;
import java.awt.*;

public class WelcomeUserNameScreen extends JFrame {
        private Controller controller;
        private JPanel pnlMain = new JPanel();
        private JLabel welcomeUsername;
        private JButton btnDoctors;
        private JButton btnPatients;
        private JButton btnAppointments;
        private JButton logOut;

        public WelcomeUserNameScreen(Controller controller) {
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
            //btnAdmin.setOpaque(true);
            pnlMain.add(btnDoctors);

            //sätt layout för admin-knapp
            springLayout.putConstraint(SpringLayout.NORTH, btnDoctors, 120, SpringLayout.NORTH, pnlMain);
            springLayout.putConstraint(SpringLayout.WEST, btnDoctors, 150, SpringLayout.WEST, pnlMain);
            springLayout.putConstraint(SpringLayout.EAST, btnDoctors, -150, SpringLayout.EAST, pnlMain);

            btnPatients = new JButton("Patients");
            btnPatients.setVerticalAlignment(SwingConstants.CENTER);
            btnPatients.setHorizontalAlignment(SwingConstants.CENTER);
            btnPatients.setFont(new Font(Font.SERIF, Font.BOLD, 15));
            pnlMain.add(btnPatients);

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

            logOut = new JButton("Log out");
            logOut.setVerticalAlignment(SwingConstants.CENTER);
            logOut.setHorizontalAlignment(SwingConstants.CENTER);
            logOut.setFont(new Font(Font.SERIF, Font.BOLD, 15));
            pnlMain.add(logOut);

            springLayout.putConstraint(SpringLayout.NORTH, logOut, 40, SpringLayout.SOUTH, btnAppointments);
            springLayout.putConstraint(SpringLayout.WEST, logOut, 150, SpringLayout.WEST, pnlMain);
            springLayout.putConstraint(SpringLayout.EAST, logOut, -150, SpringLayout.EAST, pnlMain);


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