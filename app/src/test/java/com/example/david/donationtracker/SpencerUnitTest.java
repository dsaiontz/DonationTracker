package com.example.david.donationtracker;

import org.junit.Test;

import java.util.ArrayList;

import static com.example.david.donationtracker.Locations.getAllLocations;

public class SpencerUnitTest {
    @Test
    public void filterByCategoryIsCorrect() {
        Donations donations = new Donations();

        ArrayList<DonationCategory> donationCategories = new ArrayList<>();
        donationCategories.add(DonationCategory.CLOTHING);
        donationCategories.add(DonationCategory.ELECTRONICS);
        donationCategories.add(DonationCategory.HAT);
        donationCategories.add(DonationCategory.HOUSEHOLD);
        donationCategories.add(DonationCategory.KITCHEN);
        donationCategories.add(DonationCategory.OTHER);

        ArrayList<Location> locations = getAllLocations();

        ArrayList<Donation> allDonations = donations.getAllDonations();
        int size = allDonations.size();
        int count = 0;
        String locationName = locations.get(0).getName();

        for (DonationCategory category : donationCategories) {
            ArrayList<Donation> filteredList = donations.filterByCategory(category, false, locationName);
            for (Donation donation : filteredList) {
                assert (donation.getCategory().equals(category));
            }
            count += filteredList.size();
        }
        assert (count == size); // should grab and count each donation if we search by each category


    }
}
