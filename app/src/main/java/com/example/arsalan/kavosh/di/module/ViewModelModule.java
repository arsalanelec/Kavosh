package com.example.arsalan.kavosh.di.module;

import com.example.arsalan.kavosh.di.ViewModelKey;
import com.example.arsalan.kavosh.viewModel.AudioListViewModel;
import com.example.arsalan.kavosh.viewModel.ContextureViewModel;
import com.example.arsalan.kavosh.viewModel.ExcavationItemListViewModel;
import com.example.arsalan.kavosh.viewModel.ExcavationItemSupervisorViewModel;
import com.example.arsalan.kavosh.viewModel.ExcavationItemViewModel;
import com.example.arsalan.kavosh.viewModel.FeatureViewModel;
import com.example.arsalan.kavosh.viewModel.FileViewModel;
import com.example.arsalan.kavosh.viewModel.FoundViewModel;
import com.example.arsalan.kavosh.viewModel.LayerFeatureViewModel;
import com.example.arsalan.kavosh.viewModel.LayerViewModel;
import com.example.arsalan.kavosh.viewModel.PhotoListViewModel;
import com.example.arsalan.kavosh.viewModel.ProjectListViewModel;
import com.example.arsalan.kavosh.viewModel.SupervisorListViewModel;
import com.example.arsalan.kavosh.viewModel.SurFoundViewModel;
import com.example.arsalan.kavosh.viewModel.SurveyListViewModel;
import com.example.arsalan.kavosh.viewModel.SurveyProjectViewModel;
import com.example.arsalan.kavosh.viewModel.SurveyViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    abstract ViewModelProvider.Factory bindViewModelFactory(MyViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(ProjectListViewModel.class)
    abstract ViewModel bindProjectListViewModel(ProjectListViewModel projectListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ExcavationItemListViewModel.class)
    abstract ViewModel bindExcavationItemListViewModel(ExcavationItemListViewModel excavationItemListViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(ExcavationItemViewModel.class)
    abstract ViewModel bindExcavationItemViewModel(ExcavationItemViewModel excavationItemViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ExcavationItemSupervisorViewModel.class)
    abstract ViewModel bindExcavationItemSupervisorViewModel(ExcavationItemSupervisorViewModel excavationItemSupervisorViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LayerFeatureViewModel.class)
    abstract ViewModel bindLayerFeatureViewModel(LayerFeatureViewModel layerFeatureViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ContextureViewModel.class)
    abstract ViewModel bindContextureViewModel(ContextureViewModel contextureViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PhotoListViewModel.class)
    abstract ViewModel bindPhotoListViewModel(PhotoListViewModel photoListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AudioListViewModel.class)
    abstract ViewModel bindAudioListViewModel(AudioListViewModel audioListViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(FileViewModel.class)
    abstract ViewModel bindFileViewModel(FileViewModel fileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LayerViewModel.class)
    abstract ViewModel bindLayerViewModel(LayerViewModel layerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FoundViewModel.class)
    abstract ViewModel bindFoundViewModel(FoundViewModel foundViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FeatureViewModel.class)
    abstract ViewModel bindFeatureViewModel(FeatureViewModel featureViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SurveyViewModel.class)
    abstract ViewModel bindSurveyViewModel(SurveyViewModel surveyViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(SurveyListViewModel.class)
    abstract ViewModel bindSurveyListViewModel(SurveyListViewModel surveyListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SurFoundViewModel.class)
    abstract ViewModel bindSurFoundViewModel(SurFoundViewModel surFoundViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SurveyProjectViewModel.class)
    abstract ViewModel bindSurveyProjectViewModel(SurveyProjectViewModel surveyProjectViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SupervisorListViewModel.class)
    abstract ViewModel bindSupervisorListViewModel(SupervisorListViewModel supervisorListViewModel);

}
