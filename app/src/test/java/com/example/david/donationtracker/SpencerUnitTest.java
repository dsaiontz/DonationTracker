package com.example.david.donationtracker;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getContext;
import static com.example.david.donationtracker.Locations.getAllLocations;

public class SpencerUnitTest {
    FirebaseFirestore db;
    ArrayList<Donation> donations = new ArrayList<Donation>();

    @Test
    public void filterByCategoryIsCorrect() {

        db = FirebaseFirestore.getInstance(FirebaseApp.initializeApp(getContext()));

        ArrayList<DonationCategory> donationCategories = new ArrayList<>();
        donationCategories.add(DonationCategory.CLOTHING);
        donationCategories.add(DonationCategory.ELECTRONICS);
        donationCategories.add(DonationCategory.HAT);
        donationCategories.add(DonationCategory.HOUSEHOLD);
        donationCategories.add(DonationCategory.KITCHEN);
        donationCategories.add(DonationCategory.OTHER);

        ArrayList<Location> locations = getAllLocations();
        int size = donations.size();
        int count = 0;
        Donations myDonationRef = new Donations();

        for (DonationCategory category : donationCategories) {
            ArrayList<Donation> filteredList = myDonationRef.filterByCategory(category, false);
            for (Donation donation : filteredList) {
                assert (donation.getCategory().equals(category));
            }
            count += filteredList.size();
        }
        assert (count == size); // should grab and count each donation if we search by each category


    }
}
