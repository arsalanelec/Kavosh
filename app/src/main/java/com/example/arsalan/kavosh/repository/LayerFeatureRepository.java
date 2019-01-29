package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.LayerFeature;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.LayerFeatureDao;
import com.example.arsalan.kavosh.room.UserDao;

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
public class LayerFeatureRepository {
    private final LayerFeatureDao layerFeatureDao;
    private final UserDao userDao;
    private final Executor executor;
    private static final String TAG = "LayerFeatureRepository";
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public LayerFeatureRepository(LayerFeatureDao layerFeatureDao, UserDao userDao, Executor executor) {
        this.layerFeatureDao = layerFeatureDao;
        this.userDao = userDao;
        this.executor = executor;
    }

    public LiveData<List<LayerFeature>> getLayerFeatureList(String ItemId) {
        refreshList(ItemId);
        // return a LiveData directly from the database.
        return layerFeatureDao.loadAllListByItemId(ItemId);
    }


    private void refreshList(String id) {
        Log.d("LayerFeatureRepository", "!ItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            Call<List<LayerFeature>> call = mRetrofit.create(ApiInterface.class).getLayerFeatureList(mToken.getAccessToken(), id);
            try {
                Response<List<LayerFeature>> response = call.execute();
                if (response.isSuccessful()) {

                    Log.d(getClass().getCanonicalName(), "run: response.isSuccessful cnt:" + response.body().size());
                    layerFeatureDao.deleteByExcavationId(id);
                    Log.d(getClass().getCanonicalName(), "run: save:" + layerFeatureDao.saveList(response.body()).length);

                } else {
                    Log.d("LayerFeatureRepository", "run: response.error");
                }
            } catch (Throwable t){
                Log.d(TAG, "refreshList: error:"+t.getLocalizedMessage());
            }
        });
    }

}
