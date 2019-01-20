package com.example.arsalan.kavosh.wokrmanager;

import android.content.Context;
import android.util.Log;

import com.example.arsalan.kavosh.application.MyApplication;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.model.RetroResponse;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SurveyUploadWorker extends Worker implements Injectable {

    private final String TAG = "ExcavationUploadWorker";
    @Inject
    Retrofit mRetrofit;
    @Inject
    Token mToken;


    public SurveyUploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        if (getApplicationContext() instanceof MyApplication) {
            ((MyApplication) getApplicationContext()).workerInjector().inject(this);
        }

        Map<String, RequestBody> inputMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : getInputData().getKeyValueMap().entrySet()) {
            if (entry.getValue() instanceof String) {
                inputMap.put(entry.getKey(), RequestBody.create(MediaType.parse("text/plain"), (String) entry.getValue()));
            }
        }

        try {
            Call<RetroResponse> call = mRetrofit.create(ApiInterface.class).updateOrCreateSurvey(mToken.getAccessToken(), inputMap);
            Response<RetroResponse> response = call.execute();
            if (response.isSuccessful()) {
                Log.d(TAG, "run: response.isSuccessful:");
                return Result.success();
            } else {
                Log.d(TAG, "run: response.error:" + response.errorBody().string());
                return Result.failure();
            }
        } catch (Throwable t) {
            Log.d(TAG, "doWork: error:" + t.getLocalizedMessage());
            return Result.failure();

        }
    }

}
