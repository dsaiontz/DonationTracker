package com.example.david.donationtracker;

import java.util.HashMap;

public final class Credentials {

    private static HashMap<String, User> users;

    private Credentials() {
        users = new HashMap<String, User>();
    }

    public static void add(User user) {
        users.put(user.getUsername(), user);
    }

    public static User get(String username) {
        return users.get(username);
    }

    public static HashMap<String, User> getUsers() {
        return users;
    }

    public static void set(String username, User user) {
        users.remove(username);
        users.put(username, user);
    }

}
