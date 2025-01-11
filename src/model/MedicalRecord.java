package model;

public class MedicalRecord {
    private int id;
    private String diagnosis;
    private String description;
    private String prescription;
    private int doctorId;
    private int patientId;


    public MedicalRecord(String diagnosis, String description, String prescription, int doctorId, int patientId) {
        this.diagnosis = diagnosis;
        this.description = description;
        this.prescription = prescription;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
