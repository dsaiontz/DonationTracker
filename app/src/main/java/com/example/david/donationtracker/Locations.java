package com.example.david.donationtracker;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Locations {

    private static HashMap<String, Location> locations = new HashMap<>();

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

}
