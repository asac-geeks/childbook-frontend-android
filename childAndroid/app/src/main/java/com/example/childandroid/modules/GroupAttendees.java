package com.example.childandroid.modules;

import java.util.Date;

public class GroupAttendees {
    private Integer id;

    private Date createdAt;

    private Date modifiedAt;

    AppUser appUser;

    Groups group;

    public GroupAttendees(AppUser appUser, Groups group) {
        this.appUser = appUser;
        this.group = group;
    }
    public GroupAttendees() {
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }
}
