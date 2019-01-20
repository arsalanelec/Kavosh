package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.Layer;
import com.example.arsalan.kavosh.repository.LayerRepository;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class LayerViewModel extends ViewModel {
    private LayerRepository layerRepository;
    private LiveData<Layer> layerLiveData;

    @Inject //  parameter is provided by Dagger 2
    public LayerViewModel(LayerRepository layerRepository) {
        this.layerRepository = layerRepository;

    }

    public void initial(String itemId) {
        layerLiveData = layerRepository.getLayerById(itemId);
    }

    public LiveData<Layer> getLayer() {
        return layerLiveData;
    }
}
