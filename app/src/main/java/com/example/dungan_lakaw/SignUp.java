package com.example.dungan_lakaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText editTextFullName, editTextEmail, editTextPassword, editTextAge, editTextAddress;
    Button signUpButton;
    TextView login;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        editTextFullName = findViewById(R.id.fullname);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextAge = findViewById(R.id.age);
        editTextAddress = findViewById(R.id.address);

        signUpButton = findViewById(R.id.register);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName, email, password, age, address;
                fullName = String.valueOf(editTextFullName.getText());
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                age = String.valueOf(editTextAge.getText());
                address = String.valueOf(editTextAddress.getText());

                if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUp.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Save the full name in SharedPreferences
                                        String userId = mAuth.getCurrentUser().getUid(); // Get the current user's ID

                                        // Create a reference to the users node in the Firebase Realtime Database
                                        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

                                        // Create a new child node under the users node with the user's ID
                                        DatabaseReference currentUserRef = usersRef.child(userId);

                                        // Save the user's information as key-value pairs
                                        currentUserRef.child("fullName").setValue(fullName);
                                        currentUserRef.child("email").setValue(email);
                                        currentUserRef.child("age").setValue(age);
                                        currentUserRef.child("address").setValue(address);

                                        Toast.makeText(SignUp.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUp.this, SignIn.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SignUp.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
