package controller;

import view.DoctorsScreen;
import view.LogInScreen;
import view.PatientsScreen;
import view.UpcomingAppointmentsScreen;

public class Controller {
    public static void main(String[] args) {
        UpcomingAppointmentsScreen upcomingAppointmentsScreen = new UpcomingAppointmentsScreen(new Controller());
    }
}