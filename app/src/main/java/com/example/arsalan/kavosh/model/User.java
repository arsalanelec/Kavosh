package com.example.arsalan.kavosh.model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String email;
    @SerializedName("national_id")
    private String nationalId;
    private String mobile;
    @SerializedName("degree_edu")
    private int eduDegree;

    public User() {
        this.id = UUID.randomUUID().toString();

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getEduDegree() {
        return eduDegree;
    }

    public void setEduDegree(int eduDegree) {
        this.eduDegree = eduDegree;
    }
}
