package com.example.weather_reporting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
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

public class MainActivity extends AppCompatActivity {

    private TextView temperature, cityText, longitudeText, latitudeText;
    private Button refreshLoc;
    private LocationManager locationManager;
    private LocationListener listener;
    Button viewBycity,viewBycoord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        temperature = (TextView) findViewById(R.id.tempText);
        cityText = (TextView) findViewById(R.id.cityText);
        latitudeText = (TextView) findViewById(R.id.latitudeText);
        longitudeText = (TextView) findViewById(R.id.longitudeText);
        viewBycity=findViewById(R.id.viewByCity);
        viewBycoord=findViewById(R.id.viewByCoord);

        viewBycity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MainActivity.this,WeatherByCity.class);
                startActivity(in);
            }
        });

        viewBycoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MainActivity.this,WeatherByCoordinate.class);
                startActivity(in);
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        findWeather();


    }

    private void findWeather() {
        String url = "https://openweathermap.org/data/2.5/weather?q=karachi,pakistan&appid=439d4b804bc8187953eb36d2a8c26a02";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_obj = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject obj = array.getJSONObject(0);
                    String minTempText = valueOf(main_obj.getDouble("temp_min"));
                    String maxTempText = valueOf(main_obj.getDouble("temp_max"));
                    String city= response.getString("name");

                    cityText.setText(city);
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