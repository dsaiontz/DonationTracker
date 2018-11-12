package com.example.david.donationtracker;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Locations {

    private static HashMap<String, Location> locations = new HashMap<>();

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

    public static void set(String locationName, Location location) {
        if (locations.containsKey(location.getName())) {
            locations.remove(location);
        }
        locations.put(locationName, location);
    }

    public static boolean containsLocation(String locationName) {
        return locations.containsKey(locationName);
    }

    public static ArrayList<Location> getAllLocations() {
        return new ArrayList<Location>(locations.values());
    }

}
