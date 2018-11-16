package com.example.david.donationtracker;

import android.support.annotation.NonNull;

/**
 * Enum for UserType
 */
public enum UserType {

    USER ("USER"),
    ADMIN ("ADMIN"),
    EMPLOYEE ("EMPLOYEE"),
    MANAGER ("MANAGER");

    private final String _userType;

    UserType(String userType) {
        _userType = userType;
    }

    /**
     * getter method that returns the user type
     * @return user type
     */
    public String getUserType() {
        return _userType;
    }

    @NonNull
    public String toString() {
        return _userType;
    }

}
