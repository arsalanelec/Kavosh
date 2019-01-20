package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class SurFound implements Parcelable {
    public static final Creator<SurFound> CREATOR = new Creator<SurFound>() {
        @Override
        public SurFound createFromParcel(Parcel in) {
            return new SurFound(in);
        }

        @Override
        public SurFound[] newArray(int size) {
            return new SurFound[size];
        }
    };
    @NonNull
    @PrimaryKey
    private String id;
    private String survey_id;
    private String contentJson;
    private int type;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public SurFound() {
        id = UUID.randomUUID().toString();
    }

    protected SurFound(Parcel in) {
        id = in.readString();
        survey_id = in.readString();
        contentJson = in.readString();
        type = in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
        deletedAt = in.readString();
    }

    @BindingAdapter({"registerNumber"})
    public static void setRegisterNumber(TextView textView, String foundJson) {
        Gson gson = new Gson();
        try {
            FoundDetail foundDetail = gson.fromJson(foundJson, FoundDetail.class);
            textView.setText(foundDetail.getRegisterNum());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"foundName"})
    public static void setFoundName(TextView textView, String foundJson) {
        Gson gson = new Gson();
        try {
            FoundDetail foundDetail = gson.fromJson(foundJson, FoundDetail.class);
            textView.setText(foundDetail.getName());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public String getTypeName() {
        switch (type) {
            case 0:
                return "معماری";
            case 1:
                return "سفال";
            case 2:
                return "ابزار سنگی";
            case 3:
                return "فلز";
            case 4:
                return "سکه";
            case 5:
                return "سرباره کوره";
            case 6:
                return "جوش کوره";

        }
        return "";
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(String survey_id) {
        this.survey_id = survey_id;
    }

    public String getContentJson() {
        return contentJson;
    }

    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(survey_id);
        parcel.writeString(contentJson);
        parcel.writeInt(type);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        parcel.writeString(deletedAt);
    }


}
