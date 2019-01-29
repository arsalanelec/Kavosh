package com.example.arsalan.kavosh.model;

import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

//راه آب
public class StairsFeature extends BaseObservable {

    public final static String[] STRUCTURE_HINTS = {"چینه ای", "خشتی", "آجری", "سنگی"};
    public final static String[] TYPE_HINTS = {"داخل ساختمان", "داخل حیاط", "داخل کوچه", "نامشخص","پشت بام","زیرزمین"};

    private String type;
    private String structure;
    private String description;
    private String quantity;
    private List<FeatureStairDetail> stairDimensions;

    @Bindable
    public String getQuantity() {
        return quantity;
    }


    public void setQuantity(String quantity) {
        if(this.quantity==null || !this.quantity.isEmpty()) {
            this.quantity = quantity;
            notifyChange();
        }
    }

    public List<FeatureStairDetail> getStairDimensions() {
        return stairDimensions;
    }

    public void setStairDimensions(List<FeatureStairDetail> stairDimensions) {
        this.stairDimensions = stairDimensions;
    }

    public StairsFeature() {
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {

        if (this.type == null || !this.type.equals(type)) {
            this.type = type;
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
