package model;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorManager {
    private Connection connection;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Doctor currentDoctor;
    //private

    public DoctorManager(Connection connection) {
        this.connection = connection;
    }

    public Doctor verifyDoctor(int medicalId) {
        String verifyQuery = "SELECT id, firstname, lastname, phone, id_specialization FROM public.doctor WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(verifyQuery)) {
            preparedStatement.setInt(1, medicalId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Doctor doctor = new Doctor
                            (resultSet.getInt("id"), resultSet.getString("firstname"), resultSet.getString("lastname"), resultSet.getString("phone"), new Specialization(resultSet.getInt("id_specialization")));
                    return doctor;
                } else {
                    System.out.println("There's no user with the given medical number.");
                    return null;
                }
        } catch (SQLException e) {
            System.out.println("Wrong medical number.");
            return null;
        }
    } catch(SQLException e) {
        System.out.println("Database error");
        return null;
    }
}

    public void subscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public Doctor getCurrentDoctor() {
        return currentDoctor;
    }

    public void setCurrentDoctor(Doctor doctor) {
        this.currentDoctor = doctor;
    }

}