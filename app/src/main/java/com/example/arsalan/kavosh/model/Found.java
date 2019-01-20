package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Found implements Parcelable {
    public static final Creator<Found> CREATOR = new Creator<Found>() {
        @Override
        public Found createFromParcel(Parcel in) {
            return new Found(in);
        }

        @Override
        public Found[] newArray(int size) {
            return new Found[size];
        }
    };
    @androidx.annotation.NonNull
    @PrimaryKey
    private String id;
    private String excavationItemId;
    private String contentJson;
    private int type;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public Found() {
        id = UUID.randomUUID().toString();
    }

    protected Found(Parcel in) {
        id = in.readString();
        excavationItemId = in.readString();
        contentJson = in.readString();
        type = in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
        deletedAt = in.readString();
    }

    public String getRegisterNumber() {
        Gson gson = new Gson();
        try {
            FoundDetail foundDetail = gson.fromJson(contentJson, FoundDetail.class);
            return foundDetail.getRegisterNum();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }
  /* @BindingAdapter({"registerNumber"})
    public static void setRegisterNumber(TextView textView, String foundJson) {
        Gson gson = new Gson();
        try {
            FoundDetail foundDetail = gson.fromJson(foundJson, FoundDetail.class);
            textView.setText(foundDetail.getRegisterNum());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }*/


    public String getFoundName() {
        Gson gson = new Gson();
        try {
            FoundDetail foundDetail = gson.fromJson(contentJson, FoundDetail.class);
            return foundDetail.getName();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getExcavationItemId() {
        return excavationItemId;
    }

    public void setExcavationItemId(String excavationItemId) {
        this.excavationItemId = excavationItemId;
    }

    public String getContentJson() {
        return contentJson;
    }

    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(excavationItemId);
        parcel.writeString(contentJson);
        parcel.writeInt(type);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        parcel.writeString(deletedAt);
    }
}
