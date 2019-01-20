package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;

import java.io.File;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.example.arsalan.kavosh.model.MyConst.BASE_URL;

/*"id": "3b6a3b49-c8ea-428f-99b8-105a7fb1b38d",
        "url": "/uploads/products//screenshot.jpg",
        "thumb_url": "/uploads/products//thumb_screenshot.jpg",
        "photoable_id": "3f475da7-a8f3-4527-8a93-5c2e40c5d2d4",
        "photoable_type": "App\\Contexture",
        "title": null,
        "created_at": "2018-09-15 18:37:22",
        "updated_at": "2018-09-15 18:37:22",
        "deleted_at": null*/
@Entity
public class Photo implements Parcelable {

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
    @NonNull
    @PrimaryKey
    private String id;
    private String url;
    private String thumbUrl;
    private String photoableId;
    private String photoableType;
    private String title;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    @Expose(serialize = false, deserialize = false)
    private String localPath;
    @Expose(serialize = false, deserialize = false)
    private String localThumbPath;


    public Photo() {
        this.id = UUID.randomUUID().toString();
    }

    protected Photo(Parcel in) {
        id = in.readString();
        url = in.readString();
        thumbUrl = in.readString();
        photoableId = in.readString();
        photoableType = in.readString();
        title = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        deletedAt = in.readString();
        localPath = in.readString();
        localThumbPath = in.readString();
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(BASE_URL + imageUrl)
                // .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

    @BindingAdapter({"thumbnailUrl"})
    public static void loadThumbImage(ImageView view, Photo photo) {
        if (photo != null)
            if (photo.getLocalPath() != null && !photo.getLocalPath().isEmpty()) {
                Glide.with(view.getContext())
                        .load(new File(photo.getLocalPath())) // Uri of the picture
                        .apply(RequestOptions.centerCropTransform())
                        .into(view);
            } else {
                Glide.with(view.getContext())
                        .load(BASE_URL + photo.getThumbUrl())
                        .apply(RequestOptions.centerCropTransform())
                        .into(view);
            }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(url);
        dest.writeString(thumbUrl);
        dest.writeString(photoableId);
        dest.writeString(photoableType);
        dest.writeString(title);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(deletedAt);
        dest.writeString(localPath);
        dest.writeString(localThumbPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPhotoableType() {
        return photoableType;
    }

    public void setPhotoableType(String photoableType) {
        this.photoableType = photoableType;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getLocalThumbPath() {
        return localThumbPath;
    }

    public void setLocalThumbPath(String localThumbPath) {
        this.localThumbPath = localThumbPath;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getPhotoableId() {
        return photoableId;
    }

    public void setPhotoableId(String photoableId) {
        this.photoableId = photoableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

}
