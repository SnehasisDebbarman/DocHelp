package com.snehasisdebbarman.sanyog;

public class ModelMedicine {
    String patient_uid,doctor_uid,prescription_id,timestamp,medicine_name,dose,howManyTimesET,when,splInstruction;

    public ModelMedicine() {
    }

    public ModelMedicine(String patient_uid, String doctor_uid, String prescription_id, String timestamp, String medicine_name, String dose, String howManyTimesET, String when, String splInstruction) {
        this.patient_uid = patient_uid;
        this.doctor_uid = doctor_uid;
        this.prescription_id = prescription_id;
        this.timestamp = timestamp;
        this.medicine_name = medicine_name;
        this.dose = dose;
        this.howManyTimesET = howManyTimesET;
        this.when = when;
        this.splInstruction = splInstruction;
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

    public String getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(String prescription_id) {
        this.prescription_id = prescription_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getHowManyTimesET() {
        return howManyTimesET;
    }

    public void setHowManyTimesET(String howManyTimesET) {
        this.howManyTimesET = howManyTimesET;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getSplInstruction() {
        return splInstruction;
    }

    public void setSplInstruction(String splInstruction) {
        this.splInstruction = splInstruction;
    }
}
