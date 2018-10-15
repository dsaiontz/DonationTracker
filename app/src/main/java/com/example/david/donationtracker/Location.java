package com.example.david.donationtracker;

public class Location {
    private String name;
    private String type;
    private String longitude;
    private String latitude;
    private String address;
    private String phoneNumber;

    public Location(String name, String type, String longitude, String latitude, String address, String phoneNumber) {
        this.name = name;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLattitude() {
        return latitude;
    }

    public void setLattitude(String lattitude) {
        this.latitude = lattitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        return getName() + ", " + getAddress() + ", " + getAddress() +
                ", " + getPhoneNumber() + ", " + getLattitude() + ", " + getLongitude();
    }
}
