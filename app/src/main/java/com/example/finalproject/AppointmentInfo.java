package com.example.finalproject;

public class AppointmentInfo {
    public AppointmentInfo() {

    }

    String BabySitterImage;
    String Sender;
    String Receiver;
    String message;
    String parent_id;
    String babysitter_id;
    String BabysitterName;
    String BabysitterPhone;
    String BabysitterEmail;
    String BabysitterRate;
    String ParentName;
    String ParentEmail;
    String ParentPhone;
    String ParentChildAge;
    String ParentReq;
    String ParentAdd;
    String ChildGender;
    String Status;
    String SenderName;

    public AppointmentInfo(String babySitterImage,String senderName,String sender, String receiver, String Message, String parent_id, String babysitter_id, String babysitterName, String babysitterPhone, String babysitterEmail, String babysitterRate, String parentName, String parentEmail, String parentPhone, String parentChildAge, String parentReq, String parentAdd, String childGender, String status) {
        this.parent_id = parent_id;
        this.babysitter_id = babysitter_id;
        BabySitterImage=babySitterImage;
        BabysitterName = babysitterName;
        message=Message;
        Sender=sender;
        Receiver=receiver;
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
        Status=status;
        SenderName=senderName;
    }

    public String getBabySitterImage() {
        return BabySitterImage;
    }

    public void setBabySitterImage(String babySitterImage) {
        BabySitterImage = babySitterImage;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
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
