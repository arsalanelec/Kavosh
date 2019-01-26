package com.example.arsalan.kavosh.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class StoveFeature extends BaseObservable {

    public static String[] contentHints = {"ذغال", "خاکستر", "جوش کوره", "قطعه سفال", "خاک حرارت دیده"};
    private static String[] typeEntries = {"داخل اتاق", "داخل حیاط", "نامشخص"};
    private static String[] structureEntries = {"چینه ای", "خشتی", "آجری", "سنگی"};
    private int type;
    private int structure;
    private String contents;
    private String description;

    public StoveFeature() {
    }

    public static String[] getStructureEntries() {
        return structureEntries;
    }

    public static void setStructureEntries(String[] structureEntries) {
        StoveFeature.structureEntries = structureEntries;
    }

    public static String[] getTypeEntries() {
        return typeEntries;
    }

    public static void setTypeEntries(String[] typeEntries) {
        StoveFeature.typeEntries = typeEntries;
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


}
