package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.Feature;
import com.example.arsalan.kavosh.repository.FeatureRepository;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class FeatureViewModel extends ViewModel {
    private FeatureRepository featureRepository;
    private LiveData<Feature> featureLiveData;

    @Inject //  parameter is provided by Dagger 2
    public FeatureViewModel(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;

    }

    public void initial(String itemId) {
        featureLiveData = featureRepository.getFeatureById(itemId);
    }

    public LiveData<Feature> getFeature() {
        return featureLiveData;
    }
}
