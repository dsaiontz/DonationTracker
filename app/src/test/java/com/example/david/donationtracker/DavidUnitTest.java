package com.example.david.donationtracker;

import org.junit.Test;

public class DavidUnitTest {

    @Test
    public void validatePasswordIsCorrect() {
        String[] validPasswords = {"password", "Password!", "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                "p@sswordscanbelong", "abc123",
                "123445678900!@#$%^&*()"};

        String[] invalidPasswords = {"pass", "word", "", "short",
                "12345", "yikes", ","};

        for (String s : validPasswords) {
            assert(Credentials.isValidPassword(s));
        }

        for (String s : invalidPasswords) {
            assert(!Credentials.isValidPassword(s));
        }
    }
}