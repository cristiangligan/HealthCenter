package controller;

import model.Admin;
import model.AdminManager;
import model.Doctor;
import model.Specialization;
import view.*;

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

    public void handleBackFromAdminLogIn() {
        logInScreen = new LogInScreen(this);
        adminLogIn.dispose();
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
    //log in as doctor

    public void handleDoctorLogIn() {
        welcomeDoctorScreen = new WelcomeDoctorScreen(this);
        doctorLogIn.dispose();
    }
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
    //ny
    public void handleBackFromRegisterNewPatient() {
        patientLogIn = new PatientLogIn(this);
        registerNewPatientScreen.dispose();
    }

    //metod för att spara ny patient som registrerar sig
    public void handleRegisterNewPatientBtn() {
        JOptionPane.showMessageDialog(null, "Patient registered");
        //new patient saved to database
    }


    //-------- WelcomeUserNameScreen - START --------
    public void handleDoctors() {
        doctorsScreen = new DoctorsScreen(this);
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
    //-------- SpecializationScreen - END --------



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
        newDoctorScreen = new NewDoctorScreen(this);
        doctorsScreen.dispose();
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
            newDoctorScreen.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Please fill in all fields adequately.");
        }
    }

    public void handleCancelNewEditDoctor() {
        doctorsScreen = new DoctorsScreen(this);
        newDoctorScreen.dispose();
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

    public int getEmployerNrChecked() {
        int employerNr = 0;
        try {
            employerNr = Integer.parseInt(newDoctorScreen.getEmployerNumberText());
        } catch (NumberFormatException e) {
            return employerNr;
        }
        return employerNr;
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