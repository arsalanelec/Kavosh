package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.SurveyProject;
import com.example.arsalan.kavosh.repository.SurveyProjectRepository;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SurveyProjectViewModel extends ViewModel {
    private SurveyProjectRepository SurveyProjectRepository;
    private LiveData<SurveyProject> surveyListLiveData;

    @Inject //  parameter is provided by Dagger 2
    public SurveyProjectViewModel(SurveyProjectRepository SurveyProjectRepository) {
        this.SurveyProjectRepository = SurveyProjectRepository;

    }

    public void initial(String projectId) {
        surveyListLiveData = SurveyProjectRepository.getSurveyProjectById(projectId);
    }

    public LiveData<SurveyProject> getSurveyProject() {
        return surveyListLiveData;
    }
}
