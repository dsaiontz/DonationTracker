package com.example.david.donationtracker;

public enum UserType {

    USER ("USER"),
    ADMIN ("ADMIN"),
    EMPLOYEE ("EMPLOYEE");

    private final String _userType;

    UserType(String userType) {
        _userType = userType;
    }

    public String getUserType() {
        return _userType;
    }

    public String toString() {
        return _userType;
    }

}
