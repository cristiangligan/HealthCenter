package view;

import controller.Controller;
import util.MedicalIdDocumentFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class PatientLogInScreen extends JFrame {
    private Controller controller;
    private JPanel pnlMain = new JPanel();
    private JLabel lblLogInPatient;
    private JLabel lblMedicalId;
    private JTextField patMedicalIdField;
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

        lblMedicalId = new JLabel("Medical ID  (9 digits)");
        lblMedicalId.setVerticalAlignment(SwingConstants.CENTER);
        lblMedicalId.setHorizontalAlignment(SwingConstants.CENTER);
        lblMedicalId.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(lblMedicalId);

        springLayout.putConstraint(SpringLayout.NORTH, lblMedicalId, 40, SpringLayout.SOUTH, lblLogInPatient);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblMedicalId, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);

        patMedicalIdField = new JTextField(15);
        pnlMain.add(patMedicalIdField);
        springLayout.putConstraint(SpringLayout.NORTH, patMedicalIdField, 10, SpringLayout.SOUTH, lblMedicalId);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, patMedicalIdField, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);
        ((AbstractDocument) patMedicalIdField.getDocument()).setDocumentFilter(new MedicalIdDocumentFilter());

        btnBack = new JButton("Back");
        pnlMain.add(btnBack);
        springLayout.putConstraint(SpringLayout.NORTH, btnBack,30, SpringLayout.SOUTH, patMedicalIdField);
        springLayout.putConstraint(SpringLayout.WEST, btnBack, 30, SpringLayout.WEST, pnlMain);
        btnBack.addActionListener(e -> controller.handleBackFromPatientLogInScreen());

        btnRegister = new JButton("Register");
        pnlMain.add(btnRegister);
        springLayout.putConstraint(SpringLayout.NORTH, btnRegister,30, SpringLayout.SOUTH, patMedicalIdField);
        springLayout.putConstraint(SpringLayout.EAST, btnRegister, -30, SpringLayout.EAST, pnlMain);
        btnRegister.addActionListener(e -> controller.handleBtnRegisterNewPatient());

        btnLogin = new JButton("Log in");
        pnlMain.add(btnLogin);
        springLayout.putConstraint(SpringLayout.NORTH, btnLogin,30, SpringLayout.SOUTH, patMedicalIdField);
        springLayout.putConstraint(SpringLayout.WEST, btnLogin, 0, SpringLayout.WEST, patMedicalIdField);
        springLayout.putConstraint(SpringLayout.EAST, btnLogin, 0, SpringLayout.EAST, patMedicalIdField);
        btnLogin.addActionListener(e -> controller.handlePatientLogIn());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 300));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public String getPatientIdText() {
        return patMedicalIdField.getText().trim(); //Returns a string whose value is this string, with all leading and trailing white space removed.
    }

    public void clearFields() {
        patMedicalIdField.setText("");
    }
/*
    public String getPatientIdText() {
    }

 */
}
