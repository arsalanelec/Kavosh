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
import com.example.arsalan.kavosh.databinding.ActivityFeatureBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.fragment.BlankFragment;
import com.example.arsalan.kavosh.fragment.FeaturePositionFragment;
import com.example.arsalan.kavosh.fragment.FoundListFragment;
import com.example.arsalan.kavosh.fragment.FtrDebrisFragment;
import com.example.arsalan.kavosh.fragment.FtrDitchFragment;
import com.example.arsalan.kavosh.fragment.FtrFireplaceFragment;
import com.example.arsalan.kavosh.fragment.FtrFloorFragment;
import com.example.arsalan.kavosh.fragment.FtrPitFragment;
import com.example.arsalan.kavosh.fragment.FtrStairsFragment;
import com.example.arsalan.kavosh.fragment.FtrStoveFragment;
import com.example.arsalan.kavosh.fragment.FtrWallFragment;
import com.example.arsalan.kavosh.fragment.HeightLevelsFragment;
import com.example.arsalan.kavosh.fragment.LayerPositionFragment;
import com.example.arsalan.kavosh.model.AudioMemo;
import com.example.arsalan.kavosh.model.Feature;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Photo;
import com.example.arsalan.kavosh.room.AudioDao;
import com.example.arsalan.kavosh.room.FeatureDao;
import com.example.arsalan.kavosh.room.FoundDao;
import com.example.arsalan.kavosh.room.LayerFeatureDao;
import com.example.arsalan.kavosh.room.PhotoDao;
import com.example.arsalan.kavosh.viewModel.FeatureViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
import com.example.arsalan.kavosh.wokrmanager.FeatureCreateUpdateWorker;
import com.example.arsalan.kavosh.wokrmanager.FeatureDeleteWorker;

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
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.example.arsalan.kavosh.model.Feature.MyEnum.FEATURE1;
import static com.example.arsalan.kavosh.model.Feature.MyEnum.FEATURE13;
import static com.example.arsalan.kavosh.model.Feature.MyEnum.FEATURE17;
import static com.example.arsalan.kavosh.model.Feature.MyEnum.FEATURE18;
import static com.example.arsalan.kavosh.model.Feature.MyEnum.FEATURE2;
import static com.example.arsalan.kavosh.model.Feature.MyEnum.FEATURE3;
import static com.example.arsalan.kavosh.model.Feature.MyEnum.FEATURE4;
import static com.example.arsalan.kavosh.model.Feature.MyEnum.FEATURE5;
import static com.example.arsalan.kavosh.model.Feature.MyEnum.FEATURE6;
import static com.example.arsalan.kavosh.utils.AudioUpload.uploadAudio;
import static com.example.arsalan.kavosh.utils.PhotoUpload.uploadPhoto;

