package com.example.weather_reporting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WeatherByCity extends AppCompatActivity {

    public EditText cityName;
    public Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_by_city);
        cityName= (EditText)findViewById(R.id.cityName);
        searchButton=(Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in= new Intent(WeatherByCity.this,CityWeather.class);
                String city= cityName.getText().toString();
                in.putExtra("City_Name",city);
                startActivity(in);
            }
        });

    }
}