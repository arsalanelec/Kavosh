package com.example.arsalan.kavosh.interfaces;

import android.view.View;

import com.example.arsalan.kavosh.model.Photo;

public interface PhotoItemClickListener {
    void onPhotoClicked(Photo photo);

    void onAddNewPhotoClicked(View view);
}
