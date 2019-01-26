package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Laver implements Parcelable {
    public static final Creator<Laver> CREATOR = new Creator<Laver>() {
        @Override
        public Laver createFromParcel(Parcel in) {
            return new Laver(in);
        }

        @Override
        public Laver[] newArray(int size) {
            return new Laver[size];
        }
    };
    private int coordination;
    private String length;
    private String width;
    private String depth;

    public Laver() {
    }

    protected Laver(Parcel in) {
        coordination = in.readInt();
        length = in.readString();
        width = in.readString();
        depth = in.readString();
    }

    public String getCoordinationString() {
        switch (coordination) {
            case 0:
                return "شمال";
            case 1:
                return "جنوب";
            case 2:
                return "شمال غربی";
            case 3:
                return "شمال شرقی";
            case 4:
                return "جنوب غربی";
            case 5:
                return "جنوب شرقی";
            case 6:
                return "غرب";
            case 7:
                return "شرق";
        }
        return "";
    }

    public int getCoordination() {
        return coordination;
    }

    public void setCoordination(int coordination) {
        this.coordination = coordination;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(coordination);
        parcel.writeString(length);
        parcel.writeString(width);
        parcel.writeString(depth);
    }
}
