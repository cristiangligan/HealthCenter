package view;

import controller.Controller;
import model.Appointment;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DoctorMyAppointmentsScreen extends JFrame {
    private Controller controller;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("My appointments");
    private JButton viewBtn = new JButton("View medical record");
    private JButton backBtn = new JButton("Back");
    private JList appointmentsLst = new JList();

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

        mainPnl.add(appointmentsLst);
        springLayout.putConstraint(SpringLayout.NORTH, appointmentsLst, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, appointmentsLst, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, appointmentsLst, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, appointmentsLst, -60, SpringLayout.SOUTH, mainPnl);

        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 10, SpringLayout.SOUTH, appointmentsLst);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, appointmentsLst);
        backBtn.addActionListener(e -> controller.handleBackMyAppointmentsScreen());

        mainPnl.add(viewBtn);
        springLayout.putConstraint(SpringLayout.NORTH, viewBtn, 10, SpringLayout.SOUTH, appointmentsLst);
        springLayout.putConstraint(SpringLayout.EAST, viewBtn, 0, SpringLayout.EAST, appointmentsLst);

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
