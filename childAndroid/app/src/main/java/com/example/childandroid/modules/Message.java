package com.example.childandroid.modules;

import java.util.Date;

public class Message {

    public Message() {
    }


    private Integer id;


    private Date createdAt;


    private Date modifiedAt;

    private AppUser senderUser;

    private AppUser toUser;

    private String body;

    public Message(AppUser senderUser, AppUser toUser,String body) {
        this.senderUser = senderUser;
        this.toUser = toUser;
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

    public AppUser getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(AppUser senderUser) {
        this.senderUser = senderUser;
    }

    public AppUser getToUser() {
        return toUser;
    }

    public void setToUser(AppUser toUser) {
        this.toUser = toUser;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
