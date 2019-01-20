package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.Contexture;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.ContextureDao;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


@Singleton  // informs Dagger that this class should be constructed once
public class ContextureRepository {
    private final ContextureDao contextureDao;
    private final Executor executor;
    private final String TAG = "ExcavationItemListRepository";
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public ContextureRepository(ContextureDao contextureDao, Executor executor) {
        this.contextureDao = contextureDao;
        this.executor = executor;
    }

    public LiveData<Contexture> getContextByLayerId(String layerId) {
        refreshList(layerId);
        // return a LiveData directly from the database.
        return contextureDao.loadAllListByLayerId(layerId);
    }


    private void refreshList(String id) {
        Log.d("refreshExcavationItems", "!excavationItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            Call<Contexture> call = mRetrofit.create(ApiInterface.class).getContexture(mToken.getAccessToken(), id);
            try {
                Response<Contexture> response = call.execute();
                if (response.isSuccessful()) {

                    Log.d(getClass().getCanonicalName(), "run: save:" + contextureDao.save(response.body()));

                } else {
                    Log.d("refreshExcavationItems", "run: response.error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });
    }

}
