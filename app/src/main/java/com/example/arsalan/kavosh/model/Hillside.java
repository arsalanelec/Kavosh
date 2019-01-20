package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hillside implements Parcelable {
    public static final Creator<Hillside> CREATOR = new Creator<Hillside>() {
        @Override
        public Hillside createFromParcel(Parcel in) {
            return new Hillside(in);
        }

        @Override
        public Hillside[] newArray(int size) {
            return new Hillside[size];
        }
    };
    int coordination;
    int slope;

    public Hillside() {
    }

    protected Hillside(Parcel in) {
        coordination = in.readInt();
        slope = in.readInt();
    }

    public int getCoordination() {
        return coordination;
    }

    public void setCoordination(int coordination) {
        this.coordination = coordination;
    }

    public int getSlope() {
        return slope;
    }

    public void setSlope(int slope) {
        this.slope = slope;
    }

    public String getSlopeString() {
        switch (slope) {
            case 0:
                return "ملایم";
            case 1:
                return "تند";
            case 2:
                return "عمودی";
        }
        return "";
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(coordination);
        parcel.writeInt(slope);
    }
}
