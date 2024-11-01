package controller;

import model.Admin;
import model.AdminManager;
import view.*;

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
    private AdminManager adminManager;
    private WelcomeUserNameScreen welcomeUserNameScreen;
    private DoctorsScreen doctorsScreen;
    private NewEditDoctorScreen newEditDoctorScreen;
    private SpecializationsScreen specializationsScreen;
    private NewEditSpecializationScreen newEditSpecializationScreen;

    /*
    private ScheduleScreen scheduleScreen;
    private ChooseBookDoctorScreen chooseBookDoctorScreen;
    private DiagnosisScreen diagnosisScreen;
    private MedicalRecordsScreen medicalRecordsScreen;
    private PatientsScreen patientsScreen;
    private UpcomingAppointmentsScreen upcomingAppointmentsScreen;
    private WelcomePatientScreen welcomePatientScreen;
    private RegisterNewPatientScreen registerNewPatientScreen;
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
        adminManager = new AdminManager(connection);
        String username = adminLogIn.getUsername();
        String password = adminLogIn.getPassword();
        Admin admin = adminManager.verifyAdmin(username, password);
        if(admin != null) {
            welcomeUserNameScreen = new WelcomeUserNameScreen(this);
            welcomeUserNameScreen.setUsernameLabel(username);
            adminLogIn.dispose();
            System.out.println(adminManager.getCurrentAdmin());
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


    //-------- WelcomUserNameScreen - START --------
    public void handleDoctors() {
        doctorsScreen = new DoctorsScreen(this);
        welcomeUserNameScreen.dispose();
    }

    public void handleSpecializations() {
        specializationsScreen = new SpecializationsScreen(this);
        welcomeUserNameScreen.dispose();
    }
    //-------- WelcomUserNameScreen - END --------


    //-------- DoctorsScreen - START --------
    public void handleAddNewDoctor() {
        newEditDoctorScreen = new NewEditDoctorScreen(this);
        doctorsScreen.dispose();
    }
    //-------- DoctorsScreen - END --------


    //-------- NewEditDoctorScreen - START --------
    public void handleSaveNewDoctor() {
        doctorsScreen = new DoctorsScreen(this);
        newEditDoctorScreen.dispose();
    }
    //-------- NewEditDoctorScreen - END --------



    //-------- NewEditSpecializationScreen - START --------
    public void handleAddNewSpecialization() {
        newEditSpecializationScreen = new NewEditSpecializationScreen(this);
        specializationsScreen.dispose();
    }
    //-------- NewEditSpecializationScreen - END --------

    public static void main(String[] args) {
      Controller controller = new Controller();
    }
}