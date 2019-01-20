package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.ExcavationItem;
import com.example.arsalan.kavosh.repository.ExcavationItemListRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ExcavationItemListViewModel extends ViewModel {
    private ExcavationItemListRepository excavationItemRepo;
    private LiveData<List<ExcavationItem>> mExcavationItems;

    @Inject //  parameter is provided by Dagger 2
    public ExcavationItemListViewModel(ExcavationItemListRepository excavationItemRepo) {
        this.excavationItemRepo = excavationItemRepo;

    }

    public void initial(String projectId) {
        mExcavationItems = excavationItemRepo.getExcavationItemList(projectId);
    }

    public LiveData<List<ExcavationItem>> getExcavationItemList() {
        return mExcavationItems;
    }
}
