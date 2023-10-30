package com.example.dungan_lakaw;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Transport_Picker extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigationView;


    CardView car, walk, boat, plane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_picker);

        car = findViewById(R.id.Car);
        walk = findViewById(R.id.Walk);
        boat = findViewById(R.id.boat);
        plane = findViewById(R.id.Plane);

        car.setOnClickListener(this);
        walk.setOnClickListener(this);
        boat.setOnClickListener(this);
        plane.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String proceed;
        Intent Map;
        switch (view.getId()) {
            case R.id.Car:
                proceed = "1";
                Map = new Intent(this, RoutePicker.class);
                Map.putExtra("to proceed", proceed);
                startActivity(Map);
                break;
            case R.id.boat:
                proceed = "1";
                Map = new Intent(this, RoutePicker.class);
                Map.putExtra("to proceed", proceed);
                startActivity(Map);
                break;
            case R.id.Plane:
                proceed = "1";
                Map = new Intent(this, RoutePicker.class);
                Map.putExtra("to proceed", proceed);
                startActivity(Map);
                break;
            case R.id.Walk:
                proceed = "1";
                Map = new Intent(this, RoutePicker.class);
                Map.putExtra("to proceed", proceed);
                startActivity(Map);
                break;
        }
    }
}

