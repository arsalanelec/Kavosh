package com.example.arsalan.kavosh.model;

import com.google.gson.annotations.Expose;

public class FloorFeature {
    private int floorType;
    private int floorStruct;
    private String floorColor;
    private int contexture;
    private String surface;
    private String compositionJson;
    private double dimX;
    private double dimY;
    private double dimZ;
    private double depth;
    private String description;
    @Expose(serialize = false)
    private String createdAt;
    @Expose(serialize = false)
    private String updatedAt;
    @Expose(serialize = false)
    private String deletedAt;

    public FloorFeature() {
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public int getFloorType() {
        return floorType;
    }

    public void setFloorType(int floorType) {
        this.floorType = floorType;
    }

    public int getFloorStruct() {
        return floorStruct;
    }

    public void setFloorStruct(int floorStruct) {
        this.floorStruct = floorStruct;
    }

    public String getFloorColor() {
        return floorColor;
    }

    public void setFloorColor(String floorColor) {
        this.floorColor = floorColor;
    }

    public int getContexture() {
        return contexture;
    }

    public void setContexture(int contexture) {
        this.contexture = contexture;
    }

    public String getCompositionJson() {
        return compositionJson;
    }

    public void setCompositionJson(String compositionJson) {
        this.compositionJson = compositionJson;
    }


    public double getDimX() {
        return dimX;
    }

    public void setDimX(double dimX) {
        this.dimX = dimX;
    }

    public double getDimY() {
        return dimY;
    }

    public void setDimY(double dimY) {
        this.dimY = dimY;
    }

    public double getDimZ() {
        return dimZ;
    }

    public void setDimZ(double dimZ) {
        this.dimZ = dimZ;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
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

    @Override
    public String toString() {
        return "FloorFeature{" +
                "floorType=" + floorType +
                ", floorStruct=" + floorStruct +
                ", floorColor='" + floorColor + '\'' +
                ", contexture='" + contexture + '\'' +
                ", compositionJson='" + compositionJson + '\'' +
                ", dimX=" + dimX +
                ", dimY=" + dimY +
                ", dimZ=" + dimZ +
                ", depth=" + depth +
                ", name='" + description + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}
