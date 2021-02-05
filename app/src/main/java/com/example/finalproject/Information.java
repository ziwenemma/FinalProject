package com.example.finalproject;

public class Information {
    public Information(){
    }


    String id, parentName, ChildAge,Address,Phone,Email,Requirement,gender;

    public Information(String id, String parentName,  String childAge, String address, String phone, String email, String requirement, String gender) {
        this.parentName = parentName;
        this.ChildAge = childAge;
        this.Address = address;
        this.Phone = phone;
        this.Email = email;
        this.Requirement = requirement;
        this.gender = gender;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChildAge() {
        return ChildAge;
    }

    public void setChildAge(String childAge) {
        ChildAge = childAge;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRequirement() {
        return Requirement;
    }

    public void setRequirement(String requirement) {
        Requirement = requirement;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
