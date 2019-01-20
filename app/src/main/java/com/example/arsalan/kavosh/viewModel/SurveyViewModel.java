package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.Survey;
import com.example.arsalan.kavosh.repository.SurveyRepository;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SurveyViewModel extends ViewModel {
    private SurveyRepository surveyRepository;
    private LiveData<Survey> surveyLiveData;

    @Inject //  parameter is provided by Dagger 2
    public SurveyViewModel(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;

    }

    public void initial(String itemId) {
        surveyLiveData = surveyRepository.getSurveyById(itemId);
    }


    public LiveData<Survey> getSurvey() {
        return surveyLiveData;
    }
}
