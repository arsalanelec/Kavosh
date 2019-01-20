package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.SurveyProject;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.SurveyProjectDao;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


@Singleton  // informs Dagger that this class should be constructed once
public class SurveyProjectRepository {
    private static final String TAG = "SurveyProjectRepository";
    private final SurveyProjectDao surveyProjectDao;
    private final Executor executor;
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public SurveyProjectRepository(SurveyProjectDao projectDao, Executor executor) {
        this.surveyProjectDao = projectDao;
        this.executor = executor;
    }

    public LiveData<SurveyProject> getSurveyProjectById(String id) {
        refreshSurvey(id);
        // return a LiveData directly from the database.
        return surveyProjectDao.loadById(id);
    }


    private void refreshSurvey(String id) {
        Log.d(getClass().getSimpleName(), "ItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            if (id == null) return;
            try {
                Call<SurveyProject> call = mRetrofit.create(ApiInterface.class).getSurveyProject(mToken.getAccessToken(), id);
                Response<SurveyProject> response = call.execute();
                if (response.isSuccessful()) {
                    Log.d(getClass().getCanonicalName(), "run: save:" + surveyProjectDao.save(response.body()));
                } else {
                    Log.d(TAG, "run: response.error");
                }
            } catch (Throwable t) {
                Log.d(TAG, "refreshSurvey: error:" + t.getLocalizedMessage());
            }
        });
    }


}
