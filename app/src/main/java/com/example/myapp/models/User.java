package com.example.myapp.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {

    private String uid;
    private String email;
    Map<String, String> patientKeys;

    public User() {
//        Toast.makeText(User.this, "Inside user constructor.",Toast.LENGTH_SHORT).show();
        //Log.w("Inside user constructor", "loadPost:onCancelled");
    }

    public void setPatientKeys(Map<String, String> patientKeys) {
        this.patientKeys = patientKeys;
    }

    public void addPatientKey(String key, String name) {
        this.patientKeys.put(key,name);
    }



    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
        this.patientKeys = new HashMap<>();
    }



    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("uid", uid);
        result.put("email", email);
        result.put("keys",patientKeys);


        return result;
    }
}