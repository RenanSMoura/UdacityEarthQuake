package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Renan on 27/07/2017.
 */

public class EarthquakeArrayAdapter extends ArrayAdapter<Earthquake> {
    private static final  String LOCATION_SEPARATOR = " of ";
    private DecimalFormat decimalFormat;
    private int           magnitudeColor;

    public EarthquakeArrayAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0,earthquakes);
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        //Crio o item de lista
        View listItemView = convertView;
        //Strings de localização
        String primaryLocation;
        String locationOffset;
        //CONFING decilamFormat;


        //Caso for null, forço o inflate da listView passango o contexto + layout
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_earthquake,parent,false);
        }
        //Recupero a posição o objeto
        Earthquake currentEarthquake = getItem(position);


        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeTextView.setText(formatDecimal(currentEarthquake.getMagnitude()));

        GradientDrawable magnitudeCircle =(GradientDrawable) magnitudeTextView.getBackground();
        magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        magnitudeCircle.setColor(magnitudeColor);
        //recupero o valor da localização
        String originalLocation = currentEarthquake.getLocation();

        if(originalLocation.contains(LOCATION_SEPARATOR)){
            String[] strings = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = strings[0]; // 74km NW of  --EXAMPLE
            primaryLocation = strings[1]; // Rumoi, Japan  -- EXAMPLE
        }else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        //LOCATION OFFSET
        TextView locationOffSetTextView = (TextView) listItemView.findViewById(R.id.location_offset);
        locationOffSetTextView.setText(locationOffset);

        //LOCATION PRIMARY
        TextView primaryLocationTextView = (TextView) listItemView.findViewById(R.id.primary_location);
        primaryLocationTextView.setText(primaryLocation);

        //DATE FORMAT
        Date dateObject = new Date(currentEarthquake.getmTimeInMiliSeconds());
        String formattedDate = formatDate(dateObject);
        String formattedTime = formatTime(dateObject);

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(formattedDate);

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(formattedTime);

        return listItemView;
    }

    /**
     * Retorna a data string formatada (i.e. "Mar 3, 1984") de um objeto Date.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy", Locale.ENGLISH);
        return dateFormat.format(dateObject);
    }

    /**
     * Retorna a data string formatada (i.e. "4:30 PM") de um objeto Date.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a",Locale.ENGLISH);
        return timeFormat.format(dateObject);
    }

    private String formatDecimal(double magnitude ){
        decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);

    }
}
