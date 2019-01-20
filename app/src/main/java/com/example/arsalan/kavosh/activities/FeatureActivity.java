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
import com.example.arsalan.kavosh.fragment.FloorFragment;
import com.example.arsalan.kavosh.fragment.WallFragment;
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
import com.example.arsalan.kavosh.wokrmanager.FeatureDeleteWorker;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.example.arsalan.kavosh.utils.AudioUpload.uploadAudio;
import static com.example.arsalan.kavosh.utils.PhotoUpload.uploadPhoto;

public class FeatureActivity extends AppCompatActivity implements Injectable, HasSupportFragmentInjector
        , WallFragment.OnFragmentInteractionListener
        , FloorFragment.OnFragmentInteractionListener {

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

    public FeatureActivity() {
        mContext = this;
    }

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
                    /*   if (mFeature.getStructure_index() == 1) {
                    WallFragment wallFragment = WallFragment.newInstance(mFeature);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, wallFragment)
                            .commit();
                      }*/
                    FloorFragment fragment = FloorFragment.newInstance(mFeature);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, fragment)
                            .commit();
                    viewModel.getFeature().removeObserver(this);
                }
            }
        });


    }


    public String getNewRegistrationCode() {
        return increaseCode(mRegistrationCoding, mFoundDao.getNumberOfRows(mExcavationItemId));
    }

/*

    private void updateFeature(Feature feature) {
        mFeatureDao.save(feature);
        Data inputDataFeature = new Data.Builder()
                .putString("token", mToken.getAccessToken())
                .putString("id", feature.getId())
                .putString("name", feature.getName())
                .putString("height_level_h", feature.getHeightLevelH())
                .putString("height_level_l", feature.getHeightLevelL())
                .putString("position", feature.getPosition())
                .build();
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        OneTimeWorkRequest FeatureUploadWork = new OneTimeWorkRequest.Builder(FeatureUploadWorker.class)
                .setConstraints(constraints).setInputData(inputDataFeature).build();
        WorkManager.getInstance().enqueue(FeatureUploadWork);
        WorkManager.getInstance().getWorkInfoByIdLiveData(FeatureUploadWork.getId())
                .observe(FeatureActivity.this, workStatus ->
                        Log.d(getClass().getSimpleName(), "updateFeature: status:" + workStatus)
                );
    }
*/

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
    public void onUpdateFeature(Feature feature) {
        mFeatureDao.save(feature);
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
}
