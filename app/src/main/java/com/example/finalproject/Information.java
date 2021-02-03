package com.example.finalproject;

public class Information {
    public Information(String parentName, String childName, String childAge, String childNum, String add, String phone, String emailAdd, String requirement) {

    }


    String parentName, childName,ChildAge,ChildNum,Address,Phone,Email,Requirement,gender;

    public Information(String parentName, String childName, String childAge, String childNum, String address, String phone, String email, String requirement, String gender) {
        this.parentName = parentName;
        this.childName = childName;
        ChildAge = childAge;
        ChildNum = childNum;
        Address = address;
        Phone = phone;
        Email = email;
        Requirement = requirement;
        this.gender = gender;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildAge() {
        return ChildAge;
    }

    public void setChildAge(String childAge) {
        ChildAge = childAge;
    }

    public String getChildNum() {
        return ChildNum;
    }

    public void setChildNum(String childNum) {
        ChildNum = childNum;
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
