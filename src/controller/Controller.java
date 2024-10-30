package controller;

import view.*;

import javax.naming.ldap.Control;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Controller {
    private Connection connection;
    private LogInScreen logInScreen;
    private AdminLogIn adminLogIn;
    private DoctorLogIn doctorLogIn;
    private PatientLogIn patientLogIn;

    /*
    private ScheduleScreen scheduleScreen;
    private ChooseBookDoctorScreen chooseBookDoctorScreen;
    private DiagnosisScreen diagnosisScreen;
    private DoctorNewEditScreen doctorNewEditScreen;
    private DoctorsScreen doctorsScreen;
    private MedicalRecordsScreen medicalRecordsScreen;
    private PatientsScreen patientsScreen;
    private UpcomingAppointmentsScreen upcomingAppointmentsScreen;
    private WelcomeUserNameScreen welcomeUserNameScreen;
    private WelcomePatientScreen welcomePatientScreen;
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