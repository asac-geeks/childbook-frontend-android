package com.example.childandroid.modules;

import java.util.Date;

public class Likes {

    private Integer id;

    private Date createdAt;

    private Date modifiedAt;

    public Likes(AppUser appUser, Post post) {
        this.appUser = appUser;
        this.post = post;
    }

    public Likes() {
    }

    AppUser appUser;

    // ................................. Yazan added by ......................................
    GroupPost groupPost;

    public GroupPost getGroupPost() {
        return groupPost;
    }

    public void setGroupPost(GroupPost groupPost) {
        this.groupPost = groupPost;
    }

    public Likes(AppUser appUser, GroupPost groupPost) {
        this.appUser = appUser;
        this.groupPost = groupPost;
    }

// ................................. Yazan added by ......................................


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
}
