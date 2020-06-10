package com.snehasisdebbarman.sanyog;

public class ModelPatient {

    String id,patientAge,patientBloodGroup,patientBloodPressure,patientBodyTemp,patientEmail,patientName,patientPhoneET,patientWeight,patientGender;

    public ModelPatient() {
    }

    public ModelPatient(String id, String patientAge, String patientBloodGroup, String patientBloodPressure, String patientBodyTemp, String patientEmail, String patientName, String patientPhoneET, String patientWeight, String patientGender) {
        this.id = id;
        this.patientAge = patientAge;
        this.patientBloodGroup = patientBloodGroup;
        this.patientBloodPressure = patientBloodPressure;
        this.patientBodyTemp = patientBodyTemp;
        this.patientEmail = patientEmail;
        this.patientName = patientName;
        this.patientPhoneET = patientPhoneET;
        this.patientWeight = patientWeight;
        this.patientGender = patientGender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientBloodGroup() {
        return patientBloodGroup;
    }

    public void setPatientBloodGroup(String patientBloodGroup) {
        this.patientBloodGroup = patientBloodGroup;
    }

    public String getPatientBloodPressure() {
        return patientBloodPressure;
    }

    public void setPatientBloodPressure(String patientBloodPressure) {
        this.patientBloodPressure = patientBloodPressure;
    }

    public String getPatientBodyTemp() {
        return patientBodyTemp;
    }

    public void setPatientBodyTemp(String patientBodyTemp) {
        this.patientBodyTemp = patientBodyTemp;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhoneET() {
        return patientPhoneET;
    }

    public void setPatientPhoneET(String patientPhoneET) {
        this.patientPhoneET = patientPhoneET;
    }

    public String getPatientWeight() {
        return patientWeight;
    }

    public void setPatientWeight(String patientWeight) {
        this.patientWeight = patientWeight;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }
}
