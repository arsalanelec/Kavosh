package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/*<item>دیوار</item>
        <item>پی دیوار</item>
        <item>کف</item>
        <item>پله</item>
        <item>اجاق</item>
        <item>آتشدان</item>
        <item>کوره</item>
        <item>چاله تدفین</item>
        <item>توده خاکستر</item>
        <item>توده سنگ</item>
        <item>توده سفال</item>
        <item>نخاله</item>
        <item>آوار</item>
        <item>دیگر</item>*/

@Entity
public class Feature implements Parcelable {
    private int structureIndex;

    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String contentJson;
    private String structureName;
    private String heightLevelH;
    private String heightLevelL;
    private String position;

    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public Feature() {
        id = UUID.randomUUID().toString();
    }

    protected Feature(Parcel in) {
        structureIndex = in.readInt();
        id = in.readString();
        name = in.readString();
        contentJson = in.readString();
        structureName = in.readString();
        heightLevelH = in.readString();
        heightLevelL = in.readString();
        position = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        deletedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(structureIndex);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(contentJson);
        dest.writeString(structureName);
        dest.writeString(heightLevelH);
        dest.writeString(heightLevelL);
        dest.writeString(position);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(deletedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Feature> CREATOR = new Creator<Feature>() {
        @Override
        public Feature createFromParcel(Parcel in) {
            return new Feature(in);
        }

        @Override
        public Feature[] newArray(int size) {
            return new Feature[size];
        }
    };

    public int getStructureIndex() {
        return structureIndex;
    }

    public String getHeightLevelH() {
        return heightLevelH;
    }

    public void setHeightLevelH(String heightLevelH) {
        this.heightLevelH = heightLevelH;
    }

    public String getHeightLevelL() {
        return heightLevelL;
    }

    public void setHeightLevelL(String heightLevelL) {
        this.heightLevelL = heightLevelL;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getContentJson() {
        return contentJson;
    }

    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
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

    public void setStructureIndex(int structureIndex) {
        this.structureIndex = structureIndex;
    }

    public enum MyEnum {
        FEATURE1("دیوار"),
        FEATURE2("پی دیوار"),
        FEATURE3("کف"),
        FEATURE4("پله"),
        FEATURE5("اجاق"),
        FEATURE6("آتشدان"),
        FEATURE7("کوره"),
        FEATURE8("چاله تدفین"),
        FEATURE9("توده خاکستر"),
        FEATURE10("توده سنگ"),
        FEATURE11("توده سفال"),
        FEATURE12("نخاله"),
        FEATURE13("آوار"),
        FEATURE17("گودال آشغال-چاله"),
        FEATURE18("راه آب"),
        FEATURE100("دیگر");

        private String featureName;

        MyEnum(String featureName) {
            this.featureName = featureName;
        }

        @Override
        public String toString() {
            return featureName;
        }
    }
}
