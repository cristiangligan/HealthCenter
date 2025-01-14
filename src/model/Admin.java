package model;

import model.common.User;
import model.common.UserType;

public class Admin extends User {
    private final String username;
    private final String password;

    public Admin(String username, String password) {
        this.type = UserType.ADMIN;

        this.username = username;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return username;
    }
}
