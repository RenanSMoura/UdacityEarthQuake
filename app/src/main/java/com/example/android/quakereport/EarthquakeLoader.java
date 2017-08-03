package com.example.android.quakereport;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import utils.EarthquakeNetworkConnection;

/**
 * Created by Renan on 02/08/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /** Query URL */
    private String mUrl;


    public EarthquakeLoader(Context context,String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.v(LOG_TAG,"OnStartLoading");
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if(mUrl == null){
            return null;
        }
        Log.v(LOG_TAG,"LoadInBackgroud");
        List<Earthquake> earthquakes = EarthquakeNetworkConnection.fetchEarthquakeData(mUrl);

        return earthquakes;
    }
    public static boolean checkNetworkConnection(Context context){
        ConnectivityManager cm =  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
