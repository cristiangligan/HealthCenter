package model;

import model.common.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager {
    private final Connection connection;
    private User currentUser;

    public UserManager(Connection connection) {
        this.connection = connection;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public Patient getPatient(int medicalId) {
        Patient patient = null;
        String selectQuery = "SELECT * FROM public.patient WHERE patient.id = " + medicalId;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    String gender = resultSet.getString("gender");
                    String address = resultSet.getString("address");
                    String phone = resultSet.getString("phone");
                    String birthdate = resultSet.getDate("birthdate").toString();
                    String regDate = resultSet.getDate("reg_date").toString();
                    patient = new Patient(id, firstName, lastName, gender, address, phone, birthdate, regDate);
                } else {
                    System.out.println("There's no patient with the given ID!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            patient = null;
        }
        return patient;
    }

    public Doctor getDoctor(int employerNr) {
        Doctor doctor = null;
        String verifyQuery =
            "SELECT " +
                "doctor.id as doctor_id, doctor.firstname, doctor.lastname, doctor.phone, " +
                "specialization.id as spec_id, specialization.name as spec_name, specialization.visit_cost as visit_cost " +
                "FROM public.doctor JOIN public.specialization ON doctor.id_specialization = specialization.id " +
                "WHERE doctor.id = " + employerNr;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(verifyQuery);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int specId = resultSet.getInt("spec_id");
                    String specName = resultSet.getString("spec_name");
                    int visitCost = resultSet.getInt("visit_cost");
                    Specialization specialization = new Specialization(specId, specName, visitCost);

                    int doctorId = resultSet.getInt("doctor_id");
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    String phone = resultSet.getString("phone");
                    doctor = new Doctor(doctorId, firstName, lastName, phone, specialization);
                } else {
                    System.out.println("There's no doctor with the given ID!");
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            doctor = null;
        }
        return doctor;
    }

    public ArrayList<MedicalRecord> getMedicalRecords(Patient patient) {
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
        String selectQuery = "SELECT * FROM public.medical_record WHERE medical_record.id_patient = " + patient.getMedicalId();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String diagnosis = resultSet.getString("diagnosis");
                    String description = resultSet.getString("description");
                    String prescription = resultSet.getString("prescription");
                    int doctorId = resultSet.getInt("id_doctor");
                    int patientId = resultSet.getInt("id_patient");
                    MedicalRecord medicalRecord = new MedicalRecord(diagnosis, description, prescription, doctorId, patientId);
                    medicalRecord.setId(id);
                    medicalRecords.add(medicalRecord);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            medicalRecords = null;
        }
        return medicalRecords;
    }
}
