package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.SurFound;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.SurFoundDao;

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
public class SurFoundRepository {
    private final static String TAG = "SurSurFoundRepository";
    private final SurFoundDao surFoundDao;
    private final Executor executor;
    @Inject
    Retrofit mRetrofit;
    @Inject
    Token mToken;

    @Inject
    public SurFoundRepository(SurFoundDao surFoundDao, Executor executor) {
        this.surFoundDao = surFoundDao;
        this.executor = executor;
    }

    public LiveData<List<SurFound>> getAllBySurveyId(String id) {
        refreshList(id);
        // return a LiveData directly from the database.
        return surFoundDao.loadBySurveyId(id);
    }


    private void refreshList(String id) {
        Log.d("refreshExcavationItems", "!excavationItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            Call<List<SurFound>> call = mRetrofit.create(ApiInterface.class).getSurFounds(mToken.getAccessToken(), id);
            try {
                Response<List<SurFound>> response = call.execute();
                if (response.isSuccessful()) {

                    Log.d(getClass().getCanonicalName(), "run: save list:" + surFoundDao.saveList(response.body()));

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
