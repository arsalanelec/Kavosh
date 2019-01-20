package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.SurFound;
import com.example.arsalan.kavosh.repository.SurFoundRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SurFoundViewModel extends ViewModel {
    private SurFoundRepository surFoundRepository;
    private LiveData<List<SurFound>> surFoundLiveData;

    @Inject //  parameter is provided by Dagger 2
    public SurFoundViewModel(SurFoundRepository surFoundRepository) {
        this.surFoundRepository = surFoundRepository;

    }

    public void initial(String surveyId) {
        surFoundLiveData = surFoundRepository.getAllBySurveyId(surveyId);
    }

    public LiveData<List<SurFound>> getSurFounds() {
        return surFoundLiveData;
    }
}
