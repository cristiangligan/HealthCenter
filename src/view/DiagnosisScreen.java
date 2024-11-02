package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class DiagnosisScreen extends JFrame {
    private Controller controller;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("Diagnosis");
    private JLabel doctorDateLbl = new JLabel("Doctor | Date");
    private JLabel descriptionLbl = new JLabel("Description");
    private JLabel prescriptionLbl = new JLabel("Prescription");
    private JLabel costLbl = new JLabel("Cost: 000$");
    private JButton backBtn = new JButton("Back");
    private JTextArea descriptionArea = new JTextArea();
    private JTextArea prescriptionArea = new JTextArea();

    public DiagnosisScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(doctorDateLbl);
        springLayout.putConstraint(SpringLayout.EAST, doctorDateLbl, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, doctorDateLbl, 0, SpringLayout.SOUTH, titleLbl);

        mainPnl.add(descriptionArea);
        springLayout.putConstraint(SpringLayout.NORTH, descriptionArea, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, descriptionArea, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, descriptionArea, -310, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, descriptionArea, -60, SpringLayout.SOUTH, mainPnl);

        mainPnl.add(prescriptionArea);
        springLayout.putConstraint(SpringLayout.NORTH, prescriptionArea, 0, SpringLayout.NORTH, descriptionArea);
        springLayout.putConstraint(SpringLayout.WEST, prescriptionArea, 20, SpringLayout.EAST, descriptionArea);
        springLayout.putConstraint(SpringLayout.EAST, prescriptionArea, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, prescriptionArea, 0, SpringLayout.SOUTH, descriptionArea);

        mainPnl.add(descriptionLbl);
        springLayout.putConstraint(SpringLayout.SOUTH, descriptionLbl, -10, SpringLayout.NORTH, descriptionArea);
        springLayout.putConstraint(SpringLayout.WEST, descriptionLbl, 0, SpringLayout.WEST, descriptionArea);

        mainPnl.add(prescriptionLbl);
        springLayout.putConstraint(SpringLayout.SOUTH, prescriptionLbl, -10, SpringLayout.NORTH, prescriptionArea);
        springLayout.putConstraint(SpringLayout.WEST, prescriptionLbl, 0, SpringLayout.WEST, prescriptionArea);

        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 10, SpringLayout.SOUTH, descriptionArea);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, descriptionArea);
        backBtn.addActionListener(e -> controller.handleBackFromDiagnosisScreen());

        mainPnl.add(costLbl);
        springLayout.putConstraint(SpringLayout.NORTH, costLbl, 0, SpringLayout.NORTH, backBtn);
        springLayout.putConstraint(SpringLayout.EAST, costLbl, 0, SpringLayout.EAST, prescriptionArea);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
