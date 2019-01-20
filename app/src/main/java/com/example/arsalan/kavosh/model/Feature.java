package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Feature implements Parcelable {
    public static final Creator<Feature> CREATOR = new Creator<Feature>() {
        @Override
        public Feature createFromParcel(Parcel in) {
            return new Feature(in);
        }

        @Override
        public Feature[] newArray(int size) {
            return new Feature[size];
        }
    };
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String contentJson;
    private String structureName;
    private int structure_index;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public Feature() {
        id = UUID.randomUUID().toString();
    }

    protected Feature(Parcel in) {
        id = in.readString();
        name = in.readString();
        contentJson = in.readString();
        structureName = in.readString();
        structure_index = in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
        deletedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(contentJson);
        dest.writeString(structureName);
        dest.writeInt(structure_index);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentJson() {
        return contentJson;
    }

    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
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

    public int getStructure_index() {
        return structure_index;
    }

    public void setStructure_index(int structure_index) {
        this.structure_index = structure_index;
    }
}
