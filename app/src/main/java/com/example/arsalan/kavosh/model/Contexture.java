package com.example.arsalan.kavosh.model;

import com.google.gson.annotations.Expose;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
    "id": "2fe77cdc-b399-4dcb-a59a-0cc684000425",
    "accumulation": 2,
    "layer_contexture": 3,
    "layers_feature_id": "d6ac9a20-763b-411c-a4c6-5f97385cffab",
    "soil_color": "قهوه ای تیره",
    "Composition": "ترکیب",
    "tools": "ابزار",
    "sample_depth": 23,
    "type": 1,
    "name": "توضیحات",
    "created_at": "2018-09-14 18:52:24",
    "updated_at": "2018-09-14 18:52:24",
    "deleted_at": null*/
@Entity
public class Contexture {
    @NonNull
    @PrimaryKey
    private String id;
    private int accumulation;
    private int layerContexture;
    private String layerId;
    private String soilColor;
    private String composition;
    private String heightLevelH;
    private String heightLevelL;
    private String tools;
    private int sampleDepth;
    private int type;
    private String description;
    @Expose(serialize = false)
    private String createdAt;
    @Expose(serialize = false)
    private String updatedAt;
    @Expose(serialize = false)
    private String deletedAt;

    public Contexture() {
        this.id = UUID.randomUUID().toString();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getHeightLevelH() {
        return heightLevelH;
    }

    public void setHeightLevelH(String heightLevelH) {
        this.heightLevelH = heightLevelH;
    }

    public String getHeightLevelL() {
        return heightLevelL;
    }

    public void setHeightLevelL(String heightLevelL) {
        this.heightLevelL = heightLevelL;
    }

    public int getAccumulation() {
        return accumulation;
    }

    public void setAccumulation(int accumulation) {
        this.accumulation = accumulation;
    }

    public int getLayerContexture() {
        return layerContexture;
    }

    public void setLayerContexture(int layerContexture) {
        this.layerContexture = layerContexture;
    }

    public String getLayerId() {
        return layerId;
    }

    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    public String getSoilColor() {
        return soilColor;
    }

    public void setSoilColor(String soilColor) {
        this.soilColor = soilColor;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public int getSampleDepth() {
        return sampleDepth;
    }

    public void setSampleDepth(int sampleDepth) {
        this.sampleDepth = sampleDepth;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}
