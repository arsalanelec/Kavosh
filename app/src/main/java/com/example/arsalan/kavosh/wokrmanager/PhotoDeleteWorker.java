package com.example.arsalan.kavosh.wokrmanager;

import android.content.Context;
import android.util.Log;

import com.example.arsalan.kavosh.application.MyApplication;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.RetroResponse;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoDeleteWorker extends Worker implements Injectable {

    private final String TAG = getClass().getSimpleName();
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;
    private MultipartBody.Part thumbBody;

    public PhotoDeleteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        if (getApplicationContext() instanceof MyApplication) {
            ((MyApplication) getApplicationContext()).workerInjector().inject(this);
        }

        Log.d(TAG, "start Call");

        Call<RetroResponse> call = mRetrofit.create(ApiInterface.class).deletePhoto(mToken.getAccessToken(), getInputData().getString(MyConst.EXTRA_ID));
        try {
            Response<RetroResponse> response = call.execute();
            if (response.isSuccessful()) {
                Log.d(TAG, "run: response.isSuccessful:" + response.body().getStatus());
                return Result.success();
            } else {
                Log.d(TAG, "run: response.error:" + response.errorBody().string());
                return Result.failure();
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return Result.retry();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

}
