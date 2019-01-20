package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.ExcavationItemSupervisorHistory;
import com.example.arsalan.kavosh.repository.ExcavationItemSupervisorListRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ExcavationItemSupervisorViewModel extends ViewModel {
    private ExcavationItemSupervisorListRepository supervisorListRepository;
    private LiveData<List<ExcavationItemSupervisorHistory>> mExcavationItem;

    @Inject //  parameter is provided by Dagger 2
    public ExcavationItemSupervisorViewModel(ExcavationItemSupervisorListRepository supervisorListRepository) {
        this.supervisorListRepository = supervisorListRepository;

    }

    public void initial(String id) {
        mExcavationItem = supervisorListRepository.getExcavationItemSupervisorList(id);
    }

    public LiveData<List<ExcavationItemSupervisorHistory>> getExcavationItems() {
        return mExcavationItem;
    }
}
