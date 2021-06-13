package com.example.childandroid.modules;

public class UsersFollowers {
    private Integer id;


    AppUser appUser;

    AppUser appUserFollower;

    public UsersFollowers(AppUser applicationUser, AppUser applicationUserFollower) {
        this.appUser = applicationUser;
        this.appUserFollower = applicationUserFollower;
    }

    public UsersFollowers() {
    }

    public Integer getId() {
        return id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public AppUser getAppUserFollower() {
        return appUserFollower;
    }
}

