package controller;

import view.DoctorsScreen;
import view.LogInScreen;
import view.PatientsScreen;

public class Controller {
    public static void main(String[] args) {
        PatientsScreen patientsScreen = new PatientsScreen(new Controller());
    }
}