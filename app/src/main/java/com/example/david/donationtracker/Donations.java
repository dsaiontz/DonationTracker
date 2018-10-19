package com.example.david.donationtracker;

import java.util.ArrayList;
import java.util.HashMap;

public class Donations {

    //Stores all donations, key is each location
    private static HashMap<String, ArrayList<Donation>> donations = new HashMap<>();

    //GILRS misspelled in csv file also???
    private final static String[] VALID_LOCATIONS = new String[]
            {"AFD Station", "BOYS & GILRS CLUB W.W. WOOLFOLK", "PATHWAY UPPER ROOM CHRISTIAN MINISTRIES",
                    "PAVILION OF HOPE INC", "D&D CONVENIENCE STORE", "KEEP NORTH FULTON BEAUTIFUL"};

    public static void addDonation(Donation donation) {
        String loc = donation.getLocation().getName();
        if (donations.containsKey(loc)) {
            donations.get(loc).add(donation);
        } else {
            ArrayList<Donation> newList = new ArrayList<>();
            newList.add(donation);
            donations.put(loc, newList);
        }
    }

    public static String[] getValidLocations() {return VALID_LOCATIONS;}

    public static HashMap<String, ArrayList<Donation>> getDonations() {return donations;}
}
