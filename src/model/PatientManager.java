package model;

import controller.Controller;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;

public class PatientManager extends JFrame {
    private Controller controller;
    private Connection connection;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Patient currentPatient;
    private Specialization selectedSpecialization;

    public PatientManager(Connection connection) {
        this.connection = connection;
    }

    public Patient verifyPatient(int patientId) throws SQLException {
        String verifyQuery = "SELECT id, firstname, lastname, gender, address, phone, birthdate, reg_date FROM public.patient WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(verifyQuery)) {
            preparedStatement.setInt(1, patientId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Patient(resultSet.getInt("id"), resultSet.getString("firstname"), resultSet.getString("lastname"), resultSet.getString("gender"), resultSet.getString("address"), resultSet.getString("phone"), resultSet.getString("birthdate").toString(), resultSet.getString("reg_date").toString());
                } else {
                    System.out.println("There's no patient with the given medical number.");
                    return null;
                }
            } catch (SQLException e) {
                System.out.println("Wrong medical number.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Database error");
            return null;
        }
    }

    public boolean saveNewPatient(String firstName, String lastName, String gender, String address, String phone, String birthDate) {
        String verifyQuery = "INSERT INTO public.patient (firstname, lastname, gender, address, phone, birthdate, reg_date) VALUES (?, ?, ?, ?, ?, ?, CURRENT_DATE)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(verifyQuery)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, String.valueOf(java.sql.Date.valueOf(birthDate)));

            java.sql.Date sqlBirthDate = java.sql.Date.valueOf(birthDate);
            preparedStatement.setDate(6, sqlBirthDate);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error saving new patient: " + e.getMessage());
            return false;
        }
    }


    public void subscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    public Patient getCurrentPatient() {
        return currentPatient;
    }
    public void setCurrentPatient(Patient patient) {
        this.currentPatient = patient;
    }
}
