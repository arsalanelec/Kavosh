package com.example.arsalan.kavosh.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
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
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Photo;
import com.github.chrisbanes.photoview.PhotoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import static com.example.arsalan.kavosh.model.MyConst.BASE_URL;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ShowPictureActivity extends Activity {


    private Photo mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);
        //hide();
        ProgressBar pbLoading = findViewById(R.id.pbLoading);

        mPhoto = getIntent().getParcelableExtra(MyConst.EXTRA_MODEL);
        TextView title = findViewById(R.id.txtTitle);
        title.setText(mPhoto.getTitle());
        PhotoView photoView = findViewById(R.id.photo_view);
        SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                photoView.setImageBitmap(resource);
            }
        };

        if (mPhoto.getLocalPath() != null && !mPhoto.getLocalPath().isEmpty()) {
            pbLoading.setVisibility(View.GONE);
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(mPhoto.getLocalPath())
                    .into(simpleTarget);
        } else if (mPhoto.getUrl() != null && !mPhoto.getUrl().isEmpty()) {
            pbLoading.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .addListener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            pbLoading.setVisibility(View.GONE);
                            Toast.makeText(ShowPictureActivity.this, "خطایی پیش آمده!\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                            finish();
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

    }

    /*@SuppressLint("InlinedApi")
    private void hide() {
        // Hide UI first
        ActionBar actionBar = getActionBar();
        *//*if (actionBar != null) {
            actionBar.hide();
        }*//*
        View mContentView = findViewById(R.id.fullscreen_content);

       *//* mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
     *//**//* | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION*//**//*);*//*


    }*/

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
                            //deleteExcavatoionItem(mExcavationItemId);
                            finish();
                        })
                        .setNegativeButton("خیر", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create().show();


                return true;

        }
        return false;
    }
}
