package com.example.arsalan.kavosh.model;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
        "id": "ab63a1ef-c4ad-4efd-a0c8-6ab9404d5061",
        "name": "1002",
        "status": 1,
        "excavation_item_id": "f9434a51-4c70-43b9-8495-6242f2e6fd72",
        "created_at": "2018-09-11 03:35:29",
        "updated_at": "2018-09-11 03:35:29",
        "deleted_at": null,
        "type": 1*/
@Entity
public class LayerFeature {
    @NonNull
    @PrimaryKey
    private String id;
    private String excavationItemId;
    private String name;
    private int status;
    private int type;
    private String childrenId;
    private String childrenType;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public LayerFeature() {
        this.id = UUID.randomUUID().toString();

    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getExcavationItemId() {
        return excavationItemId;
    }

    public void setExcavationItemId(String excavationItemId) {
        this.excavationItemId = excavationItemId;
    }

    public String getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(String childrenId) {
        this.childrenId = childrenId;
    }

    public String getChildrenType() {
        return childrenType;
    }

    public void setChildrenType(String childrenType) {
        this.childrenType = childrenType;
    }

    public String getName() {
        if(name==null || name.isEmpty()) return "نامشخص";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        return "LayerFeature{" +
                "id='" + id + '\'' +
                ", excavationItemId='" + excavationItemId + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
