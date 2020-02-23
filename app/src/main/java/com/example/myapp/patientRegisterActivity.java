package com.example.myapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.models.Patient;
import com.example.myapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class patientRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "patientRegisterActivity";

    private EditText patientName;
    private EditText patientGender;
    private EditText patientAge;
    private EditText patientRemarks;

    private Button buttonRegisterPatient;

    private String name;
    private String gender;
    private String age;
    private String remarks;
    private User user = null;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

//        mUser = database.getReference().child("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//        this.user = new User();
//        if (this.user == null) {
//            Toast.makeText(patientRegisterActivity.this, "User not created!!",Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(patientRegisterActivity.this, "New user created!",Toast.LENGTH_SHORT).show();
//        }
//        ValueEventListener userListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                patientRegisterActivity.this.user = dataSnapshot.getValue(User.class);
//                if (patientRegisterActivity.this.user == null) {
//                    Toast.makeText(patientRegisterActivity.this, "User now null!!",Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(patientRegisterActivity.this, "User not null yay!",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                Toast.makeText(patientRegisterActivity.this, "Failed to load post.",Toast.LENGTH_SHORT).show();
//            }
//        };
//        mUser.addValueEventListener(userListener);


        //get user data from database i.e get the keys stored instead of the the following line
        user = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(), FirebaseAuth.getInstance().getCurrentUser().getEmail());

        patientName = findViewById(R.id.patientName);
        patientGender = findViewById(R.id.patientGender);
        patientAge = findViewById(R.id.patientAge);
        patientRemarks = findViewById(R.id.patientRemarks);
        buttonRegisterPatient = findViewById(R.id.buttonRegisterPatient);

        buttonRegisterPatient.setOnClickListener(patientRegisterActivity.this);
    }

    @Override
    public void onClick(View view) {

        if (view == buttonRegisterPatient) {
            registerPatient();
        }

    }

    private void registerPatient() {
        Toast.makeText(patientRegisterActivity.this, "Inside registerPatient.",Toast.LENGTH_SHORT).show();

        name = patientName.getText().toString().trim();
        gender = patientGender.getText().toString().trim();
        age = patientAge.getText().toString().trim();
        remarks = patientRemarks.getText().toString().trim();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = database.getReference();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Generating key for the new patient using push
        String key = mDatabase.child("patients").push().getKey();

        //Patient object to be stored
        Patient patient = new Patient(uid, name, gender, age, remarks);

//        if (this.user == null) {
//            Toast.makeText(patientRegisterActivity.this, "User does not exist.",Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(patientRegisterActivity.this, "User exists.",Toast.LENGTH_SHORT).show();
//        }



        //adding the key to the logged in user
        this.user.addPatientKey(key, name);

        // Storing Patient and User Objects in maps
        Map<String, Object> patientValues = patient.toMap();
        Map<String, Object> userValues = this.user.toMap();


        // Map to be used for updating database
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/users/"+uid+"/", userValues);
        childUpdates.put("/patients/"+key, patientValues);

        // updating database
        mDatabase.updateChildren(childUpdates);


        Toast.makeText(patientRegisterActivity.this, "Adding data to db",Toast.LENGTH_LONG).show();


    }

}
