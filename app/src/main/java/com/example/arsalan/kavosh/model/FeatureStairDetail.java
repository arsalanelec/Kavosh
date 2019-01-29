package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FeatureStairDetail implements Parcelable {
    private String number;
    private String length;
    private String width;
    private String height;

    public FeatureStairDetail() {
    }

    protected FeatureStairDetail(Parcel in) {
        number = in.readString();
        length = in.readString();
        width = in.readString();
        height = in.readString();
    }

    public static final Creator<FeatureStairDetail> CREATOR = new Creator<FeatureStairDetail>() {
        @Override
        public FeatureStairDetail createFromParcel(Parcel in) {
            return new FeatureStairDetail(in);
        }

        @Override
        public FeatureStairDetail[] newArray(int size) {
            return new FeatureStairDetail[size];
        }
    };

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(number);
        dest.writeString(length);
        dest.writeString(width);
        dest.writeString(height);
    }
}
