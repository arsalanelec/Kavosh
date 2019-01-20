package com.example.arsalan.kavosh.application;

import android.app.Activity;
import android.app.Application;

import com.example.arsalan.kavosh.di.AppInjector;
import com.example.arsalan.kavosh.di.HasWorkerInjector;

import javax.inject.Inject;

import androidx.work.Worker;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Arsalan on 10-02-2018.
 */

public class MyApplication extends Application implements HasActivityInjector, HasWorkerInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Worker> workerDispatchingAndroidInjector;


    @Override
    public void onCreate() {
        AppInjector.init(this);
        super.onCreate();

    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Worker> workerInjector() {
        return workerDispatchingAndroidInjector;
    }


}
