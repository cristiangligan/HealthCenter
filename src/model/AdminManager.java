package model;

import javax.print.Doc;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminManager {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final String UPDATE_DOCTORS_LIST = "update_doctors_list";
    public static final String UPDATE_SPECIALIZATION_LIST = "update_specialization_list";
    private Connection connection;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private Admin currentAdmin;
    private Specialization selectedSpecialization;

    public AdminManager(Connection connection) {
        this.connection = connection;
    }

    public Admin verifyAdmin(Admin admin) {
        String verifyQuery = "SELECT admin.id FROM public.admin WHERE admin.username = ? AND admin.password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(verifyQuery)) {
            String username = admin.getUsername();
            String password = admin.getPassword();

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    currentAdmin = new Admin(username, password);
                    currentAdmin.setId(id);
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

    public List<Patient> getPatients() {
        List<Patient> patients = new ArrayList<>();
        String selectPatientData = "SELECT * FROM public.patient";
        try {
            PreparedStatement statement = connection.prepareStatement(selectPatientData);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String gender = resultSet.getString("gender");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String birthdate = resultSet.getDate("birthdate").toString();
                String regdate = resultSet.getDate("reg_date").toString();
                patients.add(new Patient(id, firstname, lastname, gender, address, phone, birthdate, regdate));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patients;
    }

    public List<Specialization> getSpecializations() {
        ArrayList<Specialization> specializations = new ArrayList<>();
        String selectFlashcardSetData = "SELECT * FROM public.specialization";
        try {
            PreparedStatement statement = connection.prepareStatement(selectFlashcardSetData);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int visit_cost = resultSet.getInt("visit_cost");
                Specialization specialization = new Specialization(name, visit_cost);
                specialization.setId(id);
                specializations.add(specialization);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specializations;
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

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }

    //add new doctor
    public void saveNewDoctor(Doctor doctor) {
        if (doctor != null) {
            String insertQuery = "INSERT INTO public.doctor (id, firstname, lastname, phone, id_specialization) VALUES (?, ?, ?, ?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(insertQuery);
                int id = doctor.getEmployerNr();
                String firstName = doctor.getFirstName();
                String lastName = doctor.getLastName();
                String phone = doctor.getPhone();
                Specialization specialization = doctor.getSpecialization();
                statement.setInt(1, id);
                statement.setString(2, firstName);
                statement.setString(3, lastName);
                statement.setString(4, phone);
                statement.setInt(5, specialization.getId());
                int rowCount = statement.executeUpdate();
                propertyChangeSupport.firePropertyChange(UPDATE_DOCTORS_LIST, null, getDoctors());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveEditedDoctor(Doctor editedDoctor) {
        if (editedDoctor != null) {
            String updateQuery = "UPDATE public.doctor SET firstName = ?, lastName = ?, phone = ?, id_specialization = ? WHERE doctor.id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, editedDoctor.getFirstName());
                statement.setString(2, editedDoctor.getLastName());
                statement.setString(3, editedDoctor.getPhone());
                statement.setInt(4, editedDoctor.getSpecialization().getId());
                statement.setInt(5, editedDoctor.getEmployerNr());

                int rowsUpdate = statement.executeUpdate();
                if (rowsUpdate > 0) {
                    propertyChangeSupport.firePropertyChange(UPDATE_DOCTORS_LIST, null, getDoctors());
                    System.out.println("Doctor updated successfully.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error updating doctor " + e.getMessage(), e);
            }
        }
    }

    //delete doctor
    public void deleteDoctor(Doctor doctor) {
        String deleteQuery = "DELETE FROM public.doctor WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, doctor.getEmployerNr());
            int rowsDelete = statement.executeUpdate();
            if (rowsDelete > 0) {
                System.out.println("Doctor deleted successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting doctor from database.", e);
        }
    }

    public void saveSpecialization(Specialization specialization) {
        if (specialization != null) {
            String insertQuery = "INSERT INTO public.specialization (name, visit_cost) VALUES (?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(insertQuery);
                String name = specialization.getName();
                int visit_cost = specialization.getCost();
                statement.setString(1, name);
                statement.setInt(2, visit_cost);
                int rowCount = statement.executeUpdate();
                propertyChangeSupport.firePropertyChange(UPDATE_SPECIALIZATION_LIST, null, getSpecializations());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveEditedSpecialization(Specialization editedSpecialization) {
        if (editedSpecialization != null) {
            String updateQuery = "UPDATE public.specialization SET name = ?, visit_cost = ? WHERE specialization.id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, editedSpecialization.getName());
                statement.setInt(2, editedSpecialization.getCost());
                statement.setInt(3, editedSpecialization.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //kontrollmetod för att kontrollera om en specialisering är kopplad till en läkare:
    public boolean isSpecializationAssignedToDoctors(int specializationId) {
        //COUNT counts the amout of rows in the doctor table where the condition id =? == true
        String query = "SELECT COUNT (*) AS doctor_count FROM doctor WHERE id_specialization = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, specializationId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int doctorCount = resultSet.getInt("doctor_count");
                    return doctorCount > 0; //returns true if there's doctors with the specialization (doctorCount == 0 means no doctors)
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return false;
    }

    //radera specialisering från databasen
    public void deleteSpecialization(Specialization specialization) {
        if (isSpecializationAssignedToDoctors(specialization.getId())) {
            throw new RuntimeException("Cannot delete specialization. It is assigned to one or more doctors.");
        }
        String deleteQuery = "DELETE FROM public.specialization WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, specialization.getId());
            int rowsDelete = statement.executeUpdate();
            if (rowsDelete > 0) {
                System.out.println("Specialization deleted successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting specialization from database.", e);
        }
    }

    public ArrayList<Appointment> getUpcomingAppointments(Date currentDate) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        String selectAppointmentData = "SELECT * FROM public.appointment";
        try {
            PreparedStatement statement = connection.prepareStatement(selectAppointmentData);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String dateString = resultSet.getString("date");
                Date appointmentDate = simpleDateFormat.parse(dateString);
                if (appointmentDate.after(currentDate)) {
                    int id = resultSet.getInt("id");
                    int idDoctor = resultSet.getInt("id_doctor");
                    int idPatient = resultSet.getInt("id_patient");
                    String time = resultSet.getString("time");
                    Appointment appointment = new Appointment(idDoctor, idPatient, time, dateString);
                    appointment.setId(id);
                    appointments.add(appointment);
                }
            }
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }

    public void setSelectedSpecialization(Specialization specialization) {
        this.selectedSpecialization = specialization;
    }

    public Specialization getSelectedSpecialization() {
        return selectedSpecialization;
    }

    public void subscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void unsubscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}

