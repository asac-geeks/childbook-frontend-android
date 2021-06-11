package com.example.childandroid.modules;

import java.util.Date;

public class Comment {
    private Integer id;

    private AppUser appUser;

    private Post post;

    // ................................. Yazan added by ......................................
    private GroupPost groupPost;

    public Comment(AppUser appUser, Post post, String body, GroupPost groupPost) {
        this.appUser = appUser;
        this.post = post;
        this.body = body;
        this.groupPost = groupPost;
    }

    public Comment(AppUser appUser, String body, GroupPost groupPost) {
        this.appUser = appUser;
        this.groupPost = groupPost;
        this.body = body;
    }


    public GroupPost getGroupPost() {
        return groupPost;
    }

    public void setGroupPost(GroupPost groupPost) {
        this.groupPost = groupPost;
    }

    // ................................. Yazan added by ......................................
    private String body;

    private Date createdAt;

    private Date modifiedAt;

    public Comment(AppUser appUser, Post post, String body) {
        this.appUser = appUser;
        this.post = post;
        this.body = body;
    }

    public Comment() {
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Post getPost() {
        return null;
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
}
