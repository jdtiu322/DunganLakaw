package com.example.dungan_lakaw;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.dungan_lakaw.posts.PostAdapter;
import com.example.dungan_lakaw.posts.PostEditor;
import com.example.dungan_lakaw.posts.Postcards;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import android.Manifest;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private ImageButton transportButton;
    private ImageButton journalButton;
    private ImageButton hotelButton;
    private ImageButton locationButton;

    static final float DEFAULT_ZOOM = 15f;

    static final int PERMISSION_REQUEST_LOCATION = 20213;

    private FusedLocationProviderClient fusedLocationClient;

    private RecyclerView Forumrecyclerview;
    private PostAdapter postAdapter;
    private ArrayList<Postcards> postarraylist;

    private ImageButton btnaddpost;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        transportButton = rootView.findViewById(R.id.transport);
        journalButton = rootView.findViewById(R.id.journal);
        hotelButton = rootView.findViewById(R.id.hotel);
        locationButton = rootView.findViewById(R.id.location);
        btnaddpost = rootView.findViewById(R.id.btnaddpost);

        InitializePosts(rootView); // Initialize the RecyclerView and post list
        populateWithFakePosts(); // Populate the list with fake posts (you can replace this with your own data)


        btnaddpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent posteditor = new Intent(getContext(), PostEditor.class);
                startActivity(posteditor);
            }
        });

        transportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent1 = new Intent(getContext(), Transport_Picker.class);
              startActivity(intent1);

            }
        });

        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent2 = new Intent(getContext(), NotesActivity.class);
              startActivity(intent2);
            }
        });

        hotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getContext(), HotelActivity.class);
                startActivity(intent3);
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current location coordinates
                LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Request location permissions if not granted
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    return;
                }

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // Create the shareable location URL
                String locationUrl = "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;

                // Share the location URL
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Location");
                shareIntent.putExtra(Intent.EXTRA_TEXT, locationUrl);
                startActivity(Intent.createChooser(shareIntent, "Share Location"));
            }
        });
        return rootView;
    }
    private void CreatePost(Postcards post) {
        postarraylist.add(post);
        postAdapter.notifyDataSetChanged();
    }
    private void InitializePosts(View rootView) {
        Forumrecyclerview = rootView.findViewById(R.id.forumrecycler);
        Forumrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        postarraylist = new ArrayList<>();

        postAdapter = new PostAdapter(getContext(), postarraylist);
        Forumrecyclerview.setAdapter(postAdapter);

        postAdapter.setOnButtonClickListener(new PostAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(int position) {
                Postcards post = postarraylist.get(position);
                post.setUpvotes(post.getUpvotes() + 1);
                postAdapter.notifyItemChanged(position);
            }
        });
    }

    private void populateWithFakePosts() {
        File internalStorageDir = getContext().getFilesDir();
        File[] files = internalStorageDir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try (FileInputStream fis = new FileInputStream(file);
                         ObjectInputStream ois = new ObjectInputStream(fis)) {

                        // Read the object from the file
                        Object obj = ois.readObject();
                        if (obj instanceof Postcards) {
                            Postcards post = (Postcards) obj;
                            postarraylist.remove(post); // Remove the old post if it exists
                            postarraylist.add(post); // Add the updated post
                        }

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        // Add fake posts
        Postcards fakePost1 = new Postcards("Lebron James", "Bahalag pildi basta naa sa boracay mga boss", 10, 5);
        postarraylist.add(fakePost1);

        Postcards fakePost2 = new Postcards("Nikola Jokic", "Sardinas sa mo mga manoy", 15, 8);
        postarraylist.add(fakePost2);

        Postcards fakePost3 = new Postcards("Gian Lariosa", "Kinsa ganahan mangisda dira", 10, 5);
        postarraylist.add(fakePost3);

        Postcards fakePost4 = new Postcards("Jomar Magdalaga", "Wassup guys, swim2 ta ninyo", 15, 8);
        postarraylist.add(fakePost4);

        Postcards fakePost5 = new Postcards("Stephen Curry", "Apas diri bron, naa mi station 1", 15, 8);
        postarraylist.add(fakePost5);



        // Notify the adapter that the data has changed
        postAdapter.notifyDataSetChanged();
    }


}
