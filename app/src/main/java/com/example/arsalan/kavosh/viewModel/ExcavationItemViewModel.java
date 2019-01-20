package com.example.arsalan.kavosh.viewModel;

import com.example.arsalan.kavosh.model.ExcavationItem;
import com.example.arsalan.kavosh.repository.ExcavationItemListRepository;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ExcavationItemViewModel extends ViewModel {
    private ExcavationItemListRepository excavationItemRepo;
    private LiveData<ExcavationItem> mExcavationItem;

    @Inject //  parameter is provided by Dagger 2
    public ExcavationItemViewModel(ExcavationItemListRepository excavationItemRepo) {
        this.excavationItemRepo = excavationItemRepo;

    }

    public void initial(String id) {
        mExcavationItem = excavationItemRepo.getExcavationItem(id);
    }

    public LiveData<ExcavationItem> getExcavationItem() {
        return mExcavationItem;
    }
}
