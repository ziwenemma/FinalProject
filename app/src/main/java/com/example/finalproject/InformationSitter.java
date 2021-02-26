package com.example.finalproject;

public class InformationSitter {
    public InformationSitter(){
    }

    String image,id,sitterName,sitterAge,sitterCity,sitterRate,sitterDesc,sitterEmail,sitterPhone,sitterGender;


    public InformationSitter(String image,String id,String sitterName,  String sitterAge, String sitterCity, String sitterRate, String sitterDesc, String sitterEmail, String sitterPhone,String sitterGender) {
        this.id = id;
        this.image=image;
        this.sitterName = sitterName;
        this.sitterAge = sitterAge;
        this.sitterCity = sitterCity;
        this.sitterRate = sitterRate;
        this.sitterDesc = sitterDesc;
        this.sitterEmail = sitterEmail;
        this.sitterPhone = sitterPhone;
        this.sitterGender = sitterGender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSitterName() {
        return sitterName;
    }

    public void setSitterName(String sitterName) {
        this.sitterName = sitterName;
    }

    public String getSitterAge() {
        return sitterAge;
    }

    public void setSitterAge(String sitterAge) {
        this.sitterAge = sitterAge;
    }

    public String getSitterCity() {
        return sitterCity;
    }

    public void setSitterCity(String sitterCity) {
        this.sitterCity = sitterCity;
    }

    public String getSitterRate() {
        return sitterRate;
    }

    public void setSitterRate(String sitterRate) {
        this.sitterRate = sitterRate;
    }

    public String getSitterDesc() {
        return sitterDesc;
    }

    public void setSitterDesc(String sitterDesc) {
        this.sitterDesc = sitterDesc;
    }

    public String getSitterEmail() {
        return sitterEmail;
    }

    public void setSitterEmail(String sitterEmail) {
        this.sitterEmail = sitterEmail;
    }

    public String getSitterPhone() {
        return sitterPhone;
    }

    public void setSitterPhone(String sitterPhone) {
        this.sitterPhone = sitterPhone;
    }

    public String getSitterGender() {
        return sitterGender;
    }

    public void setSitterGender(String sitterGender) {
        this.sitterGender = sitterGender;
    }
}
