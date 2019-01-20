package com.example.arsalan.kavosh.model;

import android.widget.TextView;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Project {
    /* "id": 1,
        "name": "ex1",
        "type": 1,
        "status": 1,
        "created_at": "2018-08-27 15:18:29",
        "updatedAt": "2018-08-27 15:18:29",
        "deletedAt": null*/
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private int type;
    private int status;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public Project() {
        this.id = UUID.randomUUID().toString();
    }

    /*@BindingAdapter({"foundName"})
        public static void setFoundName(TextView textView, String foundJson) {
            Gson gson = new Gson();
            try {
                FoundDetail foundDetail = gson.fromJson(foundJson, FoundDetail.class);
                textView.setText(foundDetail.getName());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }*/
    @BindingAdapter("TypeName")
    public static void getTypeName(TextView textView, int type) {
        switch (type) {
            case 1:
                textView.setText("کاوش");
                return;
            case 2:
                textView.setText("بررسی");
                return;

        }
        return;
    }

    @Override
    public String toString() {
        return "Project{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}
