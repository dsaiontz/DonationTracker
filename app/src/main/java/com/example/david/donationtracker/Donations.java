package com.example.david.donationtracker;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Donations {

    private static HashMap<Location, ArrayList<Donation>> donations = new HashMap<>();

    private static final List<String> validLocations = Arrays.asList("AFD Station", "BOYS & GILRS CLUB W.W. WOOLFOLK",
            "PATHWAY UPPER ROOM CHRISTIAN MINISTRIES", "PAVILION OF HOPE INC",
            "D&D CONVENIENCE STORE", "KEEP NORTH FULTON BEAUTIFUL");

    public static List<String> getValidLocations() {
        return validLocations;
    }

    public static void addDonation(Donation donation) {
        Location location = donation.getLocation();
        if (donations.containsKey(location)) {
            ArrayList<Donation> donationsAtLocation = donations.get(location);
            donationsAtLocation.add(donation);
            donations.put(location, donationsAtLocation);
        } else {
            ArrayList<Donation> newList = new ArrayList<>();
            newList.add(donation);
            donations.put(location, newList);
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
