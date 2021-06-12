package com.example.childandroid.modules;

import java.util.Date;

public class TemporaryShare  {
    private Integer id;

    private boolean isSeen;
    private Date createdAt;

    private Date modifiedAt;

    public TemporaryShare(AppUser appUser, Post post) {
        this.appUser = appUser;
        this.post = post;
    }

    public TemporaryShare() {
    }

    AppUser appUser;

    Post post;

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
