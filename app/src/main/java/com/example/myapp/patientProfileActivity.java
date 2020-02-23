package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class patientProfileActivity extends AppCompatActivity {

    private static final String TAG = "patientProfileActivity";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mPatients;

    private TextView textViewName;
    private TextView textViewGender;
    private TextView textViewAge;
    private TextView textViewRemarks;

    private Button buttonViewImages;
    private Button buttonUploadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        //read from database the patient key received from the intent and display details, button for show uploaded images and ask for new upload.
        Intent intent = getIntent();
        String key = intent.getStringExtra(patientsViewActivity.EXTRA_MESSAGE);
//        Toast.makeText(patientProfileActivity.this,key,Toast.LENGTH_LONG).show();
        mPatients = database.getReference().child("patients/"+key);

        textViewName = findViewById(R.id.textViewName);
        textViewAge = findViewById(R.id.textViewAge);
        textViewGender = findViewById(R.id.textViewGender);
        textViewRemarks = findViewById(R.id.textViewRemarks);

        buttonUploadImage = findViewById(R.id.buttonUploadImage);
        buttonViewImages = findViewById(R.id.buttonViewPatients);


        ValueEventListener patientListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Patient patient = new Patient();
                patient = dataSnapshot.getValue(Patient.class);
                textViewName.setText(patient.getName());
                textViewAge.setText(patient.getAge());
                textViewGender.setText(patient.getGender());
                textViewRemarks.setText(patient.getRemarks());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(patientProfileActivity.this, "Failed to load post.",Toast.LENGTH_SHORT).show();
            }
        };
        mPatients.addValueEventListener(patientListener);

    }





}
