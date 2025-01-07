package view;

import controller.Controller;
import model.Doctor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ChooseBookDoctorScreen extends JFrame {
    private Controller controller;
    private JPanel mainPnl = new JPanel();
    private JLabel titleLbl = new JLabel("Choose doctor to book");
    private JButton backBtn = new JButton("Back");
    private JButton bookTimeBtn = new JButton("Book a time");
    private JList doctorsLst = new JList();
    private JComboBox<String> searchBox;
    private List<Doctor> allDoctors = new ArrayList<>(); //lagrar alla läkare

    public ChooseBookDoctorScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Health Center");
        this.setContentPane(mainPnl);
        SpringLayout springLayout = new SpringLayout();
        mainPnl.setLayout(springLayout);

        titleLbl.setFont(new Font(null, Font.PLAIN, 32));
        mainPnl.add(titleLbl);
        springLayout.putConstraint(SpringLayout.NORTH, titleLbl, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.WEST, titleLbl, 20, SpringLayout.WEST, mainPnl);

        //mainPnl.add(searchField);
        searchBox = new JComboBox<>();
        searchBox.setEditable(true);
        mainPnl.add(searchBox);
        springLayout.putConstraint(SpringLayout.NORTH, searchBox, 10, SpringLayout.NORTH, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, searchBox, -20, SpringLayout.EAST, mainPnl);
        //DocumentListner för att lyssna på textändringar
        ((JTextField) searchBox.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    filterDoctors();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    filterDoctors();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    filterDoctors();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        mainPnl.add(doctorsLst);
        springLayout.putConstraint(SpringLayout.NORTH, doctorsLst, 20, SpringLayout.SOUTH, searchBox);
        springLayout.putConstraint(SpringLayout.WEST, doctorsLst, 20, SpringLayout.WEST, mainPnl);
        springLayout.putConstraint(SpringLayout.EAST, doctorsLst, -20, SpringLayout.EAST, mainPnl);
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

        loadAllDoctors();
    }

    public void loadAllDoctors() {
        try {
            allDoctors = controller.getAllDoctors();
            displayDoctors(allDoctors);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load doctors:" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void displayDoctors(List<Doctor> doctors) {
        doctorsLst.setListData(doctors.toArray());
    }

    public Doctor getSelectedDoctor() {
        return (Doctor) doctorsLst.getSelectedValue();
    }

    public boolean isDoctorSelected() {
        return doctorsLst.getSelectedIndex() != -1;
    }

    public void filterDoctors() throws SQLException {
        //schemalägger en uppgift som ska köras i EDT (event dispatch thread) vid ett senare tillfälle.
        SwingUtilities.invokeLater(() -> {
            try {
                String searchTerm = ((JTextField) searchBox.getEditor().getEditorComponent()).getText().toLowerCase();
                List<Doctor> allDoctors = controller.getAllDoctors(); //hämta alla läkare
                Vector<String> filteredDoctors = new Vector<>();

                for (Doctor doctor : allDoctors) {
                    String doctorInfo = doctor.getFirstName() + " " + doctor.getLastName() + " " + (doctor.getSpecialization() + ")");
                    if (doctorInfo.toLowerCase().contains(searchTerm)) {
                        filteredDoctors.add(doctorInfo);
                    }
                }
                searchBox.setModel(new DefaultComboBoxModel<>(filteredDoctors));

                if (!filteredDoctors.isEmpty()) {
                    searchBox.showPopup();
                } else {
                    searchBox.hidePopup();
                }
                ((JTextField) searchBox.getEditor().getEditorComponent()).setText(searchTerm);
                ((JTextField) searchBox.getEditor().getEditorComponent()).setCaretPosition(searchTerm.length());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}