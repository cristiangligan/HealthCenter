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
    private WelcomeAdminScreen welcomeAdminScreen;
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
    private EditInfoPatientScreen editInfoPatientScreen;
    */

    public Controller() {
        welcomeAdminScreen = new WelcomeAdminScreen(this);
        //logInScreen = new LogInScreen(this);
        //logInToDataBase();
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
            welcomeAdminScreen = new WelcomeAdminScreen(this);
            welcomeAdminScreen.setUsernameLabel(username);
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

    public void handleBackFromDoctorsScreen() {
        welcomeAdminScreen = new WelcomeAdminScreen(this);
        //welcomeUserNameScreen.setUsernameLabel(adminManager.getCurrentAdmin().toString());
        welcomeAdminScreen.setUsernameLabel("temporary");
        doctorsScreen.dispose();
    }
    //-------- LogInScreen - END --------







    //-------- WelcomeUserNameScreen - START --------
    public void handleDoctors() {
        doctorsScreen = new DoctorsScreen(this);
        welcomeAdminScreen.dispose();
    }

    public void handleSpecializations() {
        specializationsScreen = new SpecializationsScreen(this);
        welcomeAdminScreen.dispose();
    }

    public void handleLogOut() {
        logInScreen = new LogInScreen(this);
        connection = null;
        //adminManager.setCurrentAdmin(null);
        welcomeAdminScreen.dispose();
    }
    //-------- WelcomeUserNameScreen - END --------






    //-------- DoctorsScreen - START --------
    public void handleAddNewDoctor() {
        newEditDoctorScreen = new NewEditDoctorScreen(this);
        doctorsScreen.dispose();
    }

    public void handleEditDoctor() {
        newEditDoctorScreen = new NewEditDoctorScreen(this);
        doctorsScreen.dispose();
    }
    //-------- DoctorsScreen - END --------







    //-------- NewEditDoctorScreen - START --------
    public void handleSaveDoctor() {
        doctorsScreen = new DoctorsScreen(this);
        newEditDoctorScreen.dispose();
    }

    public void handleCancelNewEditDoctor() {
        doctorsScreen = new DoctorsScreen(this);
        newEditDoctorScreen.dispose();
    }
    //-------- NewEditDoctorScreen - END --------




    //-------- SpecializationsScreen - START --------
    public void handleAddNewSpecialization() {
        newEditSpecializationScreen = new NewEditSpecializationScreen(this);
        specializationsScreen.dispose();
    }

    public void handleEditSpecialization() {
        newEditSpecializationScreen = new NewEditSpecializationScreen(this);
        specializationsScreen.dispose();
    }
    public void handleBackFromSpecializationsScreen() {
        welcomeAdminScreen = new WelcomeAdminScreen(this);
        //welcomeUserNameScreen.setUsernameLabel(adminManager.getCurrentAdmin().toString());
        welcomeAdminScreen.setUsernameLabel("temporary");
        specializationsScreen.dispose();
    }
    //-------- SpecializationsScreen - END --------




    //-------- NewEditSpecializationScreen - START --------
    public void handleSaveSpecialization() {
        doctorsScreen = new DoctorsScreen(this);
        newEditDoctorScreen.dispose();
    }

    public void handleCancelNewEditSpecialization() {
        specializationsScreen = new SpecializationsScreen(this);
        newEditSpecializationScreen.dispose();
    }
    //-------- NewEditSpecializationScreen - END --------



    public static void main(String[] args) {
      Controller controller = new Controller();
    }
}