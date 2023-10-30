package com.example.dungan_lakaw.posts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dungan_lakaw.HomePage;
import com.example.dungan_lakaw.R;
import com.example.dungan_lakaw.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PostEditor extends AppCompatActivity {

    private EditText posttxt;
    private Button btnaddpost;

    private DatabaseReference usersRef;
    private List<Postcards> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_editor);

        // Initialize Firebase Database reference
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        posttxt = findViewById(R.id.editText);
        btnaddpost = findViewById(R.id.btnsubmitpost);

        postList = new ArrayList<>();

        btnaddpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String posttext = posttxt.getText().toString();

                // Get the current user
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();

                    // Get a reference to the "users" node in the Firebase database
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

                    // Create a new child node under the "users" node with the user's ID
                    DatabaseReference currentUserRef = usersRef.child(userId);

                    // Retrieve the user from the current user's reference in the database
                    currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Get the user object
                                User user = dataSnapshot.getValue(User.class);

                                if (user != null) {
                                    // Get the full name from the user object
                                    String fullName = user.getFullName();

                                    // Create a new post instance with the full name
                                    Postcards post = new Postcards(fullName, posttext, 0, 0);

                                    // Save the post to a file
                                    File internalStorageDir = getFilesDir();
                                    String filename = "post_" + System.currentTimeMillis() + ".txt";
                                    File file = new File(internalStorageDir, filename);

                                    try (FileOutputStream fos = new FileOutputStream(file);
                                         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                                        oos.writeObject(post);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    startActivity(new Intent(PostEditor.this, HomePage.class));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle the cancellation
                            // ...
                        }
                    });
                }
            }
        });
    }
}
