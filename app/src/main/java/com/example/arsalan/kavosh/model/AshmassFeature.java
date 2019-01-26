package com.example.arsalan.kavosh.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class AshmassFeature extends BaseObservable {

    public static String[] contentHints = {"سفال", "ابزار سنگی", "خاکستر"};
    private static String[] structureEntries = {"چینه ای", "خشتی", "آجری", "سنگی"};
    private int type;
    private int structure;
    private String contents;
    private String description;
    private String length;
    private String width;
    private String depth;

    public AshmassFeature() {
    }

    public static String[] getStructureEntries() {
        return structureEntries;
    }

    public static void setStructureEntries(String[] structureEntries) {
        AshmassFeature.structureEntries = structureEntries;
    }

    @Bindable
    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        if (this.length == null || !this.length.equals(length)) {
            this.length = length;
            notifyChange();
        }
    }

    @Bindable
    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        if (this.width == null || !this.width.equals(width)) {
            this.width = width;
            notifyChange();
        }
    }

    @Bindable
    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        if (this.depth == null || !this.depth.equals(depth)) {
            this.depth = depth;
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
