package com.example.david.donationtracker;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Donation class
 */
public class Donation implements Serializable {

    private final LocalDateTime time;
    private final Location location;
    private final String shortDescription;
    private final String fullDescription;
    private final double value;
    private final DonationCategory category;
    // --Commented out by Inspection (11/16/18 10:29 AM):private String comments;
    // --Commented out by Inspection (11/16/18 1:14 PM):private String pictureURI;

//    public Donation(Time time, Location location, String shortDescription,
//                    String fullDescription, double value, DonationCategory category,
//                    String comments, String pictureURI) {
//        this.time = time;
//        this.location = location;
//        this.shortDescription = shortDescription;
//        this.fullDescription = fullDescription;
//        this.value = value;
//        this.category = category;
//        this.comments = comments;
//        this.pictureURI = pictureURI;
//    }

    //delete after adding comments and pictureURI

    /**
     * constructor
     * @param location of donation
     * @param shortDescription of donation
     * @param fullDescription of donation
     * @param value of donation
     * @param category of donation
     */
    public Donation(Location location, String shortDescription,
                    String fullDescription, double value, DonationCategory category) {
        this.location = location;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.value = value;
        this.category = category;
//        this.time = org.threeten.bp.LocalDateTime.now();
        this.time = LocalDateTime.now();
    }

// --Commented out by Inspection START (11/16/18 10:45 AM):
//    public Donation(String donationId, Location location, String shortDescription,
//                    String fullDescription, double value, DonationCategory category) {
//        this.donationId = donationId;
//        this.location = location;
//        this.shortDescription = shortDescription;
//        this.fullDescription = fullDescription;
//        this.value = value;
//        this.category = category;
//        this.time = LocalDateTime.now();
//    }
// --Commented out by Inspection STOP (11/16/18 10:45 AM)

// --Commented out by Inspection START (11/16/18 10:49 AM):
//    public String getDonationId() {
//        return donationId;
//    }
// --Commented out by Inspection STOP (11/16/18 10:49 AM)


    /**
     * getter method for time
     * @return time
     */
    public String getTime() {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.toString();

    }

// --Commented out by Inspection START (11/16/18 1:25 PM):
//    public void setTime(LocalDateTime time) {
//        this.time = time;
//    }
// --Commented out by Inspection STOP (11/16/18 1:25 PM)

    /**
     * getter method for location
     * @return location
     */
    public Location getLocation() {
        return location;
    }

// --Commented out by Inspection START (11/16/18 1:25 PM):
//    public void setLocation(Location location) {
//        this.location = location;
//    }
// --Commented out by Inspection START (11/16/18 1:25 PM):
//// --Commented out by Inspection STOP (11/16/18 1:25 PM)

    /**
     * Getter method for short description
     * @return short description
     */
    public String getShortDescription() {
        return shortDescription;
    }

// --Commented out by Inspection START (11/16/18 1:25 PM):
//    public void setShortDescription(String shortDescription) {
//        this.shortDescription = shortDescription;
// --Commented out by Inspection STOP (11/16/18 1:25 PM)
//    }

    /**
     * getter method for full description
     * @return full description
     */
    public String getFullDescription() {
        return fullDescription;
    }

// --Commented out by Inspection START (11/16/18 1:25 PM):
//    public void setFullDescription(String fullDescription) {
//        this.fullDescription = fullDescription;
//    }
// --Commented out by Inspection STOP (11/16/18 1:25 PM)

    /**
     * getter method for value
     * @return value
     */
    public double getValue() {
        return value;
    }

// --Commented out by Inspection START (11/16/18 1:30 PM):
//    public void setValue(double value) {
//        this.value = value;
//    }
// --Commented out by Inspection STOP (11/16/18 1:30 PM)

    /**
     * getter method for category
     * @return category
     */
    public DonationCategory getCategory() {
        return category;
    }

// --Commented out by Inspection START (11/16/18 1:25 PM):
//    public void setCategory(DonationCategory category) {
//        this.category = category;
//    }
// --Commented out by Inspection STOP (11/16/18 1:25 PM)

//    public String getComments() {
//        return comments;
//    }
//
//    public void setComments(String comments) {
//        this.comments = comments;
//    }
//
//    public String getPictureURI() {
//        return pictureURI;
//    }
//
//    public void setPictureURI(String pictureURI) {
//        this.pictureURI = pictureURI;
//    }

    @NonNull
    @Override
    public String toString() {
        return "Donation\n" + "Location: " + location.getName()
                + "\nTime: " + time + "\nShort Description: " + shortDescription
                + "\nFull Description: " + fullDescription + "\nValue: "
                + value + "\nCategory: " + category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass())) return false;
        Donation donation = (Donation) o;
        return Double.compare(donation.getValue(), getValue()) == 0 &&
                Objects.equals(getTime(), donation.getTime()) &&
                Objects.equals(getLocation(), donation.getLocation()) &&
                getCategory() == donation.getCategory();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTime(),
                getLocation(), getValue(), getCategory());
    }

}
