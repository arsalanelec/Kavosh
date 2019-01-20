package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.Contexture;
import com.example.arsalan.kavosh.repository.ContextureRepository;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ContextureViewModel extends ViewModel {
    private ContextureRepository contextureRepository;
    private LiveData<Contexture> contextureLiveData;

    @Inject //  parameter is provided by Dagger 2
    public ContextureViewModel(ContextureRepository contextureRepository) {
        this.contextureRepository = contextureRepository;

    }

    public void initial(String layerId) {
        contextureLiveData = contextureRepository.getContextByLayerId(layerId);
    }

    public LiveData<Contexture> getContexture() {
        return contextureLiveData;
    }
}
