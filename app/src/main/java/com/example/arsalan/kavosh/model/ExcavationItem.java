package com.example.arsalan.kavosh.model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class ExcavationItem {

    @NonNull
    @PrimaryKey
    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private int type;

    @SerializedName("name")
    private String name;

    @SerializedName("sub_trail_trench_no")
    private String subTrailTrenchNo;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("sub_trail_trench_name")
    private String subTrailTrenchName;

    @SerializedName("sub_trail_trench_type")
    private int subTrailTrenchType;

    @SerializedName("dim_lenght")
    private double dimLenght;

    @SerializedName("lenght_direction")
    private int lenghtDirection;

    @SerializedName("dim_width")
    private int dimWidth;

    @SerializedName("width_direction")
    private double widthDirection;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("altitude")
    private double altitude;

    @SerializedName("benchmark_hi_seas")
    private double benchmarkHiSeas;

    @SerializedName("dim_x")
    private double dimX;

    @SerializedName("dim_y")
    private double dimY;

    @SerializedName("dim_z")
    private double dimZ;

    @SerializedName("date")
    private String date;

    @SerializedName("deleted_at")
    private String deletedAt;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("excavation_project_id")
    private String excavationProjectId;

    @SerializedName("supervisor_id")
    private String supervisorId;

    @SerializedName("supervisor_name")
    private String supervisorName;

    private String layerFeatureCoding;

    private String registrationCoding;

    private int status;

    public ExcavationItem() {
        this.id = UUID.randomUUID().toString();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLayerFeatureCoding() {
        return layerFeatureCoding;
    }

    public void setLayerFeatureCoding(String layerFeatureCoding) {
        this.layerFeatureCoding = layerFeatureCoding;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getRegistrationCoding() {
        return registrationCoding;
    }

    public void setRegistrationCoding(String registrationCoding) {
        this.registrationCoding = registrationCoding;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTrailTrenchNo() {
        return subTrailTrenchNo;
    }

    public void setSubTrailTrenchNo(String subTrailTrenchNo) {
        this.subTrailTrenchNo = subTrailTrenchNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubTrailTrenchName() {
        return subTrailTrenchName;
    }

    public void setSubTrailTrenchName(String subTrailTrenchName) {
        this.subTrailTrenchName = subTrailTrenchName;
    }

    public int getSubTrailTrenchType() {
        return subTrailTrenchType;
    }

    public void setSubTrailTrenchType(int subTrailTrenchType) {
        this.subTrailTrenchType = subTrailTrenchType;
    }

    public double getDimLenght() {
        return dimLenght;
    }

    public void setDimLenght(double dimLenght) {
        this.dimLenght = dimLenght;
    }

    public int getLenghtDirection() {
        return lenghtDirection;
    }

    public void setLenghtDirection(int lenghtDirection) {
        this.lenghtDirection = lenghtDirection;
    }

    public int getDimWidth() {
        return dimWidth;
    }

    public void setDimWidth(int dimWidth) {
        this.dimWidth = dimWidth;
    }

    public double getWidthDirection() {
        return widthDirection;
    }

    public void setWidthDirection(double widthDirection) {
        this.widthDirection = widthDirection;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getBenchmarkHiSeas() {
        return benchmarkHiSeas;
    }

    public void setBenchmarkHiSeas(double benchmarkHiSeas) {
        this.benchmarkHiSeas = benchmarkHiSeas;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
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

    public String getExcavationProjectId() {
        return excavationProjectId;
    }

    public void setExcavationProjectId(String excavationProjectId) {
        this.excavationProjectId = excavationProjectId;
    }
}