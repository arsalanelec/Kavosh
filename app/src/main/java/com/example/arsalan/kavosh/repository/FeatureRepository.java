package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.Feature;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.FeatureDao;
import com.example.arsalan.kavosh.room.UserDao;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


@Singleton  // informs Dagger that this class should be constructed once
public class FeatureRepository {
    private final FeatureDao featureDao;
    private final UserDao userDao;
    private final Executor executor;
    private final String TAG = getClass().getSimpleName();
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public FeatureRepository(FeatureDao featureDao, UserDao userDao, Executor executor) {
        this.featureDao = featureDao;
        this.userDao = userDao;
        this.executor = executor;
    }


  /*  public LiveData<List<Feature>> getFeatureList(String ItemId) {
        refreshList(ItemId);
        // return a LiveData directly from the database.
        return featureDao.loadAll();
    }*/

    public LiveData<Feature> getFeatureById(String ItemId) {
        refreshFeature(ItemId);
        // return a LiveData directly from the database.
        return featureDao.loadById(ItemId);
    }

    private void refreshFeature(String id) {
        Log.d(getClass().getSimpleName(), "ItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            if (id == null) return;
            Call<Feature> call = mRetrofit.create(ApiInterface.class).getFeature(mToken.getAccessToken(), id);
            try {
                Response<Feature> response = call.execute();
                if (response.isSuccessful()) {
                    Log.d(getClass().getCanonicalName(), "run: save:" + featureDao.save(response.body()));
                } else {
                    Log.d("FeatureRepository", "run: response.error");
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
            Call<List<Feature>> call = mRetrofit.create(ApiInterface.class).getFeatureList(mToken.getAccessToken(), id);
            try {
                Response<List<Feature>> response = call.execute();
                if (response.isSuccessful()) {

                    Log.d(getClass().getCanonicalName(), "run: response.isSuccessful cnt:" + response.body().size());
                    Log.d(getClass().getCanonicalName(), "run: save:" + featureDao.saveList(response.body()).length);

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
