package com.example.arsalan.kavosh.repository;

import android.util.Log;

import com.example.arsalan.kavosh.model.AudioMemo;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.AudioDao;

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
public class AudioListRepository {
    private final AudioDao audioMemoDao;
    private final Executor executor;
    private final String TAG = "AudioMemoListRepository";
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public AudioListRepository(AudioDao audioMemoDao, Executor executor) {
        this.audioMemoDao = audioMemoDao;
        this.executor = executor;
    }

    public LiveData<List<AudioMemo>> getAudioMemoList(String parentId) {
        refreshAudioMemoList(parentId);
        // return a LiveData directly from the database.
        return audioMemoDao.loadAllByParentId(parentId);
    }

    public LiveData<AudioMemo> getAudioMemo(String itemId) {
        // return a LiveData directly from the database.
        return audioMemoDao.loadById(itemId);
    }

    private void refreshAudioMemoList(String parentId) {
        Log.d("refreshAudioMemos", "!audioMemoExist , token:" + mToken.getAccessToken());
        executor.execute(() -> {
            Call<List<AudioMemo>> call = mRetrofit.create(ApiInterface.class).getAudioList(mToken.getAccessToken(), parentId);
            try {
                Response<List<AudioMemo>> response = call.execute();
                if (response.isSuccessful()) {

                    Log.d("refreshAudioMemos", "run: response.isSuccessful cnt:" + response.body().size());
                    Log.d("refreshAudioMemos", "run: save:" + audioMemoDao.saveList(response.body()).length);

                } else {
                    Log.d("refreshAudioMemos", "run: response.error:" + response.errorBody().string());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });
    }

}
