package view;

import controller.Controller;
import model.Specialization;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class SpecializationsScreen extends JFrame {
    private Controller controller;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("Specializations");
    private JButton addBtn = new JButton("Add");
    private JButton editBtn = new JButton("Edit");
    private JButton deleteBtn = new JButton("Delete");
    private JButton backBtn = new JButton("Back");
    private JList specializationLst = new JList();

    public SpecializationsScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(specializationLst);
        springLayout.putConstraint(SpringLayout.NORTH, specializationLst, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, specializationLst, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, specializationLst, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, specializationLst, -60, SpringLayout.SOUTH, mainPnl);

        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 10, SpringLayout.SOUTH, specializationLst);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, specializationLst);
        backBtn.addActionListener(e -> controller.handleBackFromSpecializationsScreen());

        mainPnl.add(addBtn);
        springLayout.putConstraint(SpringLayout.SOUTH, addBtn, -10, SpringLayout.NORTH, specializationLst);
        springLayout.putConstraint(SpringLayout.WEST, addBtn, 0, SpringLayout.WEST, specializationLst);
        addBtn.addActionListener(e -> controller.handleAddNewSpecialization());

        mainPnl.add(deleteBtn);
        springLayout.putConstraint(SpringLayout.SOUTH, deleteBtn, -10, SpringLayout.NORTH, specializationLst);
        springLayout.putConstraint(SpringLayout.EAST, deleteBtn, 0, SpringLayout.EAST, specializationLst);

        mainPnl.add(editBtn);
        springLayout.putConstraint(SpringLayout.SOUTH, editBtn, 0, SpringLayout.SOUTH, addBtn);
        springLayout.putConstraint(SpringLayout.WEST, editBtn, 140, SpringLayout.EAST, addBtn);
        editBtn.addActionListener(e -> controller.handleEditSpecialization());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void displaySpecializations(List<Specialization> specializations) {
        specializationLst.setListData(specializations.toArray());
    }
}
