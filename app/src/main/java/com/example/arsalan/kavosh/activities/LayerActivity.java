package com.example.arsalan.kavosh.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.ActivityLayerBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.fragment.ContextureFragment;
import com.example.arsalan.kavosh.fragment.FoundListFragment;
import com.example.arsalan.kavosh.fragment.HeightLevelsFragment;
import com.example.arsalan.kavosh.fragment.LayerPositionFragment;
import com.example.arsalan.kavosh.model.Layer;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.room.FoundDao;
import com.example.arsalan.kavosh.room.LayerDao;
import com.example.arsalan.kavosh.room.LayerFeatureDao;
import com.example.arsalan.kavosh.viewModel.LayerViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
import com.example.arsalan.kavosh.wokrmanager.LayerDeleteWorker;
import com.example.arsalan.kavosh.wokrmanager.LayerUploadWorker;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class LayerActivity extends AppCompatActivity implements Injectable, HasSupportFragmentInjector
        , HeightLevelsFragment.OnFragmentInteractionListener
        , LayerPositionFragment.OnFragmentInteractionListener
        , FoundListFragment.OnFragmentInteractionListener {

    private static final String TAG = "LayerActivity";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    Token mToken;
    @Inject
    LayerDao mLayerDao;
    @Inject
    LayerFeatureDao mLayerFeatureDao;
    @Inject
    FoundDao mFoundDao;

    @Inject
    MyViewModelFactory mFactory;
    private String mRegistrationCoding;
    private String mExcavationItemId;
    private Context mContext;
    private Boolean canDelete;
    private String mLayerName;
    private String mLayerId;
    private Layer mLayer;

    public LayerActivity() {
        mContext = this;
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayerName = getIntent().getStringExtra(MyConst.EXTRA_EXCAVATION_LAYER_FEATURE_NAME);
        mLayerId = getIntent().getStringExtra(MyConst.EXTRA_EXCAVATION_LAYER_FEATURE_ID);
        Log.d(TAG, "onCreate: layerId:" + mLayerId);
        mRegistrationCoding = getIntent().getStringExtra(MyConst.EXTRA_REGISTRATION_CODING);
        mExcavationItemId = getIntent().getStringExtra(MyConst.EXTRA_EXCAVATION_ITEM_ID);
        canDelete = getIntent().getBooleanExtra(MyConst.EXTRA_CAN_DELETE, false);
        ActivityLayerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_layer);
        LayerViewModel viewModel = ViewModelProviders.of(this, mFactory).get(LayerViewModel.class);
        viewModel.initial(mLayerId);
        viewModel.getLayer().observe(this, new Observer<Layer>() {
            @Override
            public void onChanged(@Nullable Layer layer) {
                if (layer != null) {
                    mLayer = layer;
                    binding.viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
                    binding.tabLayout.setupWithViewPager(binding.viewPager);
                    viewModel.getLayer().removeObserver(this);
                }
            }
        });


        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.d(getClass().getSimpleName(), "onPageSelected: ");
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.d(getClass().getSimpleName(), "onPageScrollStateChanged: ");
            }
        });
    }

    @Override
    public void updateHeightLevelH(String jsonString) {
        mLayer.setHeightLevelH(jsonString);
        Log.d(getClass().getSimpleName(), "updateHeightLevelH: " + jsonString);
        updateLayer(mLayer);
    }

    @Override
    public void updateHeightLevelL(String jsonString) {
        mLayer.setHeightLevelL(jsonString);
        updateLayer(mLayer);
    }

    @Override
    public void UpdateLayerPosition(String positionJson) {
        mLayer.setPosition(positionJson);
        updateLayer(mLayer);
    }

    @Override
    public String getNewRegistrationCode() {
        return increaseCode(mRegistrationCoding, mFoundDao.getNumberOfRows(mExcavationItemId));
    }

    private void updateLayer(Layer layer) {
        mLayerDao.save(layer);
        Data inputDataLayer = new Data.Builder()
                .putString("token", mToken.getAccessToken())
                .putString("id", layer.getId())
                .putString("name", layer.getName())
                .putString("height_level_h", layer.getHeightLevelH())
                .putString("height_level_l", layer.getHeightLevelL())
                .putString("position", layer.getPosition())
                .build();
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        OneTimeWorkRequest LayerUploadWork = new OneTimeWorkRequest.Builder(LayerUploadWorker.class)
                .setConstraints(constraints).setInputData(inputDataLayer).build();
        WorkManager.getInstance().enqueue(LayerUploadWork);
        WorkManager.getInstance().getWorkInfoByIdLiveData(LayerUploadWork.getId())
                .observe(LayerActivity.this, workStatus ->
                        Log.d(getClass().getSimpleName(), "updateLayer: status:" + workStatus)
                );
    }

    private String increaseCode(String codingStart, int count) {

        if (codingStart.length() > 0) {
            Character lastChar = codingStart.charAt(codingStart.length() - 1);
            String sample = codingStart;
            String pattern = sample.substring(0, codingStart.length() - 1);

            int cnt2 = 0;
            for (int cnt = 1; cnt <= count; cnt++) {
                if ((lastChar >= 65 && lastChar < 90)) {
                    lastChar++;
                    sample = pattern + (char) (lastChar);


                } else if (lastChar <= 57) {
                    cnt2++;
                    sample = pattern + Integer.valueOf(lastChar - 48 + cnt2);

                } else {
                    pattern += (char) (lastChar - 1);
                    sample = pattern + "1";
                    lastChar = '1';
                }
            }
            return sample;
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (canDelete)
            getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                TextView titleTV = new TextView(this);
                titleTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                int dip = 8;
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dip,
                        getResources().getDisplayMetrics()
                );
                titleTV.setPadding(px, px, px, px);
                titleTV.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                titleTV.setText("حذف");
                new AlertDialog.Builder(this)
                        .setCustomTitle(titleTV)
                        .setMessage("آیا مایلید این مورد حذف شود؟")
                        .setPositiveButton("بلی", (dialogInterface, i) -> {
                            deleteLayer(mLayerId);
                            finish();
                        })
                        .setNegativeButton("خیر", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create().show();


                return true;

        }
        return false;
    }

    private void deleteLayer(String id) {
        mLayerFeatureDao.deleteByChildId(id);
        mLayerDao.delete(id);
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString(MyConst.EXTRA_LAYER_ID, id).build();


        OneTimeWorkRequest Work = new OneTimeWorkRequest.Builder(LayerDeleteWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();
        WorkManager.getInstance().enqueue(Work);

    }

    private class PagerAdapter extends FragmentPagerAdapter {
        String[] titles = {"بافت", "سطوح ارتفاعی", "موقعیت لایه", "یافته"};

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
            if (i == 0) {
                return ContextureFragment.newInstance(mLayerName, mLayerId);
            } else if (i == 1) {
                //  return HeightLevelsFragment.newInstance();
                return HeightLevelsFragment.newInstance(mLayer.getHeightLevelH(), mLayer.getHeightLevelL());
            } else if (i == 2) {
                return LayerPositionFragment.newInstance(mLayer.getPosition(), mExcavationItemId);
            } else if (i == 3) {
                return FoundListFragment.newInstance(mLayerName, mLayerId, mExcavationItemId);

            } else
                return ContextureFragment.newInstance(mLayerName, mLayerId);
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
