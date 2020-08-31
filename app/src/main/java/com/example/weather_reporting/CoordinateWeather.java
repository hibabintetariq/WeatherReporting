package com.example.weather_reporting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.String.valueOf;

public class CoordinateWeather extends AppCompatActivity {

    TextView retrievedLatitude,retrievedLongitude,retrievedtimezoneText,  latitudeText, longitudeText, temperature, link;
    Button weatherByCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate_weather);
        retrievedLatitude = findViewById(R.id.retrievedLatitude);
        retrievedLongitude = findViewById(R.id.retrievedLongitude);
        retrievedtimezoneText = findViewById(R.id.retrievedTimezoneText);
        latitudeText = findViewById(R.id.latitudeText);
        longitudeText = findViewById(R.id.longitudeText);
        temperature = findViewById(R.id.tempText);
       // link = findViewById(R.id.linkText);

        Intent in= getIntent();
        Bundle coordinate = in.getExtras();
        String longitude_var =coordinate.getString("longitude");
        String latitude_var = coordinate.getString("latitude");


       findWeather(longitude_var,latitude_var);
       weatherByCity = findViewById(R.id.viewByCity);
       weatherByCity.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent in= new Intent(CoordinateWeather.this,WeatherByCity.class);
               startActivity(in);

           }
       });
    }

   private void findWeather(String longitude_var,String latitude_var) {
        String url = "https://openweathermap.org/data/2.5/weather?q=karachi,pakistan&appid=439d4b804bc8187953eb36d2a8c26a02";
        String first = "https://api.openweathermap.org/data/2.5/weather?lat=";
        String second = "&appid=89a59e1a762b6f58335b4e7a3e05af33";
        String newURLFirstPart= "https://api.openweathermap.org/data/2.5/onecall?lat="  ;
        String newURLSecondPart=  latitude_var + "&lon=" + longitude_var ;
        String newURLthirdPart= "&appid=89a59e1a762b6f58335b4e7a3e05af33";
        String finalURL= first+latitude_var+"&lon="+longitude_var+second ;
       // link.setText(finalURL);
        retrievedLatitude.setText(latitude_var);
        retrievedLongitude.setText(longitude_var);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, finalURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_obj = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject obj = array.getJSONObject(0);
                    String minTempText = valueOf(main_obj.getDouble("temp_min"));
                    String maxTempText = valueOf(main_obj.getDouble("temp_max"));
                    double min_temp=Double.parseDouble (minTempText);
                    double max_temp=Double.parseDouble (maxTempText);
                    double avg_temp=( min_temp+max_temp) /2;
                    String tempInText= valueOf(avg_temp);
                    temperature.setText(tempInText);

                    //for latitude and longitude
                    JSONObject coord_obj = response.getJSONObject("coord");
                    JSONArray coord_array = response.getJSONArray("weather");
                    JSONObject coordinate_obj = array.getJSONObject(0);
                    String longitude= valueOf(coord_obj.getDouble("lon"));
                    longitudeText.setText("Longitude: "+longitude);
                    String latitude= valueOf(coord_obj.getDouble("lat"));
                    latitudeText.setText("Latitude: "+latitude);

                    JSONObject tz_obj = response.getJSONObject("timezone");
                    JSONArray tz_array = response.getJSONArray("weather");
                    JSONObject timezone_obj = array.getJSONObject(0);
                    String timezone= valueOf(tz_obj.getDouble("timezone"));
                    retrievedtimezoneText.setText("Timezone: "+timezone);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
    }

}