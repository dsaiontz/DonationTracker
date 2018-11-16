package com.example.david.donationtracker;

public enum DonationCategory {

    CLOTHING ("CLOTHING"),
    HAT ("HAT"),
    KITCHEN ("KITCHEN"),
    ELECTRONICS ("ELECTRONICS"),
    HOUSEHOLD ("HOUSEHOLD"),
    OTHER ("OTHER");

    private final String donationCategory;

    DonationCategory(String donationCategory) {
        this.donationCategory = donationCategory;
    }

// --Commented out by Inspection START (11/16/18 10:30 AM):
//    public String getDonationCategory() {
//        return donationCategory;
//    }
// --Commented out by Inspection STOP (11/16/18 10:30 AM)

    public String toString() {
        return donationCategory;
    }
}
