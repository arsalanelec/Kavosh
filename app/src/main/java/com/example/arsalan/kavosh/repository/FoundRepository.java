package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.Found;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.FoundDao;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


@Singleton  // informs Dagger that this class should be constructed once
public class FoundRepository {
    private final FoundDao foundDao;
    private final Executor executor;
    private final String TAG = "ExcavationItemListRepository";
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public FoundRepository(FoundDao foundDao, Executor executor) {
        this.foundDao = foundDao;
        this.executor = executor;
    }

    public LiveData<List<Found>> getList(String excavationItemId, String layerFeatureId, int type) {
        refreshList(excavationItemId);
        // return a LiveData directly from the database.
        return foundDao.loadList(excavationItemId, layerFeatureId, type);
    }


    private void refreshList(String id) {
        Log.d("refreshExcavationItems", "!excavationItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            Call<List<Found>> call = mRetrofit.create(ApiInterface.class).getFounds(mToken.getAccessToken(), id);
            try {
                Response<List<Found>> response = call.execute();
                if (response.isSuccessful()) {
                    foundDao.deleteWithExcavationItemId(id);
                    Log.d(getClass().getCanonicalName(), "run: save list:" + foundDao.saveList(response.body()));

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