public class FeatureActivity extends AppCompatActivity implements Injectable, HasSupportFragmentInjector
        , FtrWallFragment.OnFragmentInteractionListener
        , FtrFloorFragment.OnFragmentInteractionListener
        , FtrDebrisFragment.OnFragmentInteractionListener
        , FtrFireplaceFragment.OnFragmentInteractionListener
        , FtrStoveFragment.OnFragmentInteractionListener
        , FtrPitFragment.OnFragmentInteractionListener
        , FtrDitchFragment.OnFragmentInteractionListener
        , FtrStairsFragment.OnFragmentInteractionListener
        , FeaturePositionFragment.OnFragmentInteractionListener
        , HeightLevelsFragment.OnFragmentInteractionListener
        , LayerPositionFragment.OnFragmentInteractionListener
        , FoundListFragment.OnFragmentInteractionListener {

    private static final String TAG = "FeatureActivity";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    FeatureDao mFeatureDao;
    @Inject
    LayerFeatureDao mLayerFeatureDao;
    @Inject
    FoundDao mFoundDao;

    @Inject
    PhotoDao mPhotoDao;

    @Inject
    AudioDao mAudioDao;

    @Inject
    MyViewModelFactory factory;
    private String mRegistrationCoding;
    private String mExcavationItemId;
    private Context mContext;
    private Boolean canDelete;
    private String mFeatureName;
    private String mFeatureId;
    private Feature mFeature;


    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeatureName = getIntent().getStringExtra(MyConst.EXTRA_EXCAVATION_LAYER_FEATURE_NAME);
        mFeatureId = getIntent().getStringExtra(MyConst.EXTRA_EXCAVATION_LAYER_FEATURE_ID);
        Log.d(TAG, "onCreate: featureId:" + mFeatureId);
        mRegistrationCoding = getIntent().getStringExtra(MyConst.EXTRA_REGISTRATION_CODING);
        mExcavationItemId = getIntent().getStringExtra(MyConst.EXTRA_EXCAVATION_ITEM_ID);
        canDelete = getIntent().getBooleanExtra(MyConst.EXTRA_CAN_DELETE, false);
        ActivityFeatureBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_feature);
        FeatureViewModel viewModel = ViewModelProviders.of(this, factory).get(FeatureViewModel.class);
        viewModel.initial(mFeatureId);
        viewModel.getFeature().observe(this, new Observer<Feature>() {
            @Override
            public void onChanged(@Nullable Feature feature) {
                if (feature != null) {
                    mFeature = feature;
                    /*Fragment fragment = new BlankFragment();
                    if (mFeature.getStructureIndex() == FEATURE1.ordinal() || mFeature.getStructureIndex() == FEATURE2.ordinal()) {
                        fragment = FtrWallFragment.newInstance(mFeature.getId(), mFeature.getContentJson());

                    } else if (mFeature.getStructureIndex() == FEATURE3.ordinal()) {
                        fragment = FtrFloorFragment.newInstance(mFeature.getId(), mFeature.getContentJson());


                    } else if (mFeature.getStructureIndex() == FEATURE6.ordinal()) {//آتشدان
                        fragment = FtrFireplaceFragment.newInstance(mFeature.getId(), mFeature.getContentJson());

                    } else if (mFeature.getStructureIndex() == FEATURE13.ordinal()) {//آوار
                        fragment = FtrDebrisFragment.newInstance(mFeature.getId(), mFeature.getContentJson());

                    } else if (mFeature.getStructureIndex() == FEATURE5.ordinal()) {// اجاق
                        fragment = FtrStoveFragment.newInstance(mFeature.getId(), mFeature.getContentJson());
                    } else if (mFeature.getStructureIndex() == FEATURE17.ordinal()) {// چاله
                        fragment = FtrPitFragment.newInstance(mFeature.getId(), mFeature.getContentJson());
                    } else if (mFeature.getStructureIndex() == FEATURE18.ordinal()) { //راه آب
                        fragment = FtrDitchFragment.newInstance(mFeature.getId(), mFeature.getContentJson());
                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, fragment)
                            .commit();*/
                    binding.viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), mFeature));
                    binding.viewPager.setOffscreenPageLimit(0);
                    binding.tabLayout.setupWithViewPager(binding.viewPager);
                }
                viewModel.getFeature().

                        removeObserver(this);
            }
        });
    }

    @Override
    public void onUpdatePosition(String positionJson) {
        mFeature.setPosition(positionJson);
        storeFeature(mFeature);
    }

    @Override
    public String getNewRegistrationCode() {
        return increaseCode(mRegistrationCoding, mFoundDao.getNumberOfRows(mExcavationItemId));
    }


    private class PagerAdapter extends FragmentPagerAdapter {
        String[] titles = {"ابعاد", "موقعیت فضایی", "سطوح ارتفاعی", "موقعیت فیچر", "یافته"};

        Feature feature;

        public PagerAdapter(FragmentManager fm, Feature feature) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    Fragment fragment = new BlankFragment();
                    if (mFeature.getStructureIndex() == FEATURE1.ordinal() || mFeature.getStructureIndex() == FEATURE2.ordinal()) {
                        fragment = FtrWallFragment.newInstance(mFeature.getId(), mFeature.getContentJson());

                    } else if (mFeature.getStructureIndex() == FEATURE3.ordinal()) {
                        fragment = FtrFloorFragment.newInstance(mFeature.getId(), mFeature.getContentJson());


                    } else if (mFeature.getStructureIndex() == FEATURE6.ordinal()) {//آتشدان
                        fragment = FtrFireplaceFragment.newInstance(mFeature.getId(), mFeature.getContentJson());

                    } else if (mFeature.getStructureIndex() == FEATURE13.ordinal()) {//آوار
                        fragment = FtrDebrisFragment.newInstance(mFeature.getId(), mFeature.getContentJson());

                    } else if (mFeature.getStructureIndex() == FEATURE5.ordinal()) {// اجاق
                        fragment = FtrStoveFragment.newInstance(mFeature.getId(), mFeature.getContentJson());
                    } else if (mFeature.getStructureIndex() == FEATURE17.ordinal()) {// چاله
                        fragment = FtrPitFragment.newInstance(mFeature.getId(), mFeature.getContentJson());
                    } else if (mFeature.getStructureIndex() == FEATURE18.ordinal()) { //راه آب
                        fragment = FtrDitchFragment.newInstance(mFeature.getId(), mFeature.getContentJson());
                    } else if (mFeature.getStructureIndex() == FEATURE4.ordinal()) { //پله
                        fragment = FtrStairsFragment.newInstance(mFeature.getId(), mFeature.getContentJson());
                    }
                    return fragment;

                case 1:
                    return FeaturePositionFragment.newInstance(mFeature.getPosition());
                case 2:
                    return HeightLevelsFragment.newInstance(mFeature.getHeightLevelH(), mFeature.getHeightLevelL());
                case 3:
                    return LayerPositionFragment.newInstance(mFeature.getPosition(), mExcavationItemId);
                case 4:
                    return FoundListFragment.newInstance(mFeature.getName(), mFeatureId, mExcavationItemId);

              /*  return ContextureFragment.newInstance(mLayerName, mLayerId);
            } else if (i == 1) {
                //  return HeightLevelsFragment.newInstance();
                return HeightLevelsFragment.newInstance(mLayer.getHeightLevelH(), mLayer.getHeightLevelL());
            } else if (i == 2) {
                return LayerPositionFragment.newInstance(mLayer.getPosition(), mExcavationItemId);
            } else if (i == 3) {
                return FoundListFragment.newInstance(mLayerName, mLayerId, mExcavationItemId);

            } else
                return ContextureFragment.newInstance(mLayerName, mLayerId);*/
            }
            return new BlankFragment();

        }

        @Override
        public int getCount() {
            return titles.length;
        }
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
    public void updateHeightLevelH(String jsonString) {
        mFeature.setHeightLevelH(jsonString);
        Log.d(getClass().getSimpleName(), "updateHeightLevelH: " + jsonString);
        storeFeature(mFeature);
    }

    @Override
    public void updateHeightLevelL(String jsonString) {
        mFeature.setHeightLevelL(jsonString);
        storeFeature(mFeature);
    }

    @Override
    public void UpdateLayerFeaturePosition(String positionJson) {
        mFeature.setPosition(positionJson);
        storeFeature(mFeature);
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
                            deleteFeature(mFeatureId);
                            finish();
                        })
                        .setNegativeButton("خیر", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create().show();


                return true;

        }
        return false;
    }

    private void deleteFeature(String id) {
        mLayerFeatureDao.deleteByChildId(id);
        mFeatureDao.delete(id);
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString(MyConst.EXTRA_LAYER_ID, id).build();


        OneTimeWorkRequest Work = new OneTimeWorkRequest.Builder(FeatureDeleteWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();
        WorkManager.getInstance().enqueue(Work);

    }

    @Override
    public void onUpdateFeature(String featureContentJson) {

        mFeature.setContentJson(featureContentJson);
        storeFeature(mFeature);
        Log.d(TAG, "onUpdateFeature: ");
    }

    @Override
    public void onUploadAudio(AudioMemo audio) {
        mAudioDao.save(audio);
        uploadAudio(audio, FeatureActivity.this);

    }

    @Override
    public void onUploadPhoto(Photo photo) {
        mPhotoDao.save(photo);
        uploadPhoto(photo, FeatureActivity.this);

    }

    private void storeFeature(Feature feature) {

        mFeatureDao.save(feature);

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();

        Data inputDataLayer = new Data.Builder()
                .putString("id", feature.getId())
                .putString("name", feature.getName())
                .putString("structure", feature.getStructureName())
                .putString("structure_index", String.valueOf(feature.getStructureIndex()))
                .putString("content_json", feature.getContentJson())
                .putString("height_level_h", feature.getHeightLevelH())
                .putString("height_level_l", feature.getHeightLevelL())
                .putString("position", feature.getPosition())
                .build();

        OneTimeWorkRequest featureUploadWork = new OneTimeWorkRequest.Builder(FeatureCreateUpdateWorker.class)
                .setConstraints(constraints).setInputData(inputDataLayer).build();

        WorkManager.getInstance().enqueue(featureUploadWork);


    }

}
