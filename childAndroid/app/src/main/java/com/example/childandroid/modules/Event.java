package com.example.childandroid.modules;

import java.util.Date;
import java.util.List;

public class Event {
    private Integer id;

    private AppUser appUser;
    private String body;
    private String location;

    private Date createdAt;

    private Date modifiedAt;

    private String imageUrl;

    private String title;

    private List<EventAttendees> eventAttendees;

    public Event() {}

    public Event(AppUser appUser, String body, String location, String imageUrl,String title) {
        this.appUser = appUser;
        this.body = body;
        this.location = location;
        this.imageUrl = imageUrl;
        this.title= title;
    }

    public Integer getId() {
        return id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<EventAttendees> getEventAttendees() {
        return eventAttendees;
    }

    public void setEventAttendees(List<EventAttendees> eventAttendees) {
        this.eventAttendees = eventAttendees;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
