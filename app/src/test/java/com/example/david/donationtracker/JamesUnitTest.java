package com.example.david.donationtracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JamesUnitTest {

    @Test
    public void validateLocationsIsCorrect() {

        Iterable<Location> locs = Locations.getAllLocations();
        List<Location> notLocs = new ArrayList<>();
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
