package com.example.david.donationtracker;

import android.content.Intent;

import org.junit.Test;

public class JonathanUnitTest {

    @Test
    public void validateValidDonationValue() {

        String[] validValues = {"-2147483647", "2147483647", "-500", "500", "1", "5354353", "90000", "60000","42332535","0"};

        String[] invalidValues = {"aValue", "true", "false", "23.7","432.12","23V","0.0","1.0"};


        for (String val : validValues) {
            System.out.println(val);
            assert(Donations.isValidValue(val));
        }

        for (String val : invalidValues) {
            System.out.println(val);
            assert(!Donations.isValidValue(val));
        }
    }
}