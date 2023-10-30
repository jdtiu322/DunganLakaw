package com.example.dungan_lakaw;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    private String currentUserEmail;

    MaterialButton signOut;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView userFullNameTextView = view.findViewById(R.id.userfullname);
        TextView userAgeTextView = view.findViewById(R.id.userage);
        TextView userAddressTextView = view.findViewById(R.id.useraddress);
        TextView userEmailTextView = view.findViewById(R.id.useremail);
        signOut = view.findViewById(R.id.logout);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Get the currently signed-in user's email
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUserEmail = currentUser.getEmail();
        }

        // Query the users node in the Firebase Realtime Database to get the data associated with the email
        Query query = usersRef.orderByChild("email").equalTo(currentUserEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        String fullName = user.getFullName();
                        String age = user.getAge();
                        String address = user.getAddress();
                        String email = user.getEmail();

                        userFullNameTextView.setText(fullName);
                        userAgeTextView.setText(age+" years old");
                        userAddressTextView.setText(address);
                        userEmailTextView.setText(email);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
            }
        });

        return view;
    }
}
