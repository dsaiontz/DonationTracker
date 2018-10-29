package com.example.david.donationtracker;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    public static ArrayList<Donation> getDonations(Location location) {
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

}
