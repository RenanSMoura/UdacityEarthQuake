package com.example.android.quakereport;

/**
 * Created by Renan on 27/07/2017.
 */

public class Earthquake {

    private double mMagnitude;

    private String mLocation;

    private long mTimeInMiliSeconds;

    private String mUrl;

    public Earthquake(double magnitude, String location, long timeInMiliSeconds,String url) {
        this.mMagnitude = magnitude;
        this.mLocation = location;
        this.mTimeInMiliSeconds = timeInMiliSeconds;
        this.mUrl = url;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public long getmTimeInMiliSeconds() {
        return mTimeInMiliSeconds;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getmUrl(){
        return mUrl;
    }
}
