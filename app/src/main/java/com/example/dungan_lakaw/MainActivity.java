package com.example.dungan_lakaw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button1;
    private Button button2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);


        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                // Handle button1 click
                Intent intent1 = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent1);
                break;
            case R.id.button2:
                // Handle button2 click
                Intent intent2 = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent2);
                break;
            default:
                // Do nothing
                break;
        }
    }
}