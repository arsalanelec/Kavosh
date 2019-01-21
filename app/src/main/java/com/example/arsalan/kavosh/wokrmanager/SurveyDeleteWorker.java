package com.example.arsalan.kavosh.wokrmanager;

import android.content.Context;
import android.util.Log;

import com.example.arsalan.kavosh.application.MyApplication;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.RetroResponse;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SurveyDeleteWorker extends Worker implements Injectable {

    private static final String TAG = "SurveyDeleteWorker";
    @Inject
    Retrofit mRetrofit;
    @Inject
    Token mToken;


    public SurveyDeleteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        if (getApplicationContext() instanceof MyApplication) {
            ((MyApplication) getApplicationContext()).workerInjector().inject(this);
        }

        try {
            Call<RetroResponse> call = mRetrofit.create(ApiInterface.class).deleteSurveyItem(mToken.getAccessToken(), getInputData().getString(MyConst.EXTRA_ID));
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
