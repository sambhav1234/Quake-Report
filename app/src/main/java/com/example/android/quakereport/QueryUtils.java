package com.example.android.quakereport;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryUtils {
  private static final String LOG_TAG=QueryUtils.class.getSimpleName();
    /** Sample JSON response for a USGS query */

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {

    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */

    private  static List<Earthquake> exactFeaturesFromJson(String earthQuakeJson)
    {
        if(TextUtils.isEmpty(earthQuakeJson))
            return null;

        List<Earthquake> earthquakes=new ArrayList<>();
        try
        {
         JSONObject jsonObject=new JSONObject(earthQuakeJson);
         JSONArray jsonArray=jsonObject.getJSONArray("features");

         for(int i=0;i<jsonArray.length();i++)
         {
             JSONObject jsonObject1=jsonArray.getJSONObject(i);

             JSONObject properties=jsonObject1.getJSONObject("properties");
             // Extract the value for the key called "mag"
             double magnitude = properties.getDouble("mag");

             // Extract the value for the key called "place"
             String location = properties.getString("place");

             // Extract the value for the key called "time"
             long time = properties.getLong("time");

             // Extract the value for the key called "url"
             String url = properties.getString("url");
             Earthquake earthquake=new Earthquake(magnitude,location,time,url);
             earthquakes.add(earthquake);
         }


        } catch (JSONException e) {
            Log.e(LOG_TAG,"problem in parsing");
        }
        return  earthquakes;


    }



    public static List<Earthquake>  fectchEarthquake(String requestUrl)
    {
        URL url=createUrl(requestUrl);
String jsonReponse=null;
        try
        {
            jsonReponse=makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("QueryUtils","problem in mearging");
        }
        List<Earthquake> list=exactFeaturesFromJson(jsonReponse);
        return list;


    }
    private static URL createUrl(String stringUrl)
    {
        URL url=null;
        if(stringUrl=="")
            return null;
        try{
            url=new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;


    }

    private static String makeHttpRequest(URL url) throws IOException
    {

        String str="";
        if(url==null)
            return str;
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;

        try{
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200)
            {
                inputStream=urlConnection.getInputStream();
                str=readFromReader(inputStream);

            }else Log.e(LOG_TAG,"problem in creating jsonResponse");

        }catch (IOException e) {
            Log.e(LOG_TAG, "problem in httprequest", e);
        }finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();
        }

return str;

    }

    private  static String readFromReader(InputStream inputStream) throws IOException
    {
        StringBuilder stringBuilder=new StringBuilder();
       if(inputStream!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            while(line!=null)
            {
                stringBuilder.append(line);
                line=bufferedReader.readLine();


            }
        }

       return stringBuilder.toString();


    }




}
