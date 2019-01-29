package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Layer implements Parcelable {
    public static final Creator<Layer> CREATOR = new Creator<Layer>() {
        @Override
        public Layer createFromParcel(Parcel in) {
            return new Layer(in);
        }

        @Override
        public Layer[] newArray(int size) {
            return new Layer[size];
        }
    };
    @NonNull
    @PrimaryKey
   private String id;
   private String name;
   private String heightLevelH;
   private String heightLevelL;
   private String position;
   private String createdAt;
   private String updatedAt;
   private String deletedAt;

    public Layer() {
        id = UUID.randomUUID().toString();
    }

    protected Layer(Parcel in) {
        id = in.readString();
        name = in.readString();
        heightLevelH = in.readString();
        heightLevelL = in.readString();
        position = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        deletedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(heightLevelH);
        dest.writeString(heightLevelL);
        dest.writeString(position);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(deletedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeightLevelH() {
        return heightLevelH;
    }

    public void setHeightLevelH(String heightLevelH) {
        this.heightLevelH = heightLevelH;
    }

    public String getHeightLevelL() {
        return heightLevelL;
    }

    public void setHeightLevelL(String heightLevelL) {
        this.heightLevelL = heightLevelL;
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
