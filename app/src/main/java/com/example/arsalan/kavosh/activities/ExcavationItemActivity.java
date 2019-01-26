package com.example.arsalan.kavosh.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.ActivityExcavationItemBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.fragment.LayerFeatureListFragment;
import com.example.arsalan.kavosh.fragment.SupervisorListFragment;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.room.ExcavationItemDao;
import com.example.arsalan.kavosh.viewModel.ExcavationItemViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
import com.example.arsalan.kavosh.wokrmanager.ExcavationItemDeleteWorker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class ExcavationItemActivity extends AppCompatActivity implements Injectable, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    ExcavationItemViewModel viewModel;

    @Inject
    MyViewModelFactory factory;

    @Inject
    ExcavationItemDao mExcavationItemDao;
    @Inject
    Token mToken;

    private String mExcavationItemId;
    private String mProjectName;
    private String getmExcavationItemLayerCoding;
    private String mRegistrationCoding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExcavationItemId = getIntent().getStringExtra(MyConst.EXTRA_EXCAVATION_ITEM_ID);
        mProjectName = getIntent().getStringExtra(MyConst.EXTRA_PROJECT_NAME);
        getmExcavationItemLayerCoding = getIntent().getStringExtra(MyConst.EXTRA_EXCAVATION_ITEM_LAYER_CODING);
        mRegistrationCoding = getIntent().getStringExtra(MyConst.EXTRA_REGISTRATION_CODING);

        setTitle(mProjectName);
        ActivityExcavationItemBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_excavation_item);

        viewModel = ViewModelProviders.of(ExcavationItemActivity.this, factory).get(ExcavationItemViewModel.class);
        viewModel.initial(mExcavationItemId);
        viewModel.getExcavationItem().observe(ExcavationItemActivity.this, excavationItem -> {
            if (excavationItem != null) {
                binding.txtWorkshopTrenchTrialName.setText(excavationItem.getName());
                binding.txtTrenchTrialName.setText(excavationItem.getSubTrailTrenchName());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat formatEn = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    if (excavationItem.getCreatedAt() != null && !excavationItem.getCreatedAt().isEmpty()) {
                        Date date = format.parse(excavationItem.getCreatedAt());
                        binding.txtDateEn.setText(formatEn.format(date));
                        PersianCalendar faDate = new PersianCalendar();
                        faDate.setTime(date);
                        binding.txtDateFa.setText(faDate.getPersianLongDate());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                binding.txtProjectName.setText(mProjectName);
            } else {
                finish();
            }

        });
        binding.viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);

    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                TextView titleTV = new TextView(this);
                titleTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                titleTV.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                titleTV.setText("حذف");
                new AlertDialog.Builder(this)
                        .setCustomTitle(titleTV)
                        .setMessage("آیا مایلید این مورد حذف شود؟")
                        .setPositiveButton("بلی", (dialogInterface, i) -> {
                            deleteExcavatoionItem(mExcavationItemId);
                            finish();
                        })
                        .setNegativeButton("خیر", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create().show();


                return true;

        }
        return false;
    }

    private void deleteExcavatoionItem(String id) {
        mExcavationItemDao.delete(id);

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString(MyConst.EXTRA_EXCAVATION_ITEM_ID, id).build();


        OneTimeWorkRequest Work = new OneTimeWorkRequest.Builder(ExcavationItemDeleteWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();
        WorkManager.getInstance().enqueue(Work);

    }

    private class PagerAdapter extends FragmentPagerAdapter {
        String[] titles = {"لایه ها و فیچرها", "سرپرست ها"};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 1) {
                return SupervisorListFragment.newInstance(mExcavationItemId);
            } else if (i == 0) {
                return LayerFeatureListFragment.newInstance(mExcavationItemId, getmExcavationItemLayerCoding, mRegistrationCoding);
            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
