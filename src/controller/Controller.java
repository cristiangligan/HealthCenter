package controller;

import model.*;
import model.common.User;
import model.managers.AdminManager;
import model.managers.DoctorManager;
import model.managers.PatientManager;
import model.managers.UserManager;
import view.*;
import view.admin.*;
import view.doctor.*;
import view.patient.*;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

public class Controller implements PropertyChangeListener {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Connection connection;

    // Managers
    private AdminManager adminManager;
    private DoctorManager doctorManager;
    private PatientManager patientManager;
    private UserManager userManager;

    // Admin screens
    private AdminLogInScreen adminLogInScreen;
    private AdminWelcomeScreen adminWelcomeScreen;
    private AdminDoctorsScreen adminDoctorsScreen;
    private AdminNewDoctorScreen adminNewDoctorScreen;
    private AdminEditDoctorScreen adminEditDoctorScreen;
    private AdminPatientsScreen adminPatientsScreen;
    private AdminUpcomingAppointmentsScreen adminUpcomingAppointmentsScreen;
    private AdminSpecializationsScreen adminSpecializationsScreen;
    private AdminAddSpecializationScreen adminAddSpecializationScreen;
    private AdminEditSpecializationScreen adminEditSpecializationScreen;

    // Doctor screens
    private DoctorLogInScreen doctorLogInScreen;
    private DoctorWelcomeScreen doctorWelcomeScreen;
    private DoctorPatientsScreen doctorPatientsScreen;
    private DoctorScheduleScreen doctorScheduleScreen;
    private DoctorMyAppointmentsScreen doctorMyAppointmentsScreen;

    // Patient screens
    private PatientLogInScreen patientLogInScreen;
    private PatientRegisterScreen patientRegisterScreen;
    private PatientWelcomeScreen patientWelcomeScreen;
    private PatientViewMyInfoScreen patientViewMyInfoScreen;
    private PatientEditInfoScreen patientEditInfoScreen;
    private PatientChooseBookDoctorScreen patientChooseBookDoctorScreen;
    private PatientScheduleScreen patientScheduleScreen;

