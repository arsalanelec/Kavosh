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

package com.example.arsalan.kavosh.di.module;

import android.app.Application;

import com.example.arsalan.kavosh.room.AudioDao;
import com.example.arsalan.kavosh.room.ContextureDao;
import com.example.arsalan.kavosh.room.ExcavationItemDao;
import com.example.arsalan.kavosh.room.ExcavationItemSupervisorDao;
import com.example.arsalan.kavosh.room.FeatureDao;
import com.example.arsalan.kavosh.room.FoundDao;
import com.example.arsalan.kavosh.room.LayerDao;
import com.example.arsalan.kavosh.room.LayerFeatureDao;
import com.example.arsalan.kavosh.room.MyDatabase;
import com.example.arsalan.kavosh.room.PhotoDao;
import com.example.arsalan.kavosh.room.ProjectDao;
import com.example.arsalan.kavosh.room.SupervisorDao;
import com.example.arsalan.kavosh.room.SurFoundDao;
import com.example.arsalan.kavosh.room.SurveyDao;
import com.example.arsalan.kavosh.room.SurveyProjectDao;
import com.example.arsalan.kavosh.room.UserDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
public class AppModule {
    @Singleton
    @Provides
    MyDatabase provideDb(Application app) {
        return Room
                .databaseBuilder(app, MyDatabase.class, "mydatabase.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    Executor provideExecutor() {
        return Executors.newFixedThreadPool(3);
    }

    @Singleton
    @Provides
    UserDao provideUserDao(MyDatabase db) {
        return db.userDao();
    }

    @Singleton
    @Provides
    ProjectDao provideProjectDao(MyDatabase db) {
        return db.projectDao();
    }

    @Singleton
    @Provides
    ExcavationItemDao provideExcavationItemDao(MyDatabase db) {
        return db.excavationItemDao();
    }

    @Singleton
    @Provides
    ExcavationItemSupervisorDao provideExcavationItemSupervisorDao(MyDatabase db) {
        return db.excavationItemSupervisorDao();
    }

    @Singleton
    @Provides
    LayerFeatureDao provideLayerFeatureDao(MyDatabase db) {
        return db.layerFeatureDao();
    }

    @Singleton
    @Provides
    ContextureDao provideContextureDao(MyDatabase db) {
        return db.contextureDao();
    }

    @Singleton
    @Provides
    PhotoDao providePhotoDao(MyDatabase db) {
        return db.photoDao();
    }

    @Singleton
    @Provides
    AudioDao provideAudioDao(MyDatabase db) {
        return db.audioDao();
    }

    @Singleton
    @Provides
    LayerDao provideLayerDao(MyDatabase db) {
        return db.layerDao();
    }

    @Singleton
    @Provides
    FoundDao provideFoundDao(MyDatabase db) {
        return db.foundDao();
    }

    @Singleton
    @Provides
    FeatureDao provideFeatureDao(MyDatabase db) {
        return db.featureDao();
    }

    @Singleton
    @Provides
    SurveyDao provideSurveyDao(MyDatabase db) {
        return db.surveyDao();
    }

    @Singleton
    @Provides
    SurFoundDao provideSurFoundDao(MyDatabase db) {
        return db.surFoundDao();
    }

    @Singleton
    @Provides
    SurveyProjectDao provideSurveyProjectDao(MyDatabase db) {
        return db.surveyProjectDao();
    }

    @Singleton
    @Provides
    SupervisorDao provideSupervisorDao(MyDatabase db) {
        return db.supervisorDao();
    }

}
