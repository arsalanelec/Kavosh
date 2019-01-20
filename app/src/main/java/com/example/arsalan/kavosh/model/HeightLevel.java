package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class HeightLevel implements Parcelable {
    public static final Creator<HeightLevel> CREATOR = new Creator<HeightLevel>() {
        @Override
        public HeightLevel createFromParcel(Parcel in) {
            return new HeightLevel(in);
        }

        @Override
        public HeightLevel[] newArray(int size) {
            return new HeightLevel[size];
        }
    };
    private int coordination;
    private double value;
    private int reference;

    public HeightLevel() {
    }

    protected HeightLevel(Parcel in) {
        coordination = in.readInt();
        value = in.readDouble();
        reference = in.readInt();
    }

    @BindingAdapter({"coordinationName"})
    public static void setCoordinationName(TextView view, int coordination) {

        switch (coordination) {
            case 0:
                view.setText("شمال");
                break;
            case 1:
                view.setText("جنوب");
                break;
            case 2:
                view.setText("شمال غربی");
                break;
            case 3:
                view.setText("شمال شرقی");
                break;
            case 4:
                view.setText("جنوب غربی");
                break;
            case 5:
                view.setText("جنوب شرقی");
                break;
            case 6:
                view.setText("غرب");
                break;
            case 7:
                view.setText("شرق");
                break;
        }
    }

    @BindingAdapter({"referenceName"})
    public static void setReferenceName(TextView view, int reference) {

        switch (reference) {
            case 1:
                view.setText("آبهای آزاد");
                break;
            case 2:
                view.setText("بنچ مارک اصلی");
                break;
            case 3:
                view.setText("بیس فرعی");
                break;

        }
    }

    public int getCoordination() {
        return coordination;
    }

    public void setCoordination(int coordination) {
        this.coordination = coordination;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(coordination);
        parcel.writeDouble(value);
        parcel.writeInt(reference);
    }
}
