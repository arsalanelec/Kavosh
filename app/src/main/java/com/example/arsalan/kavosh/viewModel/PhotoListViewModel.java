package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.Photo;
import com.example.arsalan.kavosh.repository.PhotoListRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class PhotoListViewModel extends ViewModel {
    private PhotoListRepository excavationItemRepo;
    private LiveData<List<Photo>> mPhotos;

    @Inject //  parameter is provided by Dagger 2
    public PhotoListViewModel(PhotoListRepository excavationItemRepo) {
        this.excavationItemRepo = excavationItemRepo;

    }

    public void initial(String projectId) {
        mPhotos = excavationItemRepo.getPhotoList(projectId);
    }

    public LiveData<List<Photo>> getPhotoList() {
        return mPhotos;
    }
}
