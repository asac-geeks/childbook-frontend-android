package com.example.childandroid.modules;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Groups {
    private Integer id;

    private AppUser appUser;

    private String body;
    private Date createdAt;

    private Date modifiedAt;

    private String imageUrl;
    private String title;

    private Set<GroupPost> groupPosts;

    private List<GroupAttendees> groupAttendees;

    public Groups() { }

    public Groups(AppUser appUser, String title, String body, String imageUrl) {
        this.appUser = appUser;
        this.body = body;
        this.imageUrl = imageUrl;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GroupAttendees> getGroupAttendees() {
        return groupAttendees;
    }

    public Set<GroupPost> getGroupPosts() {
        return groupPosts;
    }

    public void setGroupPosts(Set<GroupPost> groupPosts) {
        this.groupPosts = groupPosts;
    }

    public void setGroupAttendees(List<GroupAttendees> groupAttendees) {
        this.groupAttendees = groupAttendees;
    }
}
