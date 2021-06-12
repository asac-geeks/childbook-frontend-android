package com.example.childandroid.modules;

import java.util.Date;

public class Share {

    private Integer id;

    private Date createdAt;

    private Date modifiedAt;

    public Share(AppUser appUser, Post post) {
        this.appUser = appUser;
        this.post = post;
    }

    public Share() {
    }

    AppUser appUser;


    Post post;
    // ................................. Yazan added by ......................................
    GroupPost groupPost;

    public Share(AppUser appUser, GroupPost groupPost) {
        this.appUser = appUser;
        this.groupPost = groupPost;
    }


    public GroupPost getGroupPost() {
        return groupPost;
    }

    public void setGroupPost(GroupPost groupPost) {
        this.groupPost = groupPost;
    }


    // ................................. Yazan added by ......................................



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
