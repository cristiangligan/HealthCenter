package controller;

import view.*;

import javax.naming.ldap.Control;

public class Controller {
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
     */

    public Controller() {
        logInScreen = new LogInScreen(this);
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