package view;

import controller.Controller;
import util.MedicalIdDocumentFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class PatientLogInScreen extends JFrame {
    private final Controller controller;
    private final JTextField patMedicalIdField;

    public PatientLogInScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        JPanel pnlMain = new JPanel();
        this.setContentPane(pnlMain);
        SpringLayout springLayout = new SpringLayout();
        pnlMain.setLayout(springLayout);

        JLabel lblLogInPatient = new JLabel("Patient");
        lblLogInPatient.setVerticalAlignment(SwingConstants.CENTER);
        lblLogInPatient.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogInPatient.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        lblLogInPatient.setOpaque(true); //ej genomskinligt
        pnlMain.add(lblLogInPatient);

        springLayout.putConstraint(SpringLayout.NORTH, lblLogInPatient, 30, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblLogInPatient, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);

        JLabel lblMedicalId = new JLabel("Medical ID  (9 digits)");
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

        JButton btnBack = new JButton("Back");
        pnlMain.add(btnBack);
        springLayout.putConstraint(SpringLayout.NORTH, btnBack,30, SpringLayout.SOUTH, patMedicalIdField);
        springLayout.putConstraint(SpringLayout.WEST, btnBack, 30, SpringLayout.WEST, pnlMain);
        btnBack.addActionListener(e -> controller.handleBackFromPatientLogInScreen());

        JButton btnRegister = new JButton("Register");
        pnlMain.add(btnRegister);
        springLayout.putConstraint(SpringLayout.NORTH, btnRegister,30, SpringLayout.SOUTH, patMedicalIdField);
        springLayout.putConstraint(SpringLayout.EAST, btnRegister, -30, SpringLayout.EAST, pnlMain);
        btnRegister.addActionListener(e -> controller.handleBtnRegisterNewPatient());

        JButton btnLogin = new JButton("Log in");
        pnlMain.add(btnLogin);
        btnLogin.addActionListener(e -> controller.handlePatientLogIn());
        this.getRootPane().setDefaultButton(btnLogin);
        springLayout.putConstraint(SpringLayout.NORTH, btnLogin,30, SpringLayout.SOUTH, patMedicalIdField);
        springLayout.putConstraint(SpringLayout.WEST, btnLogin, 0, SpringLayout.WEST, patMedicalIdField);
        springLayout.putConstraint(SpringLayout.EAST, btnLogin, 0, SpringLayout.EAST, patMedicalIdField);

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
