package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class LayerCoordination implements Parcelable {
    public static final Creator<LayerCoordination> CREATOR = new Creator<LayerCoordination>() {
        @Override
        public LayerCoordination createFromParcel(Parcel in) {
            return new LayerCoordination(in);
        }

        @Override
        public LayerCoordination[] newArray(int size) {
            return new LayerCoordination[size];
        }
    };
    private String name;
    private boolean[] coordinations = new boolean[6];

    public LayerCoordination() {
    }

    protected LayerCoordination(Parcel in) {
        name = in.readString();
        coordinations = in.createBooleanArray();
    }

    @BindingAdapter({"coordination"})
    public static void setCoordination(TextView textView, LayerCoordination layerCoordination) {
        boolean[] coordinationArr = layerCoordination.getCoordinations();
        String coordination = "";
        if (coordinationArr[0]) coordination += "شمال،";
        if (coordinationArr[1]) coordination += "چنوب،";
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
