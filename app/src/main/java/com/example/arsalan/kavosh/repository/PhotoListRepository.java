package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.Photo;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.PhotoDao;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


@Singleton  // informs Dagger that this class should be constructed once
public class PhotoListRepository {
    private final PhotoDao photoDao;
    private final Executor executor;
    private final String TAG = "PhotoListRepository";
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public PhotoListRepository(PhotoDao photoDao, Executor executor) {
        this.photoDao = photoDao;
        this.executor = executor;
    }

    public LiveData<List<Photo>> getPhotoList(String parentId) {
        refreshPhotoList(parentId);
        // return a LiveData directly from the database.
        return photoDao.loadAllByParentId(parentId);
    }

    public LiveData<Photo> getPhoto(String itemId) {
        // return a LiveData directly from the database.
        return photoDao.loadById(itemId);
    }

    private void refreshPhotoList(String parentId) {
        Log.d("refreshPhotos", "!photoExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            Call<List<Photo>> call = mRetrofit.create(ApiInterface.class).getPhotoList(mToken.getAccessToken(), parentId);
            try {
                Response<List<Photo>> response = call.execute();
                if (response.isSuccessful()) {

                    Log.d("refreshPhotos", "run: response.isSuccessful cnt:" + response.body().size());
                    Log.d("refreshPhotos", "run: save:" + photoDao.saveList(response.body()).length);

                } else {
                    Log.d("refreshPhotos", "run: response.error");
                }
            } catch (Throwable throwable) {
                Log.d(TAG, "refreshPhotoList: error throws: " + throwable.getLocalizedMessage());
            }
        });
    }

}
