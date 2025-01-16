package view.patient;

import controller.Controller;
import model.Doctor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PatientChooseBookDoctorScreen extends JFrame {
    private final Controller controller;
    private final JList doctorsLst = new JList();
    private final JTextField searchField = new JTextField();
    private String filterWord = "";
    private List<Doctor> doctors;

    public PatientChooseBookDoctorScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        JPanel mainPnl = new JPanel();
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        JLabel titleLbl = new JLabel("Choose doctor to book");
        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 20, SpringLayout.WEST, mainPnl);

        mainPnl.add(searchField);
        springLayout.putConstraint(SpringLayout.SOUTH, searchField, 0, SpringLayout.SOUTH, titleLbl);
        springLayout.putConstraint(SpringLayout.EAST, searchField, -20, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, searchField, 20, SpringLayout.EAST, titleLbl);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterWord = searchField.getText();
                displayFilteredDoctors();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterWord = searchField.getText();
                displayFilteredDoctors();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterWord = searchField.getText();
                displayFilteredDoctors();
            }
        });

        JScrollPane scrollPane = new JScrollPane(doctorsLst);
        mainPnl.add(scrollPane);
        doctorsLst.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    controller.handleBookATimeBtn();
                }
            }
        });
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 20, SpringLayout.SOUTH, searchField);
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, -20, SpringLayout.EAST, mainPnl);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -60, SpringLayout.SOUTH, mainPnl);

        JButton backBtn = new JButton("Back");
        mainPnl.add(backBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 10, SpringLayout.SOUTH, scrollPane);
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, scrollPane);
        backBtn.addActionListener(e -> controller.handleBackFromChooseBookDoctorScreen());

        JButton bookTimeBtn = new JButton("Book a time");
        mainPnl.add(bookTimeBtn);
        springLayout.putConstraint(SpringLayout.NORTH, bookTimeBtn, 10, SpringLayout.SOUTH, scrollPane);
        springLayout.putConstraint(SpringLayout.EAST, bookTimeBtn, 0, SpringLayout.EAST, scrollPane);
        bookTimeBtn.addActionListener(e -> controller.handleBookATimeBtn());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void displayDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
        doctorsLst.setListData(this.doctors.toArray());
    }

    public void displayFilteredDoctors() {
        List<Doctor> filteredDoctors =
            doctors
                .stream()
                .filter( doctor -> {
                    String firstName = doctor.getFirstName().toLowerCase();
                    String lastName = doctor.getLastName().toLowerCase();
                    String specializationName = doctor.getSpecialization().getName().toLowerCase();
                    String filter = filterWord.toLowerCase();
                    return firstName.contains(filter) ||
                        lastName.contains(filter) ||
                        specializationName.contains(filter);
                })
                .toList();
        doctorsLst.setListData(filteredDoctors.toArray());
    }

    public Doctor getSelectedDoctor() {
        return (Doctor) doctorsLst.getSelectedValue();
    }

    public boolean isDoctorSelected() {
        return doctorsLst.getSelectedIndex() != -1;
    }
}