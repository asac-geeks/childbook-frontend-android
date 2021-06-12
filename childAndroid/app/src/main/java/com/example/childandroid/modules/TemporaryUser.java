package com.example.childandroid.modules;

import java.time.LocalDate;

public class TemporaryUser {
    private int id;
    private String userName;
    private String password;
    private String parentEmail;
    private String location;

    public TemporaryUser(String userName, String password, String parentEmail, LocalDate dateOfBirth,String location) {

    }

    private LocalDate dateOfBirth;

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    private String serialNumber;


    public TemporaryUser() {

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "TemporaryUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", parentEmail='" + parentEmail + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
