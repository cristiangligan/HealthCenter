package model;

public class UserManager {
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public boolean isCurrentUserSet() {
        return currentUser != null;
    }

    public boolean isCurrentUserAdmin() {
        return currentUser instanceof Admin;
    }

    public boolean isCurrentUserDoctor() {
        return currentUser instanceof Doctor;
    }

    public boolean isCurrentUserPatient() {
        return currentUser instanceof Patient;
    }
}
