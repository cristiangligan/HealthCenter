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
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public ArrayList<Appointment> getMyAppointments(Doctor selectedDoctor, String nextMondayDate) {
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