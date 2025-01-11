package model;

public class Patient {
    private int medicalId;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String phone;
    private String birthDate;
    private String registrationDate;

    public Patient(int medicalId, String firstName, String lastName, String gender, String address, String phone, String birthDate, String registrationDate) {
        this.medicalId = medicalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
    }

    public int getMedicalId() {
        return medicalId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
