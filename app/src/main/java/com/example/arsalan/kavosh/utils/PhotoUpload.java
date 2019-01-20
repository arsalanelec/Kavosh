package com.example.arsalan.kavosh.utils;

import android.util.Log;

import com.example.arsalan.kavosh.model.Photo;
import com.example.arsalan.kavosh.wokrmanager.PhotoUploadWorker;

import androidx.lifecycle.LifecycleOwner;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class PhotoUpload {
    private static final String TAG = "PhotoUpload";

    public static void uploadPhoto(Photo photo, LifecycleOwner owner) {

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString("id", photo.getId())
                .putString("title", photo.getTitle())
                .putString("image", photo.getLocalPath())
                .putString("photoable_type", photo.getPhotoableType())
                .putString("photoable_id", photo.getPhotoableId())

                .build();

        OneTimeWorkRequest uploadWorker = new OneTimeWorkRequest.Builder(PhotoUploadWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData).build();


        WorkManager.getInstance().enqueue(uploadWorker);

        WorkManager.getInstance().getWorkInfoByIdLiveData(uploadWorker.getId())
                .observe(owner, workStatus -> {
                    // Do something with the status
                    if (workStatus != null && workStatus.getState().isFinished()) {

                        Log.d(TAG, "uploadWorker: finished!");
                    } else if (workStatus != null) {
                        Log.d(TAG, "status:" + workStatus);
                    }
                });
    }
}
