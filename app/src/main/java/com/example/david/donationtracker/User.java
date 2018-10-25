package com.example.david.donationtracker;

import java.util.Arrays;
import java.util.List;

public class User {

    private String username;
    private String password;
    private UserType userType;
    private Location location;


    User(String name, String pass, UserType type, Location loc) {
        username = name;
        password = pass;
        userType = type;
        location = loc;
    }

    User(String name, String pass) {
        this(name, pass, UserType.USER, null);
    }

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getPassword() {
        return password;
    }

    public Location getLocation() { return location; }

    public void setUsername(String name) {
        username = name;
    }

    public void setPassword(String pass) {
        password = pass;
    }

    public void setUserType(UserType type) {
        userType = type;
    }

    public void setLocation(Location location) { this.location = location; }

    public String toString() {
        return username + " " + userType;
    }

}
