package com.example.arsalan.kavosh.utils;

import android.util.Log;

import com.example.arsalan.kavosh.model.AudioMemo;
import com.example.arsalan.kavosh.wokrmanager.AudioUploadWorker;

import androidx.lifecycle.LifecycleOwner;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class AudioUpload {
    private static final String TAG = "AudioUpload";

    public static void uploadAudio(AudioMemo audioMemo, LifecycleOwner owner) {
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString("id", audioMemo.getId())
                .putString("title", audioMemo.getTitle())
                .putString("audio", audioMemo.getLocalPath())
                .putString("audioable_type", audioMemo.getAudioable_type())
                .putString("audioable_id", audioMemo.getAudioable_id())
                .build();

        OneTimeWorkRequest uploadWorker = new OneTimeWorkRequest.Builder(AudioUploadWorker.class)
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
