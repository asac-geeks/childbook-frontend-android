package com.example.childandroid.modules;

import java.util.Set;

public class Parent {
    public Parent(String parentEmail, String parentPassword) {
        this.parentEmail = parentEmail;
        this.parentPassword = parentPassword;
    }

    public Parent() {
    }

    Set<AppUser> appUsers;

    private int id;

    private String parentEmail;
    private String parentPassword;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public String getParentPassword() {
        return parentPassword;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public void setParentPassword(String parentPassword) {
        this.parentPassword = parentPassword;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }
}
