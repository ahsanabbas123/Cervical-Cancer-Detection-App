package com.example.myapp.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Patient {

    private String uid;
    private String name;
    private String gender;
    private String age;
    private String remarks;

    public Patient() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Patient(String uid, String name, String gender, String age, String remarks) {
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.remarks = remarks;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("uid", uid);
        result.put("name", name);
        result.put("gender", gender);
        result.put("age", age);
        result.put("remarks", remarks);

        return result;
    }


}