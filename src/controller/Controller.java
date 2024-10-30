package controller;

import view.*;

public class Controller {
    public static void main(String[] args) {
        ChooseBookDoctorScreen chooseBookDoctorScreen = new ChooseBookDoctorScreen(new Controller());
        DiagnosisScreen diagnosisScreen = new DiagnosisScreen(new Controller());
        DoctorNewEditScreen doctorNewEditScreen = new DoctorNewEditScreen(new Controller());
        DoctorsScreen doctorsScreen = new DoctorsScreen(new Controller());
        LogInScreen logInScreen = new LogInScreen(new Controller());
        MedicalRecordsScreen medicalRecordsScreen = new MedicalRecordsScreen(new Controller());
        PatientsScreen patientsScreen = new PatientsScreen(new Controller());
        UpcomingAppointmentsScreen upcomingAppointmentsScreen = new UpcomingAppointmentsScreen(new Controller());
        ScheduleScreen scheduleScreen = new ScheduleScreen(new Controller());
    }
}