/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.arsalan.kavosh.di;

import android.app.Application;

import com.example.arsalan.kavosh.application.MyApplication;
import com.example.arsalan.kavosh.di.module.AndroidWorkerInjectionModule;
import com.example.arsalan.kavosh.di.module.AppModule;
import com.example.arsalan.kavosh.di.module.ExcavationActivityModule;
import com.example.arsalan.kavosh.di.module.ExcavationItemActivityModule;
import com.example.arsalan.kavosh.di.module.FeatureActivityModule;
import com.example.arsalan.kavosh.di.module.FoundActivityModule;
import com.example.arsalan.kavosh.di.module.GalleryActivityModule;
import com.example.arsalan.kavosh.di.module.LayerActivityModule;
import com.example.arsalan.kavosh.di.module.MainActivityModule;
import com.example.arsalan.kavosh.di.module.NetModule;
import com.example.arsalan.kavosh.di.module.SurFoundDetailActivityModule;
import com.example.arsalan.kavosh.di.module.SurveyActivityModule;
import com.example.arsalan.kavosh.di.module.SurveyItemListActivityModule;
import com.example.arsalan.kavosh.di.module.WorkerModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AndroidWorkerInjectionModule.class,
        WorkerModule.class
        , AppModule.class
        , MainActivityModule.class
        , NetModule.class
        , ExcavationActivityModule.class
        , ExcavationItemActivityModule.class
        , LayerActivityModule.class
        , GalleryActivityModule.class
        , FoundActivityModule.class
        , FeatureActivityModule.class
        , SurveyActivityModule.class
        , SurFoundDetailActivityModule.class
        , SurveyItemListActivityModule.class
})

public interface AppComponent {
    void inject(MyApplication myApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder baseUrl(@Named("baseUrl") String baseUrl);

        AppComponent build();
    }
}
