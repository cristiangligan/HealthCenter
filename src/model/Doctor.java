package model;

import model.common.User;
import model.common.UserType;

public class Doctor extends User {
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final Specialization specialization;

    public Doctor(int employerNr, String firstName, String lastName, String phone, Specialization specialization) {
        this.type = UserType.DOCTOR;

        this.id = employerNr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.specialization = specialization;
    }

    public void setEmployerNr(int employerNr) {
        this.id = employerNr;
    }

    public int getEmployerNr() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " ("  + specialization.getName() + " | " + specialization.getCost() + "$)";
    }
}
