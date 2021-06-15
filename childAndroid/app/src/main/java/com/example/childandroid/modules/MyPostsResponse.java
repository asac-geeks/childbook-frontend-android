package com.example.childandroid.modules;


import java.util.Set;

public class MyPostsResponse {
    private Set<Post> posts;
    private Set<Share> shares;
    public MyPostsResponse() {
    }

    public MyPostsResponse(Set<Post> posts, Set<Share> shares) {
        this.posts = posts;
        this.shares = shares;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Share> getShares() {
        return shares;
    }

    public void setShares(Set<Share> shares) {
        this.shares = shares;
    }
}
