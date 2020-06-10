package com.snehasisdebbarman.sanyog;

public class ModelDoctor {

    private String Phone,doc_uid,email,image,location,name,qualification,speciality;

    public ModelDoctor() {
    }

    public ModelDoctor(String phone, String doc_uid, String email, String image, String location, String name, String qualification, String speciality) {
        Phone = phone;
        this.doc_uid = doc_uid;
        this.email = email;
        this.image = image;
        this.location = location;
        this.name = name;
        this.qualification = qualification;
        this.speciality = speciality;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDoc_uid() {
        return doc_uid;
    }

    public void setDoc_uid(String doc_uid) {
        this.doc_uid = doc_uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
