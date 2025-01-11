package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DoctorManager {
    private Connection connection;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Doctor currentDoctor;
    //private

    public DoctorManager(Connection connection) {
        this.connection = connection;
    }

    public Doctor verifyDoctor(int medicalId) {
        String verifyQuery =
            "SELECT " +
                "doctor.id as doctor_id, doctor.firstname, doctor.lastname, doctor.phone, " +
                "specialization.id as spec_id, specialization.name as spec_name, specialization.visit_cost as visit_cost " +
            "FROM public.doctor JOIN public.specialization ON doctor.id_specialization = specialization.id " +
            "WHERE doctor.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(verifyQuery)) {
            preparedStatement.setInt(1, medicalId);

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
                    return new Doctor(doctorId, firstName, lastName, phone, specialization);
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

    public ArrayList<Appointment> getMyUpcomingAppointments(Doctor selectedDoctor, String nextMondayDate) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        Appointment appointment = null;
        String selectQuery = "SELECT * FROM public.appointment WHERE appointment.id_doctor = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, selectedDoctor.getEmployerNr());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int patientId = resultSet.getInt("id_patient");
                    int doctorId = resultSet.getInt("id_doctor");
                    String time = resultSet.getString("time");
                    String dateString = resultSet.getString("date");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = simpleDateFormat.parse(dateString);
                    Date mondayString = simpleDateFormat.parse(nextMondayDate);
                    if (date.compareTo(mondayString) >= 0) {
                        appointment = new Appointment(doctorId, patientId, time, dateString);
                        appointment.setId(id);
                        appointments.add(appointment);
                    }
                }
            } catch (SQLException e) {
                return appointments;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }

    public ArrayList<Appointment> getDoctorsAppointments(Doctor doctor) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        Appointment appointment = null;
        String selectQuery = "SELECT * FROM public.appointment WHERE appointment.id_doctor = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, doctor.getEmployerNr());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int patientId = resultSet.getInt("id_patient");
                    if (patientId != 0) {
                        int id = resultSet.getInt("id");
                        int doctorId = resultSet.getInt("id_doctor");
                        String time = resultSet.getString("time");
                        String dateString = resultSet.getString("date");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        appointment = new Appointment(doctorId, patientId, time, dateString);
                        appointment.setId(id);
                        appointments.add(appointment);
                    }
                }
            } catch (SQLException e) {
                return appointments;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }

    public void makeTimeslotUnavailable(Appointment appointment) {
        if (appointment != null) {
            String insertQuery = "INSERT INTO public.appointment (id_doctor, id_patient, time, date) VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(insertQuery);
                int id_doctor = appointment.getDoctorId();
                int id_patient = appointment.getPatientId();
                String time = appointment.getTime();
                String date = appointment.getDate();
                statement.setInt(1, id_doctor);
                statement.setNull(2, Types.INTEGER);
                statement.setString(3, time);
                statement.setString(4, date);
                int rowCount = statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<Patient> getDoctorsPatients(Doctor doctor) {
        ArrayList<Patient> patients = new ArrayList<>();
        ArrayList<Integer> ints = new ArrayList<>();
        String selectDistinctPatientsQuery = "SELECT DISTINCT ON (id_patient) id_patient FROM public.appointment WHERE appointment.id_doctor = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectDistinctPatientsQuery)) {
            preparedStatement.setInt(1, doctor.getEmployerNr());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_patient");
                    if (id != 0) {
                        ints.add(id);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String selectPatientsQuery = "SELECT * FROM public.patient WHERE id = ?";
        for(Integer integer: ints) {
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(selectPatientsQuery)) {
                preparedStatement2.setInt(1, integer);
                try (ResultSet resultSet = preparedStatement2.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String firstName = resultSet.getString("firstname");
                        String lastName = resultSet.getString("lastname");
                        String gender = resultSet.getString("gender");
                        String address = resultSet.getString("address");
                        String phone = resultSet.getString("phone");
                        String birthdate =  resultSet.getDate("birthdate").toString();
                        String regdate = resultSet.getDate("reg_date").toString();
                        patients.add(new Patient(id, firstName, lastName, gender, address, phone, birthdate, regdate));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return patients;
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