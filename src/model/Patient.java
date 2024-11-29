package model;

public class Patient {
    private int patientMedicalId;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String phone;
    private String birthDate;
    private String registrationDate;

    public Patient(int patientMedicalId, String firstName, String lastName, String gender, String address, String phone, String birthDate, String registrationDate) {
        this.patientMedicalId = patientMedicalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
    }

    public int getPatientMedicalId() {
        return patientMedicalId;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
