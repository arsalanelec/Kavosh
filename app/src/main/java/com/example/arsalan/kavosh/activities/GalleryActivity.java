package com.example.arsalan.kavosh.activities;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Photo;
import com.example.arsalan.kavosh.room.PhotoDao;
import com.example.arsalan.kavosh.wokrmanager.PhotoDeleteWorker;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.legacy.app.FragmentPagerAdapter;
import androidx.legacy.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.example.arsalan.kavosh.model.MyConst.BASE_URL;

public class GalleryActivity extends AppCompatActivity implements Injectable, DeletePhotoListener, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    PhotoDao mPhotoDao;
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("'گالری تصاویر");
        ArrayList<Photo> photos = getIntent().getParcelableArrayListExtra(MyConst.EXTRA_MODEL_List);
        int index = getIntent().getIntExtra(MyConst.EXTRA_INDEX, 0);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), photos);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(index);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                invalidateFragmentMenus(position);
            }


            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    private void invalidateFragmentMenus(int position) {
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            mSectionsPagerAdapter.getItem(i).setHasOptionsMenu(i == position);
        }
        invalidateOptionsMenu(); //or respectively its support method.
    }

    public void onDeletePhoto(String id) {
        Log.d(getClass().getSimpleName(), "onDeletePhoto: id:" + id);
        deletePhoto(id);
    }

    private void deletePhoto(String id) {
        if (mPhotoDao == null) {
            Log.d(getClass().getSimpleName(), "deletePhoto: Null");
            return;
        }
        mPhotoDao.delete(id);
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString(MyConst.EXTRA_ID, id).build();

        OneTimeWorkRequest Work = new OneTimeWorkRequest.Builder(PhotoDeleteWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();
        WorkManager.getInstance().enqueue(Work);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private Photo mPhoto;
        private DeletePhotoListener mListener;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Photo photo) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putParcelable(MyConst.EXTRA_MODEL, photo);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_gallery, container, false);
            ProgressBar pbLoading = v.findViewById(R.id.pbLoading);
            mPhoto = getArguments().getParcelable(MyConst.EXTRA_MODEL);
            TextView title = v.findViewById(R.id.txtTitle);
            title.setText(mPhoto.getTitle());
            PhotoView photoView = v.findViewById(R.id.photo_view);
            SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    photoView.setImageBitmap(resource);
                }
            };

            if (mPhoto.getLocalPath() != null && !mPhoto.getLocalPath().isEmpty()) {
                pbLoading.setVisibility(View.GONE);
                Glide.with(getActivity())
                        .asBitmap()
                        .load(mPhoto.getLocalPath())
                        .into(simpleTarget);
            } else if (mPhoto.getUrl() != null && !mPhoto.getUrl().isEmpty()) {
                pbLoading.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .asBitmap()
                        .addListener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                pbLoading.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "خطایی پیش آمده!\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                                getActivity().finish();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                pbLoading.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                        .thumbnail(0.1f)
                        .load(BASE_URL + mPhoto.getUrl())
                        .into(simpleTarget);
            }

            setHasOptionsMenu(true);

            return v;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.menu_delete, menu);
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_delete:
                    TextView titleTV = new TextView(getContext());
                    titleTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    titleTV.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                    titleTV.setText("حذف");
                    int dip = 8;
                    int px = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            dip,
                            getResources().getDisplayMetrics()
                    );
                    titleTV.setPadding(px, px, px, px);
                    new AlertDialog.Builder(getContext())
                            .setCustomTitle(titleTV)
                            .setMessage("آیا مایلید این مورد حذف شود؟")
                            .setPositiveButton("بلی", (dialogInterface, i) -> {
                                //deleteExcavatoionItem(mExcavationItemId);
                                mListener.onDeletePhoto(mPhoto.getId());
                                getActivity().finish();
                            })
                            .setNegativeButton("خیر", (dialogInterface, i) -> dialogInterface.dismiss())
                            .create().show();

                    return true;

            }
            return false;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof DeletePhotoListener) {
                mListener = (DeletePhotoListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement DeletePhotoListener");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            mListener = null;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {
        ArrayList<Photo> photos;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Photo> photos) {
            super(fm);
            this.photos = photos;
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, photos.get(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return photos.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {


            return photos.get(position).getTitle();

        }
    }
}
