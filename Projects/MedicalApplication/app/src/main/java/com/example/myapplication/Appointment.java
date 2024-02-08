package com.example.myapplication;

public class Appointment {
    private String docName, apptDate, apptTime;

    public Appointment(String docName, String apptDate, String apptTime) {
        this.docName = docName;
        this.apptDate = apptDate;
        this.apptTime = apptTime;
    }

    public String getDocName() {
        return docName;
    }

    public String getApptDate() {
        return apptDate;
    }

    public String getApptTime() {
        return apptTime;
    }
}
