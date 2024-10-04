package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class LogInScreen extends JFrame {
    private Controller controller;
    private JPanel pnlMain = new JPanel();
    private JLabel lblLogInAs;
    private JButton btnAdmin;
    private JButton btnDoctor;
    private JButton btnPatient;

    public LogInScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(pnlMain);
        SpringLayout springLayout = new SpringLayout();
        pnlMain.setLayout(springLayout);

        lblLogInAs = new JLabel("Log in as:");
        lblLogInAs.setVerticalAlignment(SwingConstants.CENTER);
        lblLogInAs.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogInAs.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        lblLogInAs.setBackground(Color.red);
        lblLogInAs.setOpaque(true);
        pnlMain.add(lblLogInAs);
        springLayout.putConstraint(SpringLayout.NORTH, lblLogInAs, 40, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.WEST, lblLogInAs, 10, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, lblLogInAs, -10, SpringLayout.EAST, pnlMain);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
