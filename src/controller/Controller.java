package controller;

import model.Admin;
import model.LogInManager;
import view.*;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Controller {
    private Connection connection;
    private LogInScreen logInScreen;
    private AdminLogIn adminLogIn;
    private DoctorLogIn doctorLogIn;
    private PatientLogIn patientLogIn;

    private LogInManager logInManager;
    private WelcomeUserNameScreen welcomeUserNameScreen;

    /*
    private ScheduleScreen scheduleScreen;
    private ChooseBookDoctorScreen chooseBookDoctorScreen;
    private DiagnosisScreen diagnosisScreen;
    private DoctorNewEditScreen doctorNewEditScreen;
    private DoctorsScreen doctorsScreen;
    private MedicalRecordsScreen medicalRecordsScreen;
    private PatientsScreen patientsScreen;
    private UpcomingAppointmentsScreen upcomingAppointmentsScreen;
     */

    public Controller() {
        logInScreen = new LogInScreen(this);
        logInToDataBase();
    }

    public void logInToDataBase() {
        String URL = "jdbc:postgresql://pgserver.mau.se:5432/ao8732";
        String user = "am6056";
        String password = "ady9oo1j";

        try {
            connection = DriverManager.getConnection(URL, user, password);

            if (connection != null) {
                System.out.println("Connected successfully!");

            } else {
                System.out.println("Not successful!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }


    //-------- AdminLogIn - START --------
    public void handleAdminLogIn() {
        logInManager = new LogInManager(connection);
        String username = adminLogIn.getUsername();
        String password = adminLogIn.getPassword();
        Admin admin = logInManager.verifyAdmin(username, password);
        if(admin != null) {
            welcomeUserNameScreen = new WelcomeUserNameScreen(this);
            welcomeUserNameScreen.setUsernameLabel(username);
            adminLogIn.dispose();
            System.out.println(logInManager.getCurrentAdmin());
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect username or password.");
            adminLogIn.clearFields();
        }
    }

    //-------- AdminLogIn - END --------



    //-------- LogInScreen - START --------

    public void chooseAdminLogin() {
        adminLogIn = new AdminLogIn(this);
        logInScreen.dispose();
    }

    public void chooseDoctorLogin() {
        doctorLogIn = new DoctorLogIn(this);
        logInScreen.dispose();
    }

    public void choosePatientLogin() {
        patientLogIn = new PatientLogIn(this);
        logInScreen.dispose();
    }

    //-------- LogInScreen - END --------







    public static void main(String[] args) {
      Controller controller = new Controller();
    }
}