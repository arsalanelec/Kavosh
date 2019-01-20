package com.example.arsalan.kavosh.activities;

import android.os.Bundle;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.ActivitySurveyItemListBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.fragment.SurveyItemListFragment;
import com.example.arsalan.kavosh.fragment.SurveyStaffManageFragment;
import com.example.arsalan.kavosh.model.MyConst;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class SurveyItemListActivity extends AppCompatActivity implements Injectable, HasSupportFragmentInjector {
    private static final String TAG = "SurveyItemListActivity";
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private String mSurveyProjectId;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySurveyItemListBinding b = DataBindingUtil.setContentView(this, R.layout.activity_survey_item_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSurveyProjectId = bundle.getString(MyConst.EXTRA_ID, "0");
        } else {
            throw new IllegalStateException("SurveyProject Id must be send");
        }
        b.viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        b.tabLayout.setupWithViewPager(b.viewPager);

    }

    public interface SurveyOnClickListener {
        void OnClickListener(String surveyId);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] title = {"آثار", "اعضای هیات"};

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SurveyItemListFragment.newInstance(mSurveyProjectId, "");
                case 1:
                    return SurveyStaffManageFragment.newInstance(mSurveyProjectId, "");
            }
            return null;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }


}
