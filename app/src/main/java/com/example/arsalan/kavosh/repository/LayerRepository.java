package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.Layer;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.LayerDao;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


@Singleton  // informs Dagger that this class should be constructed once
public class LayerRepository {
    private final LayerDao layerDao;
    private final Executor executor;
    private final String TAG = "ExcavationItemListRepository";
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public LayerRepository(LayerDao layerDao, Executor executor) {
        this.layerDao = layerDao;
        this.executor = executor;
    }


  /*  public LiveData<List<Layer>> getLayerList(String ItemId) {
        refreshList(ItemId);
        // return a LiveData directly from the database.
        return layerDao.loadAll();
    }*/

    public LiveData<Layer> getLayerById(String ItemId) {
        refreshLayer(ItemId);
        // return a LiveData directly from the database.
        return layerDao.loadById(ItemId);
    }

    private void refreshLayer(String id) {
        Log.d(getClass().getSimpleName(), "ItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            if (id == null) return;
            Call<Layer> call = mRetrofit.create(ApiInterface.class).getLayer(mToken.getAccessToken(), id);
            try {
                Response<Layer> response = call.execute();
                if (response.isSuccessful()) {
                    Log.d(getClass().getCanonicalName(), "run: save:" + layerDao.save(response.body()));
                } else {
                    Log.d("LayerRepository", "run: response.error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });
    }

   /* private void refreshList(String id) {
        Log.d("refreshExcavationItems", "!excavationItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            Call<List<Layer>> call = mRetrofit.create(ApiInterface.class).getLayerList(mToken.getAccessToken(), id);
            try {
                Response<List<Layer>> response = call.execute();
                if (response.isSuccessful()) {

                    Log.d(getClass().getCanonicalName(), "run: response.isSuccessful cnt:" + response.body().size());
                    Log.d(getClass().getCanonicalName(), "run: save:" + layerDao.saveList(response.body()).length);

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
*/
}
