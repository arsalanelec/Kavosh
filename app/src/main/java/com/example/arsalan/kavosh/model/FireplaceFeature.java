package com.example.arsalan.kavosh.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class FireplaceFeature extends BaseObservable {

    public static String[] contentHints = {"ذغال", "خاکستر", "جوش کوره", "قطعه سفال", "خاک حرارت دیده"};
    public static String[] placeHint = {"در داخل اتاق", "در داخل حیاط", "در فضای باز", "نامشخص"};
    private static String[] structureEntries = {"چینه ای", "خشتی", "آجری", "سنگی"};
    private int type;
    private int structure;
    private String contents;
    private String place;
    private String description;

    public FireplaceFeature() {
    }

    public static String[] getStructureEntries() {
        return structureEntries;
    }

    public static void setStructureEntries(String[] structureEntries) {
        FireplaceFeature.structureEntries = structureEntries;
    }

    public static String[] getPlaceHint() {
        return placeHint;
    }

    public static void setPlaceHint(String[] placeHint) {
        FireplaceFeature.placeHint = placeHint;
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
    public int getStructure() {
        return structure;
    }

    public void setStructure(int structure) {
        if (this.structure != structure) {
            this.structure = structure;
            notifyChange();
        }
    }

    @Bindable
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        if (this.contents == null || !this.contents.equals(contents)) {
            this.contents = contents;
            notifyChange();
        }
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


}
