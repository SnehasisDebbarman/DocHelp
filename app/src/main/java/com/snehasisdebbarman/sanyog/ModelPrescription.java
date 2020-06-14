package com.snehasisdebbarman.sanyog;

public class ModelPrescription {

    String prescription_id;
    String patient_uid;
    String doctor_uid;
    String timestamp;
    String patient_name;
    String patient_email;
    String patientAge;
    String patientBloodGroup;
    String patientBloodPressure;
    String patientBodyTemp;
    String patientWeight;
    String patientSugar;

    public String getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(String prescription_id) {
        this.prescription_id = prescription_id;
    }

    public String getPatient_uid() {
        return patient_uid;
    }

    public void setPatient_uid(String patient_uid) {
        this.patient_uid = patient_uid;
    }

    public String getDoctor_uid() {
        return doctor_uid;
    }

    public void setDoctor_uid(String doctor_uid) {
        this.doctor_uid = doctor_uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_email() {
        return patient_email;
    }

    public void setPatient_email(String patient_email) {
        this.patient_email = patient_email;
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

    public String getPatientWeight() {
        return patientWeight;
    }

    public void setPatientWeight(String patientWeight) {
        this.patientWeight = patientWeight;
    }

    public String getPatientSugar() {
        return patientSugar;
    }

    public void setPatientSugar(String patientSugar) {
        this.patientSugar = patientSugar;
    }

    public ModelPrescription(String prescription_id, String patient_uid, String doctor_uid, String timestamp, String patient_name, String patient_email, String patientAge, String patientBloodGroup, String patientBloodPressure, String patientBodyTemp, String patientWeight, String patientSugar) {
        this.prescription_id = prescription_id;
        this.patient_uid = patient_uid;
        this.doctor_uid = doctor_uid;
        this.timestamp = timestamp;
        this.patient_name = patient_name;
        this.patient_email = patient_email;
        this.patientAge = patientAge;
        this.patientBloodGroup = patientBloodGroup;
        this.patientBloodPressure = patientBloodPressure;
        this.patientBodyTemp = patientBodyTemp;
        this.patientWeight = patientWeight;
        this.patientSugar = patientSugar;
    }

    public ModelPrescription() {
    }
}
