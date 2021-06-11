package com.example.childandroid.modules;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Post{
    private String body;

    private Date createdAt;

    private Date modifiedAt;

    private AppUser appUser;

    private List<Comment> comments;
    private Integer id;

    private String imageUrl;
    private String videoSrc;
    private String videoType;
    private String postTitle;

    Set<Likes> likes;


    Set<Share> shares;

    public Post(String body, AppUser appUser, String imageUrl, String videoSrc, String videoType, String postTitle, boolean isPublic) {
        this.body = body;
        this.appUser = appUser;
        this.imageUrl = imageUrl;
        this.videoSrc = videoSrc;
        this.videoType = videoType;
        this.postTitle = postTitle;
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    private boolean isPublic;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Post() {
    }

    public Integer getId() {
        return id;
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

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getVideoSrc() {
        return videoSrc;
    }

    public void setVideoSrc(String videoSrc) {
        this.videoSrc = videoSrc;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public Set<Likes> getLikes() {
        return likes;
    }

    public void setLikes(Set<Likes> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<Share> getShares() {
        return shares;
    }

    public void setShares(Set<Share> shares) {
        this.shares = shares;
    }
}
