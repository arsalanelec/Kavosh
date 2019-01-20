package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import java.util.UUID;

import androidx.databinding.BindingAdapter;

public class Vegetation implements Parcelable {
    public static final Creator<Vegetation> CREATOR = new Creator<Vegetation>() {
        @Override
        public Vegetation createFromParcel(Parcel in) {
            return new Vegetation(in);
        }

        @Override
        public Vegetation[] newArray(int size) {
            return new Vegetation[size];
        }
    };
    String id;
    String description;
    int type;
    private boolean[] coordinations = new boolean[8];

    public Vegetation() {
        this.id = UUID.randomUUID().toString();
    }

    protected Vegetation(Parcel in) {
        id = in.readString();
        description = in.readString();
        type = in.readInt();
        coordinations = in.createBooleanArray();
    }

    @BindingAdapter({"coordination"})
    public static void setCoordinationText(TextView textView, Vegetation vegetation) {
        boolean[] coordinationArr = vegetation.getCoordinations();
        String coordination = "";
        if (coordinationArr[0]) coordination += "شمال،";
        if (coordinationArr[1]) coordination += "جنوب،";
        if (coordinationArr[2]) coordination += "شمال غربی،";
        if (coordinationArr[3]) coordination += "شمال شرقی،";
        if (coordinationArr[4]) coordination += "جنوب غربی،";
        if (coordinationArr[5]) coordination += "جنوب شرقی،";
        if (coordinationArr[6]) coordination += "شرق،";
        if (coordinationArr[7]) coordination += "غرب";
        if (!coordination.isEmpty() && coordination.charAt(coordination.length() - 1) == '،')
            coordination = coordination.substring(0, coordination.length() - 1);
        textView.setText(coordination);
    }

    @BindingAdapter("VegetationTypeName")
    public static void getTypeName(TextView textView, int type) {
        switch (type) {
            case 0:
                textView.setText("کشاورزی");
                return;
            case 1:
                textView.setText("جنگلی");
                return;
            case 2:
                textView.setText("مرتع");
                return;
            case 3:
                textView.setText("بایر");
                return;
        }
        return;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(description);
        dest.writeInt(type);
        dest.writeBooleanArray(coordinations);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean[] getCoordinations() {
        return coordinations;
    }

    public void setCoordinations(boolean[] coordinations) {
        this.coordinations = coordinations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
