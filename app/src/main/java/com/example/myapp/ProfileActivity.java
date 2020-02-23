package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

    private Button buttonRegisterPatient;
    private Button buttonViewPatients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
         mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    finish();
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                }
            }
        };

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail =  findViewById(R.id.textViewUserEmail);
        buttonLogout =  findViewById(R.id.buttonLogout);
        buttonRegisterPatient = findViewById(R.id.buttonRegisterPatient);
        buttonViewPatients = findViewById(R.id.buttonViewPatients);

        //displaying logged in user name`
        textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        buttonRegisterPatient.setOnClickListener(this);
        buttonViewPatients.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        }
        if (view == buttonRegisterPatient) {
            startActivity(new Intent(ProfileActivity.this, patientRegisterActivity.class));
        }
        if (view == buttonViewPatients) {
            startActivity(new Intent(ProfileActivity.this, patientsViewActivity.class));
        }

    }
    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(mAuthListener);

    }
}