package com.example.david.donationtracker;

import android.util.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Locations {

    private static HashMap<String, Location> locations = new HashMap<>();

    private static Location currentLocation;

    private static ArrayList<Location> csvLocations;

    public static ArrayList<Location> getCsvLocations() {
        return csvLocations;
    }

    public static void setCsvLocations(ArrayList<Location> newLocations) {
        csvLocations = newLocations;
    }

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

    public static Location[] getAllLocations() {
        return (Location[]) locations.values().toArray();
    }

}
