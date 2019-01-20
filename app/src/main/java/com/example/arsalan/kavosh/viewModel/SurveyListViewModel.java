package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.Survey;
import com.example.arsalan.kavosh.repository.SurveyRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SurveyListViewModel extends ViewModel {
    private SurveyRepository surveyRepository;
    private LiveData<List<Survey>> surveyListLiveData;

    @Inject //  parameter is provided by Dagger 2
    public SurveyListViewModel(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;

    }

    public void initial(String projectId) {
        surveyListLiveData = surveyRepository.getSurveyListById(projectId);
    }

    public LiveData<List<Survey>> getSurveyList() {
        return surveyListLiveData;
    }
}
