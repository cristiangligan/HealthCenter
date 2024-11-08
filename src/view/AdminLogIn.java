package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class AdminLogIn extends JFrame {
        private Controller controller;
        private JPanel pnlMain = new JPanel();
        private JLabel lblLogInAdmin;
        private JLabel lblUsername;
        private JLabel lblPassword;
        private JTextField usernameField;
        private JTextField passwordField;
        private JButton btnBack;
        private JButton btnLogin;

        public AdminLogIn(Controller controller) {
                this.controller = controller;
                this.setTitle("Health Center");
                this.setContentPane(pnlMain);
                SpringLayout springLayout = new SpringLayout();
                pnlMain.setLayout(springLayout);

                lblLogInAdmin = new JLabel("Admin");
                lblLogInAdmin.setVerticalAlignment(SwingConstants.CENTER);
                lblLogInAdmin.setHorizontalAlignment(SwingConstants.CENTER);
                lblLogInAdmin.setFont(new Font(Font.SERIF, Font.BOLD, 20));
                lblLogInAdmin.setBackground(Color.red);
                lblLogInAdmin.setOpaque(true); //ej genomskinligt
                pnlMain.add(lblLogInAdmin);

                //layout för var labeln "Admin" ska hamna
                springLayout.putConstraint(SpringLayout.NORTH, lblLogInAdmin, 30, SpringLayout.NORTH, pnlMain);
                springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblLogInAdmin, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);
                //springLayout.putConstraint(SpringLayout.EAST, lblLogInAdmin, -10, SpringLayout.EAST, pnlMain);

                lblUsername = new JLabel("Username");
                lblUsername.setVerticalAlignment(SwingConstants.CENTER);
                lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
                lblUsername.setFont(new Font(Font.SERIF, Font.BOLD, 15));
                pnlMain.add(lblUsername);

                springLayout.putConstraint(SpringLayout.NORTH, lblUsername, 40, SpringLayout.SOUTH, lblLogInAdmin);
                springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblUsername, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);
                //springLayout.putConstraint(SpringLayout.EAST, lblUsername, -10, SpringLayout.EAST, pnlMain);

                usernameField = new JTextField(15);
                pnlMain.add(usernameField);
                springLayout.putConstraint(SpringLayout.NORTH, usernameField, 10, SpringLayout.SOUTH, lblUsername);
                springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameField, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);
                usernameField.setText("cristian");

                lblPassword = new JLabel("Password");
                lblPassword.setFont(new Font(Font.SERIF, Font.BOLD, 16));
                pnlMain.add(lblPassword);
                springLayout.putConstraint(SpringLayout.NORTH, lblPassword, 30, SpringLayout.SOUTH, usernameField);
                springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblPassword, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);

                passwordField = new JTextField();
                pnlMain.add(passwordField);
                springLayout.putConstraint(SpringLayout.NORTH, passwordField, 10, SpringLayout.SOUTH, lblPassword);
                springLayout.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, usernameField);
                springLayout.putConstraint(SpringLayout.EAST, passwordField, 0, SpringLayout.EAST, usernameField);
                passwordField.setText("password123");

                btnBack = new JButton("Back");
                pnlMain.add(btnBack);
                springLayout.putConstraint(SpringLayout.NORTH, btnBack,20, SpringLayout.SOUTH, passwordField);
                springLayout.putConstraint(SpringLayout.WEST, btnBack, 100, SpringLayout.WEST, pnlMain);
                btnBack.addActionListener(e -> controller.handleBackFromAdminLogIn());

                btnLogin = new JButton("Login");
                pnlMain.add(btnLogin);
                springLayout.putConstraint(SpringLayout.NORTH, btnLogin,20, SpringLayout.SOUTH, passwordField);
                springLayout.putConstraint(SpringLayout.WEST, btnLogin, -100, SpringLayout.EAST, pnlMain);
                btnLogin.addActionListener(e -> controller.handleAdminLogIn());

                this.pack();
                this.setVisible(true);
                this.setSize(new Dimension(600, 400));
                this.setResizable(false);
                this.setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        }

        public String getUsername() {
                return usernameField.getText();
        }

        public String getPassword() {
                return passwordField.getText();
        }

        public void clearFields() {
                usernameField.setText("");
                passwordField.setText("");
        }
}
