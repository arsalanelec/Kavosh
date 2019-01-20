package com.example.arsalan.kavosh.activities;

import android.os.Bundle;
import android.util.Log;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.ActivitySurveyBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.dialog.AddSurFoundDialog;
import com.example.arsalan.kavosh.fragment.BlankFragment;
import com.example.arsalan.kavosh.fragment.SurfoundListFragment;
import com.example.arsalan.kavosh.fragment.SurveyHillDestructionDetailFragment;
import com.example.arsalan.kavosh.fragment.SurveyHillExtraDetailFragment;
import com.example.arsalan.kavosh.fragment.SurveyPropertiesFragment;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.SurFound;
import com.example.arsalan.kavosh.model.Survey;
import com.example.arsalan.kavosh.model.SurveyDetail;
import com.example.arsalan.kavosh.room.SurFoundDao;
import com.example.arsalan.kavosh.room.SurveyDao;
import com.example.arsalan.kavosh.viewModel.SurveyViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
import com.example.arsalan.kavosh.wokrmanager.SurFoundUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.SurveyUploadWorker;
import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class SurveyActivity extends AppCompatActivity implements Injectable, HasSupportFragmentInjector,
        SurveyPropertiesFragment.OnFragmentInteractionListener,
        SurfoundListFragment.OnFragmentInteractionListener
        , AddSurFoundDialog.OnFragmentInteractionListener
        , SurveyHillExtraDetailFragment.OnFragmentInteractionListener
        , SurveyHillDestructionDetailFragment.OnFragmentInteractionListener {
    private static final String TAG = "SurveyActivity";
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    MyViewModelFactory mFactory;
    @Inject
    SurveyDao mSurveyDao;

    @Inject
    SurFoundDao mSurFoundDao;

    private Survey mCurrentSurvey;
    private SurveyDetail mSurveyDetail;

    private String mSurveyExtraDetailJson;
    private String mSurveyDestructionDetailJson;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String surveyId = getIntent().getExtras().getString(MyConst.EXTRA_ID);
        String projectName = getIntent().getExtras().getString(MyConst.EXTRA_PROJECT_NAME);
        ActivitySurveyBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_survey);

        SurveyViewModel viewModel = ViewModelProviders.of(this, mFactory).get(SurveyViewModel.class);
        viewModel.initial(surveyId);
        viewModel.getSurvey().observe(SurveyActivity.this, new Observer<Survey>() {
            @Override
            public void onChanged(@Nullable Survey survey) {
                if (survey != null) {
                    getSupportActionBar().setTitle(projectName + ":" + survey.getName());
                    mCurrentSurvey = survey;
                    Gson gson = new Gson();
                    Log.d(TAG, "onChanged:" + "survey id is:" + survey.getId());
                    mSurveyDetail = gson.fromJson(mCurrentSurvey.getContentJson(), SurveyDetail.class);
                    if (mSurveyDetail != null) {
                        mSurveyExtraDetailJson = mSurveyDetail.getExtraDetailJson();
                        mSurveyDestructionDetailJson = mSurveyDetail.getDestructionDetailJson();

                        if (mSurveyExtraDetailJson == null || mSurveyExtraDetailJson.isEmpty())
                            mSurveyExtraDetailJson = "{}";

                        if (mSurveyDestructionDetailJson == null || mSurveyDestructionDetailJson.isEmpty())
                            mSurveyDestructionDetailJson = "{}";

                    } else {
                        mSurveyDetail = new SurveyDetail();
                        mSurveyExtraDetailJson = "{}";
                        mSurveyDestructionDetailJson = "{}";

                    }
                } else {
                    Log.d(TAG, "onChanged:" + "survey is null");
                    mCurrentSurvey = new Survey();
                }
                binding.viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), mCurrentSurvey));
                binding.tabLayout.setupWithViewPager(binding.viewPager);
                viewModel.getSurvey().removeObserver(this);
            }
        });


    }

    @Override
    public void onSurveyDetailUpdate(SurveyDetail surveyDetail) {
        mSurveyDetail = surveyDetail;
        mSurveyDetail.setExtraDetailJson(mSurveyExtraDetailJson);
        mSurveyDetail.setDestructionDetailJson(mSurveyDestructionDetailJson);
        Gson gson = new Gson();
        mCurrentSurvey.setContentJson(gson.toJson(mSurveyDetail));
        updateSurvey(mCurrentSurvey);
    }


    private void updateSurvey(Survey survey) {
        mSurveyDao.save(survey);
        Data inputData = new Data.Builder()
                .putString("id", survey.getId())
                .putString("content_json", survey.getContentJson())
                /*.putString("address", survey.getAddress())
                .putString("code_name", survey.getAddress())
                .putString("easting", survey.getAddress())
                .putString("address", survey.getAddress())*/

                .build();
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        OneTimeWorkRequest surveyUploadReq = new OneTimeWorkRequest.Builder(SurveyUploadWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();
        WorkManager.getInstance().enqueueUniqueWork(
                survey.getId(), ExistingWorkPolicy.REPLACE, surveyUploadReq)
                .getState()
                .observe(SurveyActivity.this, state -> Log.d(TAG, "updateSurvey: status:" + state));
    }

    @Override
    public void onSaveSurFound(SurFound surFound) {
    }

    @Override
    public void onAddSurFound(SurFound surFound) {
        surFound.setSurvey_id(mCurrentSurvey.getId());
        mSurFoundDao.save(surFound);
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString("id", surFound.getId())
                .putString("survey_id", surFound.getSurvey_id())
                .putString("content_json", surFound.getContentJson())
                .putString("type", String.valueOf(surFound.getType()))
                .build();

        OneTimeWorkRequest uploadFound = new OneTimeWorkRequest.Builder(SurFoundUploadWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();
        WorkManager.getInstance().enqueue(uploadFound);

    }

    @Override
    public void onSurveyExtraDetail(String detail) {
        mSurveyExtraDetailJson = detail;
        mSurveyDetail.setExtraDetailJson(mSurveyExtraDetailJson);
        Gson gson = new Gson();
        mCurrentSurvey.setContentJson(gson.toJson(mSurveyDetail));
        updateSurvey(mCurrentSurvey);
    }

    @Override
    public void onSurveyDestructionDetailUpdate(String detail) {
        mSurveyDestructionDetailJson = detail;
        mSurveyDetail.setDestructionDetailJson(mSurveyDestructionDetailJson);
        Gson gson = new Gson();
        mCurrentSurvey.setContentJson(gson.toJson(mSurveyDetail));
        updateSurvey(mCurrentSurvey);
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        String[] titles = {"مشخصات", "جزییات", "آسیب شناسی", "یافته ها"};
        Survey survey;

        public PagerAdapter(FragmentManager fm, Survey survey) {

            super(fm);
            this.survey = survey;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0) {
                return SurveyPropertiesFragment.newInstance(survey.getContentJson(), survey.getId());
            } else if (i == 1) {
                Log.d(TAG, "extraDetail json: " + mSurveyExtraDetailJson);
                return SurveyHillExtraDetailFragment.newInstance(mSurveyExtraDetailJson);

            } else if (i == 2) {
                Log.d(TAG, "extraDetail json: " + mSurveyDestructionDetailJson);
                return SurveyHillDestructionDetailFragment.newInstance(mSurveyDestructionDetailJson);

            } else if (i == 3) {
                return SurfoundListFragment.newInstance(survey.getId());
            }
            /*else if (i == 1) {
                //  return HeightLevelsFragment.newInstance();
                return HeightLevelsFragment.newInstance(mLayer.getHeightLevelH(), mLayer.getHeightLevelL());
            } else if (i == 2) {
                return LayerPositionFragment.newInstance(mLayer.getPosition(), mExcavationItemId);
            } else if (i == 3) {
                return FoundListFragment.newInstance(mLayerName, mLayerId, mExcavationItemId);

            } else
                return ContextureFragment.newInstance(mLayerName, mLayerId);
        */
            return new BlankFragment();
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
