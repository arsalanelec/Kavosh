package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.Supervisor;
import com.example.arsalan.kavosh.repository.SupervisorListRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SupervisorListViewModel extends ViewModel {
    private SupervisorListRepository supervisorListRepository;
    private LiveData<List<Supervisor>> supervisorListLd;

    @Inject //  parameter is provided by Dagger 2
    public SupervisorListViewModel(SupervisorListRepository supervisorListRepository) {
        this.supervisorListRepository = supervisorListRepository;

    }

    public void initial(String projectId) {
        supervisorListLd = supervisorListRepository.getSurveyListById(projectId);
    }

    public LiveData<List<Supervisor>> getSupervisors() {
        return supervisorListLd;
    }
}
