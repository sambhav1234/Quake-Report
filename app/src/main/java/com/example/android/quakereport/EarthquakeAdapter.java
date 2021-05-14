package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private int getMagnitudeColor(double magnitude) {
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

    public static final String LOCATION_SEPARATOR="of";

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes)
    {
     super(context,0,earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null)
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item,parent,false);


        Earthquake currentEartquake=getItem(position);


        TextView magnitudeView=(TextView)listItemView.findViewById(R.id.magnitude);
        DecimalFormat deci=new DecimalFormat("0.0");
        String formattedDeci=deci.format(currentEartquake.getmMagnitude());
        magnitudeView.setText(formattedDeci);
        GradientDrawable magniotudeCircle=(GradientDrawable)magnitudeView.getBackground();
        int magnicolo=getMagnitudeColor(currentEartquake.getmMagnitude());
        magniotudeCircle.setColor(magnicolo);


        String currentLocation=currentEartquake.getmLocation();
        String off_Set="";
        String primaryLocation="";
        if(currentLocation.contains(LOCATION_SEPARATOR))
        {
            String[] part=currentLocation.split(LOCATION_SEPARATOR);
            off_Set=part[0]+LOCATION_SEPARATOR;
            primaryLocation=part[1];

        }else
        {
            off_Set=getContext().getString(R.string.Near_the);
            primaryLocation=currentLocation;
        }
        TextView off=(TextView)listItemView.findViewById(R.id.location_offset);
        off.setText(off_Set);
        TextView prim=(TextView)listItemView.findViewById(R.id.primary_location);
        prim.setText(primaryLocation);


        Date date= new Date(currentEartquake.getmTimeInMillisecond());


        TextView dateView=(TextView)listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(date);
        dateView.setText(formattedDate);

        TextView timeView=(TextView)listItemView.findViewById(R.id.time);
        String formattedTime=formatTime(date);
        timeView.setText(formattedTime);


        return listItemView;
    }
}
