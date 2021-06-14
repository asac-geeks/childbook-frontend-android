package com.example.childandroid.modules;

public class UpdateLocationRequest {
    private String location;

    public UpdateLocationRequest(String location) {
        this.location = location;
    }
    public UpdateLocationRequest() {
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
