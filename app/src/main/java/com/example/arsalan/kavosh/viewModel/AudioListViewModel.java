package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.AudioMemo;
import com.example.arsalan.kavosh.repository.AudioListRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AudioListViewModel extends ViewModel {
    private AudioListRepository excavationItemRepo;
    private LiveData<List<AudioMemo>> mAudioMemos;

    @Inject //  parameter is provided by Dagger 2
    public AudioListViewModel(AudioListRepository excavationItemRepo) {
        this.excavationItemRepo = excavationItemRepo;

    }

    public void initial(String parentId) {
        mAudioMemos = excavationItemRepo.getAudioMemoList(parentId);
    }

    public LiveData<List<AudioMemo>> getAudioMemoList() {
        return mAudioMemos;
    }
}
