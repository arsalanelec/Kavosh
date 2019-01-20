package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.Project;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.ProjectDao;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


@Singleton  // informs Dagger that this class should be constructed once
public class ProjectListRepository {
    private final ProjectDao projectDao;
    private final Executor executor;
    private final String TAG = "ProjectListRepository";
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public ProjectListRepository(ProjectDao projectDao, Executor executor) {
        this.projectDao = projectDao;
        this.executor = executor;
    }

    public LiveData<List<Project>> getProjectList() {
        refreshProjectList();
        // return a LiveData directly from the database.
        return projectDao.loadAllList();
    }

    private void refreshProjectList() {
        Log.d("refreshProjectList", "!projectExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            Call<List<Project>> call = mRetrofit.create(ApiInterface.class).getProjectList(mToken.getAccessToken());
            try {
                Response<List<Project>> response = call.execute();
                if (response.isSuccessful()) {
                    Log.d("refreshProjectList", "run: response.isSuccessful cnt:" + response.body().size());
                    projectDao.deleteAll();
                    Log.d("refreshProjectList", "run: save:" + projectDao.saveList(response.body()).length);

                    for (Project p :
                            response.body()) {

                    }
                } else {
                    Log.d("refreshProjectList", "run: response.error");
                }
            } catch (Throwable t) {
                Log.d(TAG, "refreshProjectList: error:" + t.getLocalizedMessage());
            }
        });
    }

}
