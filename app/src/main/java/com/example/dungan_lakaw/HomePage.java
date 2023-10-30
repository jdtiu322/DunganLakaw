package com.example.dungan_lakaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    FriendsFragment friendsFragment = new FriendsFragment();
    CalendarFragment calendarFragment = new CalendarFragment();
    MessagesFragment messagesFragment = new MessagesFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        break;
                    case R.id.calendar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, calendarFragment).commit();
                        break;
                    case R.id.friends:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, friendsFragment).commit();
                        break;
                    case R.id.messages:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, messagesFragment).commit();
                        break;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                        break;
                }
                return true;
            }
        });
    }
}
