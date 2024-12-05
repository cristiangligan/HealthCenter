package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class AddSpecializationScreen extends JFrame {
    private Controller controller;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("Specialization");
    private JLabel specializationNameLbl = new JLabel("Name of specialization");
    private JLabel costLbl = new JLabel("Cost");

    private JTextField specializationNameField = new JTextField();
    private JTextField costField = new JTextField();

    private JButton cancelBtn = new JButton("Cancel");
    private JButton saveBtn = new JButton("Save");

    public AddSpecializationScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(specializationNameField);
        specializationNameField.setPreferredSize(new Dimension(0, 40));
        springLayout.putConstraint(SpringLayout.NORTH, specializationNameField, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, specializationNameField, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, specializationNameField, -310, SpringLayout.EAST, mainPnl);


        mainPnl.add(costField);
        costField.setPreferredSize(new Dimension(0, 40));
        springLayout.putConstraint(SpringLayout.NORTH, costField, 40, SpringLayout.SOUTH, specializationNameField);
        springLayout.putConstraint(SpringLayout.WEST, costField, 0, SpringLayout.WEST, specializationNameField);
        springLayout.putConstraint(SpringLayout.EAST, costField, 0, SpringLayout.EAST, specializationNameField);

        mainPnl.add(specializationNameLbl);
        springLayout.putConstraint(SpringLayout.SOUTH, specializationNameLbl, -5, SpringLayout.NORTH, specializationNameField);
        springLayout.putConstraint(SpringLayout.WEST, specializationNameLbl, 0, SpringLayout.WEST, specializationNameField);

        mainPnl.add(costLbl);
        springLayout.putConstraint(SpringLayout.SOUTH, costLbl, -5, SpringLayout.NORTH, costField);
        springLayout.putConstraint(SpringLayout.WEST, costLbl, 0, SpringLayout.WEST, costField);

        mainPnl.add(cancelBtn);
        springLayout.putConstraint(SpringLayout.WEST, cancelBtn, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, cancelBtn, -20, SpringLayout.SOUTH, mainPnl);
        cancelBtn.addActionListener(e -> controller.handleCancelAddSpecialization());

        mainPnl.add(saveBtn);
        springLayout.putConstraint(SpringLayout.NORTH, saveBtn, 0, SpringLayout.NORTH, cancelBtn);
        springLayout.putConstraint(SpringLayout.EAST, saveBtn, -40, SpringLayout.EAST, mainPnl);
        saveBtn.addActionListener(e -> controller.handleSaveNewSpecialization());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String getSpecializationName() {
        return specializationNameField.getText();
    }

    public String getCost() {
        return costField.getText();
    }
}
