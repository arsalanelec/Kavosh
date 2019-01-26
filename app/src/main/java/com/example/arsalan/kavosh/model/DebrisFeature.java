package com.example.arsalan.kavosh.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class DebrisFeature extends BaseObservable {

    public static String[] propertiesHint = {"دارای تکه های بزرگ و به هم پیوسته", "زاویه های مشخص", "زاویه های گوناگون"};
    public static String[] placeHint = {"قرارگرفته بر روی کف حیاط", "قرارگرفته بر روی کف اتاق", "قرارگرفته بر روی کف کوچه", "قرارگرفته بر روی کف نا مشخص", "قرار کرفته بر روی آوار"};
    private static String[] structureEntries = {"چینه ای", "خشتی", "آجری", "سنگی"};
    private static String[] debrisType = {"آوار سقف", "آوار دیوار", "آوار نامشخص و درهم"};
    private int type;
    private int structure;
    private String properties;
    private String place;
    private String description;

    public DebrisFeature() {
    }

    public static String[] getStructureEntries() {
        return structureEntries;
    }

    public static void setStructureEntries(String[] structureEntries) {
        DebrisFeature.structureEntries = structureEntries;
    }

    public static String[] getPropertiesHint() {
        return propertiesHint;
    }

    public static void setPropertiesHint(String[] propertiesHint) {
        DebrisFeature.propertiesHint = propertiesHint;
    }

    public static String[] getPlaceHint() {
        return placeHint;
    }

    public static void setPlaceHint(String[] placeHint) {
        DebrisFeature.placeHint = placeHint;
    }

    public static String[] getDebrisType() {
        return debrisType;
    }

    public static void setDebrisType(String[] debrisType) {
        DebrisFeature.debrisType = debrisType;
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
    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        if (this.properties == null || !this.properties.equals(properties)) {
            this.properties = properties;
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
