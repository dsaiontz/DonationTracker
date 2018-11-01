package com.example.david.donationtracker;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.time.format.DateTimeFormatter;

public class Donation implements Serializable {

    private org.threeten.bp.LocalDateTime time;
    private Location location;
    private String shortDescription;
    private String fullDescription;
    private double value;
    private DonationCategory category;
    private String comments;
    private String pictureURI;
    private String donationId;

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
    public Donation(Location location, String shortDescription,
                    String fullDescription, double value, DonationCategory category) {
        this.location = location;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.value = value;
        this.category = category;
        this.time = org.threeten.bp.LocalDateTime.now();
        //this.time = LocalDateTime.now();
    }

    public Donation(String donationId, Location location, String shortDescription,
                    String fullDescription, double value, DonationCategory category) {
        this.donationId = donationId;
        this.location = location;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.value = value;
        this.category = category;
        this.time = org.threeten.bp.LocalDateTime.now();
        //this.time = LocalDateTime.now();
    }

<<<<<<< HEAD
//    public Donation(String location, String shortDescription,
//                    String fullDescription, double value, DonationCategory category) {
//        this.location = location;
//        this.shortDescription = shortDescription;
//        this.fullDescription = fullDescription;
//        this.value = value;
//        this.category = category;
//        this.time = LocalDateTime.now();
//    }


    public String getDonationId() {
        return donationId;
    }

    public void setDonationId(String donationId) {
        this.donationId = donationId;
    }

=======
>>>>>>> master
    public String getTime() {
        org.threeten.bp.format.DateTimeFormatter formatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);

    }

    public void setTime(org.threeten.bp.LocalDateTime time) {
        this.time = time;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public DonationCategory getCategory() {
        return category;
    }

    public void setCategory(DonationCategory category) {
        this.category = category;
    }

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
        if (o == null || getClass() != o.getClass()) return false;
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
