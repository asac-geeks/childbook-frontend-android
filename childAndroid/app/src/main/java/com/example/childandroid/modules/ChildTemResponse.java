package com.example.childandroid.modules;

import java.util.HashSet;
import java.util.Set;

public class ChildTemResponse {
    private Set<TemporaryComment> comments = new HashSet();
    private Set<TemporaryPost> posts = new HashSet();
    private Set<TemporaryShare> shares = new HashSet();

    public ChildTemResponse(Set<TemporaryComment> comments, Set<TemporaryPost> posts, Set<TemporaryShare> shares) {
        this.comments = comments;
        this.posts = posts;
        this.shares = shares;
    }

    public ChildTemResponse() {

    }

    public Set<TemporaryComment> getComments() {
        return comments;
    }

    public void setComments(Set<TemporaryComment> comments) {
        this.comments = comments;
    }

    public Set<TemporaryPost> getPosts() {
        return posts;
    }

    public void setPosts(Set<TemporaryPost> posts) {
        this.posts = posts;
    }

    public Set<TemporaryShare> getShares() {
        return shares;
    }

    public void setShares(Set<TemporaryShare> shares) {
        this.shares = shares;
    }
}
