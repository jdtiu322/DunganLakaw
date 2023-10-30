package com.example.dungan_lakaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignIn extends AppCompatActivity {
    EditText editTextemail, editTextpassword;
    MaterialButton logInButton;
    FirebaseAuth mAuth;
    TextView register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        editTextemail = findViewById(R.id.email);
        editTextpassword = findViewById(R.id.password);
        logInButton = findViewById(R.id.login);
        register = findViewById(R.id.signUp);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(editTextemail.getText());
                password = String.valueOf(editTextpassword.getText());
                if(TextUtils.isEmpty(email) ||  TextUtils.isEmpty(password) ){
                    Toast.makeText(SignIn.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(SignIn.this, HomePage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignIn.this, "Invalid email or password. Try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
