package com.example.david.donationtracker;

import org.junit.Test;

import java.util.ArrayList;

public class JamesUnitTest {

    @Test
    public void validateLocationsIsCorrect() {

        ArrayList<Location> locs = Locations.getAllLocations();
        ArrayList<Location> notLocs = new ArrayList<>();
        notLocs.add(new Location("notALoc1"));
        notLocs.add(new Location("notALoc2"));
        notLocs.add(new Location("notALoc3"));

        for (Location s : locs) {
            System.out.println(s);
            assert(Locations.containsLocation(s.getName()));
        }

        for (Location s : notLocs) {
            System.out.println(s);
            assert(!Locations.containsLocation(s.getName()));
        }
    }
}
