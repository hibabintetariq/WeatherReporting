package com.example.weather_reporting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class CityWeather extends AppCompatActivity {
    TextView retrievedCity,retrievedCityText, latitudeText, longitudeText, temperature, link;
    Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        retrievedCity = (TextView) findViewById(R.id.retrievedCity);
        String city= getIntent().getStringExtra("City_Name");
        retrievedCity.setText(city);
        String city_var= retrievedCity.getText().toString();
        link= findViewById(R.id.linkText);
        retrievedCityText = findViewById(R.id.retrievedCityText);
        temperature= findViewById(R.id.tempText);
        latitudeText= findViewById(R.id.latitudeText);
        longitudeText=findViewById(R.id.longitudeText);

        search= findViewById(R.id.viewByCoord);
        search
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

        //now  retrieving weather by city name
       findWeather(city_var);
    }
    private void findWeather(String city_var) {
        String url = "https://openweathermap.org/data/2.5/weather?q=karachi,pakistan&appid=439d4b804bc8187953eb36d2a8c26a02";
        String first = "http://api.openweathermap.org/data/2.5/weather?q=";
        String second = "&appid=89a59e1a762b6f58335b4e7a3e05af33";
        String newURL= "https://api.openweathermap.org/data/2.5/weather?q=" + city_var + "&appid=89a59e1a762b6f58335b4e7a3e05af33";
        link.setText(newURL);
        String AllahPleaseHelpMe= link.getText().toString();

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, AllahPleaseHelpMe, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_obj = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject obj = array.getJSONObject(0);
                    String minTempText = valueOf(main_obj.getDouble("temp_min"));
                    String maxTempText = valueOf(main_obj.getDouble("temp_max"));
                    String cityname= response.getString("name");
                    retrievedCityText.setText(cityname);

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