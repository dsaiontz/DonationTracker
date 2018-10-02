package com.example.david.donationtracker;

import java.util.HashMap;

public class Credentials {

    private static HashMap<String, User> users;

    public void add(User user) {
        users.put(user.getUsername(), user);
    }

    public User get(String username) {
        return users.get(username);
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void set(String username, User user) {
        users.remove(username);
        users.put(username, user);
    }

}
