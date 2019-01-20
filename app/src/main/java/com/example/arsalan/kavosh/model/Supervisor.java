package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;


@Entity(
        primaryKeys = {"projectId", "userId"}
/*        foreignKeys = {
                @ForeignKey(entity = Project.class,
                        parentColumns = "id",
                        childColumns = "projectId"),
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = CASCADE)

        }*/)
public class Supervisor implements Parcelable {

    public static final Creator<Supervisor> CREATOR = new Creator<Supervisor>() {
        @Override
        public Supervisor createFromParcel(Parcel in) {
            return new Supervisor(in);
        }

        @Override
        public Supervisor[] newArray(int size) {
            return new Supervisor[size];
        }
    };
    @NonNull
    public final String userId;
    @NonNull
    public final String projectId;
    public String name;
    public int status;
    public int role;
    public String email;
    public String nationalId;
    public String mobile;
    public int eduDegree;
    public Date dateAdded;
    /* "user_id": "328f3748-7077-4939-9899-12a4389abbe5",
            "project_id": "818e1c08-fa6a-41ed-95b2-3bfbc1c392ff",
            "name": "تست۱",
            "status": 1,
            "role": 0,
            "email": "tesr@test.com",
            "national_id": 1111111113,
            "mobile": "09171111112",
            "degree_edu": 0,
            "date_added": null,
            "date_removed": null*/
    public Date dateRemoved;


    public Supervisor(String userId, String projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }

    protected Supervisor(Parcel in) {
        userId = in.readString();
        projectId = in.readString();
        name = in.readString();
        status = in.readInt();
        role = in.readInt();
        email = in.readString();
        nationalId = in.readString();
        mobile = in.readString();
        eduDegree = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(projectId);
        dest.writeString(name);
        dest.writeInt(status);
        dest.writeInt(role);
        dest.writeString(email);
        dest.writeString(nationalId);
        dest.writeString(mobile);
        dest.writeInt(eduDegree);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getEduDegreeTitle() {
        switch (eduDegree) {
            case 0:
                return "دیپلم";
            case 1:
                return "فوق دیپلم";
            case 2:
                return "لیسانس";
            case 3:
                return "فوق لیسانس";
            case 4:
                return "دکترا";

        }
        return "";
    }

    public String getStatusString() {
        if (status == 1) {
            return "فعال";
        }
        return "غیرفعال";
    }

    public String getDateAddedPers() {
        try {
            return new PersianCalendar(dateAdded.getTime()).getPersianLongDate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "نا مشخص";
    }

    public String getDateRemovedPers() {
        try {
            return new PersianCalendar(dateRemoved.getTime()).getPersianLongDate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "نا مشخص";
    }

    public String getUserId() {
        return userId;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getEduDegree() {
        return eduDegree;
    }

    public void setEduDegree(int eduDegree) {
        this.eduDegree = eduDegree;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateRemoved() {
        return dateRemoved;
    }

    public void setDateRemoved(Date dateRemoved) {
        this.dateRemoved = dateRemoved;
    }

}
