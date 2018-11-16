package com.example.david.donationtracker;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Locations {

    private static final Map<String, Location> locations = new HashMap<>();

    private static Location currentLocation;

    public static void setCurrentLocation(Location location) { currentLocation = location; }

    public static Location getCurrentLocation() { return currentLocation; }

    public static void add(Location location) {
        if (!locations.containsKey(location.getName())) {
            locations.put(location.getName(), location);
        } else {
            Log.w("Warning", "Tried to add a location to Locations which already existed");
        }
    }

    public static Location get(String locationName) {
        return locations.get(locationName);
    }

// --Commented out by Inspection START (11/16/18 10:30 AM):
//    public static void set(String locationName, Location location) {
//        if (locations.containsKey(location.getName())) {
//            locations.remove(location);
//        }
//        locations.put(locationName, location);
//    }
// --Commented out by Inspection STOP (11/16/18 10:30 AM)

    public static boolean containsLocation(String locationName) {
        return locations.containsKey(locationName);
    }

    public static ArrayList<Location> getAllLocations() {
        return new ArrayList<>(locations.values());
    }

}
