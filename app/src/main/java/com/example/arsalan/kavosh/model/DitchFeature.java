package com.example.arsalan.kavosh.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

//راه آب
public class DitchFeature extends BaseObservable {

    public final static String[] STRUCTURE_HINTS = {"چینه ای", "خشتی", "آجری", "سنگی", "سفالی"};
    public final static String[] SHAPE_HINTS = {"لوله", "تنبوشه", "ناودانی", "نهری"};
    public final static String[] PLACE_HINTS = {"داخل ساختمان", "داخل حیاط", "روی دیوار", "نامشخص"};
    private int type;
    private String structure;
    private String description;
    private String slope;
    private String shape;
    private String place;
    private int slopeFromCord;
    private int slopeToCord;


    public DitchFeature() {
    }

    @Bindable
    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    @Bindable
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        if (this.place == null || !this.place.equals(place)) {
            this.place = place;
            notifyChange();
        }
    }

    @Bindable
    public int getSlopeFromCord() {
        return slopeFromCord;
    }

    public void setSlopeFromCord(int slopeFromCord) {
        if (this.slopeFromCord != slopeFromCord) {
            this.slopeFromCord = slopeFromCord;
            notifyChange();
        }
    }

    @Bindable
    public int getSlopeToCord() {
        return slopeToCord;
    }

    public void setSlopeToCord(int slopeToCord) {
        if (this.slopeToCord != slopeToCord) {
            this.slopeToCord = slopeToCord;
            notifyChange();
        }
    }

    @Bindable
    public String getSlope() {
        return slope;
    }

    public void setSlope(String slope) {
        if (this.slope == null || !this.slope.equals(slope)) {
            this.slope = slope;
            notifyChange();
        }
    }


    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (this.description == null || !this.description.equals(description)) {
            this.description = description;
            notifyChange();
        }
    }

    @Bindable
    public int getType() {
        return type;
    }

    public void setType(int type) {
        if (this.type != type) {
            this.type = type;
            notifyChange();
        }
    }

    @Bindable
    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        if (this.structure == null || !this.structure.equals(structure)) {
            this.structure = structure;
            notifyChange();
        }
    }


}
