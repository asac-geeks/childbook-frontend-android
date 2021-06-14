package com.example.childandroid.modules;

import java.util.Date;

public class TemporaryComment {

    private Integer id;

    private AppUser appUser;

    private Post post;

    private String body;

    private boolean isSeen;

    private Date createdAt;

    private Date modifiedAt;

    public TemporaryComment(AppUser appUser, Post post, String body) {
        this.appUser = appUser;
        this.post = post;
        this.body = body;
    }

    public TemporaryComment() {
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getId() {
        return id;
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

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
