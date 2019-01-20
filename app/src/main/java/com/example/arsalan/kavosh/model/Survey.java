package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

@Entity
public class Survey implements Parcelable {
    public static final Creator<Survey> CREATOR = new Creator<Survey>() {
        @Override
        public Survey createFromParcel(Parcel in) {
            return new Survey(in);
        }

        @Override
        public Survey[] newArray(int size) {
            return new Survey[size];
        }
    };
    private static final String TAG = "Survey";
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String nameAlt;
    private String address;
    private String contentJson;
    private double easting;
    private double northing;
    private double elevation;
    private String dateStart;
    private int type;
    private String registrationNum;
    private String codeName;
    private String surveyProjectId;

    protected Survey(Parcel in) {
        id = in.readString();
        name = in.readString();
        nameAlt = in.readString();
        address = in.readString();
        contentJson = in.readString();
        easting = in.readDouble();
        northing = in.readDouble();
        elevation = in.readDouble();
        dateStart = in.readString();
        type = in.readInt();
        registrationNum = in.readString();
        codeName = in.readString();
        surveyProjectId = in.readString();
    }

    public Survey() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(nameAlt);
        dest.writeString(address);
        dest.writeString(contentJson);
        dest.writeDouble(easting);
        dest.writeDouble(northing);
        dest.writeDouble(elevation);
        dest.writeString(dateStart);
        dest.writeInt(type);
        dest.writeString(registrationNum);
        dest.writeString(codeName);
        dest.writeString(surveyProjectId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public void setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
    }

    public String getSurveyProjectId() {
        return surveyProjectId;
    }

    public void setSurveyProjectId(String surveyProjectId) {
        this.surveyProjectId = surveyProjectId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAlt() {
        return nameAlt;
    }

    public void setNameAlt(String nameAlt) {
        this.nameAlt = nameAlt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getSurveyType() {

        String s = "";
        switch (type) {
            case 0:
                s = "تپه";
                break;
            case 1:
                s = "محوطه";
                break;
            case 2:
                s = "قبرستان";
                break;
            case 3:
                s = "بنا";
                break;
            case 4:
                s = "تپه قبرستان";
                break;
            case 5:
                s = "قلعه";
                break;
            case 6:
                s = "غار";
                break;
            case 7:
                s = "پناهگاه صخره ای";
                break;
            case 8:
                s = "بافت تاریخی فرهنگی";
                break;
            case 9:
                s = "نقش برجسته";
                break;
            case 10:
                s = "نقوش صخره ای";
                break;
            case 11:
                s = "مجموعه باستانی و تاریخی-فرهنگی";
                break;
            case 12:
                s = "استودان";
                break;
        }
        return s;
    }

    //@BindingAdapter({"surveyType"})
    // public static void setSurveyType(TextView textView, int type) {
    public String getSurveyTypeString() {
        String s = "";
        switch (type) {
            case 0:
                s = "تپه";
                break;
            case 1:
                s = "محوطه";
                break;
            case 2:
                s = "قبرستان";
                break;
            case 3:
                s = "بنا";
                break;
            case 4:
                s = "تپه قبرستان";
                break;
            case 5:
                s = "قلعه";
                break;
            case 6:
                s = "غار";
                break;
            case 7:
                s = "پناهگاه صخره ای";
                break;
            case 8:
                s = "بافت تاریخی فرهنگی";
                break;
            case 9:
                s = "نقش برجسته";
                break;
            case 10:
                s = "نقوش صخره ای";
                break;
            case 11:
                s = "مجموعه باستانی و تاریخی-فرهنگی";
                break;
            case 12:
                s = "استودان";
                break;
        }
        return s;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public double getEasting() {
        return easting;
    }

    public void setEasting(double easting) {
        this.easting = easting;
    }

    public double getNorthing() {
        return northing;
    }

    public void setNorthing(double northing) {
        this.northing = northing;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateStartPersian() {
        SimpleDateFormat formatEn = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatEn.parse(dateStart);
            PersianCalendar faDate = new PersianCalendar();
            faDate.setTime(date);
            return faDate.getPersianLongDate();

        } catch (Throwable t) {
            Log.d(TAG, "getDateStartPersian: error:" + t.getLocalizedMessage());
        }
        return "";
    }

}
