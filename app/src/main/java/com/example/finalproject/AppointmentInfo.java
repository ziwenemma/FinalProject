package com.example.finalproject;

public class AppointmentInfo {
    public AppointmentInfo() {

    }

    String babysitter_id, BabysitterName, BabysitterPhone, BabysitterEmail, BabysitterRate;

    public AppointmentInfo(String babysitter_id, String babysitterName, String babysitterPhone, String babysitterEmail, String babysitterRate) {
        this.babysitter_id = babysitter_id;
        BabysitterName = babysitterName;
        BabysitterPhone = babysitterPhone;
        BabysitterEmail = babysitterEmail;
        BabysitterRate = babysitterRate;
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
