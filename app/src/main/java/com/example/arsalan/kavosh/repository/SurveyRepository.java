package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.Survey;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.SurveyDao;

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
public class SurveyRepository {
    private static final String TAG = "SurveyRepository";
    private final SurveyDao surveyDao;
    private final Executor executor;
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public SurveyRepository(SurveyDao surveyDao, Executor executor) {
        this.surveyDao = surveyDao;
        this.executor = executor;
    }

    public LiveData<Survey> getSurveyById(String ItemId) {
        refreshSurvey(ItemId);
        // return a LiveData directly from the database.
        return surveyDao.loadById(ItemId);
    }

    public LiveData<List<Survey>> getSurveyListById(String projectId) {
        refreshSurveyList(projectId);
        // return a LiveData directly from the database.
        return surveyDao.loadListByProjectId(projectId);
    }

    private void refreshSurvey(String id) {
        Log.d(getClass().getSimpleName(), "ItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            if (id == null) return;
            try {
                Call<Survey> call = mRetrofit.create(ApiInterface.class).getSurvey(mToken.getAccessToken(), id);
                Response<Survey> response = call.execute();
                if (response.isSuccessful()) {
                    Log.d(getClass().getCanonicalName(), "run: save:" + surveyDao.save(response.body()));
                } else {
                    Log.d("SurveyRepository", "run: response.error:" + response.errorBody().string());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });
    }

    private void refreshSurveyList(String id) {
        Log.d(getClass().getSimpleName(), "ItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            if (id == null) return;
            try {

                Call<List<Survey>> call = mRetrofit.create(ApiInterface.class).getSurveyProjectItems(mToken.getAccessToken(), id);
                Response<List<Survey>> response = call.execute();
                if (response.isSuccessful()) {
                    surveyDao.deleteByProjectId(id);
                    Log.d(getClass().getCanonicalName(), "run: saveList:" + surveyDao.saveList(response.body()));
                } else {
                    Log.d("SurveyRepository", "run: response.error");
                }
            } catch (Throwable t) {
                Log.d(TAG, "refreshSurveyList: error:" + t.getLocalizedMessage());
            }
        });
    }


}
