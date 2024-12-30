package controller;

import model.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Controller implements PropertyChangeListener {
    private Connection connection;
    private LogInScreen logInScreen;
    private AdminLogIn adminLogIn;
    private DoctorLogInScreen doctorLogInScreen;
    private PatientLogInScreen patientLogInScreen;
    private AdminManager adminManager;
    private WelcomeAdminScreen welcomeAdminScreen;
    private DoctorsScreenInAdmin doctorsScreenInAdmin;
    private NewDoctorScreen newDoctorScreen;
    private PatientsScreenFromAdmin patientsScreenFromAdmin;
    private PatientsScreenFromDoctor patientsScreenFromDoctor;
    private SpecializationsScreen specializationsScreen;
    private AddSpecializationScreen addSpecializationScreen;
    private EditSpecializationScreen editSpecializationScreen;
    private MedicalRecordsScreen medicalRecordsScreen;
    private DiagnosisScreen diagnosisScreen;
    private UpcomingAppointmentsScreen upcomingAppointmentsScreen;
    private RegisterNewPatientScreen registerNewPatientScreen;
    private WelcomePatientScreen welcomePatientScreen;
    private ChooseBookDoctorScreen chooseBookDoctorScreen;
    private PatientScheduleScreen patientScheduleScreen;
    private WelcomeDoctorScreen welcomeDoctorScreen;
    private EditDoctorScreen editDoctorScreen;
    private DoctorManager doctorManager;
    private PatientManager patientManager;

    //nytt
    private EditInfoPatientScreen editInfoPatientScreen;
    private ViewMyInfoPatient viewMyInfoPatient;

    //constructor
    public Controller() {
        logInScreen = new LogInScreen(this);
        logInToDataBase();
    }





    //------------------------------------------CONNECTION - START----------------------------------------------
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
    //------------------------------------------CONNECTION - END-------------------------------------------





    //------------------------------------------ADMIN - START----------------------------------------------
    //-------- LogIn/LogOut - START --------
    public void chooseAdminLogin() {
        adminLogIn = new AdminLogIn(this);
        logInScreen.dispose();
    }



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



    public void logOutAdmin() {
        logInScreen = new LogInScreen(this);
        adminManager.setCurrentAdmin(null);
        welcomeAdminScreen.dispose();
    }
    //-------- LogIn/LogOut - END --------



    //-------- Doctors - START --------
    public void handleDoctorsFromAdmin() {
        doctorsScreenInAdmin = new DoctorsScreenInAdmin(this);
        doctorsScreenInAdmin.displayDoctors(adminManager.getDoctors());
        welcomeAdminScreen.dispose();
    }

    public void handleBackFromDoctorsScreen() {
        welcomeAdminScreen = new WelcomeAdminScreen(this);
        welcomeAdminScreen.setUsernameLabel(adminManager.getCurrentAdmin().toString());
        doctorsScreenInAdmin.dispose();
    }



    public void handleAddNewDoctor() {
        newDoctorScreen = new NewDoctorScreen(this);
        doctorsScreenInAdmin.dispose();
    }



    public void handleEditDoctor() {
        Doctor selectedDoctor = doctorsScreenInAdmin.getSelectedDoctor();
        if (selectedDoctor == null) {
            JOptionPane.showMessageDialog(null, "Please select a doctor to edit.");
            return;
        }
        editDoctorScreen = new EditDoctorScreen(this, selectedDoctor);
        Specialization specialization = selectedDoctor.getSpecialization();
        editDoctorScreen.setSpecializationSelectedDoctor(specialization);
        doctorsScreenInAdmin.dispose();
    }



    public void handleDeleteDoctor() {
        Doctor selectedDoctor = doctorsScreenInAdmin.getSelectedDoctor();
        if (selectedDoctor == null) {
            JOptionPane.showMessageDialog(null, "Please select a doctor to delete.");
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected doctor?","Delete doctor", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            adminManager.deleteDoctor(selectedDoctor); //delete the selected doctor from the database
            doctorsScreenInAdmin.displayDoctors(adminManager.getDoctors()); //uppdatera gränssnittet
            JOptionPane.showMessageDialog(null, "Doctor deleted successfully.");
        }
    }



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
            doctorsScreenInAdmin = new DoctorsScreenInAdmin(this);
            doctorsScreenInAdmin.displayDoctors(adminManager.getDoctors());
            newDoctorScreen.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Please fill in all fields adequately.");
        }
    }



    public void handleSaveEditedDoctor() {
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
        doctorsScreenInAdmin = new DoctorsScreenInAdmin(this);
        doctorsScreenInAdmin.displayDoctors(adminManager.getDoctors());
        editDoctorScreen.dispose();
    }



    public void handleCancelNewDoctor() {
        doctorsScreenInAdmin = new DoctorsScreenInAdmin(this);
        doctorsScreenInAdmin.displayDoctors(adminManager.getDoctors());
        newDoctorScreen.dispose();
    }



    public void handleCancelEditDoctor() {
        doctorsScreenInAdmin = new DoctorsScreenInAdmin(this);
        doctorsScreenInAdmin.displayDoctors(adminManager.getDoctors());
        editDoctorScreen.dispose();
    }



    public void handleBackFromPatientScreenInDoctor() {
        welcomeDoctorScreen = new WelcomeDoctorScreen(this);
        welcomeDoctorScreen.setWelcomeDoctorLabel(doctorManager.getCurrentDoctor().toString());
        patientsScreenFromDoctor.dispose();
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



    private void handleUpdateDoctorList(List<Doctor> doctors) {
        if (doctorsScreenInAdmin != null) {
            doctorsScreenInAdmin.displayDoctors(doctors);
        }
    }
    //-------- Doctors - END --------



    //-------- Patients - START --------
    public void handlePatients() {
        patientsScreenFromAdmin = new PatientsScreenFromAdmin(this);
        patientsScreenFromAdmin.displayPatients(adminManager.getPatients());
        welcomeAdminScreen.dispose();
    }



    public void handleBackFromPatientScreen() {
        welcomeAdminScreen = new WelcomeAdminScreen(this);
        welcomeAdminScreen.setUsernameLabel(adminManager.getCurrentAdmin().toString());
        patientsScreenFromAdmin.dispose();
    }



    public void handleViewMedicalRecord() {
        medicalRecordsScreen = new MedicalRecordsScreen(this);
        patientsScreenFromDoctor.dispose();
    }



    public void handleBackFromMedicalRecord() {
        patientsScreenFromAdmin = new PatientsScreenFromAdmin(this);
        patientsScreenFromAdmin.displayPatients(adminManager.getPatients());
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
    //-------- Patients - END --------



    //-------- Appointments - START --------
    public void handleAppointments() {
        upcomingAppointmentsScreen = new UpcomingAppointmentsScreen(this);
        welcomeAdminScreen.dispose();
    }

    public void handleBackFromUpcomingAppointmentsScreen() {
        welcomeAdminScreen = new WelcomeAdminScreen(this);
        welcomeAdminScreen.setUsernameLabel(adminManager.getCurrentAdmin().toString());
        upcomingAppointmentsScreen.dispose();
    }
    //-------- Appointments - END --------



    //-------- Specializations - START --------
    public void handleSpecializations() {
        specializationsScreen = new SpecializationsScreen(this);
        handleUpdateSpecializationList(adminManager.getSpecializations());
        welcomeAdminScreen.dispose();
    }



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



    public void handleSaveNewSpecialization() {
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

    public void handleSaveEditedSpecialization() {
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


    public void handleCancelAddSpecialization() {
        specializationsScreen = new SpecializationsScreen(this);
        handleUpdateSpecializationList(adminManager.getSpecializations());
        adminManager.setSelectedSpecialization(null);
        editSpecializationScreen.dispose();
    }



    private void handleUpdateSpecializationList(List<Specialization> specializations) {
        if (specializationsScreen != null) {
            specializationsScreen.displaySpecializations(specializations);
        }
    }
    //-------- Specializations - END --------
    //------------------------------------------ADMIN - END------------------------------------------------





    //------------------------------------------DOCTOR - START---------------------------------------------
    //-------- LogIn/LogOut - START --------
    public void chooseDoctorLogin() {
        doctorLogInScreen = new DoctorLogInScreen(this);
        logInScreen.dispose();
    }



    public void handleBackFromDoctorLogInScreen() {
        try {
            logInScreen = new LogInScreen(this);
            doctorLogInScreen.dispose();
        }catch (NullPointerException e) {
            System.out.println(e);
        }
    }



    public void handleDoctorLogIn() {
        doctorManager = new DoctorManager(connection);
        doctorManager.subscribeListener(this);

        try {
            int medicalId = doctorLogInScreen.getMedicalId();
            Doctor doctor = doctorManager.verifyDoctor(medicalId);

            if (doctor != null) {
                doctorManager.setCurrentDoctor(doctor);
                welcomeDoctorScreen = new WelcomeDoctorScreen(this);
                welcomeDoctorScreen.setWelcomeDoctorLabel(doctor.toString());
                doctorLogInScreen.dispose();
                System.out.println(doctorManager.getCurrentDoctor());
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect medical number. Please try again.");
                doctorLogInScreen.clearFields();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Medical ID must be a number");
            doctorLogInScreen.clearFields();
        }
    }



    public void logOutDoctor() {
        logInScreen = new LogInScreen(this);
        welcomeDoctorScreen.dispose();
    }
    //-------- LogIn/LogOut - END --------



    public void viewMyPatients() {
        patientsScreenFromDoctor = new PatientsScreenFromDoctor(this);
        welcomeDoctorScreen.dispose();
    }

    public void viewMySchedule() {
    }


    public void viewMyAppointments() {
    }
    //------------------------------------------DOCTOR - END-----------------------------------------------





    //------------------------------------------PATIENT - START--------------------------------------------
    //-------- LogIn/LogOut - START --------
    public void choosePatientLogin() {
        patientLogInScreen = new PatientLogInScreen(this);
        logInScreen.dispose();
    }



    public void handleBackFromPatientLogInScreen() {
        logInScreen = new LogInScreen(this);
        patientLogInScreen.dispose();
    }



    public void handlePatientLogIn() {
        patientManager = new PatientManager(connection);
        patientManager.subscribeListener(this);

        try {
            String patientIdText = String.valueOf(patientLogInScreen.getPatientIdText());
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
                patientLogInScreen.dispose();
                System.out.println(patientManager.getCurrentPatient());
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect medical number. Please try again.");
                patientLogInScreen.clearFields();
                ;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Medical number must be a valid number. Please try again.");
            patientLogInScreen.clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void logInAsPatient() {
        if(patientManager == null) {
            patientManager = new PatientManager(connection);
        }
        welcomePatientScreen = new WelcomePatientScreen(this);
        patientLogInScreen.dispose();
    }



    public void logOutPatient() {
        patientLogInScreen = new PatientLogInScreen(this);
        welcomePatientScreen.dispose();
    }
    //-------- LogIn/LogOut - END --------


    //-------- Registration - START --------
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
            welcomePatientScreen = new WelcomePatientScreen(this);
            Patient currentPatient = patientManager.getPatientInfo(firstName, lastName);
            welcomePatientScreen.setWelcomePatient(currentPatient.getFirstName() + " " + currentPatient.getLastName());
        } else {
            JOptionPane.showMessageDialog(null, "Failed to register patient. Please try again.");
        }
    }



    public void handleBtnRegisterNewPatient() {
        registerNewPatientScreen = new RegisterNewPatientScreen(this);
        patientLogInScreen.dispose();
    }



    public void handleBackFromRegisterNewPatientScreen() {
        patientLogInScreen = new PatientLogInScreen(this);
        registerNewPatientScreen.dispose();
    }
    //-------- Registration - END --------




    public void handleViewMyInfoPatient() {
        Patient loggedInPatient = patientManager.getLoggedInPatient(); //hämtar den inloggade patienten
        if (loggedInPatient != null) {
            viewMyInfoPatient = new ViewMyInfoPatient(this);
            viewMyInfoPatient.displayMyInfoPatient(loggedInPatient);
            welcomePatientScreen.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error, no patient is currently logged in.");
        }
    }

    public void handleEditMyInfoButton() {
        Patient loggedInPatient = patientManager.getLoggedInPatient();
        if (loggedInPatient != null) {
            editInfoPatientScreen = new EditInfoPatientScreen(this);
            editInfoPatientScreen.displayEditMyInfo(loggedInPatient);
            viewMyInfoPatient.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error: No patient is currently logged in.");
        }
    }

    public void handleBackFromMyInfo() {
        welcomePatientScreen = new WelcomePatientScreen(this);
        welcomePatientScreen.setWelcomePatient(patientManager.getCurrentPatient().toString());
        viewMyInfoPatient.dispose();
    }

    public void handleCancelFromEditMyInfo() {
        if (editInfoPatientScreen != null) {
            editInfoPatientScreen.dispose();
            viewMyInfoPatient = new ViewMyInfoPatient(this);
            Patient loggedInPatient = patientManager.getLoggedInPatient();
            viewMyInfoPatient.displayMyInfoPatient(loggedInPatient);
        }
    }

    public void handleSaveEditedPatientInfo() {
        String editedFirstName = editInfoPatientScreen.getFirstName();
        String editedLastName = editInfoPatientScreen.getLastName();
        String editedAddress = editInfoPatientScreen.getAddress();
        String editedPhone = editInfoPatientScreen.getPhone();
        String editedBirthDate = editInfoPatientScreen.getBirthDate();
        String editedGender = editInfoPatientScreen.getGender();

        if (editedFirstName == null || editedFirstName.isBlank() || editedLastName == null || editedLastName.isBlank() || editedAddress == null || editedAddress.isBlank() || editedPhone == null || editedPhone.isBlank() || editedBirthDate == null || editedBirthDate.isBlank() || editedGender == null || editedGender.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
            return;
        }

        if (!editedPhone.matches("\\d+") ) {
            JOptionPane.showMessageDialog(null, "Phone number must contain only digits.");
            return;
        }

        Patient loggedInPatient = patientManager.getLoggedInPatient();
        if (loggedInPatient == null) {
            JOptionPane.showMessageDialog(null, "No patient is currently logged in.");
            return;
        }

        loggedInPatient.setFirstName(editedFirstName);
        loggedInPatient.setLastName(editedLastName);
        loggedInPatient.setGender(editedGender);
        loggedInPatient.setAddress(editedAddress);
        loggedInPatient.setPhone(editedPhone);
        loggedInPatient.setBirthDate(editedBirthDate);

        boolean success = false;
        try {
            success = patientManager.updatePatientInfo(loggedInPatient);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (success) {
            JOptionPane.showMessageDialog(null, "Patient information successfully updated");
            viewMyInfoPatient = new ViewMyInfoPatient(this);
            viewMyInfoPatient.displayMyInfoPatient(loggedInPatient);
            editInfoPatientScreen.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error updating patient information. Please try again.");
        }

    }



    public void bookAnAppointmentPatient() {
        chooseBookDoctorScreen = new ChooseBookDoctorScreen(this);
        chooseBookDoctorScreen.displayDoctors(patientManager.getDoctors());
        welcomePatientScreen.dispose();
    }



    public void handleBookAnAppointment(JButton button) {
        String time = button.getText();
        String day = null;
        Color dayColor = Color.LIGHT_GRAY;
        String date;

        if (button.getBackground() == Color.RED) {
            day = "monday";
            dayColor = Color.RED;
        } else if (button.getBackground() == Color.ORANGE) {
            day = "tuesday";
            dayColor = Color.ORANGE;
        } else if (button.getBackground() == Color.YELLOW) {
            day = "wednesday";
            dayColor = Color.YELLOW;
        } else if (button.getBackground() == Color.GREEN) {
            day = "thursday";
            dayColor = Color.GREEN;
        } else if (button.getBackground() == Color.BLUE) {
            day = "friday";
            dayColor = Color.BLUE;
        }

        Calendar calendar = Calendar.getInstance();
        if (day != null && day.equals("monday")) {
            calendar.add(Calendar.DAY_OF_WEEK, 3);
        } else if (day != null && day.equals("tuesday")) {
            calendar.add(Calendar.DAY_OF_WEEK, 4);
        } else if (day != null && day.equals("wednesday")) {
            calendar.add(Calendar.DAY_OF_WEEK, 5);
        } else if (day != null && day.equals("thursday")) {
            calendar.add(Calendar.DAY_OF_WEEK, 6);
        } else if (day != null && day.equals("friday")) {
            calendar.add(Calendar.DAY_OF_WEEK, 7);
        }

        Date currentDate = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(currentDate);

        int patientId = patientManager.getCurrentPatient().getPatientMedicalId();
        int doctorId = patientManager.getSelectedDoctor().getEmployerNr();

        Appointment appointment = new Appointment(doctorId, patientId, time, date);
        int answer = JOptionPane.showConfirmDialog(null,
            "Book an appointment on " + date + ": " + time, "HealthCenter", JOptionPane.YES_NO_OPTION);
        if (answer == 0) {
            patientManager.bookAppointment(appointment);
            patientScheduleScreen.setButtonsAvailability(dayColor, time);
        }
    }



    public void handleBookATimeBtn() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            if(chooseBookDoctorScreen.isDoctorSelected()) {
                patientManager.setSelectedDoctor(chooseBookDoctorScreen.getSelectedDoctor());
                patientScheduleScreen = new PatientScheduleScreen(this);
                cal.add(Calendar.WEEK_OF_YEAR, 1);
                patientScheduleScreen.setWeekDoctorLbl(String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)), patientManager.getSelectedDoctor().toString());
                Appointment appointment = patientManager.existAppointmentWithDoctor(patientManager.getCurrentPatient(), patientManager.getSelectedDoctor());
                if(appointment != null) {
                    Color dayColor = null;
                    String dateString = appointment.getDate();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = simpleDateFormat.parse(dateString);
                        cal.setTime(date);
                        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                        if(dayOfWeek == 2) {
                            dayColor = Color.RED;
                        } else if (dayOfWeek == 3) {
                            dayColor = Color.ORANGE;
                        } else if (dayOfWeek == 4) {
                            dayColor = Color.YELLOW;
                        } else if (dayOfWeek == 5) {
                            dayColor = Color.GREEN;
                        } else if (dayOfWeek == 6) {
                            dayColor = Color.BLUE;
                        }
                        patientScheduleScreen.setButtonsAvailability(dayColor, appointment.getTime());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                chooseBookDoctorScreen.dispose();
                ArrayList<JButton> buttons = (ArrayList<JButton>) patientScheduleScreen.getButtonList();
            }
        } else {
            JOptionPane.showMessageDialog(null, "You can only book appointments on fridays for the upcoming week.");
        }
    }



    public void handleBackFromChooseBookDoctorScreen() {
        welcomePatientScreen = new WelcomePatientScreen(this);
        welcomePatientScreen.setWelcomePatient(patientManager.getCurrentPatient().toString());
        patientManager.setSelectedDoctor(null);
        chooseBookDoctorScreen.displayDoctors(patientManager.getDoctors());
        chooseBookDoctorScreen.dispose();
    }



    public void handleBackFromScheduleScreen() {
        chooseBookDoctorScreen = new ChooseBookDoctorScreen(this);
        chooseBookDoctorScreen.displayDoctors(patientManager.getDoctors());
        patientScheduleScreen.dispose();
    }
    //------------------------------------------PATIENT - END--------------------------------------------------------



    //------------------------------------------PROPERTY_CHANGE - START----------------------------------------------
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
    //------------------------------------------PROPERTY_CHANGE - END------------------------------------------------



    public static void main(String[] args) {
      Controller controller = new Controller();
    }
}