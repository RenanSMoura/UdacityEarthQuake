package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by Renan on 27/07/2017.
 */

public class Earthquake {

    private String mMagnitude;

    private String mLocation;

    private String mDate;

    public Earthquake(String magnitude, String location, String date) {
        this.mMagnitude = magnitude;
        this.mLocation = location;
        this.mDate = date;
    }

    public String getMagnitude() {
        return mMagnitude;
    }

    public String getDate() {
        return mDate;
    }

    public String getLocation() {
        return mLocation;
    }
}
