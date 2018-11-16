package com.example.david.donationtracker;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

class Donations {

    private static final Map<Location, ArrayList<Donation>> donations = new HashMap<>();
    private static Donation currentDonation;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static Donation getCurrentDonation() {
        return currentDonation;
    }

    public static void setCurrentDonation(Donation donation) {
        currentDonation = donation;
    }

    @SuppressWarnings("ConstantConditions")
    private static void addDonation(Donation donation) {
        Location location = donation.getLocation();
        if (!donations.containsKey(location)) {
            ArrayList<Donation> donationsAtLocation = new ArrayList<>();
            donationsAtLocation.add(donation);
            donations.put(location, donationsAtLocation);
        } else {
            donations.get(location).add(donation);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void getAllDonationsFromDatabase() {
        db.collection("locations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("getCollection", document.getId() + " => " + document.getData());
                                DocumentReference doc = document.getReference();
                                Map<String, Object> locationData = document.getData();
                                final Location location = new Location((String) locationData.get("name"),
                                        (String) locationData.get("type"), (String) locationData.get("longitude"),
                                        (String) locationData.get("latitude"), (String) locationData.get("address"),
                                        (String) locationData.get("phoneNumber"));
                                doc.collection("donations").get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("getIndividualDonation", "Successfully retrieved donation");
                                                    for (QueryDocumentSnapshot currentDonation : task.getResult()) {
                                                        Map<String, Object> donationData = currentDonation.getData();
                                                        Donation curr = new Donation(location,
                                                                (String) donationData.get("shortDescription"), (String) donationData.get("longDescription"),
                                                                (double) donationData.get("donationValue"), DonationCategory.valueOf((String) donationData.get("donationCategory")));
                                                        addDonation(curr);
                                                    }
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.d("getCollection", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private Iterable<Donation> getDonations(Location location) {
        if (donations.containsKey(location)) {
            return donations.get(location);
        } else {
            return new ArrayList<>();
        }
    }

// --Commented out by Inspection START (11/16/18 10:30 AM):
//    public static Location linkedLocation(Donation donation) {
//        if (donations.containsValue(donation)) {
//            for (Location l : donations.keySet()) {
//                if (donations.get(l).equals(donation)) {
//                    return l;
//                }
//            }
//        }
//        return null;
//    }
// --Commented out by Inspection STOP (11/16/18 10:30 AM)

    public ArrayList<Donation> getAllDonations() {
        ArrayList<Donation> allLocations = new ArrayList<>();
        for(Collection<Donation> list : donations.values()) {
            allLocations.addAll(list);
        }
        return allLocations;

    }

    public ArrayList<Donation> filterByCategory(DonationCategory category, boolean allDonos) {
        if (category == null) {
            throw new NullPointerException("Donation category can't be null for searching");
        }
        ArrayList<Donation> filteredList = new ArrayList<>();
        if (allDonos) {
            filteredList.addAll(getAllDonations());
        } else {
            for (Donation donation : getAllDonations()) {
                if (donation.getCategory() == category) {
                    filteredList.add(donation);
                }
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

    public ArrayList<Donation> filterByValue(double min, double max, boolean allDonos, String loc) {
        ArrayList<Donation> filteredList = new ArrayList<>();
        if (allDonos) {
            for (Donation donation : getAllDonations()) {
                if (donation.getValue() > min && donation.getValue() < max) {
                    filteredList.add(donation);
                }
            }
        } else {
            for (Donation donation : getDonations(Locations.get(loc))) {
                if (donation.getValue() > min && donation.getValue() < max) {
                    filteredList.add(donation);
                }
            }
        }
        return filteredList;
    }

// --Commented out by Inspection START (11/16/18 10:46 AM):
//    private String removeInvalidCharacters(String text) {
//        return text.replaceAll("[^A-Za-z0-9()\\[\\]]", ""); // replaces all non valid characters in the string
//    }
// --Commented out by Inspection STOP (11/16/18 10:46 AM)

}
