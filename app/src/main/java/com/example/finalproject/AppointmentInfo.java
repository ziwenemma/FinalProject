package com.example.finalproject;

public class AppointmentInfo {
    public AppointmentInfo() {

    }

    String babysitter_id, BabysitterName, BabysitterPhone, BabysitterEmail, BabysitterRate,ParentName,ParentEmail,ParentPhone,ParentChildAge,ParentReq,ParentAdd,ChildGender;

    public AppointmentInfo(String babysitter_id, String babysitterName, String babysitterPhone, String babysitterEmail, String babysitterRate, String parentName, String parentEmail, String parentPhone, String parentChildAge, String parentReq, String parentAdd, String childGender) {
        this.babysitter_id = babysitter_id;
        BabysitterName = babysitterName;
        BabysitterPhone = babysitterPhone;
        BabysitterEmail = babysitterEmail;
        BabysitterRate = babysitterRate;
        ParentName = parentName;
        ParentEmail = parentEmail;
        ParentPhone = parentPhone;
        ParentChildAge = parentChildAge;
        ParentReq = parentReq;
        ParentAdd = parentAdd;
        ChildGender = childGender;
    }

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String parentName) {
        ParentName = parentName;
    }

    public String getParentEmail() {
        return ParentEmail;
    }

    public void setParentEmail(String parentEmail) {
        ParentEmail = parentEmail;
    }

    public String getParentPhone() {
        return ParentPhone;
    }

    public void setParentPhone(String parentPhone) {
        ParentPhone = parentPhone;
    }

    public String getParentChildAge() {
        return ParentChildAge;
    }

    public void setParentChildAge(String parentChildAge) {
        ParentChildAge = parentChildAge;
    }

    public String getParentReq() {
        return ParentReq;
    }

    public void setParentReq(String parentReq) {
        ParentReq = parentReq;
    }

    public String getParentAdd() {
        return ParentAdd;
    }

    public void setParentAdd(String parentAdd) {
        ParentAdd = parentAdd;
    }

    public String getChildGender() {
        return ChildGender;
    }

    public void setChildGender(String childGender) {
        ChildGender = childGender;
    }

    public String getBabysitter_id() {
        return babysitter_id;
    }

    public void setBabysitter_id(String babysitter_id) {
        this.babysitter_id = babysitter_id;
    }

    public String getBabysitterName() {
        return BabysitterName;
    }

    public void setBabysitterName(String babysitterName) {
        BabysitterName = babysitterName;
    }

    public String getBabysitterPhone() {
        return BabysitterPhone;
    }

    public void setBabysitterPhone(String babysitterPhone) {
        BabysitterPhone = babysitterPhone;
    }

    public String getBabysitterEmail() {
        return BabysitterEmail;
    }

    public void setBabysitterEmail(String babysitterEmail) {
        BabysitterEmail = babysitterEmail;
    }

    public String getBabysitterRate() {
        return BabysitterRate;
    }

    public void setBabysitterRate(String babysitterRate) {
        BabysitterRate = babysitterRate;
    }
}
