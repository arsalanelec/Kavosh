package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.Found;
import com.example.arsalan.kavosh.repository.FoundRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class FoundViewModel extends ViewModel {
    private FoundRepository foundRepository;
    private LiveData<List<Found>> foundLiveData;

    @Inject //  parameter is provided by Dagger 2
    public FoundViewModel(FoundRepository foundRepository) {
        this.foundRepository = foundRepository;

    }

    public void initial(String excavationItemId,String layerFeatureId, int type) {
        foundLiveData = foundRepository.getList(excavationItemId,layerFeatureId, type);
    }

    public LiveData<List<Found>> getFounds() {
        return foundLiveData;
    }
}
