package view.doctor;

import controller.Controller;
import model.Appointment;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DoctorMyAppointmentsScreen extends JFrame {
    private Controller controller;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("My appointments");
    private JButton backBtn = new JButton("Back");
    private final JList appointmentsLst = new JList();

    public DoctorMyAppointmentsScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 40, SpringLayout.WEST, mainPnl);

        JScrollPane scrollPane = new JScrollPane(appointmentsLst);
        mainPnl.add(scrollPane);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -60, SpringLayout.SOUTH, mainPnl);

        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 10, SpringLayout.SOUTH, scrollPane);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, scrollPane);
        backBtn.addActionListener(e -> controller.handleBackMyAppointmentsScreen());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void displayAppointments(ArrayList<Appointment> appointments) {
        appointmentsLst.setListData(appointments.toArray());
    }
}
