package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class ScheduleScreen extends JFrame {
    private Controller controller;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("Schedule");
    private JLabel weekDoctorLbl = new JLabel("Week | Doctor");
    private JLabel mondayLbl = new JLabel("Monday");
    private JLabel tuesdayLbl = new JLabel("Tuesday");
    private JLabel wednesdayLbl = new JLabel("Wednesday");
    private JLabel thursdayLbl = new JLabel("Thursday");
    private JLabel fridayLbl = new JLabel("Friday");

    private JButton monday9Btn = new JButton("09.00-09.45");
    private JButton monday10Btn = new JButton("10.00-10.45");
    private JButton monday11Btn = new JButton("11.00-11.45");
    private JButton monday13Btn = new JButton("13.00-13.45");

    private JButton tuesday9Btn = new JButton("09.00-09.45");
    private JButton tuesday10Btn = new JButton("10.00-10.45");
    private JButton tuesday11Btn = new JButton("11.00-11.45");
    private JButton tuesday13Btn = new JButton("13.00-13.45");

    private JButton wednesday9Btn = new JButton("09.00-09.45");
    private JButton wednesday10Btn = new JButton("10.00-10.45");
    private JButton wednesday11Btn = new JButton("11.00-11.45");
    private JButton wednesday13Btn = new JButton("13.00-13.45");

    private JButton thursday9Btn = new JButton("09.00-09.45");
    private JButton thursday10Btn = new JButton("10.00-10.45");
    private JButton thursday11Btn = new JButton("11.00-11.45");
    private JButton thursday13Btn = new JButton("13.00-13.45");

    private JButton friday9Btn = new JButton("09.00-09.45");
    private JButton friday10Btn = new JButton("10.00-10.45");
    private JButton friday11Btn = new JButton("11.00-11.45");
    private JButton friday13Btn = new JButton("13.00-13.45");

    private JButton backBtn = new JButton("Back");

    public ScheduleScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 40, SpringLayout.WEST, mainPnl);

        mainPnl.add(weekDoctorLbl);
        springLayout.putConstraint(SpringLayout.EAST, weekDoctorLbl, -40, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, weekDoctorLbl, 0, SpringLayout.SOUTH, titleLbl);

        mainPnl.add(monday9Btn);
        monday9Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, monday9Btn, 100, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, monday9Btn, 10, SpringLayout.WEST, mainPnl);

        mainPnl.add(monday10Btn);
        monday10Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, monday10Btn, 10, SpringLayout.SOUTH, monday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, monday10Btn, 0, SpringLayout.WEST, monday9Btn);

        mainPnl.add(monday11Btn);
        monday11Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, monday11Btn, 10, SpringLayout.SOUTH, monday10Btn);
        springLayout.putConstraint(SpringLayout.WEST, monday11Btn, 0, SpringLayout.WEST, monday10Btn);

        mainPnl.add(monday13Btn);
        monday13Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, monday13Btn, 10, SpringLayout.SOUTH, monday11Btn);
        springLayout.putConstraint(SpringLayout.WEST, monday13Btn, 0, SpringLayout.WEST, monday11Btn);


        mainPnl.add(tuesday9Btn);
        tuesday9Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, tuesday9Btn, 0, SpringLayout.NORTH, monday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, tuesday9Btn, 10, SpringLayout.EAST, monday9Btn);

        mainPnl.add(tuesday10Btn);
        tuesday10Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, tuesday10Btn, 10, SpringLayout.SOUTH, tuesday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, tuesday10Btn, 0, SpringLayout.WEST, tuesday9Btn);

        mainPnl.add(tuesday11Btn);
        tuesday11Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, tuesday11Btn, 10, SpringLayout.SOUTH, tuesday10Btn);
        springLayout.putConstraint(SpringLayout.WEST, tuesday11Btn, 0, SpringLayout.WEST, tuesday10Btn);

        mainPnl.add(tuesday13Btn);
        tuesday13Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, tuesday13Btn, 10, SpringLayout.SOUTH, tuesday11Btn);
        springLayout.putConstraint(SpringLayout.WEST, tuesday13Btn, 0, SpringLayout.WEST, tuesday11Btn);


        mainPnl.add(wednesday9Btn);
        wednesday9Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, wednesday9Btn, 0, SpringLayout.NORTH, tuesday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, wednesday9Btn, 10, SpringLayout.EAST, tuesday9Btn);

        mainPnl.add(wednesday10Btn);
        wednesday10Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, wednesday10Btn, 10, SpringLayout.SOUTH, wednesday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, wednesday10Btn, 0, SpringLayout.WEST, wednesday9Btn);

        mainPnl.add(wednesday11Btn);
        wednesday11Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, wednesday11Btn, 10, SpringLayout.SOUTH, wednesday10Btn);
        springLayout.putConstraint(SpringLayout.WEST, wednesday11Btn, 0, SpringLayout.WEST, wednesday10Btn);

        mainPnl.add(wednesday13Btn);
        wednesday13Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, wednesday13Btn, 10, SpringLayout.SOUTH, wednesday11Btn);
        springLayout.putConstraint(SpringLayout.WEST, wednesday13Btn, 0, SpringLayout.WEST, wednesday11Btn);


        mainPnl.add(thursday9Btn);
        thursday9Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, thursday9Btn, 0, SpringLayout.NORTH, wednesday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, thursday9Btn, 10, SpringLayout.EAST, wednesday9Btn);

        mainPnl.add(thursday10Btn);
        thursday10Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, thursday10Btn, 10, SpringLayout.SOUTH, thursday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, thursday10Btn, 0, SpringLayout.WEST, thursday9Btn);

        mainPnl.add(thursday11Btn);
        thursday11Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, thursday11Btn, 10, SpringLayout.SOUTH, thursday10Btn);
        springLayout.putConstraint(SpringLayout.WEST, thursday11Btn, 0, SpringLayout.WEST, thursday10Btn);

        mainPnl.add(thursday13Btn);
        thursday13Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, thursday13Btn, 10, SpringLayout.SOUTH, thursday11Btn);
        springLayout.putConstraint(SpringLayout.WEST, thursday13Btn, 0, SpringLayout.WEST, thursday11Btn);


        mainPnl.add(friday9Btn);
        friday9Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, friday9Btn, 0, SpringLayout.NORTH, thursday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, friday9Btn, 10, SpringLayout.EAST, thursday9Btn);

        mainPnl.add(friday10Btn);
        friday10Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, friday10Btn, 10, SpringLayout.SOUTH, friday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, friday10Btn, 0, SpringLayout.WEST, friday9Btn);

        mainPnl.add(friday11Btn);
        friday11Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, friday11Btn, 10, SpringLayout.SOUTH, friday10Btn);
        springLayout.putConstraint(SpringLayout.WEST, friday11Btn, 0, SpringLayout.WEST, friday10Btn);

        mainPnl.add(friday13Btn);
        friday13Btn.setPreferredSize(new Dimension(108, 40));
        springLayout.putConstraint(SpringLayout.NORTH, friday13Btn, 10, SpringLayout.SOUTH, friday11Btn);
        springLayout.putConstraint(SpringLayout.WEST, friday13Btn, 0, SpringLayout.WEST, friday11Btn);

        mainPnl.add(mondayLbl);
        mondayLbl.setHorizontalAlignment(SwingConstants.CENTER);
        springLayout.putConstraint(SpringLayout.SOUTH, mondayLbl, -10, SpringLayout.NORTH, monday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, mondayLbl, 0, SpringLayout.WEST, monday9Btn);
        springLayout.putConstraint(SpringLayout.EAST, mondayLbl, 0, SpringLayout.EAST, monday9Btn);

        mainPnl.add(tuesdayLbl);
        tuesdayLbl.setHorizontalAlignment(SwingConstants.CENTER);
        springLayout.putConstraint(SpringLayout.SOUTH, tuesdayLbl, -10, SpringLayout.NORTH, tuesday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, tuesdayLbl, 0, SpringLayout.WEST, tuesday9Btn);
        springLayout.putConstraint(SpringLayout.EAST, tuesdayLbl, 0, SpringLayout.EAST, tuesday9Btn);

        mainPnl.add(wednesdayLbl);
        wednesdayLbl.setHorizontalAlignment(SwingConstants.CENTER);
        springLayout.putConstraint(SpringLayout.SOUTH, wednesdayLbl, -10, SpringLayout.NORTH, wednesday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, wednesdayLbl, 0, SpringLayout.WEST, wednesday9Btn);
        springLayout.putConstraint(SpringLayout.EAST, wednesdayLbl, 0, SpringLayout.EAST, wednesday9Btn);

        mainPnl.add(thursdayLbl);
        thursdayLbl.setHorizontalAlignment(SwingConstants.CENTER);
        springLayout.putConstraint(SpringLayout.SOUTH, thursdayLbl, -10, SpringLayout.NORTH, thursday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, thursdayLbl, 0, SpringLayout.WEST, thursday9Btn);
        springLayout.putConstraint(SpringLayout.EAST, thursdayLbl, 0, SpringLayout.EAST, thursday9Btn);

        mainPnl.add(fridayLbl);
        fridayLbl.setHorizontalAlignment(SwingConstants.CENTER);
        springLayout.putConstraint(SpringLayout.SOUTH, fridayLbl, -10, SpringLayout.NORTH, friday9Btn);
        springLayout.putConstraint(SpringLayout.WEST, fridayLbl, 0, SpringLayout.WEST, friday9Btn);
        springLayout.putConstraint(SpringLayout.EAST, fridayLbl, 0, SpringLayout.EAST, friday9Btn);

        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.SOUTH, backBtn, -20, SpringLayout.SOUTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, monday13Btn);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}