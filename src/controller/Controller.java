package controller;

import view.DoctorsScreen;
import view.LogInScreen;

public class Controller {
    public static void main(String[] args) {
        DoctorsScreen doctorsScreen = new DoctorsScreen(new Controller());
    }
}