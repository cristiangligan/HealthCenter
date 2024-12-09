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

    public boolean updatePatientInfo(Patient patient) throws SQLException {
        String selectQuery = "UPDATE public.patient SET firstname = ?, lastname = ?, gender = ?, address = ? , phone = ?, birthdate = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getLastName());
            preparedStatement.setString(3, patient.getGender());
            preparedStatement.setString(4, patient.getAddress());
            preparedStatement.setString(5, patient.getPhone());
            preparedStatement.setDate(6, java.sql.Date.valueOf(patient.getBirthDate()));
            preparedStatement.setInt(7, patient.getPatientMedicalId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                setCurrentPatient(patient);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error updating patient info: " + e.getMessage());
        }
        return false;
    }

    public void subscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    public Patient getLoggedInPatient() {
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
            String insertQuery = "INSERT INTO public.appointment (id_doctor, id_patient, time, date) VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(insertQuery);
                int id_doctor = appointment.getDoctorId();
                int id_patient = appointment.getPatientId();
                String time = appointment.getTime();
                String date = appointment.getDate();
                statement.setInt(1, id_doctor);
                statement.setInt(2, id_patient);
                statement.setString(3, time);
                statement.setString(4, date);
                int rowCount = statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Appointment existAppointmentWithDoctor(Patient currentPatient, Doctor selectedDoctor) {
        Appointment appointment = null;
        String selectQuery = "SELECT * FROM public.appointment WHERE appointment.id_patient = ? AND appointment.id_doctor = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, currentPatient.getPatientMedicalId());
            preparedStatement.setInt(2, selectedDoctor.getEmployerNr());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int patientId = resultSet.getInt("id_patient");
                    int doctorId = resultSet.getInt("id_doctor");
                    String time = resultSet.getString("time");
                    String date = resultSet.getString("date");
                    appointment = new Appointment(doctorId, patientId, time, date);
                }
            } catch (SQLException e) {
                return appointment;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointment;
    }

    public Patient getCurrentPatient() {
        return currentPatient;
    }
}
