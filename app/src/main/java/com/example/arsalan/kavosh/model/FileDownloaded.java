package com.example.arsalan.kavosh.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class FileDownloaded extends BaseObservable {
    boolean finished;
    int progress;
    String localPath;

    public FileDownloaded() {
    }

    @Bindable
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
        notifyChange();

    }

    @Bindable
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        notifyChange();
    }

    @Bindable
    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
