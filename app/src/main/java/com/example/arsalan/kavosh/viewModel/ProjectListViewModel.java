package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.Project;
import com.example.arsalan.kavosh.repository.ProjectListRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ProjectListViewModel extends ViewModel {
    private ProjectListRepository projectRepo;

    @Inject //  parameter is provided by Dagger 2
    public ProjectListViewModel(ProjectListRepository projectRepo) {
        this.projectRepo = projectRepo;

    }

    public LiveData<List<Project>> getProjectList() {
        return projectRepo.getProjectList();
    }
}
