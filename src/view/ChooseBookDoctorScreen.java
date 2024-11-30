package view;

import controller.Controller;
import model.Doctor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChooseBookDoctorScreen extends JFrame {
    private Controller controller;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("Choose doctor to book");
    private JButton backBtn = new JButton("Back");
    private JButton bookTimeBtn = new JButton("Book a time");
    private JList doctorsLst = new JList();

    public ChooseBookDoctorScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(doctorsLst);
        springLayout.putConstraint(SpringLayout.NORTH, doctorsLst, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, doctorsLst, 40, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, doctorsLst, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, doctorsLst, -60, SpringLayout.SOUTH, mainPnl);

        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 10, SpringLayout.SOUTH, doctorsLst);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, doctorsLst);
        backBtn.addActionListener(e -> controller.handleBackFromChooseBookDoctorScreen());

        mainPnl.add(bookTimeBtn);
        springLayout.putConstraint(SpringLayout.NORTH, bookTimeBtn, 10, SpringLayout.SOUTH, doctorsLst);
        springLayout.putConstraint(SpringLayout.EAST, bookTimeBtn, 0, SpringLayout.EAST, doctorsLst);
        bookTimeBtn.addActionListener(e -> controller.handleBookATimeBtn());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void displayDoctors(List<Doctor> doctors) {
        doctorsLst.setListData(doctors.toArray());
    }
}
