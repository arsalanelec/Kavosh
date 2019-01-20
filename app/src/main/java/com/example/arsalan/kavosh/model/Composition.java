package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Composition implements Parcelable {
    public static final Creator<Composition> CREATOR = new Creator<Composition>() {
        @Override
        public Composition createFromParcel(Parcel in) {
            return new Composition(in);
        }

        @Override
        public Composition[] newArray(int size) {
            return new Composition[size];
        }
    };
    private String type;
    private String percent;
    private String dimension;
    private int meter_mili;
    private List<String> shapes;
    private transient String shapesSt;

    public Composition() {
    }

    protected Composition(Parcel in) {
        type = in.readString();
        percent = in.readString();
        dimension = in.readString();
        meter_mili = in.readInt();
        shapes = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(percent);
        dest.writeString(dimension);
        dest.writeInt(meter_mili);
        dest.writeStringList(shapes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public List<String> getShapes() {
        return shapes;
    }

    public void setShapes(List<String> shapes) {
        this.shapes = shapes;
    }

    public String getShapesSt() {
        String result = "";
        for (int i = 0; i < shapes.size(); i++) {
            result += shapes.get(i) + " ";
        }
        return result;
    }

    public void setShapesSt(String shapesSt) {
        this.shapesSt = shapesSt;
    }

    public int getMeter_mili() {
        return meter_mili;
    }

    public void setMeter_mili(int meter_mili) {
        this.meter_mili = meter_mili;
    }
}
