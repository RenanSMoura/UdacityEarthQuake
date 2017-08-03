/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&limit=20";

    private ArrayList<Earthquake> earthquakesList;

    private ListView earthquakeListView;

    private EarthquakeArrayAdapter earthquakeArrayAdapter;

    private static final int EARTHQUAKE_LOADER_ID = 1;

    private EarthquakeLoader earthquakeLoader;

    /** TextView que é mostrada quando a lista é vazia */
    private TextView mEmptyStateTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        earthquakeArrayAdapter = new EarthquakeArrayAdapter(EarthquakeActivity.this,new ArrayList<Earthquake>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(earthquakeArrayAdapter);
        earthquakeListView.setEmptyView(mEmptyStateTextView);


       if(earthquakeLoader.checkNetworkConnection(EarthquakeActivity.this)){
           LoaderManager loaderManager = getSupportLoaderManager();
           loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null,this);
       }else{
           mProgressBar.setVisibility(View.GONE);
           mEmptyStateTextView.setText(R.string.no_internet_connection);
       }


            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Earthquake earthquake = earthquakeArrayAdapter.getItem(position);
                    Uri uri = Uri.parse(earthquake.getmUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);

                }
            });

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG,"Iniciando o OnCreateLoader");
        return new EarthquakeLoader(this,USGS_REQUEST_URL);


    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {

        mEmptyStateTextView.setText(R.string.no_earthquakes);
        mProgressBar.setVisibility(View.GONE);
        earthquakeArrayAdapter.clear();
        Log.v(LOG_TAG,"OnLoadFinished");

        if(earthquakes != null && !earthquakes.isEmpty()){
            earthquakeArrayAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.v(LOG_TAG,"onLoaderReset");
        earthquakeArrayAdapter.clear();
    }


}
