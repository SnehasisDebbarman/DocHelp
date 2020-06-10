package com.snehasisdebbarman.sanyog;

public class ModelPrescription {

    String prescription_id,patient_uid,doctor_uid,timestamp,patient_name,patient_email;

    public ModelPrescription() {
    }

    public ModelPrescription(String prescription_id, String patient_uid, String doctor_uid, String timestamp, String patient_name, String patient_email) {
        this.prescription_id = prescription_id;
        this.patient_uid = patient_uid;
        this.doctor_uid = doctor_uid;
        this.timestamp = timestamp;
        this.patient_name = patient_name;
        this.patient_email = patient_email;
    }

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
}
