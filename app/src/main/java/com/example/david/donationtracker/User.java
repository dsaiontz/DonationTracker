package com.example.david.donationtracker;

import java.util.Arrays;
import java.util.List;

public class User {

    private String username;
    private String password;
    private UserType userType;


    User(String name, String pass, UserType type) {
        username = name;
        password = pass;
        userType = type;
    }

    User(String name, String pass) {
        this(name, pass, UserType.USER);
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

    public void setUsername(String name) {
        username = name;
    }

    public void setPassword(String pass) {
        password = pass;
    }

    public void setUserType(UserType type) {
        userType = type;
    }

    public String toString() {
        return username + " " + userType;
    }

}
