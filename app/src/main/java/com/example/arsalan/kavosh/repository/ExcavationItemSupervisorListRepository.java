package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.ExcavationItemSupervisorHistory;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.ExcavationItemSupervisorDao;

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
public class ExcavationItemSupervisorListRepository {
    private final ExcavationItemSupervisorDao supervisorDao;
    private final Executor executor;
    private final String TAG = "ExcavationItemListRepository";
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public ExcavationItemSupervisorListRepository(ExcavationItemSupervisorDao supervisorDao, Executor executor) {
        this.supervisorDao = supervisorDao;
        this.executor = executor;
    }

    public LiveData<List<ExcavationItemSupervisorHistory>> getExcavationItemSupervisorList(String itemId) {
        refreshExcavationItemSupervisorList(itemId);
        // return a LiveData directly from the database.
        return supervisorDao.loadAllListByItemId(itemId);
    }

    private void refreshExcavationItemSupervisorList(String itemId) {
        Log.d("refreshExcavationItems", "!excavationItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            Call<List<ExcavationItemSupervisorHistory>> call = mRetrofit.create(ApiInterface.class).getExcavationItemSupervisorList(mToken.getAccessToken(), itemId);
            try {
                Response<List<ExcavationItemSupervisorHistory>> response = call.execute();
                if (response.isSuccessful()) {

                    Log.d("refreshExcavationItems", "run: response.isSuccessful cnt:" + response.body().size());
                    Log.d("refreshExcavationItems", "run: save:" + supervisorDao.saveList(response.body()).length);

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
