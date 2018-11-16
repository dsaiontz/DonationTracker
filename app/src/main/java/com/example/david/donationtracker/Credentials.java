package com.example.david.donationtracker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Credentials {

    // Regex Patterns
    private Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+" +
                            "@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);

    // --Commented out by Inspection (11/16/18 10:29 AM):public static User getCurrentUser()
    // { return currentUser; }

//    public static void add(User user) {
//        users.put(user.getUsername(), user);
//    }

// --Commented out by Inspection START (11/16/18 10:29 AM):
//// --Commented out by Inspection START (11/16/18 10:29 AM):
// --Commented out by Inspection START (11/16/18 10:29 AM):
//////    public static User get(String username) {
//////        return users.get(username);
//// --Commented out by Inspection STOP (11/16/18 10:29 AM)
// --Commented out by Inspection STOP (11/16/18 10:29 AM)
//    }
// --Commented out by Inspection STOP (11/16/18 10:29 AM)

// --Commented out by Inspection START (11/16/18 10:29 AM):
//    public static boolean containsKey(String key) {
//        return users.containsKey(key);
//    }
// --Commented out by Inspection STOP (11/16/18 10:29 AM)

    public boolean isValidUsername(String username) {
        Matcher match = VALID_EMAIL_ADDRESS_REGEX.matcher(username);
        return match.find();
    }

    public boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

}

