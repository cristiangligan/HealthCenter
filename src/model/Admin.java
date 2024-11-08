package model;

public class Admin {
    private int id;
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public void setId(int id) {
        this.id = id;
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
