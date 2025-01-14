package model.common;

public abstract class User {
    protected int id;
    protected UserType type;

    public boolean isAdmin() {
        return type == UserType.ADMIN;
    }

    public boolean isDoctor() {
        return type == UserType.DOCTOR;
    }

    public boolean isPatient() {
        return type == UserType.PATIENT;
    }
}
