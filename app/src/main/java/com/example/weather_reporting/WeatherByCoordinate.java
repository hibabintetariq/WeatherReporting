package com.example.weather_reporting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WeatherByCoordinate extends AppCompatActivity {


    EditText longtitude, latitude;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_by_coordinate);
        longtitude = findViewById(R.id.LongitudeValue);
        latitude = findViewById(R.id.LatitudeValue);
        search = findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in= new Intent( WeatherByCoordinate.this,CoordinateWeather.class);
                Bundle coordinate= new Bundle();
                coordinate.putString("longitude",longtitude.getText().toString());
                coordinate.putString("latitude",latitude.getText().toString());

                /*String []coordinates= {longtitude.getText().toString(),latitude.getText().toString()};
                in.putExtra("longitude",coordinates[0]);
                in.putExtra("latitude",coordinates[1]);*/
                in.putExtras(coordinate);
                startActivity(in);
            }
        });
    }
}