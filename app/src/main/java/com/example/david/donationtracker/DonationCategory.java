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

    public String getDonationCategory() {
        return donationCategory;
    }

    public String toString() {
        return donationCategory;
    }
}
