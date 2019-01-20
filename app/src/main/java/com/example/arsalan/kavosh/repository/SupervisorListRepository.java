package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.Supervisor;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.SupervisorDao;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


@Singleton  // informs Dagger that this class should be constructed once
public class SupervisorListRepository {

    private static final String TAG = "SupervisorListRepositor";
    private final SupervisorDao supervisorDao;
    private final Executor executor;
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public SupervisorListRepository(SupervisorDao supervisorDao, Executor executor) {
        this.supervisorDao = supervisorDao;
        this.executor = executor;
    }

    public LiveData<List<Supervisor>> getSurveyListById(String projectId) {
        refreshSurveyList(projectId);
        // return a LiveData directly from the database.
        return supervisorDao.loadAllListByProjectId(projectId);
    }


    private void refreshSurveyList(String id) {
        Log.d(getClass().getSimpleName(), "ItemExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            if (id == null) return;
            try {

                Call<List<Supervisor>> call = mRetrofit.create(ApiInterface.class).getProjectSupervisors(mToken.getAccessToken(), id);
                Response<List<Supervisor>> response = call.execute();
                if (response.isSuccessful()) {
                    supervisorDao.deleteByProjectId(id);
                    Log.d(getClass().getCanonicalName(), "run: saveList:" + supervisorDao.saveList(response.body()));

                } else {
                    Log.d(TAG, "run: response.error");
                }
            } catch (Throwable t) {
                Log.d(TAG, "refreshSurveyList: error:" + t.getLocalizedMessage());
            }
        });
    }


}
