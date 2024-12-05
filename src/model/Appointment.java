package model;

import java.sql.Time;
import java.util.Date;

public class Appointment {

    private int id;
    private String day;
    private String time;
    private int doctorId;
    private int patientId;


    public Appointment(String day, String time, int doctorId, int patientId) {
        this.day = day;
        this.time = time;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
