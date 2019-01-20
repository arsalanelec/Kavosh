package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

@Entity
public class SurveyProject implements Parcelable {
    public static final Creator<SurveyProject> CREATOR = new Creator<SurveyProject>() {
        @Override
        public SurveyProject createFromParcel(Parcel in) {
            return new SurveyProject(in);
        }

        @Override
        public SurveyProject[] newArray(int size) {
            return new SurveyProject[size];
        }
    };
    /*    ['id', 'name', 'type', 'license_num', 'licenseStart', 'license_end', 'el_ref_id', 'status'];
     */
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private Date licenseStart;
    private String address;
    private Date licenseEnd;
    private int type;
    private String codeName;
    private String codeStartNum;
    private String licenseNum;
    private String registrationNo;

    protected SurveyProject(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        type = in.readInt();
        codeName = in.readString();
        codeStartNum = in.readString();
        licenseNum = in.readString();
        registrationNo = in.readString();
    }

    public SurveyProject() {
        this.id = UUID.randomUUID().toString();
    }

    @BindingAdapter({"surveyType"})
    public static void setSurveyType(TextView textView, int type) {

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
        textView.setText(s);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeInt(type);
        dest.writeString(codeName);
        dest.writeString(codeStartNum);
        dest.writeString(licenseNum);
        dest.writeString(registrationNo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCodeStartNum() {
        return codeStartNum;
    }

    public void setCodeStartNum(String codeStartNum) {
        this.codeStartNum = codeStartNum;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
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

    public Date getLicenseStart() {
        return licenseStart;
    }

    public void setLicenseStart(Date licenseStart) {
        this.licenseStart = licenseStart;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }


    public Date getLicenseEnd() {
        return licenseEnd;
    }

    public void setLicenseEnd(Date licenseEnd) {
        this.licenseEnd = licenseEnd;
    }

    public String getDateStartPersian() {
        SimpleDateFormat formatEn = new SimpleDateFormat("yyyy-MM-dd");
        try {

            PersianCalendar faDate = new PersianCalendar(licenseStart.getTime());
            return faDate.getPersianLongDate();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "نا مشخص";
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }
}
