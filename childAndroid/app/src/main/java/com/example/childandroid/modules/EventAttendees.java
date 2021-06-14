package com.example.childandroid.modules;

import java.util.Date;

public class EventAttendees {

    private Integer id;

    private Date createdAt;


    private Date modifiedAt;

    AppUser appUser;

    Event event;

    public EventAttendees(AppUser appUser, Event event) {
        this.appUser = appUser;
        this.event = event;
    }
    public EventAttendees() {
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
