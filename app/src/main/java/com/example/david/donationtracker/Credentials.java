package com.example.david.donationtracker;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Credentials {

    // Regex Patterns
    private static Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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

    public static boolean isValidUsername(String username) {
        return VALID_EMAIL_ADDRESS_REGEX.matcher(username).find();
    }

}
