package controller;

import model.*;
import view.*;

import javax.print.Doc;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller implements PropertyChangeListener {
    private Connection connection;
    private LogInScreen logInScreen;
    private AdminLogIn adminLogIn;
    private DoctorLogIn doctorLogIn;
    private PatientLogIn patientLogIn;
    private AdminManager adminManager;
    private WelcomeAdminScreen welcomeAdminScreen;
    private DoctorsScreen doctorsScreen;
    private NewDoctorScreen newDoctorScreen;
    private PatientsScreen patientsScreen;
    private SpecializationsScreen specializationsScreen;
    private AddSpecializationScreen addSpecializationScreen;
    private EditSpecializationScreen editSpecializationScreen;
    private MedicalRecordsScreen medicalRecordsScreen;
    private DiagnosisScreen diagnosisScreen;
    private UpcomingAppointmentsScreen upcomingAppointmentsScreen;
    private RegisterNewPatientScreen registerNewPatientScreen;
    private WelcomePatientScreen welcomePatientScreen;
    private ChooseBookDoctorScreen chooseBookDoctorScreen;
    private ScheduleScreen scheduleScreen;
    private WelcomeDoctorScreen welcomeDoctorScreen;
    private EditDoctorScreen editDoctorScreen;
    private DoctorManager doctorManager;
    private PatientManager patientManager;


    /*
    private EditInfoPatientScreen editInfoPatientScreen;
    */

    public Controller() {
        logInScreen = new LogInScreen(this);
        logInToDataBase();
    }

    public void logInToDataBase() {
        String URL = "jdbc:postgresql://pgserver.mau.se:5432/ao8732";
        String user = "am6056";
        String password = "ady9oo1j";

        try {
            connection = DriverManager.getConnection(URL, user, password);

            if (connection != null) {
                System.out.println("Connected successfully!");

            } else {
                System.out.println("Not successful!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }


    //-------- AdminLogIn - START --------
    public void handleAdminLogIn() {
        adminManager = new AdminManager(connection);
        adminManager.subscribeListener(this);
        String username = adminLogIn.getUsername();
        String password = adminLogIn.getPassword();
        Admin admin = new Admin(username, password);
        admin = adminManager.verifyAdmin(admin);
        if(admin != null) {
            adminManager.setCurrentAdmin(admin);
            welcomeAdminScreen = new WelcomeAdminScreen(this);
            welcomeAdminScreen.setUsernameLabel(admin.toString());
            adminLogIn.dispose();
            System.out.println(adminManager.getCurrentAdmin());
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect username or password.");
            adminLogIn.clearFields();
        }
    }

    //log in as doctor
    public void handleDoctorLogIn() {
        doctorManager = new DoctorManager(connection);
        doctorManager.subscribeListener(this);

        try {
            int medicalId = doctorLogIn.getMedicalId();
            Doctor doctor = doctorManager.verifyDoctor(medicalId);

            if (doctor != null) {
                doctorManager.setCurrentDoctor(doctor);
                welcomeDoctorScreen = new WelcomeDoctorScreen(this);
                welcomeDoctorScreen.setWelcomeDoctorLabel(doctor.toString());
                doctorLogIn.dispose();
                System.out.println(doctorManager.getCurrentDoctor());
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect medical number. Please try again.");
                doctorLogIn.clearFields();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Medical ID must be a number");
            doctorLogIn.clearFields();
        }
    }

    public void handlePatientLogIn() {
        patientManager = new PatientManager(connection);
        patientManager.subscribeListener(this);

        try {
            String patientIdText = String.valueOf(patientLogIn.getPatientIdText());
            System.out.println("Input from text field " + patientIdText); //debugg

            if (patientIdText.isBlank()) {
                JOptionPane.showMessageDialog(null, "You must write your medical number. Please try again.");
                return; //avbryter processen om fältet är tomt
            }
            int patientId = Integer.parseInt(patientIdText);
            Patient patient = patientManager.verifyPatient(patientId);

            if (patient != null) {
                System.out.println("Logged in as: " + patient.getFirstName() + " " + patient.getLastName());
                patientManager.setCurrentPatient(patient);
                welcomePatientScreen = new WelcomePatientScreen(this);
                welcomePatientScreen.setWelcomePatient(patient.toString());
                patientLogIn.dispose();
                System.out.println(patientManager.getCurrentPatient());
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect medical number. Please try again.");
                patientLogIn.clearFields();
                ;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Medical number must be a valid number. Please try again.");
            patientLogIn.clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleBackFromAdminLogIn() {
        logInScreen = new LogInScreen(this);
        adminLogIn.dispose();
    }

    //metod för att spara ny patient som registrerar sig
    public void handleRegisterNewPatient() {
        patientManager = new PatientManager(connection);
        patientManager.subscribeListener(this);

        String firstName = registerNewPatientScreen.getFirstName();
        String lastName = registerNewPatientScreen.getLastname();
        String gender = registerNewPatientScreen.getGender();
        String address = registerNewPatientScreen.getAddress();
        String phone = registerNewPatientScreen.getPhone();
        String birthDate = registerNewPatientScreen.getBirthDate();

        if (firstName.isBlank() || lastName.isBlank() || gender.isBlank() || address.isBlank() || phone.isBlank() || birthDate.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields correctly.");
            return;
        }

        try {
            java.sql.Date.valueOf(birthDate);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Invalid format date. Please insert yyy-mm-dd");
            return;
        }

        boolean successSavingPatient = patientManager.saveNewPatient(firstName, lastName, gender, address, phone, birthDate);

        if (successSavingPatient) {
            JOptionPane.showMessageDialog(null, "Patient registered.");
            registerNewPatientScreen.dispose();
            logInScreen = new LogInScreen(this);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to register patient. Please try again.");
        }
    }

    public void handleBackFromRegisterNewPatient() {
        patientLogIn = new PatientLogIn(this);
        registerNewPatientScreen.dispose();
    }

    //-------- AdminLogIn - END --------




    //-------- LogInScreen - START --------
    public void chooseAdminLogin() {
        adminLogIn = new AdminLogIn(this);
        logInScreen.dispose();
    }

    public void chooseDoctorLogin() {
        doctorLogIn = new DoctorLogIn(this);
        logInScreen.dispose();
    }

    public void choosePatientLogin() {
        patientLogIn = new PatientLogIn(this);
        logInScreen.dispose();
    }

    public void handleBackFromDoctorsScreen() {
        welcomeAdminScreen = new WelcomeAdminScreen(this);
        welcomeAdminScreen.setUsernameLabel(adminManager.getCurrentAdmin().toString());
        doctorsScreen.dispose();
    }
    //-------- LogInScreen - END --------



    //-------- DoctorLogIn - START --------

    //log in as doctor - LÄGG DEN HÄR SEN

    public void handleBackFromDoctorLoggedIn() {
        try {
            logInScreen = new LogInScreen(this);
            doctorsScreen.dispose();
        }catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    //-------- DoctorLogIn - END --------

    //-------- WelcomeDoctorScreen - START --------
    public void viewMyPatients() {

    }

    public void viewMyScedule() {
    }

    public void viewMyAppointments() {
    }
    public void logOutDoctor() {
        logInScreen = new LogInScreen(this);
        welcomeDoctorScreen.dispose();
    }
    //-------- WelcomeDoctorScreen - END --------


    //log in as patient
    public void handleBackFromPatientLoggedIn() {
        logInScreen = new LogInScreen(this);
        patientLogIn.dispose();
    }
    //ny
    public void handleBtnRegisterNewPatient() {
        registerNewPatientScreen = new RegisterNewPatientScreen(this);
        patientLogIn.dispose();
    }


    //-------- WelcomeUserNameScreen - START --------
    public void handleDoctors() {
        doctorsScreen = new DoctorsScreen(this);
        doctorsScreen.displayDoctors(adminManager.getDoctors());
        welcomeAdminScreen.dispose();
    }

    //Bellas code patient-part from admin
    public void handlePatients() {
        patientsScreen = new PatientsScreen(this);
        welcomeAdminScreen.dispose();
    }

    public void handleBackFromPatientScreen() {
        welcomeAdminScreen = new WelcomeAdminScreen(this);
        patientsScreen.dispose();
    }

    public void handleViewMedicalRecord() {
        medicalRecordsScreen = new MedicalRecordsScreen(this);
        patientsScreen.dispose();
    }

    public void handleBackFromMedicalRecord() {
        patientsScreen = new PatientsScreen(this);
        medicalRecordsScreen.dispose();
    }

    public void handleViewFromMedicalRecord() {
        diagnosisScreen = new DiagnosisScreen(this);
        medicalRecordsScreen.dispose();
    }

    public void handleBackFromDiagnosisScreen() {
        medicalRecordsScreen = new MedicalRecordsScreen(this);
        diagnosisScreen.dispose();
    }

    //-------- AppointmentsScreen - START --------
    public void handleAppointments() {
        upcomingAppointmentsScreen = new UpcomingAppointmentsScreen(this);
        welcomeAdminScreen.dispose();
    }

    public void handleBackFromUpcomingAppointmentsScreen() {
        welcomeAdminScreen = new WelcomeAdminScreen(this);
        upcomingAppointmentsScreen.dispose();
    }
    //-------- AppointmentsScreen - END --------

    public void handleSpecializations() {
        specializationsScreen = new SpecializationsScreen(this);
        handleUpdateSpecializationList(adminManager.getSpecializations());
        welcomeAdminScreen.dispose();
    }

    public void logOutAdmin() {
        logInScreen = new LogInScreen(this);
        adminManager.setCurrentAdmin(null);
        welcomeAdminScreen.dispose();
    }
    //-------- WelcomeUserNameScreen - END --------






    //-------- DoctorsScreen - START --------
    public void handleAddNewDoctor() {
        newDoctorScreen = new NewDoctorScreen(this);
        doctorsScreen.dispose();
    }

    public void handleEditDoctor() {
        Doctor selectedDoctor = doctorsScreen.getSelectedDoctor();
        if (selectedDoctor == null) {
            JOptionPane.showMessageDialog(null, "Please select a doctor to edit.");
            return;
        }
        editDoctorScreen = new EditDoctorScreen(this, selectedDoctor);
        Specialization specialization = selectedDoctor.getSpecialization();
        editDoctorScreen.setSpecializationSelectedDoctor(specialization);
        doctorsScreen.dispose();
    }

    public void handleDeleteDoctor() {
        Doctor selectedDoctor = doctorsScreen.getSelectedDoctor();
        if (selectedDoctor == null) {
            JOptionPane.showMessageDialog(null, "Please select a doctor to delete.");
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected doctor?","Delete doctor", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            adminManager.deleteDoctor(selectedDoctor); //delete the selected doctor from the database
            doctorsScreen.displayDoctors(adminManager.getDoctors()); //uppdatera gränssnittet
            JOptionPane.showMessageDialog(null, "Doctor deleted successfully.");
        }
    }
    //-------- DoctorsScreen - END --------







    //-------- NewEditDoctorScreen - START --------
    public void handleSaveNewDoctor() {
        Doctor doctor = newDoctorScreen.getDoctorInfo();
        boolean isOk = false;
        if (doctor != null) {
            if (doctor.getEmployerNr() != 0) {
                if (!doctor.getFirstName().isBlank()) {
                    if (!doctor.getLastName().isBlank()) {
                        if (!doctor.getPhone().isBlank()) {
                            if (doctor.getSpecialization() != null) {
                                isOk = true;
                            }
                        }
                    }
                }
            }
        }
        if (isOk) {
            adminManager.saveNewDoctor(doctor);
            doctorsScreen = new DoctorsScreen(this);
            doctorsScreen.displayDoctors(adminManager.getDoctors());
            newDoctorScreen.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Please fill in all fields adequately.");
        }
    }

    public void handleEditedDoctor() {
        Doctor editedDoctor = editDoctorScreen.getDoctorInfo();
        if (editedDoctor == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields correctly.");
            return;
        }

        if (editedDoctor.getEmployerNr() == 0 || editedDoctor.getFirstName().isBlank() || editedDoctor.getLastName().isBlank() || editedDoctor.getPhone().isBlank() || editedDoctor.getSpecialization() == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields correctly.");
            return;
        }
        adminManager.saveEditedDoctor(editedDoctor);
        JOptionPane.showMessageDialog(null, "Doctor edited successfully.");
        doctorsScreen = new DoctorsScreen(this);
        doctorsScreen.displayDoctors(adminManager.getDoctors());
        editDoctorScreen.dispose();
    }

    public void handleCancelNewEditDoctor() {
        doctorsScreen = new DoctorsScreen(this);
        doctorsScreen.displayDoctors(adminManager.getDoctors());
        newDoctorScreen.dispose();
    }

    public void handleCancelFromEditDoctor() {
        doctorsScreen = new DoctorsScreen(this);
        doctorsScreen.displayDoctors(adminManager.getDoctors());
        editDoctorScreen.dispose();
    }

    public Specialization[] getSpecializationArray() {
        ArrayList<Specialization> specializations = (ArrayList<Specialization>) adminManager.getSpecializations();
        Specialization[] specializationArray = new Specialization[specializations.size() + 1];
        int i = 0;
        specializationArray[i] = null;
        for (int j = 0; j < specializations.size(); j++) {
            i++;
            specializationArray[i] = specializations.get(j);
        }
        return specializationArray;
    }

    /*
    public int getEmployerNrChecked() {
        int employerNr = 0;
        try {
            employerNr = Integer.parseInt(newDoctorScreen.getEmployerNumberText());
        } catch (NumberFormatException e) {
            return employerNr;
        }
        return employerNr;
    }

     */
    //la till en check för editDoctorScreen också för att kunna hämta employerNumber i editDoctorScreen, är detta rätt? behöll den gamla här ovanför
    public int getEmployerNrChecked() {
        try {
            if (editDoctorScreen != null) {
                return Integer.parseInt(editDoctorScreen.getEmployerNumberText());
            } else if (newDoctorScreen != null) {
                return Integer.parseInt(newDoctorScreen.getEmployerNumberText());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Employer number must be a valid integer.");
        }
        return 0;
    }

    //-------- NewEditDoctorScreen - END --------




    //-------- SpecializationsScreen - START --------
    public void handleAddNewSpecialization() {
        addSpecializationScreen = new AddSpecializationScreen(this);
        specializationsScreen.dispose();
    }

    public void handleEditSpecialization() {
        Specialization specialization = specializationsScreen.getSelectedSpecialization();
        if (specialization != null) {
            adminManager.setSelectedSpecialization(specialization);
            editSpecializationScreen = new EditSpecializationScreen(this);
            editSpecializationScreen.displaySpecializationInfo(specialization);
            specializationsScreen.dispose();
        }
    }
    public void handleDeleteSpecialization() {
        //hämtar den valda specialiseringen från GUI
        Specialization selectedSpecialization = specializationsScreen.getSelectedSpecialization();
        if (selectedSpecialization == null) {
            JOptionPane.showMessageDialog(null, "Please select a specialization to delete.");
            return;
        }
        if (adminManager.isSpecializationAssignedToDoctors(selectedSpecialization.getId())) {
            JOptionPane.showMessageDialog(null, "Cannot delete specialization. It is assigned to one or more doctors. Please unassign it first");
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected specialization?","Delete specialization", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            adminManager.deleteSpecialization(selectedSpecialization); //delete the selected doctor from the database
            specializationsScreen.displaySpecializations(adminManager.getSpecializations()); //uppdatera gränssnittet
            JOptionPane.showMessageDialog(null, "Specialization deleted successfully.");
        }
    }

    public void handleBackFromSpecializationsScreen() {
        welcomeAdminScreen = new WelcomeAdminScreen(this);
        welcomeAdminScreen.setUsernameLabel(adminManager.getCurrentAdmin().toString());
        specializationsScreen.dispose();
    }
    //-------- SpecializationsScreen - END --------




    //-------- NewEditSpecializationScreen - START --------
    public void handleAddSaveSpecialization() {
        String name = addSpecializationScreen.getSpecializationName();
        int cost;
        if (name == null || name.isEmpty() || name.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please input a specialization name.");
        } else {
            try {
                cost = Integer.parseInt(addSpecializationScreen.getCost());
                if (cost == 0) {
                    JOptionPane.showMessageDialog(null, "Cost can not be 0.");
                } else {
                    Specialization specialization = new Specialization(name, cost);
                    adminManager.saveSpecialization(specialization);
                    specializationsScreen = new SpecializationsScreen(this);
                    handleUpdateSpecializationList(adminManager.getSpecializations());
                    addSpecializationScreen.dispose();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please input an cost as an int.");
            }
        }
    }

    public void handleEditSaveSpecialization() {
        String editedName = editSpecializationScreen.getSpecializationName();
        int editedCost;
        if (editedName == null || editedName.isEmpty() || editedName.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please input a specialization name.");
        } else {
            try {
                editedCost = Integer.parseInt(editSpecializationScreen.getCost());
                if (editedCost == 0) {
                    JOptionPane.showMessageDialog(null, "Cost can not be 0.");
                } else {
                    Specialization specialization = adminManager.getSelectedSpecialization();
                    Specialization editedSpecialization = new Specialization(editedName, editedCost);
                    editedSpecialization.setId(specialization.getId());
                    adminManager.saveEditedSpecialization(editedSpecialization);
                    adminManager.setSelectedSpecialization(null);
                    specializationsScreen = new SpecializationsScreen(this);
                    handleUpdateSpecializationList(adminManager.getSpecializations());
                    editSpecializationScreen.dispose();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please input an cost as an int.");
            }
        }
    }

    public void handleCancelEditSpecialization() {

    }


    public void handleCancelNewEditSpecialization() {
        specializationsScreen = new SpecializationsScreen(this);
        handleUpdateSpecializationList(adminManager.getSpecializations());
        adminManager.setSelectedSpecialization(null);
        editSpecializationScreen.dispose();
    }
    //-------- NewEditSpecializationScreen - END --------

    //-------- WelcomePatientScreen - START --------
    public void logInAsPatient() {
        welcomePatientScreen = new WelcomePatientScreen(this);
        patientLogIn.dispose();
    }

    public void viewInfoPatient() {

    }

    public void bookAnAppointmentPatient() {
        chooseBookDoctorScreen = new ChooseBookDoctorScreen(this);
        welcomePatientScreen.dispose();
    }

    public void handleBookATimeBtn() {
        scheduleScreen = new ScheduleScreen(this);
        chooseBookDoctorScreen.dispose();
    }

    public void handleBackFromChooseBookDoctorScreen() {
        welcomePatientScreen = new WelcomePatientScreen(this);
        chooseBookDoctorScreen.dispose();
    }

    public void handleBackFromScheduleScreen() {
        chooseBookDoctorScreen = new ChooseBookDoctorScreen(this);
        scheduleScreen.dispose();
    }
/*
    //hur gör vi med denna?
    public void viewMedicalRecordsPatient() {
        medicalRecordsScreen = new MedicalRecordsScreen(this);
        welcomePatientScreen.dispose();
    }
 */

    public void logOutPatient() {
        patientLogIn = new PatientLogIn(this);
        welcomePatientScreen.dispose();
    }
    //-------- WelcomePatientScreen - END --------


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case AdminManager.UPDATE_SPECIALIZATION_LIST: {
                handleUpdateSpecializationList((List<Specialization>) evt.getNewValue());
                break;
            }
            case AdminManager.UPDATE_DOCTORS_LIST: {
                handleUpdateDoctorList((List<Doctor>) evt.getNewValue());
                break;
            }
        }
    }

    private void handleUpdateDoctorList(List<Doctor> doctors) {
        if (doctorsScreen != null) {
            doctorsScreen.displayDoctors(doctors);
        }
    }

    private void handleUpdateSpecializationList(List<Specialization> specializations) {
        if (specializationsScreen != null) {
            specializationsScreen.displaySpecializations(specializations);
        }
    }

    public static void main(String[] args) {
      Controller controller = new Controller();
    }
}