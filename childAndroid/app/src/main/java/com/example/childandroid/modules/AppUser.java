package com.example.childandroid.modules;


import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class AppUser {

	private int id;
	private String userName;
	private String password;
	private String email;
	Set<Likes> likes;

	Set<Share> shares;

	Set<GroupAttendees> attendees;

	Set<Groups> groups;
	public Set<Groups> getGroups() {
		return groups;
	}
	Set<GroupPost> groupPosts;
	public Set<GroupPost> getGroupPosts() {
		return this.groupPosts;
	}

	public void setGroupPosts(Set<GroupPost> groupPosts) {
		this.groupPosts = groupPosts;
	}

	private Date createdAt;

	private String location;

//	private LocalDate dateOfBirth;
//
//	public LocalDate getDateOfBirth() {
//		return dateOfBirth;
//	}
//
//	public void setDateOfBirth(LocalDate dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}

	public AppUser(String userName, String password, String email, Parent parent ,String location) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.parent = parent;
//		this.dateOfBirth = dateOfBirth;
		this.location = location;
	}

	private Parent parent;

	public AppUser() {}

	private List<Post> posts;

	private List<Comment> comments;

	public List<Event> events;

	private List<EventAttendees> eventAttendees;

	Set<UsersFollowers> users;

	Set<UsersFollowers> followers;


	Set<Message> SentFromUserMessage;

	Set<Message> SentToUserMessage;

	List<GamesApi> FavouriteGames;

	private List<TemporaryComment> temporaryComments;

	private List<TemporaryPost> temporaryPosts;

	private List<TemporaryShare> temporaryShares;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public void setLikes(Set<Likes> likes) {
		this.likes = likes;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setShares(Set<Share> shares) {
		this.shares = shares;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<EventAttendees> getEventAttendees() {
		return eventAttendees;
	}

	public void setEventAttendees(List<EventAttendees> eventAttendees) {
		this.eventAttendees = eventAttendees;
	}

	public Set<UsersFollowers> getUsers() {
		return users;
	}

	public void setUsers(Set<UsersFollowers> users) {
		this.users = users;
	}

	public Set<UsersFollowers> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<UsersFollowers> followers) {
		this.followers = followers;
	}

	public Set<Message> getSentFromUserMessage() {
		return SentFromUserMessage;
	}

	public void setSentFromUserMessage(Set<Message> sentFromUserMessage) {
		SentFromUserMessage = sentFromUserMessage;
	}

	public Set<Message> getSentToUserMessage() {
		return SentToUserMessage;
	}

	public void setSentToUserMessage(Set<Message> sentToUserMessage) {
		SentToUserMessage = sentToUserMessage;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	public Set<GroupAttendees> getAttendees() {
		return attendees;
	}

	public void setAttendees(Set<GroupAttendees> attendees) {
		this.attendees = attendees;
	}

	public List<GamesApi> getFavouriteGames() {
		return FavouriteGames;
	}

	public void setFavouriteGames(List<GamesApi> favouriteGames) {
		FavouriteGames = favouriteGames;
	}

	public List<TemporaryComment> getTemporaryComments() {
		return temporaryComments;
	}

	public void setTemporaryComments(List<TemporaryComment> temporaryComments) {
		this.temporaryComments = temporaryComments;
	}

	public void setTemporaryPosts(List<TemporaryPost> temporaryPosts) {
		this.temporaryPosts = temporaryPosts;
	}

	public Parent getParent() {
		return parent;
	}

	public void setTemporaryShares(List<TemporaryShare> temporaryShares) {
		this.temporaryShares = temporaryShares;
	}

}
