package com.example.david.donationtracker;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
    private final String name;
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

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

// --Commented out by Inspection START (11/16/18 10:46 AM):
//    public void setName(String name) {
//        this.name = name;
//    }
// --Commented out by Inspection STOP (11/16/18 10:46 AM)

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLongitude() {
        return longitude;
    }

// --Commented out by Inspection START (11/16/18 10:30 AM):
//    public void setLongitude(String longitude) {
//        this.longitude = longitude;
//    }
// --Commented out by Inspection STOP (11/16/18 10:30 AM)

    public String getLatitude() {
        return latitude;
    }

// --Commented out by Inspection START (11/16/18 10:30 AM):
// --Commented out by Inspection START (11/16/18 10:30 AM):
////    public void setLatitude(String lattitude) {
////        this.latitude = lattitude;
////    }
//// --Commented out by Inspection STOP (11/16/18 10:30 AM)
// --Commented out by Inspection STOP (11/16/18 10:30 AM)

    public String getAddress() {
        return address;
    }

// --Commented out by Inspection START (11/16/18 10:30 AM):
//    public void setAddress(String address) {
//        this.address = address;
//    }
// --Commented out by Inspection STOP (11/16/18 10:30 AM)

    public String getPhoneNumber() {
        return phoneNumber;
    }

// --Commented out by Inspection START (11/16/18 10:53 AM):
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
// --Commented out by Inspection STOP (11/16/18 10:53 AM)

    public String toString() {
        return "Name: " + getName() + "\nAddress:"
                + getAddress() + "\nType:" + getType() +
                "\nPhone Number:" + getPhoneNumber() + "\nLatitude: " + getLatitude() + "\nLongitude: " + getLongitude() + "\n\n";
    }

    private Location(Parcel in){
        String[] data = new String[6];

        in.readStringArray(data);
        this.name = data[0];
        this.type = data[1];
        this.longitude = data[2];
        this.latitude = data[3];
        this.address = data[4];
        this.phoneNumber = data[5];
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.name,
                this.type, this.longitude, this.latitude,
                this.address, this.phoneNumber});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
