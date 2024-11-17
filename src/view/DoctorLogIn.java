package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class DoctorLogIn extends JFrame {
    private Controller controller;
    private JPanel pnlMain = new JPanel();
    private JLabel lblLogInDoctor;
    private JLabel lblDoctorId;
    private JTextField doctorIdTextField;
    private JButton btnBack;
    private JButton btnLogin;

    public DoctorLogIn(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(pnlMain);
        SpringLayout springLayout = new SpringLayout();
        pnlMain.setLayout(springLayout);

        lblLogInDoctor = new JLabel("Doctor");
        lblLogInDoctor.setVerticalAlignment(SwingConstants.CENTER);
        lblLogInDoctor.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogInDoctor.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        lblLogInDoctor.setBackground(Color.red);
        lblLogInDoctor.setOpaque(true); //ej genomskinligt
        pnlMain.add(lblLogInDoctor);

        springLayout.putConstraint(SpringLayout.NORTH, lblLogInDoctor, 30, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblLogInDoctor, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);

        lblDoctorId = new JLabel("Doctor id");
        lblDoctorId.setVerticalAlignment(SwingConstants.CENTER);
        lblDoctorId.setHorizontalAlignment(SwingConstants.CENTER);
        lblDoctorId.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        pnlMain.add(lblDoctorId);

        springLayout.putConstraint(SpringLayout.NORTH, lblDoctorId, 40, SpringLayout.SOUTH, lblLogInDoctor);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblDoctorId, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);

        doctorIdTextField = new JTextField(15);
        pnlMain.add(doctorIdTextField);
        springLayout.putConstraint(SpringLayout.NORTH, doctorIdTextField, 10, SpringLayout.SOUTH, lblDoctorId);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, doctorIdTextField, 0, SpringLayout.HORIZONTAL_CENTER, pnlMain);

        btnBack = new JButton("Back");
        pnlMain.add(btnBack);
        springLayout.putConstraint(SpringLayout.NORTH, btnBack,20, SpringLayout.SOUTH, doctorIdTextField);
        springLayout.putConstraint(SpringLayout.WEST, btnBack, 100, SpringLayout.WEST, pnlMain);
        btnBack.addActionListener(e -> controller.handleBackFromDoctorLoggedIn());

        btnLogin = new JButton("Login");
        pnlMain.add(btnLogin);
        springLayout.putConstraint(SpringLayout.NORTH, btnLogin,20, SpringLayout.SOUTH, doctorIdTextField);
        springLayout.putConstraint(SpringLayout.WEST, btnLogin, -100, SpringLayout.EAST, pnlMain);
        btnLogin.addActionListener(e -> controller.handleDoctorLogIn());


        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public int getMedicalId() {
        return Integer.parseInt(doctorIdTextField.getText());
    }

    public void clearFields() {
        doctorIdTextField.setText("");
    }
}
