package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class PatientLogInScreen extends JFrame {
    private Controller controller;
    private JPanel pnlMain = new JPanel();
    private JLabel lblLogInPatient;
    private JLabel lblMedicalNumber;
    private JTextField patMedicalNbrTextField;
    private JButton btnBack;
    private JButton btnLogin;
    private JButton btnRegister;

    public PatientLogInScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(pnlMain);
        SpringLayout springLayout = new SpringLayout();
        pnlMain.setLayout(springLayout);

        lblLogInPatient = new JLabel("Patient");
        lblLogInPatient.setVerticalAlignment(SwingConstants.CENTER);
        lblLogInPatient.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogInPatient.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        lblLogInPatient.setBackground(Color.red);
        lblLogInPatient.setOpaque(true); //ej genomskinligt
        pnlMain.add(lblLogInPatient);

        springLayout.putConstraint(SpringLayout.NORTH, lblLogInPatient, 30, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblLogInPatient, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);

        lblMedicalNumber = new JLabel("Medical number");
        lblMedicalNumber.setVerticalAlignment(SwingConstants.CENTER);
        lblMedicalNumber.setHorizontalAlignment(SwingConstants.CENTER);
        lblMedicalNumber.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(lblMedicalNumber);

        springLayout.putConstraint(SpringLayout.NORTH, lblMedicalNumber, 40, SpringLayout.SOUTH, lblLogInPatient);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblMedicalNumber, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);

        patMedicalNbrTextField = new JTextField(15);
        pnlMain.add(patMedicalNbrTextField);
        springLayout.putConstraint(SpringLayout.NORTH, patMedicalNbrTextField, 10, SpringLayout.SOUTH, lblMedicalNumber);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, patMedicalNbrTextField, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);

        btnBack = new JButton("Back");
        pnlMain.add(btnBack);
        springLayout.putConstraint(SpringLayout.NORTH, btnBack,30, SpringLayout.SOUTH, patMedicalNbrTextField);
        springLayout.putConstraint(SpringLayout.WEST, btnBack, 100, SpringLayout.WEST, pnlMain);
        btnBack.addActionListener(e -> controller.handleBackFromPatientLogInScreen());

        btnLogin = new JButton("Log in");
        pnlMain.add(btnLogin);
        springLayout.putConstraint(SpringLayout.NORTH, btnLogin,30, SpringLayout.SOUTH, patMedicalNbrTextField);
        springLayout.putConstraint(SpringLayout.WEST, btnLogin, 0, SpringLayout.WEST, pnlMain);
        btnLogin.addActionListener(e -> controller.handlePatientLogIn());

        btnRegister = new JButton("Register");
        pnlMain.add(btnRegister);
        springLayout.putConstraint(SpringLayout.NORTH, btnRegister,30, SpringLayout.SOUTH, patMedicalNbrTextField);
        springLayout.putConstraint(SpringLayout.WEST, btnRegister, -100, SpringLayout.EAST, pnlMain);
        btnRegister.addActionListener(e -> controller.handleBtnRegisterNewPatient());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public String getPatientIdText() {
        return patMedicalNbrTextField.getText().trim(); //Returns a string whose value is this string, with all leading and trailing white space removed.
    }

    public void clearFields() {
        patMedicalNbrTextField.setText("");
    }
/*
    public String getPatientIdText() {
    }

 */
}
