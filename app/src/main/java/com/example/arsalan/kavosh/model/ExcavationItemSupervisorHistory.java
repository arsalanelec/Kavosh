package com.example.arsalan.kavosh.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ExcavationItemSupervisorHistory {
    /* "id": "2b9b0654-5381-4ccc-a9fa-07456cfc5ed3",
        "user_id": "fbb59d1b-2fd8-4317-9ae8-9d79d08545ff",
        "excavation_item_id": "f9434a51-4c70-43b9-8495-6242f2e6fd72",
        "enter_at": null,
        "exit_at": null,
        "created_at": "2018-09-09 19:17:02",
        "updated_at": "2018-09-09 19:17:02",
        "status": 0,
        "user_name": "ارسلان صالحی"*/
    @PrimaryKey
    @NonNull
    private String id;
    private String userId;
    private String excavationItemId;
    private String enterAt;
    private String exitAt;
    private String createdAt;
    private String updatedAt;
    private int status;
    private String userName;

    public ExcavationItemSupervisorHistory() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExcavationItemId() {
        return excavationItemId;
    }

    public void setExcavationItemId(String excavationItemId) {
        this.excavationItemId = excavationItemId;
    }

    public String getEnterAt() {
        return enterAt;
    }

    public void setEnterAt(String enterAt) {
        this.enterAt = enterAt;
    }

    public String getExitAt() {
        return exitAt;
    }

    public void setExitAt(String exitAt) {
        this.exitAt = exitAt;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
