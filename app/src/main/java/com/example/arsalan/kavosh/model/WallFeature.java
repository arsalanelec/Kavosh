package com.example.arsalan.kavosh.model;

import com.google.gson.annotations.Expose;

public class WallFeature {
    private int wallType;
    private int wallStruct;
    private String wallColor;
    private int contexture;
    private String compositionJson;
    private String mortarName;
    private String mortarColor;
    private double mortarThickness;
    private String coatingName;
    private String coatingColor;
    private double coatingThickness;
    private String tools;
    private double dimX;
    private double dimY;
    private double dimZ;
    private double depth;
    private String bricksJson;
    private String description;
    @Expose(serialize = false)
    private String createdAt;
    @Expose(serialize = false)
    private String updatedAt;
    @Expose(serialize = false)
    private String deletedAt;

    public WallFeature() {
    }

    public int getWallType() {
        return wallType;
    }

    public void setWallType(int wallType) {
        this.wallType = wallType;
    }

    public int getWallStruct() {
        return wallStruct;
    }

    public void setWallStruct(int wallStruct) {
        this.wallStruct = wallStruct;
    }

    public String getWallColor() {
        return wallColor;
    }

    public void setWallColor(String wallColor) {
        this.wallColor = wallColor;
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

    public String getMortarName() {
        return mortarName;
    }

    public void setMortarName(String mortarName) {
        this.mortarName = mortarName;
    }

    public String getMortarColor() {
        return mortarColor;
    }

    public void setMortarColor(String mortarColor) {
        this.mortarColor = mortarColor;
    }

    public double getMortarThickness() {
        return mortarThickness;
    }

    public void setMortarThickness(double mortarThickness) {
        this.mortarThickness = mortarThickness;
    }

    public String getCoatingName() {
        return coatingName;
    }

    public void setCoatingName(String coatingName) {
        this.coatingName = coatingName;
    }

    public String getCoatingColor() {
        return coatingColor;
    }

    public void setCoatingColor(String coatingColor) {
        this.coatingColor = coatingColor;
    }

    public double getCoatingThickness() {
        return coatingThickness;
    }

    public void setCoatingThickness(double coatingThickness) {
        this.coatingThickness = coatingThickness;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
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

    public String getBricksJson() {
        return bricksJson;
    }

    public void setBricksJson(String bricksJson) {
        this.bricksJson = bricksJson;
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
        return "WallFeature{" +
                "wallType=" + wallType +
                ", wallStruct=" + wallStruct +
                ", wallColor='" + wallColor + '\'' +
                ", contexture='" + contexture + '\'' +
                ", compositionJson='" + compositionJson + '\'' +
                ", mortarName='" + mortarName + '\'' +
                ", mortarColor='" + mortarColor + '\'' +
                ", mortarThickness=" + mortarThickness +
                ", coatingName='" + coatingName + '\'' +
                ", coatingColor='" + coatingColor + '\'' +
                ", coatingThickness=" + coatingThickness +
                ", tools='" + tools + '\'' +
                ", dimX=" + dimX +
                ", dimY=" + dimY +
                ", dimZ=" + dimZ +
                ", depth=" + depth +
                ", bricksJson='" + bricksJson + '\'' +
                ", name='" + description + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}
