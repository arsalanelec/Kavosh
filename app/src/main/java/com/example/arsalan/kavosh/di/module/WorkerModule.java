package com.example.arsalan.kavosh.di.module;

import com.example.arsalan.kavosh.di.WorkerKey;
import com.example.arsalan.kavosh.wokrmanager.AudioUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.ContextureUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.ExcavationItemDeleteWorker;
import com.example.arsalan.kavosh.wokrmanager.ExcavationItemSaveWorker;
import com.example.arsalan.kavosh.wokrmanager.ExcavationUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.FeatureCreateUpdateWorker;
import com.example.arsalan.kavosh.wokrmanager.FeatureDeleteWorker;
import com.example.arsalan.kavosh.wokrmanager.FoundDeleteWorker;
import com.example.arsalan.kavosh.wokrmanager.FoundUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.LayerDeleteWorker;
import com.example.arsalan.kavosh.wokrmanager.LayerFeatureSaveWorker;
import com.example.arsalan.kavosh.wokrmanager.LayerUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.PhotoDeleteWorker;
import com.example.arsalan.kavosh.wokrmanager.PhotoUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.SupervisorUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.SurFoundDeleteWorker;
import com.example.arsalan.kavosh.wokrmanager.SurFoundUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.SurveyProjectUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.SurveyUploadWorker;

import androidx.work.Worker;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
        ExcavationWorkerModule.class,
        ExcavationItemSaveWorkerModule.class,
        LayerFeatureSaveWorkerModule.class,
        ContextureUploadWorkerModule.class,
        PhotoUploadWorkerModule.class,
        AudioUploadWorkerModule.class,
        LayerUploadWorkerModule.class,
        FoundUploadWorkerModule.class,
        ExcavationItemDeleteWorkerModule.class,
        LayerDeleteWorkerModule.class,
        PhotoDeleteWorkerModule.class,
        FoundDeleteWorkerModule.class,
        FeatureCreateUpdateWorkerModule.class,
        FeatureDeleteWorkerModule.class,
        SurveyWorkerModule.class,
        SurFoundDeleteWorkerModule.class,
        SurFoundUploadWorkerModule.class,
        SurveyProjectUploadWorkerModule.class,
        SupervisorUploadWorkerModule.class
})
public abstract class WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(ExcavationUploadWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindExcavationWorkerFactory(ExcavationWorkerModule.Builder excavationWorkBuilder);

    @Binds
    @IntoMap
    @WorkerKey(ExcavationItemSaveWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindExcavationItemSaveWorkerFactory(ExcavationItemSaveWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(LayerFeatureSaveWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindLayerFeatureSaveWorkerFactory(LayerFeatureSaveWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(ContextureUploadWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindContextureUploadWorkerFactory(ContextureUploadWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(PhotoUploadWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindPhotoUploadWorkerFactory(PhotoUploadWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(AudioUploadWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindAudioUploadWorkerFactory(AudioUploadWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(LayerUploadWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindLayerUploadWorkerFactory(LayerUploadWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(FoundUploadWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindFoundUploadWorkerFactory(FoundUploadWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(ExcavationItemDeleteWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindExcavationItemDeleteWorkerFactory(ExcavationItemDeleteWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(LayerDeleteWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindLayerDeleteWorkerFactory(LayerDeleteWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(FeatureCreateUpdateWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindFeatureCreateUpdateWorkerFactory(FeatureCreateUpdateWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(FeatureDeleteWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindFeatureDeleteWorkerFactory(FeatureDeleteWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(PhotoDeleteWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindPhotoDeleteWorkerFactory(PhotoDeleteWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(FoundDeleteWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindFoundDeleteWorkerFactory(FoundDeleteWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(SurveyUploadWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindSurveyUploadWorkerFactory(SurveyWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(SurFoundUploadWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindSurFoundUploadWorkerFactory(SurFoundUploadWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(SurFoundDeleteWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindSurFoundDeleteWorkerFactory(SurFoundDeleteWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(SurveyProjectUploadWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindSurveyProjectUploadWorkerFactory(SurveyProjectUploadWorkerModule.Builder builder);

    @Binds
    @IntoMap
    @WorkerKey(SupervisorUploadWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindSupervisorUploadWorkerFactory(SupervisorUploadWorkerModule.Builder builder);

}