package controller;

import view.*;

public class Controller {
    public static void main(String[] args) {
        LogInScreen logInScreen = new LogInScreen(new Controller());
        AdminLogIn adminLogIn = new AdminLogIn(new Controller());
        DoctorLogIn doctorLogIn = new DoctorLogIn(new Controller());
        PatientLogIn patientLogIn = new PatientLogIn(new Controller());
        ScheduleScreen scheduleScreen = new ScheduleScreen(new Controller());
    }
}