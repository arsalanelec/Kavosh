package com.example.arsalan.kavosh.model;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AudioMemo {
    @NonNull
    @PrimaryKey
    private String id;

    private String url;
    private String title;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String audioable_id;
    private String audioable_type;
    private String localPath;

    public AudioMemo() {
        this.id = UUID.randomUUID().toString();
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAudioable_id() {
        return audioable_id;
    }

    public void setAudioable_id(String audioable_id) {
        this.audioable_id = audioable_id;
    }

    public String getAudioable_type() {
        return audioable_type;
    }

    public void setAudioable_type(String audioable_type) {
        this.audioable_type = audioable_type;
    }
}
