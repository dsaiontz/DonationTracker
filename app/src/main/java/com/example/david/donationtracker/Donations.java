package com.example.david.donationtracker;

import android.service.autofill.RegexValidator;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Donations {

    private static HashMap<Location, ArrayList<Donation>> donations = new HashMap<>();
    private static Donation currentDonation;

    public static Donation getCurrentDonation() {
        return currentDonation;
    }

    public static void setCurrentDonation(Donation donation) {
        currentDonation = donation;
    }

    public static void addDonation(Donation donation) {
        Location location = donation.getLocation();
        if (!donations.containsKey(location)) {
            ArrayList<Donation> donationsAtLocation = new ArrayList<>();
            donationsAtLocation.add(donation);
            donations.put(location, donationsAtLocation);
        } else {
            donations.get(location).add(donation);
        }
    }

    public ArrayList<Donation> getDonations(Location location) {
        return donations.get(location);
    }

    public static Location linkedLocation(Donation donation) {
        if (donations.containsValue(donation)) {
            for (Location l : donations.keySet()) {
                if (donations.get(l).equals(donation)) {
                    return l;
                }
            }
        }
        return null;
    }

    public ArrayList<Donation> getAllDonations() {
        ArrayList<Donation> allLocations = new ArrayList<>();
        for(ArrayList<Donation> list : donations.values()) {
            allLocations.addAll(list);
        }
        return allLocations;

    }

    public ArrayList<Donation> filterByCategory(DonationCategory category) {
        ArrayList<Donation> filteredList = new ArrayList<>();
        for (Donation donation : getAllDonations()) {
            if (donation.getCategory() == category) {
                filteredList.add(donation);
            }
        }
        return filteredList;
    }

    public ArrayList<Donation> filterByName(String searchText) {
        ArrayList<Donation> filteredList = new ArrayList<>();
//        searchText = removeInvalidCharacters(searchText); test to see if this is correctly removing invalid characters
        Pattern pattern;
        for (Donation donation : getAllDonations()) {
            pattern = Pattern.compile(searchText, Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(donation.getFullDescription()).matches()) {
                filteredList.add(donation);
            } else if (pattern.matcher(donation.getShortDescription()).matches()) {
                filteredList.add(donation);
            } else if (pattern.matcher(donation.getCategory().toString()).matches()) {
                filteredList.add(donation);
            }
        }
        return filteredList;
    }

    public ArrayList<Donation> filterByValue(double min, double max) {
        ArrayList<Donation> filteredList = new ArrayList<>();
        for (Donation donation : getAllDonations()) {
            if (donation.getValue() > min && donation.getValue() < max) {
                filteredList.add(donation);
            }
        }
        return filteredList;
    }

    private String removeInvalidCharacters(String text) {
        return text.replaceAll("[^A-Za-z0-9()\\[\\]]", ""); // replaces all non valid characters in the string
    }

}
