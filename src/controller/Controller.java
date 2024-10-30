package controller;

import view.*;

public class Controller {
    public static void main(String[] args) {
        LogInScreen logInScreen = new LogInScreen(new Controller());
        AdminLogIn adminLogIn = new AdminLogIn(new Controller());
        DoctorLogIn doctorLogIn = new DoctorLogIn(new Controller());
        PatientLogIn patientLogIn = new PatientLogIn(new Controller());
        ScheduleScreen scheduleScreen = new ScheduleScreen(new Controller());
        ChooseBookDoctorScreen chooseBookDoctorScreen = new ChooseBookDoctorScreen(new Controller());
        DiagnosisScreen diagnosisScreen = new DiagnosisScreen(new Controller());
        DoctorNewEditScreen doctorNewEditScreen = new DoctorNewEditScreen(new Controller());
        DoctorsScreen doctorsScreen = new DoctorsScreen(new Controller());
        MedicalRecordsScreen medicalRecordsScreen = new MedicalRecordsScreen(new Controller());
        PatientsScreen patientsScreen = new PatientsScreen(new Controller());
        UpcomingAppointmentsScreen upcomingAppointmentsScreen = new UpcomingAppointmentsScreen(new Controller());
        WelcomeUserNameScreen welcomeUserNameScreen = new WelcomeUserNameScreen(new Controller());
    }
}