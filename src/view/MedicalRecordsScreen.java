package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MedicalRecordsScreen extends JFrame {
    private Controller controller;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("Medical records");
    private JButton viewBtn = new JButton("View");
    private JButton backBtn = new JButton("Back");
    private JList medicalRecordsLst = new JList();

    public MedicalRecordsScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(medicalRecordsLst);
        springLayout.putConstraint(SpringLayout.NORTH, medicalRecordsLst, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, medicalRecordsLst, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, medicalRecordsLst, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, medicalRecordsLst, -60, SpringLayout.SOUTH, mainPnl);

        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 10, SpringLayout.SOUTH, medicalRecordsLst);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, medicalRecordsLst);

        mainPnl.add(viewBtn);
        springLayout.putConstraint(SpringLayout.NORTH, viewBtn, 10, SpringLayout.SOUTH, medicalRecordsLst);
        springLayout.putConstraint(SpringLayout.EAST, viewBtn, 0, SpringLayout.EAST, medicalRecordsLst);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
