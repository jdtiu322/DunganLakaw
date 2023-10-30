package com.example.dungan_lakaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.List;
import android.Manifest;


public class RoutePicker extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, View.OnClickListener {



    private GoogleMap mMap;
    private LatLng[] marker = new LatLng[2];
    private int markerCount = 0;

    ImageButton cal;

    ImageButton notes, forum, hotel;

    String proceed;

    static final float DEFAULT_ZOOM = 15f;


    static final int PERMISSION_REQUEST_LOCATION = 20213;

    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Intent tp_string = getIntent();
            proceed = tp_string.getStringExtra("to proceed");
            if(!proceed.equals("1")) {

            }
        }catch (Exception ex){
            Intent transport = new Intent(this, Transport_Picker.class);
            startActivity(transport);
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_route_picker);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        //assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
        } else {
            // Location permission granted, enable My Location button and move the camera to the user's location
            mMap.setMyLocationEnabled(true);

            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // Move the camera to the user's location
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                    }
                }
            });
        }





        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (markerCount < 2) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(latLng)
                            .title("Marker " + (markerCount + 1));
                    mMap.addMarker(markerOptions);
                    marker[markerCount] = markerOptions.getPosition();
                    markerCount++;
                    System.out.println(markerOptions.getPosition());
                } else {
                    Toast.makeText(getApplicationContext(), "You can only add two markers!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnDrawRoute = findViewById(R.id.btnmakeroute);
        btnDrawRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method that draws the route between the two points
                DrawRouteTask drawRouteTask = new DrawRouteTask();
                drawRouteTask.execute();
            }
        });
    }


    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    //.apiKey("AIzaSyC0011F5_gm_6K77JKoDyzv1TPycNZD608")

    private class DrawRouteTask extends AsyncTask<Void, Void, DirectionsResult> {

        @Override
        protected DirectionsResult doInBackground(Void... voids) {
            GeoApiContext mGeoApiContext = new GeoApiContext.Builder().apiKey("AIzaSyA1k25nA1_UwMUVeuWNhSwvUssSMHx-D2k").build();

            // Create a Directions API request with the start and end points
            DirectionsApiRequest request = DirectionsApi.newRequest(mGeoApiContext);
            request.origin(getFullName(marker[0]));
            request.destination(getFullName(marker[1]));
            request.mode(TravelMode.WALKING); // Or whatever mode you want to use

            try {
                // Call the Directions API asynchronously
                return request.await();
            } catch (ApiException | InterruptedException | IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(DirectionsResult result) {
            if (result != null && result.routes.length > 0) {
                // Get the first route from the result
                DirectionsRoute route = result.routes[0];

                // Get the decoded polyline for the route
                List<LatLng> decodedPolyline = PolyUtil.decode(route.overviewPolyline.getEncodedPath());

                // Draw the polyline on the map
                PolylineOptions polylineOptions = new PolylineOptions().addAll(decodedPolyline);
                mMap.addPolyline(polylineOptions);
            }
        }
    }

    public String getFullName(LatLng ln) {
        return ln.latitude + ", " + ln.longitude;
    }
}