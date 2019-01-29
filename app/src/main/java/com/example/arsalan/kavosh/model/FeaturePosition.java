package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class FeaturePosition extends BaseObservable implements Parcelable {
    private String tools;
    private int contextureType;
    private String depth;
    private String disNorth;
    private String disSouth;
    private String disEas;
    private String disWest;
    private String description;

    public FeaturePosition() {
    }

    protected FeaturePosition(Parcel in) {
        tools = in.readString();
        contextureType = in.readInt();
        depth = in.readString();
        disNorth = in.readString();
        disSouth = in.readString();
        disEas = in.readString();
        disWest = in.readString();
        description = in.readString();
    }

    public static final Creator<FeaturePosition> CREATOR = new Creator<FeaturePosition>() {
        @Override
        public FeaturePosition createFromParcel(Parcel in) {
            return new FeaturePosition(in);
        }

        @Override
        public FeaturePosition[] newArray(int size) {
            return new FeaturePosition[size];
        }
    };

    @Bindable
    public int getContextureType() {
        return contextureType;
    }

    public void setContextureType(int contextureType) {
        this.contextureType = contextureType;
    }

    @Bindable
    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        if (this.tools == null || !this.tools.equals(tools)) {
            this.tools = tools;
            notifyChange();
        }
    }

    @Bindable

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        if (this.depth == null || !this.depth.equals(depth)) {
            this.depth = depth;
            notifyChange();
        }
    }

    @Bindable

    public String getDisNorth() {
        return disNorth;
    }

    public void setDisNorth(String disNorth) {
        if (this.disNorth == null || !this.disNorth.equals(disNorth)) {
            this.disNorth = disNorth;
            notifyChange();
        }
    }

    @Bindable

    public String getDisSouth() {
        return disSouth;
    }

    public void setDisSouth(String disSouth) {
        if (this.disSouth == null || !this.disSouth.equals(disSouth)) {
            this.disSouth = disSouth;
            notifyChange();
        }
    }

    @Bindable

    public String getDisEas() {
        return disEas;
    }

    public void setDisEas(String disEas) {
        if (this.disEas == null || !this.disEas.equals(disEas)) {
            this.disEas = disEas;
            notifyChange();
        }
    }

    @Bindable

    public String getDisWest() {
        return disWest;
    }

    public void setDisWest(String disWest) {
        if (this.disWest == null || !this.disWest.equals(disWest)) {
            this.disWest = disWest;
            notifyChange();
        }
    }

    @Bindable

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (this.description == null || !this.description.equals(description)) {
            this.description = description;
            notifyChange();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tools);
        dest.writeInt(contextureType);
        dest.writeString(depth);
        dest.writeString(disNorth);
        dest.writeString(disSouth);
        dest.writeString(disEas);
        dest.writeString(disWest);
        dest.writeString(description);
    }
}
