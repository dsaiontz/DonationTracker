package com.example.david.donationtracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Credentials {

    private static HashMap<String, User> users = new HashMap<>();

    private static User currentUser;

    public static void setCurrentUser(User user) { currentUser = user; }

    public static User getCurrentUser() { return currentUser; }

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

    public static boolean containsKey(String key) {
        return users.containsKey(key);
    }

}
