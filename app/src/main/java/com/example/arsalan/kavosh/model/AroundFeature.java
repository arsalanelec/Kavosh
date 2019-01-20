package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import java.util.UUID;

import androidx.databinding.BindingAdapter;

public class AroundFeature implements Parcelable {
    public static final Creator<AroundFeature> CREATOR = new Creator<AroundFeature>() {
        @Override
        public AroundFeature createFromParcel(Parcel in) {
            return new AroundFeature(in);
        }

        @Override
        public AroundFeature[] newArray(int size) {
            return new AroundFeature[size];
        }
    };
    String id;
    String name;
    String description;
    int type;
    private boolean[] coordinations = new boolean[8];

    public AroundFeature() {
        this.id = UUID.randomUUID().toString();
    }

    protected AroundFeature(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        type = in.readInt();
        coordinations = in.createBooleanArray();
    }

    @BindingAdapter({"coordination"})
    public static void setCoordinationText(TextView textView, AroundFeature vegetation) {
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

    @BindingAdapter("AroundFeatureTypeName")
    public static void getTypeName(TextView textView, int type) {
        switch (type) {
            case 0:
                textView.setText("ناهموار");
                return;
            case 1:
                textView.setText("مسطح");
                return;
            case 2:
                textView.setText("دارای پستی و بلندی");
                return;
            case 3:
                textView.setText("در شیب");
                return;
            case 4:
                textView.setText("در دامنه");
                return;
        }
        return;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
