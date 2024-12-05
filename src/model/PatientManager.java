package model;

import controller.Controller;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientManager extends JFrame {
    private Controller controller;
    private Connection connection;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Patient currentPatient;

    private Doctor selectedDoctor;
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

    public List<Doctor> getDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String selectDoctorData = "SELECT * FROM public.doctor JOIN public.specialization ON doctor.id_specialization = specialization.id";
        try {
            PreparedStatement statement = connection.prepareStatement(selectDoctorData);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int employerNr = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String phone = resultSet.getString("phone");
                int specializationId = resultSet.getInt("id_specialization");
                String specializationName = resultSet.getString("name");
                int visit_cost = resultSet.getInt("visit_cost");
                Specialization specialization = new Specialization(specializationName, visit_cost);
                specialization.setId(specializationId);
                doctors.add(new Doctor(employerNr, firstname, lastname, phone, specialization));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctors;
    }

    public Patient getPatientInfo(String firstName, String lastName) {
        Patient patient = null;
        String selectQuery = "SELECT * FROM public.patient WHERE patient.firstname = ? AND patient.lastname = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");
                    String gender = resultSet.getString("gender");
                    String address = resultSet.getString("address");
                    String phone = resultSet.getString("phone");
                    String birthdate = resultSet.getDate("birthdate").toString();
                    String regdate = resultSet.getDate("reg_date").toString();
                    patient = new Patient(id, firstname, lastname, gender, address, phone, birthdate, regdate);
                }
            } catch (SQLException e) {
                return patient;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patient;
    }

    public Patient getPatientInfo(int patientId) {
        Patient patient = null;
        String selectQuery = "SELECT * FROM public.patient WHERE patient.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, patientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");
                    String gender = resultSet.getString("gender");
                    String address = resultSet.getString("address");
                    String phone = resultSet.getString("phone");
                    String birthdate = resultSet.getDate("birthdate").toString();
                    String regdate = resultSet.getDate("reg_date").toString();
                    patient = new Patient(id, firstname, lastname, gender, address, phone, birthdate, regdate);
                }
            } catch (SQLException e) {
                return patient;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patient;
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

    public void setSelectedDoctor(Doctor selectedDoctor) {
        this.selectedDoctor = selectedDoctor;
    }

    public Doctor getSelectedDoctor() {
        return selectedDoctor;
    }

    public void bookAppointment(Appointment appointment) {
        if (appointment != null) {
            String insertQuery = "INSERT INTO public.appointment (id_doctor, id_patient, day, time) VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(insertQuery);
                int id_doctor = appointment.getDoctorId();
                int id_patient = appointment.getPatientId();
                String day = appointment.getDay();
                String time = appointment.getTime();
                statement.setInt(1, id_doctor);
                statement.setInt(2, id_patient);
                statement.setString(3, day);
                statement.setString(4, time);
                int rowCount = statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean existAppointmentWithDoctor() {
        boolean exist = false;

        return exist;
    }
}
