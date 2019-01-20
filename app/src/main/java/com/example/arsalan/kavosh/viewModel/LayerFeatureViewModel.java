package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.LayerFeature;
import com.example.arsalan.kavosh.repository.LayerFeatureRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class LayerFeatureViewModel extends ViewModel {
    private LayerFeatureRepository layerFeatureRepository;
    private LiveData<List<LayerFeature>> mExcavationItems;

    @Inject //  parameter is provided by Dagger 2
    public LayerFeatureViewModel(LayerFeatureRepository layerFeatureRepository) {
        this.layerFeatureRepository = layerFeatureRepository;

    }

    public void initial(String itemId) {
        mExcavationItems = layerFeatureRepository.getLayerFeatureList(itemId);
    }

    public LiveData<List<LayerFeature>> getLayerFeatureList() {
        return mExcavationItems;
    }
}
