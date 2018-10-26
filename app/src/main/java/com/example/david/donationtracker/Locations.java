package com.example.david.donationtracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Locations {

    //NOT USEFUL AT ALL, NEED TO EDIT A LOT
    private static HashMap<String, User> locations = new HashMap<>();


    public static void add(User user) {
        locations.put(user.getUsername(), user);
    }

    public static User get(String username) {
        return locations.get(username);
    }

    public static HashMap<String, User> getUsers() {
        return locations;
    }

    public static void set(String username, User user) {
        locations.remove(username);
        locations.put(username, user);
    }

    public static boolean containsKey(String key) {
        return locations.containsKey(key);
    }

}
