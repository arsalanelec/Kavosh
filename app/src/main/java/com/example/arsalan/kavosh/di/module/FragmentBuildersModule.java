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

import com.example.arsalan.kavosh.dialog.AddSupervisorDialog;
import com.example.arsalan.kavosh.dialog.NewExcavationDialog;
import com.example.arsalan.kavosh.dialog.NewExcavationItem1Dialog;
import com.example.arsalan.kavosh.dialog.NewSurveyDialog;
import com.example.arsalan.kavosh.dialog.NewSurveyProjectDialog;
import com.example.arsalan.kavosh.fragment.ContextureFragment;
import com.example.arsalan.kavosh.fragment.FoundListFragment;
import com.example.arsalan.kavosh.fragment.FtrDebrisFragment;
import com.example.arsalan.kavosh.fragment.FtrDitchFragment;
import com.example.arsalan.kavosh.fragment.FtrFireplaceFragment;
import com.example.arsalan.kavosh.fragment.FtrFloorFragment;
import com.example.arsalan.kavosh.fragment.FtrPitFragment;
import com.example.arsalan.kavosh.fragment.FtrStoveFragment;
import com.example.arsalan.kavosh.fragment.FtrWallFragment;
import com.example.arsalan.kavosh.fragment.LayerFeatureListFragment;
import com.example.arsalan.kavosh.fragment.LayerPositionFragment;
import com.example.arsalan.kavosh.fragment.ProjectListFragment;
import com.example.arsalan.kavosh.fragment.SupervisorListFragment;
import com.example.arsalan.kavosh.fragment.SurfoundListFragment;
import com.example.arsalan.kavosh.fragment.SurveyItemListFragment;
import com.example.arsalan.kavosh.fragment.SurveyOssuaryExtraDetailFragment;
import com.example.arsalan.kavosh.fragment.SurveyPropertiesFragment;
import com.example.arsalan.kavosh.fragment.SurveyStaffManageFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ProjectListFragment contributeProjectListFragment();

    @ContributesAndroidInjector
    abstract NewExcavationDialog contributeNewExcavationDialog();

    @ContributesAndroidInjector
    abstract NewExcavationItem1Dialog contributeNewExcavationItem1Dialog();

    @ContributesAndroidInjector
    abstract LayerFeatureListFragment contributeFeatureLayerListFragment();

    @ContributesAndroidInjector
    abstract SupervisorListFragment contributeSupervisorListFragment();

    @ContributesAndroidInjector
    abstract ContextureFragment contributeContextureFragment();

    @ContributesAndroidInjector
    abstract FoundListFragment contributeFoundListFragment();

    @ContributesAndroidInjector
    abstract LayerPositionFragment contributeLayerPositionFragment();


    @ContributesAndroidInjector
    abstract FtrWallFragment contributeWallFragment();

    @ContributesAndroidInjector
    abstract FtrFloorFragment contributeFloorFragment();

    @ContributesAndroidInjector
    abstract NewSurveyDialog NewSurveyDialog();

    @ContributesAndroidInjector
    abstract SurveyPropertiesFragment surveyPropertiesFragment();

    @ContributesAndroidInjector
    abstract SurfoundListFragment SurfoundListFragment();

    @ContributesAndroidInjector
    abstract NewSurveyProjectDialog newSurveyProjectDialog();

    @ContributesAndroidInjector
    abstract SurveyItemListFragment surveyItemListFragment();


    @ContributesAndroidInjector
    abstract SurveyStaffManageFragment surveyStaffManageFragment();

    @ContributesAndroidInjector
    abstract AddSupervisorDialog addSupervisorDialog();

    @ContributesAndroidInjector
    abstract SurveyOssuaryExtraDetailFragment addSurveyOssuaryExtraDetailFragment();

    @ContributesAndroidInjector
    abstract FtrDebrisFragment addFtrDebrisFragment();

    @ContributesAndroidInjector
    abstract FtrFireplaceFragment addFtrFireplaceFragment();

    @ContributesAndroidInjector
    abstract FtrStoveFragment addFtrStoveFragment();

    @ContributesAndroidInjector
    abstract FtrPitFragment addFtrPitFragment();

    @ContributesAndroidInjector
    abstract FtrDitchFragment addFtrDitchFragment();

}