    // Common screens
    private LogInScreen logInScreen;
    private MedicalRecordsScreen medicalRecordsScreen;
    private MedicalRecordScreen medicalRecordScreen;

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
                userManager = new UserManager(connection);
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
        adminLogInScreen = new AdminLogInScreen(this);
        logInScreen.dispose();
    }

    public void handleAdminLogIn() {
        adminManager = new AdminManager(connection);
        adminManager.subscribeListener(this);
        String username = adminLogInScreen.getUsername();
        String password = adminLogInScreen.getPassword();
        Admin admin = new Admin(username, password);
        admin = adminManager.verifyAdmin(admin);
        if(admin != null) {
            userManager.setCurrentUser(admin);
            adminManager.setCurrentAdmin(admin);
            adminWelcomeScreen = new AdminWelcomeScreen(this);
            adminWelcomeScreen.setUsernameLabel(admin.toString());
            adminLogInScreen.dispose();
            System.out.println(adminManager.getCurrentAdmin());
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect username or password.");
            adminLogInScreen.clearFields();
        }
    }

    public void handleBackFromAdminLogIn() {
        logInScreen = new LogInScreen(this);
        adminLogInScreen.dispose();
    }
    //-------- LogIn/LogOut - END --------


    //-------- Doctors - START --------
    public void handleDoctorsFromAdmin() {
        adminDoctorsScreen = new AdminDoctorsScreen(this);
        adminDoctorsScreen.displayDoctors(adminManager.getDoctors());
        adminWelcomeScreen.dispose();
    }

    public void handleBackFromDoctorsScreen() {
        adminWelcomeScreen = new AdminWelcomeScreen(this);
        adminWelcomeScreen.setUsernameLabel(adminManager.getCurrentAdmin().toString());
        adminDoctorsScreen.dispose();
    }

    public void handleAddNewDoctor() {
        adminNewDoctorScreen = new AdminNewDoctorScreen(this);
        adminDoctorsScreen.dispose();
    }

    public void handleEditDoctor() {
        Doctor selectedDoctor = adminDoctorsScreen.getSelectedDoctor();
        if (selectedDoctor == null) {
            JOptionPane.showMessageDialog(null, "Please select a doctor to edit.");
            return;
        }
        adminEditDoctorScreen = new AdminEditDoctorScreen(this, selectedDoctor);
        Specialization specialization = selectedDoctor.getSpecialization();
        adminEditDoctorScreen.setSpecializationSelectedDoctor(specialization);
        adminDoctorsScreen.dispose();
    }

    public void handleDeleteDoctor() {
        Doctor selectedDoctor = adminDoctorsScreen.getSelectedDoctor();
        if (selectedDoctor == null) {
            JOptionPane.showMessageDialog(null, "Please select a doctor to delete.");
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected doctor?","Delete doctor", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            adminManager.deleteDoctor(selectedDoctor); //delete the selected doctor from the database
            adminDoctorsScreen.displayDoctors(adminManager.getDoctors()); //uppdatera gränssnittet
            JOptionPane.showMessageDialog(null, "Doctor deleted successfully.");
        }
    }

    public void handleSaveNewDoctor() {
        Doctor doctor = adminNewDoctorScreen.getDoctorInfo();
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
            adminDoctorsScreen = new AdminDoctorsScreen(this);
            adminDoctorsScreen.displayDoctors(adminManager.getDoctors());
            adminNewDoctorScreen.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Please fill in all fields adequately.");
        }
    }

    public void handleSaveEditedDoctor() {
        Doctor editedDoctor = adminEditDoctorScreen.getDoctorInfo();
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
        adminDoctorsScreen = new AdminDoctorsScreen(this);
        adminDoctorsScreen.displayDoctors(adminManager.getDoctors());
        adminEditDoctorScreen.dispose();
    }

    public void handleCancelNewDoctor() {
        adminDoctorsScreen = new AdminDoctorsScreen(this);
        adminDoctorsScreen.displayDoctors(adminManager.getDoctors());
        adminNewDoctorScreen.dispose();
    }

    public void handleCancelEditDoctor() {
        adminDoctorsScreen = new AdminDoctorsScreen(this);
        adminDoctorsScreen.displayDoctors(adminManager.getDoctors());
        adminEditDoctorScreen.dispose();
    }

    public void handleBackFromPatientScreenInDoctor() {
        doctorWelcomeScreen = new DoctorWelcomeScreen(this);
        doctorWelcomeScreen.setWelcomeDoctorLabel(doctorManager.getCurrentDoctor().toString());
        doctorPatientsScreen.dispose();
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
            if (adminEditDoctorScreen != null) {
                return Integer.parseInt(adminEditDoctorScreen.getEmployerNumberText());
            } else if (adminNewDoctorScreen != null) {
                return Integer.parseInt(adminNewDoctorScreen.getEmployerNumberText());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Employer number must be a valid integer.");
        }
        return 0;
    }

    private void handleUpdateDoctorList(List<Doctor> doctors) {
        if (adminDoctorsScreen != null) {
            adminDoctorsScreen.displayDoctors(doctors);
        }
    }
    //-------- Doctors - END --------


    //-------- Patients - START --------
    public void handlePatients() {
        adminPatientsScreen = new AdminPatientsScreen(this);
        adminPatientsScreen.displayPatients(adminManager.getPatients());
        adminWelcomeScreen.dispose();
    }

    public void handleBackFromPatientScreen() {
        adminWelcomeScreen = new AdminWelcomeScreen(this);
        adminWelcomeScreen.setUsernameLabel(adminManager.getCurrentAdmin().toString());
        adminPatientsScreen.dispose();
    }

    public void handleSetTotalCost(Patient selectedPatient) {
        ArrayList<Specialization> specializations = adminManager.getDoctorsSpecializationsFromAppointmentsWithPatient(selectedPatient);
        int totalCost = 0;
        for (Specialization specialization: specializations) {
            totalCost += specialization.getCost();
        }
        adminPatientsScreen.setTotalCostLabel(String.valueOf(totalCost));
    }
    //-------- Patients - END --------


    //-------- Appointments - START --------
    public void handleAppointments() {
        adminUpcomingAppointmentsScreen = new AdminUpcomingAppointmentsScreen(this);
        ArrayList<Appointment> appointments = adminManager.getUpcomingAppointments(Calendar.getInstance().getTime());
        adminUpcomingAppointmentsScreen.displayAppointments(appointments);
        adminWelcomeScreen.dispose();
    }

    public void handleBackFromUpcomingAppointmentsScreen() {
        adminWelcomeScreen = new AdminWelcomeScreen(this);
        adminWelcomeScreen.setUsernameLabel(adminManager.getCurrentAdmin().toString());
        adminUpcomingAppointmentsScreen.dispose();
    }
    //-------- Appointments - END --------


    //-------- Specializations - START --------
    public void handleSpecializations() {
        adminSpecializationsScreen = new AdminSpecializationsScreen(this);
        handleUpdateSpecializationList(adminManager.getSpecializations());
        adminWelcomeScreen.dispose();
    }

    public void handleAddNewSpecialization() {
        adminAddSpecializationScreen = new AdminAddSpecializationScreen(this);
        adminSpecializationsScreen.dispose();
    }

    public void handleEditSpecialization() {
        Specialization specialization = adminSpecializationsScreen.getSelectedSpecialization();
        if (specialization != null) {
            adminManager.setSelectedSpecialization(specialization);
            adminEditSpecializationScreen = new AdminEditSpecializationScreen(this);
            adminEditSpecializationScreen.displaySpecializationInfo(specialization);
            adminSpecializationsScreen.dispose();
        }
    }

    public void handleDeleteSpecialization() {
        //hämtar den valda specialiseringen från GUI
        Specialization selectedSpecialization = adminSpecializationsScreen.getSelectedSpecialization();
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
            adminSpecializationsScreen.displaySpecializations(adminManager.getSpecializations()); //uppdatera gränssnittet
            JOptionPane.showMessageDialog(null, "Specialization deleted successfully.");
        }
    }

    public void handleBackFromSpecializationsScreen() {
        adminWelcomeScreen = new AdminWelcomeScreen(this);
        adminWelcomeScreen.setUsernameLabel(adminManager.getCurrentAdmin().toString());
        adminSpecializationsScreen.dispose();
    }

    public void handleSaveNewSpecialization() {
        String name = adminAddSpecializationScreen.getSpecializationName();
        int cost;
        if (name == null || name.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please input a specialization name.");
        } else {
            try {
                cost = Integer.parseInt(adminAddSpecializationScreen.getCost());
                if (cost == 0) {
                    JOptionPane.showMessageDialog(null, "Cost can not be 0.");
                } else {
                    Specialization specialization = new Specialization(name, cost);
                    adminManager.saveSpecialization(specialization);
                    adminSpecializationsScreen = new AdminSpecializationsScreen(this);
                    handleUpdateSpecializationList(adminManager.getSpecializations());
                    adminAddSpecializationScreen.dispose();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please input an cost as an int.");
            }
        }
    }

    public void handleSaveEditedSpecialization() {
        String editedName = adminEditSpecializationScreen.getSpecializationName();
        int editedCost;
        if (editedName == null || editedName.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please input a specialization name.");
        } else {
            try {
                editedCost = Integer.parseInt(adminEditSpecializationScreen.getCost());
                if (editedCost == 0) {
                    JOptionPane.showMessageDialog(null, "Cost can not be 0.");
                } else {
                    Specialization specialization = adminManager.getSelectedSpecialization();
                    Specialization editedSpecialization = new Specialization(editedName, editedCost);
                    editedSpecialization.setId(specialization.getId());
                    adminManager.saveEditedSpecialization(editedSpecialization);
                    adminManager.setSelectedSpecialization(null);
                    adminSpecializationsScreen = new AdminSpecializationsScreen(this);
                    handleUpdateSpecializationList(adminManager.getSpecializations());
                    adminEditSpecializationScreen.dispose();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please input an cost as an int.");
            }
        }
    }

    public void handleCancelAddSpecialization() {
        adminSpecializationsScreen = new AdminSpecializationsScreen(this);
        handleUpdateSpecializationList(adminManager.getSpecializations());
        adminManager.setSelectedSpecialization(null);
        adminEditSpecializationScreen.dispose();
    }

    private void handleUpdateSpecializationList(List<Specialization> specializations) {
        if (adminSpecializationsScreen != null) {
            adminSpecializationsScreen.displaySpecializations(specializations);
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
        } catch (NullPointerException e) {
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
                userManager.setCurrentUser(doctor);
                doctorManager.setCurrentDoctor(doctor);
                doctorWelcomeScreen = new DoctorWelcomeScreen(this);
                doctorWelcomeScreen.setWelcomeDoctorLabel(doctor.toString());
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
    //-------- LogIn/LogOut - END --------


    public void viewMyPatients() {
        doctorPatientsScreen = new DoctorPatientsScreen(this);
        ArrayList<Patient> patients = doctorManager.getDoctorsPatients(doctorManager.getCurrentDoctor());
        doctorPatientsScreen.displayPatients(patients);
        doctorWelcomeScreen.dispose();
    }

    public void viewMySchedule() {
        doctorScheduleScreen = new DoctorScheduleScreen(this);
        doctorWelcomeScreen.dispose();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        doctorScheduleScreen.setWeekDoctorLbl(String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)), doctorManager.getCurrentDoctor().toString());
        int currDay = cal.get(Calendar.DAY_OF_WEEK);
        int diff = currDay - Calendar.MONDAY;
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_WEEK, -6);
        } else {
            cal.add(Calendar.DAY_OF_WEEK, -diff);
        }
        String date = simpleDateFormat.format(cal.getTime());
        ArrayList<Appointment> appointments = doctorManager.getMyUpcomingAppointments(doctorManager.getCurrentDoctor(), date);
        for (Appointment appointment : appointments) {
            if(appointment != null) {
                Color dayColor = null;
                String appointmentDateString = appointment.getDate();
                try {
                    Date appointmentDate = simpleDateFormat.parse(appointmentDateString);
                    cal.setTime(appointmentDate);
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
                    doctorScheduleScreen.setButtonsAvailability(dayColor, appointment.getTime());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void viewMyAppointments() {
        ArrayList<Appointment> appointments = doctorManager.getDoctorsAppointments(doctorManager.getCurrentDoctor());
        doctorMyAppointmentsScreen = new DoctorMyAppointmentsScreen(this);
        doctorMyAppointmentsScreen.displayAppointments(appointments);
        doctorWelcomeScreen.dispose();
    }

    public void handleBackMyAppointmentsScreen() {
        doctorMyAppointmentsScreen.dispose();
        doctorWelcomeScreen = new DoctorWelcomeScreen(this);
        doctorWelcomeScreen.setWelcomeDoctorLabel(doctorManager.getCurrentDoctor().getFirstName());
    }

    public void handleBackFromDoctorScheduleScreen() {
        doctorScheduleScreen.dispose();
        doctorWelcomeScreen = new DoctorWelcomeScreen(this);
        doctorWelcomeScreen.setWelcomeDoctorLabel(doctorManager.getCurrentDoctor().getFirstName() + " " + doctorManager.getCurrentDoctor().getLastName());
    }

    public void handleMakeTimeslotUnavailable(JButton button) {
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
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        if (day != null && day.equals("monday")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else if (day != null && day.equals("tuesday")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        } else if (day != null && day.equals("wednesday")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        } else if (day != null && day.equals("thursday")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        } else if (day != null && day.equals("friday")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        }

        Date currentDate = calendar.getTime();
        date = simpleDateFormat.format(currentDate);

        int patientId = -1;
        int doctorId = doctorManager.getCurrentDoctor().getEmployerNr();

        Appointment appointment = new Appointment(doctorId, patientId, time, date);
        int answer = JOptionPane.showConfirmDialog(null,
            "Book an appointment on " + date + ": " + time, "HealthCenter", JOptionPane.YES_NO_OPTION);
        if (answer == 0) {
            doctorManager.makeTimeslotUnavailable(appointment);
            doctorScheduleScreen.setButtonsAvailability(dayColor, time);
        }
    }

    public void handleAddMedicalRecord() {
        String dateString = simpleDateFormat.format(new Date());
        Doctor doctor = doctorManager.getCurrentDoctor();
        Patient patient = doctorManager.getSelectedPatient();
        Appointment appointment = doctorManager.getAppointmentForPatient(doctor, patient, dateString);
        if (appointment != null) {
            medicalRecordScreen = new MedicalRecordScreen(null, this);
            medicalRecordScreen.setDateLabel(dateString);
            medicalRecordScreen.setDoctorLabel(doctor.getFirstName() + " " + doctor.getLastName());
            medicalRecordScreen.setCostLabel(doctor.getSpecialization().getCost());
            medicalRecordsScreen.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "No appointment with patient today.", "Add appointment", WARNING_MESSAGE);
        }
    }

    public void handleSaveNewMedicalRecord() {
        MedicalRecord medicalRecord = medicalRecordScreen.getMedicalFromView(doctorManager.getCurrentDoctor().getEmployerNr(), doctorManager.getSelectedPatient().getMedicalId());
        if (medicalRecord.getDiagnosis().isBlank() || medicalRecord.getDescription().isBlank() || medicalRecord.getPrescription().isBlank()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Save medical record", WARNING_MESSAGE);
            return;
        }
        int option = JOptionPane.showConfirmDialog(null,
            "Are you sure you want save?", "Save medical record", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            doctorManager.saveNewMedicalRecord(medicalRecord);
            medicalRecordsScreen = new MedicalRecordsScreen(this);
            ArrayList<MedicalRecord> medicalRecords = userManager.getMedicalRecords(doctorManager.getSelectedPatient());
            medicalRecordsScreen.displayMedicalRecords(medicalRecords);
            medicalRecordScreen.dispose();
        }
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
            String patientIdText = patientLogInScreen.getPatientIdText();
            System.out.println("Input from text field " + patientIdText); //debugg

            if (patientIdText.isBlank()) {
                JOptionPane.showMessageDialog(null, "You must fill in your medical ID! Please try again.", "Patient log in", WARNING_MESSAGE);
                return; //avbryter processen om fältet är tomt
            }

            Pattern patternNineDigits = Pattern.compile("^[1-9][0-9]{8}$");
            Matcher matcher = patternNineDigits.matcher(patientIdText);
            if (!matcher.matches()) {
                JOptionPane.showMessageDialog(null, "Invalid medical ID format!\nThe medical ID must have exactly 9 digits.", "Patient log in", WARNING_MESSAGE);
                return;
            }

            int patientId = Integer.parseInt(patientIdText);
            Patient patient = patientManager.verifyPatient(patientId);

            if (patient != null) {
                System.out.println("Logged in as: " + patient.getFirstName() + " " + patient.getLastName());
                userManager.setCurrentUser(patient);
                patientManager.setCurrentPatient(patient);
                patientWelcomeScreen = new PatientWelcomeScreen(this);
                patientWelcomeScreen.setWelcomePatient(patient.toString());
                patientLogInScreen.dispose();
                System.out.println(patientManager.getCurrentPatient());
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect medical number. Please try again.", "Patient log in", WARNING_MESSAGE);
                patientLogInScreen.clearFields();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Medical number must be a valid number. Please try again.", "Patient log in", WARNING_MESSAGE);
            patientLogInScreen.clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //-------- LogIn/LogOut - END --------


    //-------- Registration - START --------
    //metod för att spara ny patient som registrerar sig
    public void handleRegisterNewPatient() {
        patientManager = new PatientManager(connection);
        patientManager.subscribeListener(this);

        String medicalIdText = patientRegisterScreen.getMedicalId();
        String firstName = patientRegisterScreen.getFirstName();
        String lastName = patientRegisterScreen.getLastname();
        String gender = patientRegisterScreen.getGender();
        String address = patientRegisterScreen.getAddress();
        String phone = patientRegisterScreen.getPhone();
        String birthDate = patientRegisterScreen.getBirthDate();

        if (medicalIdText.isBlank() || firstName.isBlank() || lastName.isBlank() || gender.isBlank() || address.isBlank() || phone.isBlank() || birthDate.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields!", "Register new patient", WARNING_MESSAGE);
            return;
        }

        Pattern patternNineDigits = Pattern.compile("^[1-9][0-9]{8}$");
        Matcher matcher = patternNineDigits.matcher(medicalIdText);
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(null, "Invalid medical ID format!\nThe medical ID must have exactly 9 digits.", "Register new patient", WARNING_MESSAGE);
            return;
        }

        if (!phone.matches("\\d+") ) {
            JOptionPane.showMessageDialog(null, "Phone number must contain only digits.", "Edit patient info", WARNING_MESSAGE);
            return;
        }

        try {
            java.sql.Date.valueOf(birthDate);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format!\nPlease fill in the date in the format \"yyyy-mm-dd\".", "Register new patient", WARNING_MESSAGE);
            return;
        }

        int medicalId = Integer.parseInt(medicalIdText);
        boolean successSavingPatient = patientManager.saveNewPatient(medicalId, firstName, lastName, gender, address, phone, birthDate);

        if (successSavingPatient) {
            JOptionPane.showMessageDialog(null, "Patient registered.");
            Patient currentPatient = patientManager.getPatientInfo(medicalId);
            userManager.setCurrentUser(currentPatient);
            patientManager.setCurrentPatient(currentPatient);
            patientRegisterScreen.dispose();
            patientWelcomeScreen = new PatientWelcomeScreen(this);
            patientWelcomeScreen.setWelcomePatient(currentPatient.getFirstName() + " " + currentPatient.getLastName());
        } else {
            JOptionPane.showMessageDialog(null, "Failed to register patient! Please try again.", "Register new patient", WARNING_MESSAGE);
        }
    }

    public void handleBtnRegisterNewPatient() {
        patientRegisterScreen = new PatientRegisterScreen(this);
        patientLogInScreen.dispose();
    }

    public void handleBackFromRegisterNewPatientScreen() {
        patientLogInScreen = new PatientLogInScreen(this);
        patientRegisterScreen.dispose();
    }
    //-------- Registration - END --------


    public void handleViewMyInfoPatient() {
        Patient loggedInPatient = patientManager.getLoggedInPatient(); //hämtar den inloggade patienten
        if (loggedInPatient != null) {
            patientViewMyInfoScreen = new PatientViewMyInfoScreen(this);
            patientViewMyInfoScreen.displayMyInfoPatient(loggedInPatient);
            patientWelcomeScreen.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error, no patient is currently logged in.");
        }
    }

    public void handleEditMyInfoButton() {
        Patient loggedInPatient = patientManager.getLoggedInPatient();
        if (loggedInPatient != null) {
            patientEditInfoScreen = new PatientEditInfoScreen(this);
            patientEditInfoScreen.displayEditMyInfo(loggedInPatient);
            patientViewMyInfoScreen.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error: No patient is currently logged in.");
        }
    }

    public void handleBackFromMyInfo() {
        patientWelcomeScreen = new PatientWelcomeScreen(this);
        patientWelcomeScreen.setWelcomePatient(patientManager.getCurrentPatient().toString());
        patientViewMyInfoScreen.dispose();
    }

    public void handleCancelFromEditMyInfo() {
        if (patientEditInfoScreen != null) {
            patientEditInfoScreen.dispose();
            patientViewMyInfoScreen = new PatientViewMyInfoScreen(this);
            Patient loggedInPatient = patientManager.getLoggedInPatient();
            patientViewMyInfoScreen.displayMyInfoPatient(loggedInPatient);
        }
    }

    public void handleSaveEditedPatientInfo() {
        String editedFirstName = patientEditInfoScreen.getFirstName();
        String editedLastName = patientEditInfoScreen.getLastName();
        String editedAddress = patientEditInfoScreen.getAddress();
        String editedPhone = patientEditInfoScreen.getPhone();
        String editedBirthDate = patientEditInfoScreen.getBirthDate();
        String editedGender = patientEditInfoScreen.getGender();

        if (editedFirstName.isBlank() || editedLastName.isBlank() || editedAddress.isBlank() || editedPhone.isBlank() || editedBirthDate.isBlank() || editedGender.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please fill in all required fields!", "Edit patient info", WARNING_MESSAGE);
            return;
        }

        if (!editedPhone.matches("\\d+") ) {
            JOptionPane.showMessageDialog(null, "Phone number must contain only digits.", "Edit patient info", WARNING_MESSAGE);
            return;
        }

        Patient loggedInPatient = patientManager.getLoggedInPatient();
        if (loggedInPatient == null) {
            JOptionPane.showMessageDialog(null, "No patient is currently logged in.", "Edit patient info", WARNING_MESSAGE);
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
            patientViewMyInfoScreen = new PatientViewMyInfoScreen(this);
            patientViewMyInfoScreen.displayMyInfoPatient(loggedInPatient);
            patientEditInfoScreen.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error updating patient information! Please try again.", "Edit patient info", WARNING_MESSAGE);
        }

    }

    public void bookAnAppointmentPatient() {
        patientChooseBookDoctorScreen = new PatientChooseBookDoctorScreen(this);
        patientChooseBookDoctorScreen.displayDoctors(patientManager.getDoctors());
        patientWelcomeScreen.dispose();
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
        date = simpleDateFormat.format(currentDate);

        int patientId = patientManager.getCurrentPatient().getMedicalId();
        int doctorId = patientManager.getSelectedDoctor().getEmployerNr();

        Appointment appointment = new Appointment(doctorId, patientId, time, date);
        int answer = JOptionPane.showConfirmDialog(null,
            "Book an appointment on " + date + ": " + time, "HealthCenter", JOptionPane.YES_NO_OPTION);
        if (answer == 0) {
            patientManager.bookAppointment(appointment);
            patientScheduleScreen.setButtonsAvailability(dayColor, time, true);
        }
    }

    public void handleBookATimeBtn() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Date currentDate = cal.getTime();
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            if(patientChooseBookDoctorScreen.isDoctorSelected()) {
                patientManager.setSelectedDoctor(patientChooseBookDoctorScreen.getSelectedDoctor());
                patientScheduleScreen = new PatientScheduleScreen(this);
                cal.add(Calendar.WEEK_OF_YEAR, 1);
                patientScheduleScreen.setWeekDoctorLbl(String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)), patientManager.getSelectedDoctor().toString());
                Appointment appointment = patientManager.getAppointmentWithDoctor(patientManager.getCurrentPatient(), patientManager.getSelectedDoctor(), currentDate);
                if(appointment != null) {
                    setButtonsAvailability(appointment, true);
                } else {
                    cal.setTime(currentDate);
                    cal.add(Calendar.DAY_OF_WEEK, 3);
                    Date nextMondayDate = cal.getTime();
                    String nextMondayDateString = simpleDateFormat.format(nextMondayDate);
                    doctorManager = new DoctorManager(connection);
                    ArrayList<Appointment> appointments = doctorManager.getMyUpcomingAppointments(patientManager.getSelectedDoctor(), nextMondayDateString);
                    for(Appointment app : appointments) {
                        setButtonsAvailability(app, false);
                    }
                }
                patientChooseBookDoctorScreen.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "You can only book appointments on fridays for the upcoming week.");
        }
    }

    public void handleBackFromChooseBookDoctorScreen() {
        patientWelcomeScreen = new PatientWelcomeScreen(this);
        patientWelcomeScreen.setWelcomePatient(patientManager.getCurrentPatient().toString());
        patientManager.setSelectedDoctor(null);
        patientChooseBookDoctorScreen.displayDoctors(patientManager.getDoctors());
        patientChooseBookDoctorScreen.dispose();
    }

    public void handleBackFromScheduleScreen() {
        patientChooseBookDoctorScreen = new PatientChooseBookDoctorScreen(this);
        patientChooseBookDoctorScreen.displayDoctors(patientManager.getDoctors());
        patientScheduleScreen.dispose();
    }

    public void setButtonsAvailability(Appointment appointment, Boolean appointmentIsBooked) {
        Color dayColor = null;
        String dateString = appointment.getDate();
        Calendar cal = Calendar.getInstance();
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
            patientScheduleScreen.setButtonsAvailability(dayColor, appointment.getTime(), appointmentIsBooked);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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



//----------------------------------------------COMMON - START---------------------------------------------------
    public void logOut() {
        User currentUser = userManager.getCurrentUser();
        logInScreen = new LogInScreen(this);

        if (currentUser.isAdmin()) {
            adminManager.setCurrentAdmin(null);
            adminWelcomeScreen.dispose();
        } else if (currentUser.isDoctor()) {
            doctorManager.setCurrentDoctor(null);
            doctorManager.setSelectedPatient(null);
            doctorWelcomeScreen.dispose();
        } else if (currentUser.isPatient()) {
            patientManager.setCurrentPatient(null);
            patientWelcomeScreen.dispose();
        }

        userManager.setCurrentUser(null);
    }

    public void handleViewMedicalRecords(Patient patient) {
        ArrayList<MedicalRecord> medicalRecords = userManager.getMedicalRecords(patient);
        medicalRecordsScreen = new MedicalRecordsScreen(this);
        medicalRecordsScreen.setTitleLabel(patient.getFirstName()+ " " + patient.getLastName());
        medicalRecordsScreen.displayMedicalRecords(medicalRecords);

        User currentUser = userManager.getCurrentUser();
        if (currentUser.isAdmin()) {
            adminPatientsScreen.dispose();
        } else if (currentUser.isDoctor()) {
            doctorManager.setSelectedPatient(patient);
            doctorPatientsScreen.dispose();
        } else if (currentUser.isPatient()) {
            patientWelcomeScreen.dispose();
        }
    }

    public void handleBackFromMedicalRecords() {
        User currentUser = userManager.getCurrentUser();

        if (currentUser.isAdmin()) {
            adminPatientsScreen = new AdminPatientsScreen(this);
            adminPatientsScreen.displayPatients(adminManager.getPatients());
        } else if (currentUser.isDoctor()) {
            Doctor currentDoctor = (Doctor) currentUser;
            doctorManager.setSelectedPatient(null);
            doctorPatientsScreen = new DoctorPatientsScreen(this);
            doctorPatientsScreen.displayPatients(doctorManager.getDoctorsPatients(currentDoctor));
        } else if (currentUser.isPatient()) {
            Patient currentPatient = (Patient) currentUser;
            patientWelcomeScreen = new PatientWelcomeScreen(this);
            patientWelcomeScreen.setWelcomePatient(currentPatient.toString());
        }

        medicalRecordsScreen.dispose();
    }

    public void handleViewDiagnosis(MedicalRecord medicalRecord) {
        medicalRecordScreen = new MedicalRecordScreen(medicalRecord,this);
        Doctor doctor = userManager.getDoctor(medicalRecord.getDoctorId());
        medicalRecordScreen.setDateLabel(medicalRecord.getDate());
        medicalRecordScreen.setDoctorLabel(doctor.getFirstName() + " " + doctor.getLastName());
        medicalRecordScreen.setCostLabel(doctor.getSpecialization().getCost());
        medicalRecordsScreen.dispose();
    }

    public void handleBackFromDiagnosis() {
        User currentUser = userManager.getCurrentUser();
        Patient patient = null;
        if (currentUser.isDoctor()) {
            patient = doctorManager.getSelectedPatient();
        } else if (currentUser.isPatient()) {
            patient = patientManager.getCurrentPatient();
        }
        ArrayList<MedicalRecord> medicalRecords = userManager.getMedicalRecords(patient);
        medicalRecordsScreen = new MedicalRecordsScreen(this);
        medicalRecordsScreen.setTitleLabel(patient.getFirstName()+ " " + patient.getLastName());
        medicalRecordsScreen.displayMedicalRecords(medicalRecords);

        medicalRecordScreen.dispose();
    }

    public Doctor getDoctor(int doctorId) {
        return userManager.getDoctor(doctorId);
    }

    public Patient getPatient(int patientId) {
        return userManager.getPatient(patientId);
    }

    public User getCurrentUser() {
        return userManager.getCurrentUser();
    }

    public boolean isCurrentUserAdmin() {
        return userManager.getCurrentUser().isAdmin();
    }

    public boolean isCurrentUserDoctor() {
        return userManager.getCurrentUser().isDoctor();
    }

    public boolean isCurrentUserPatient() {
        return userManager.getCurrentUser().isPatient();
    }
//----------------------------------------------COMMON - END-----------------------------------------------------


    public static void main(String[] args) {
      Controller controller = new Controller();
    }
}