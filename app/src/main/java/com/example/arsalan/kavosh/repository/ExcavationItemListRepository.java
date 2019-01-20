package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.ExcavationItem;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.ExcavationItemDao;
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
public class ExcavationItemListRepository {
    private final ExcavationItemDao excavationItemDao;
    private final Executor executor;
    private final String TAG = "ExcavationItemListRepository";
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public ExcavationItemListRepository(ExcavationItemDao excavationItemDao, UserDao userDao, Executor executor) {
        this.excavationItemDao = excavationItemDao;
        this.executor = executor;
    }

    public LiveData<List<ExcavationItem>> getExcavationItemList(String projectId) {
        refreshExcavationItemList(projectId);
        // return a LiveData directly from the database.
        return excavationItemDao.loadAllListByProjectId(projectId);
    }

    public LiveData<ExcavationItem> getExcavationItem(String itemId) {
        // return a LiveData directly from the database.
        return excavationItemDao.loadById(itemId);
    }

    private void refreshExcavationItemList(String projectId) {
        Log.d("refreshExcavationItems", "!excavationItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            Call<List<ExcavationItem>> call = mRetrofit.create(ApiInterface.class).getExcavationItemList(mToken.getAccessToken(), projectId);
            try {
                Response<List<ExcavationItem>> response = call.execute();
                if (response.isSuccessful()) {

                    Log.d("refreshExcavationItems", "run: response.isSuccessful cnt:" + response.body().size());
                    excavationItemDao.deleteAll(projectId);
                    Log.d("refreshExcavationItems", "run: save:" + excavationItemDao.saveList(response.body()).length);

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
