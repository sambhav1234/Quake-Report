package com.example.android.quakereport;

public class Earthquake {
    private Double mMagnitude;
    private String mLocation;
    private String mDate;
    private long mTimeInMillisecond;
    private String murl;

    public Earthquake(Double magnitude,String location,long TimeInMillisecond,String url)
    {
        mMagnitude=magnitude;
        mLocation=location;
        mTimeInMillisecond=TimeInMillisecond;
        murl=url;
    }

    public Double getmMagnitude()
    {
        return mMagnitude;
    }
    public String getmLocation(){
        return mLocation;
    }
    public String getmDate()
    {
        return mDate;
    }

    public long getmTimeInMillisecond() {
        return mTimeInMillisecond;
    }
    public String getMurl(){
        return murl;}

}
