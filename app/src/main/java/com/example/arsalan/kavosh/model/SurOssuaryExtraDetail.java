package com.example.arsalan.kavosh.model;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class SurOssuaryExtraDetail extends BaseObservable {

    private int entryCoordination;
    private String entryProperties;
    private String entryHeight;
    private String entryWidth;
    private String entryThickness;
    private String roomHeight;
    private String roomWidth;
    private String roomLength;
    private String description;

    private List<Niche> nicheList;
    private List<Laver> laverList;

    public SurOssuaryExtraDetail() {
        nicheList = new ArrayList<>();
        laverList = new ArrayList<>();
    }

    @Bindable
    public String getEntryProperties() {
        return entryProperties;
    }

    public void setEntryProperties(String entryProperties) {
        if (this.entryProperties == null || this.entryProperties != entryProperties) {
            this.entryProperties = entryProperties;
            notifyChange();
        }
    }

    @Bindable
    public String getEntryHeight() {
        return entryHeight;
    }

    public void setEntryHeight(String entryHeight) {
        if (this.entryHeight == null || this.entryHeight != entryHeight) {
            this.entryHeight = entryHeight;
            notifyChange();
        }
    }

    @Bindable
    public int getEntryCoordination() {
        return entryCoordination;
    }

    public void setEntryCoordination(int entryCoordination) {
        if (this.entryCoordination != entryCoordination) {
            this.entryCoordination = entryCoordination;
            notifyChange();
        }
    }

    @Bindable
    public String getEntryWidth() {
        return entryWidth;
    }

    public void setEntryWidth(String entryWidth) {
        if (this.entryWidth == null || this.entryWidth != entryWidth) {
            this.entryWidth = entryWidth;
            notifyChange();
        }
    }

    @Bindable
    public String getEntryThickness() {
        return entryThickness;
    }

    public void setEntryThickness(String entryThickness) {
        if (this.entryThickness == null || this.entryThickness != entryThickness) {
            this.entryThickness = entryThickness;
            notifyChange();
        }
    }

    @Bindable
    public String getRoomHeight() {
        return roomHeight;
    }

    public void setRoomHeight(String roomHeight) {
        if (this.roomHeight == null || this.roomHeight != roomHeight) {
            this.roomHeight = roomHeight;
            notifyChange();
        }
    }

    @Bindable
    public String getRoomWidth() {
        return roomWidth;
    }

    public void setRoomWidth(String roomWidth) {
        if (this.roomWidth == null || this.roomWidth != roomWidth) {
            this.roomWidth = roomWidth;
            notifyChange();
        }
    }

    @Bindable
    public String getRoomLength() {
        return roomLength;
    }

    public void setRoomLength(String roomLength) {
        if (this.roomLength == null || this.roomLength != roomLength) {
            this.roomLength = roomLength;
            notifyChange();
        }
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (this.description == null || this.description != description) {
            this.description = description;
            notifyChange();
        }
    }

    @Bindable
    public List<Niche> getNicheList() {
        return nicheList;
    }

    public void setNicheList(List<Niche> nicheList) {
        this.nicheList = nicheList;
    }

    @Bindable
    public List<Laver> getLaverList() {
        return laverList;
    }

    public void setLaverList(List<Laver> laverList) {
        this.laverList = laverList;
    }


}
