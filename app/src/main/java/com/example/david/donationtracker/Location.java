package com.example.david.donationtracker;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
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

    public Location(String name) {
        this.name = name;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String lattitude) {
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
        return getName() + "\n" +
                "\n" + getAddress() + "\n" + getAddress() +
                "\n" + getPhoneNumber() + "\n" + getLatitude() + "\n" + getLongitude() + "\n";
    }

    public Location(Parcel in){
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
