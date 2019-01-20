package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Destruction extends BaseObservable implements Parcelable {
    public static final Creator<Destruction> CREATOR = new Creator<Destruction>() {
        @Override
        public Destruction createFromParcel(Parcel in) {
            return new Destruction(in);
        }

        @Override
        public Destruction[] newArray(int size) {
            return new Destruction[size];
        }
    };
    int coordination;
    int factorType;
    String factorName;
    int level;

    public Destruction() {
    }

    protected Destruction(Parcel in) {
        coordination = in.readInt();
        factorType = in.readInt();
        factorName = in.readString();
        level = in.readInt();
    }

    @Bindable
    public int getCoordination() {
        return coordination;
    }

    public void setCoordination(int coordination) {
        this.coordination = coordination;
    }

    @Bindable
    public int getFactorType() {
        return factorType;
    }

    public void setFactorType(int factorType) {
        this.factorType = factorType;
    }

    @Bindable
    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    @Bindable
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public String getLevelString() {
        switch (level) {
            case 0:
                return "سطحی";
            case 1:
                return "کم";
            case 2:
                return "زیاد";
            case 3:
                return "بسیار زیاد";
            default:
                return "";
        }
    }

    public String getFactorTypeString() {
        switch (factorType) {
            case 0:
                return "انسانی";
            case 1:
                return "طبیعی";
            default:
                return "";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(coordination);
        parcel.writeInt(factorType);
        parcel.writeString(factorName);
        parcel.writeInt(level);
    }
}
