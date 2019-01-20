package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class GeoFeature implements Parcelable {
    public static final Creator<GeoFeature> CREATOR = new Creator<GeoFeature>() {
        @Override
        public GeoFeature createFromParcel(Parcel in) {
            return new GeoFeature(in);
        }

        @Override
        public GeoFeature[] newArray(int size) {
            return new GeoFeature[size];
        }
    };
    String id;
    String name;
    String type;
    int coordination;
    int distance;

    public GeoFeature() {
        this.id = UUID.randomUUID().toString();
    }

    protected GeoFeature(Parcel in) {
        id = in.readString();
        name = in.readString();
        type = in.readString();
        coordination = in.readInt();
        distance = in.readInt();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCoordination() {
        return coordination;
    }

    public void setCoordination(int coordination) {
        this.coordination = coordination;
    }

    public String getCoordinationName() {
        switch (coordination) {
            case 0:
                return "شمال غربی";
            case 1:
                return "شمالی شرقی";
            case 2:
                return "جنوب غربی";
            case 3:
                return "جنوب شرقی";
            case 4:
                return "غرب";
            case 5:
                return "شرق";
        }
        return "";
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeInt(coordination);
        parcel.writeInt(distance);
    }
}
