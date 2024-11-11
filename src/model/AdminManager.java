package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminManager {
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

    /*public List<Doctor> getDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        String selectFlashcardSetData = "SELECT * FROM public.doctors";
        try {
            PreparedStatement statement = connection.prepareStatement(selectFlashcardSetData);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String phone = resultSet.getString("phone");
                Specialization specialization = new Specialization(name, visit_cost);
                specialization.setId(id);
                specializations.add(specialization);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specializations;
    }*/

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }

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
                propertyChangeSupport.firePropertyChange(UPDATE_DOCTORS_LIST, null, getSpecializations());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void deleteSpecialization(Specialization specialization) {

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
