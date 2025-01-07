package model;

import javax.print.Doc;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Doctor> searchDoctors(String searchTerm) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String searchQuery = """
        SELECT d.id, d.firstname, d.lastname, d.phone, s.id AS specialization_id, s.name AS specialization_name, s.visit_cost
        FROM public.doctor d
        JOIN public.specialization s ON d.id_specialization = s.id
        WHERE LOWER(d.firstname) LIKE ? OR LOWER(d.lastname) LIKE ? OR LOWER(s.name) LIKE ?
    """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            String searchTermPattern = "%" + searchTerm.toLowerCase() + "%";
            preparedStatement.setString(1, searchTermPattern);
            preparedStatement.setString(2, searchTermPattern);
            preparedStatement.setString(3, searchTermPattern);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName= resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    String phone = resultSet.getString("phone");

                    //skapa en instans av specialization
                    int specializationId = resultSet.getInt("specialization_id");
                    String specializationName = resultSet.getString("specialization_name");
                    int visitCost = resultSet.getInt("visit_cost");
                    Specialization specialization = new Specialization(specializationName, visitCost);
                    specialization.setId(specializationId);

                    //skapa och lägg till läkaren i listan
                    Doctor doctor = new Doctor(id, firstName, lastName, phone, specialization);
                    doctors.add(doctor);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching for doctors/specializations: ");
        }
        return doctors;
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

    public List<Doctor> getDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT id, firstname, lastname, phone, id_specialization FROM public.doctor";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Doctor doctor = new Doctor(resultSet.getInt("id"), resultSet.getString("firstname"), resultSet.getString("lastname"), resultSet.getString("phone"), new Specialization(resultSet.getInt("id_specialization"))
                );
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving doctors from the databse: " + e.getMessage(), e);
        }
        return doctors;
    }
}