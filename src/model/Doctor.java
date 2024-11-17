package model;

public class Doctor {
    private int employerNr;
    private String firstName;
    private String lastName;
    private String phone;
    private Specialization specialization;

    public Doctor(int employerNr, String firstName, String lastName, String phone, Specialization specialization) {
        this.employerNr = employerNr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.specialization = specialization;
    }

    public void setEmployerNr(int employerNr) {
        this.employerNr = employerNr;
    }

    public int getEmployerNr() {
        return employerNr;
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

    public void setFirstName(String text) {
        this.firstName = firstName;
    }


    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
