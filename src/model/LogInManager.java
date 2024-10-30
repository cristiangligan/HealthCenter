package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInManager {
    private Connection connection;

    private Admin currentAdmin;

    public LogInManager(Connection connection) {
        this.connection = connection;
    }

    public Admin verifyAdmin(String username, String password) {
        String verifyQuery = "SELECT admin.id FROM public.admin WHERE admin.username = ? AND admin.password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(verifyQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    currentAdmin = new Admin(id, username, password);
                    return currentAdmin;
                } else {
                    System.out.println("User har ingen id kopplat till sig");
                    return null;
                }
            } catch (SQLException e) {
                System.out.println("Fel user or password");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Wrong query");
            return null;
        }

    }

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }
}
