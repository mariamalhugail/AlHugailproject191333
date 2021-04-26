package com.example.mobilefinal;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
class User{

    private String emailaddress;
    private String firstname;
    private String lastname;
    private String PhoneNumber;
    private int userId;

    public User(String emailaddress, String firstname, String lastname, String phoneNumber, int userId) {
        this.emailaddress = emailaddress;
        this.firstname = firstname;
        this.lastname = lastname;
        PhoneNumber = phoneNumber;
        this.userId = userId;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}