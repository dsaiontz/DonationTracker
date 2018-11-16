package com.example.david.donationtracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class JacksonUnitTest {

    @Test
    public void validateAddLocation() {

        Collection<Location> newLocations = new ArrayList<>();

        newLocations.add(new Location("Location 1"));
        newLocations.add(new Location("Location 2"));
        newLocations.add(new Location("Location 3"));
        newLocations.add(new Location("Location 4"));


        for (Location loc : newLocations) {
            //System.out.println(s);
            assert(!Locations.getAllLocations().contains(loc));
            Locations.add(loc);
        }



        for (Location loc : newLocations) {
            //System.out.println(s);
            assert(Locations.getAllLocations().contains(loc));
        }
    }
}
