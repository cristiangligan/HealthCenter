package model;

import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminManager {
    public static final String UPDATE_DOCTORS_LIST = "update_doctors_list";
    private Connection connection;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private Admin currentAdmin;

    public AdminManager(Connection connection) {
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

    /*public void addNewDoctor(Doctor doctor) {
        if (doctor != null) {
            String insertQuery = "INSERT INTO public.doctor (question, answer, flashcards_set_id) VALUES (?, ?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(insertQuery);
                statement.setString(1, question);
                statement.setString(2, answer);
                statement.setInt(3, flashcardSet.getId());
                int rowCount = statement.executeUpdate();
                propertyChangeSupport.firePropertyChange(UPDATE_FLASHCARD_LIST, null, getFlashcards(flashcardSet.getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }*/

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }
}
