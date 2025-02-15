package view;

import controller.Controller;
import model.MedicalRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

public class MedicalRecordsScreen extends JFrame {
    private final Controller controller;
    private final JLabel titleLbl = new JLabel("Medical records");
    private final JList medicalRecordsLst = new JList();

    public MedicalRecordsScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        JPanel mainPnl = new JPanel();
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 40, SpringLayout.WEST, mainPnl);

        JScrollPane scrollPane = new JScrollPane(medicalRecordsLst);
        mainPnl.add(scrollPane);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -60, SpringLayout.SOUTH, mainPnl);

        JButton backBtn = new JButton("Back");
        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 10, SpringLayout.SOUTH, scrollPane);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, scrollPane);
        backBtn.addActionListener(e -> controller.handleBackFromMedicalRecords());

        JButton viewBtn = new JButton("View");
        mainPnl.add(viewBtn);
        springLayout.putConstraint(SpringLayout.NORTH, viewBtn, 10, SpringLayout.SOUTH, scrollPane);
        springLayout.putConstraint(SpringLayout.EAST, viewBtn, 0, SpringLayout.EAST, scrollPane);
        viewBtn.addActionListener(e -> viewDiagnosis());

        JButton addMedicalRecordBtn = new JButton("Add");
        mainPnl.add(addMedicalRecordBtn);
        springLayout.putConstraint(SpringLayout.SOUTH, addMedicalRecordBtn, -10, SpringLayout.NORTH, scrollPane);
        springLayout.putConstraint(SpringLayout.EAST, addMedicalRecordBtn, 0, SpringLayout.EAST, scrollPane);
        addMedicalRecordBtn.addActionListener(e -> controller.handleAddMedicalRecord());

        if (!controller.isCurrentUserDoctor()) {
            addMedicalRecordBtn.setVisible(false);
        }
        if (controller.isCurrentUserAdmin()) {
            viewBtn.setVisible(false);
        } else {
            medicalRecordsLst.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        viewDiagnosis();
                    }
                }
            });
        }

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void displayMedicalRecords(ArrayList<MedicalRecord> medicalRecords) {
        medicalRecordsLst.setListData(medicalRecords.toArray());
    }

    private void viewDiagnosis() {
        MedicalRecord medicalRecord = getSelectedMedicalRecord();
        if (medicalRecord != null) {
            controller.handleViewDiagnosis(medicalRecord);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a patient.", "No selection", WARNING_MESSAGE);
        }
    }

    private MedicalRecord getSelectedMedicalRecord() {
        MedicalRecord medicalRecord = null;
        int selectedIndex = medicalRecordsLst.getSelectedIndex();
        if (selectedIndex != -1) {
            medicalRecord = (MedicalRecord) medicalRecordsLst.getSelectedValue();
        }
        return medicalRecord;
    }

    public void setTitleLabel(String patientName) {
        titleLbl.setText(patientName);
    }
}
