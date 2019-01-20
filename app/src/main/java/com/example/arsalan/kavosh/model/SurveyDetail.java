package com.example.arsalan.kavosh.model;

import com.example.arsalan.kavosh.BR;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class SurveyDetail extends BaseObservable {
    private double posX;
    private double posY;
    private double posZ;
    private List<GeoFeature> geoFeatureList;
    private String accessibleRoadName;
    private int accessibleRoadType;
    private int accessibleRoadCoordination;
    private int accessibleRoadDistance;
    private String description;

    private String waterResourceName;
    private String waterResourceDescription;
    private int waterResourceCoordination;
    private int waterResourceDistance;
    private List<AroundFeature> aroundFeatureList;
    private List<Vegetation> vegetationList;

    private String extraDetailJson;
    private String destructionDetailJson;

    public SurveyDetail() {
        geoFeatureList = new ArrayList<>();
        vegetationList = new ArrayList<>();
        aroundFeatureList = new ArrayList<>();
    }

    public String getDestructionDetailJson() {
        return destructionDetailJson;
    }

    public void setDestructionDetailJson(String destructionDetailJson) {
        this.destructionDetailJson = destructionDetailJson;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public List<GeoFeature> getGeoFeatureList() {
        return geoFeatureList;
    }

    public void setGeoFeatureList(List<GeoFeature> geoFeatureList) {
        this.geoFeatureList = geoFeatureList;
        notifyPropertyChanged(BR.surveyDetail);
    }

    public List<AroundFeature> getAroundFeatureList() {
        return aroundFeatureList;
    }

    public void setAroundFeatureList(List<AroundFeature> aroundFeatureList) {
        this.aroundFeatureList = aroundFeatureList;
        notifyPropertyChanged(BR.surveyDetail);
    }

    @Bindable
    public String getAccessibleRoadName() {
        return accessibleRoadName;
    }

    public void setAccessibleRoadName(String accessibleRoadName) {
        if (this.accessibleRoadName == null || !this.accessibleRoadName.equals(accessibleRoadName)) {
            this.accessibleRoadName = accessibleRoadName;
            notifyPropertyChanged(BR.surveyDetail);
        }
    }

    @Bindable
    public int getAccessibleRoadType() {
        return accessibleRoadType;
    }

    public void setAccessibleRoadType(int accessibleRoadType) {
        if (this.accessibleRoadType != accessibleRoadType) {
            this.accessibleRoadType = accessibleRoadType;
            notifyPropertyChanged(BR.surveyDetail);
        }
    }

    @Bindable
    public String getAccessibleRoadDistanceString() {
        return String.valueOf(accessibleRoadDistance);
    }

    public void setAccessibleRoadDistanceString(String value) {
        int intValue = Integer.valueOf(value);
        if (intValue != accessibleRoadDistance) {
            this.accessibleRoadDistance = intValue;
            notifyPropertyChanged(BR.surveyDetail);
        }
    }

    @Bindable
    public int getAccessibleRoadCoordination() {
        return accessibleRoadCoordination;
    }

    public void setAccessibleRoadCoordination(int accessibleRoadCoordination) {
        if (this.accessibleRoadCoordination != accessibleRoadCoordination) {
            this.accessibleRoadCoordination = accessibleRoadCoordination;
            notifyPropertyChanged(BR.surveyDetail);
        }
    }

    public int getAccessibleRoadDistance() {
        return accessibleRoadDistance;
    }

    public void setAccessibleRoadDistance(int accessibleRoadDistance) {
        this.accessibleRoadDistance = accessibleRoadDistance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Bindable
    public String getWaterResourceName() {
        return waterResourceName;
    }

    public void setWaterResourceName(String waterResourceName) {
        if (this.waterResourceName == null || this.waterResourceName.equals(waterResourceName)) {
            this.waterResourceName = waterResourceName;
            notifyPropertyChanged(BR.surveyDetail);
        }
    }

    @Bindable
    public String getWaterResourceDescription() {
        return waterResourceDescription;
    }

    public void setWaterResourceDescription(String waterResourceDescription) {
        if (this.waterResourceDescription == null || !this.waterResourceDescription.equals(waterResourceDescription)) {
            this.waterResourceDescription = waterResourceDescription;
            notifyPropertyChanged(BR.surveyDetail);
        }
    }

    @Bindable
    public int getWaterResourceCoordination() {
        return waterResourceCoordination;
    }

    public void setWaterResourceCoordination(int waterResourceCoordination) {
        if (this.waterResourceCoordination != waterResourceCoordination) {
            this.waterResourceCoordination = waterResourceCoordination;
            notifyPropertyChanged(BR.surveyDetail);
        }

    }


    public int getWaterResourceDistance() {
        return waterResourceDistance;
    }

    public void setWaterResourceDistance(int waterResourceDistance) {
        this.waterResourceDistance = waterResourceDistance;
    }

    @Bindable
    public String getWaterResourceDistanceString() {
        return String.valueOf(waterResourceDistance);
    }

    public void setWaterResourceDistanceString(String value) {
        int intValue = Integer.valueOf(value);
        if (intValue != waterResourceDistance) {
            this.waterResourceDistance = intValue;
            notifyPropertyChanged(BR.surveyDetail);
        }
    }

    public List<Vegetation> getVegetationList() {
        return vegetationList;
    }

    public void setVegetationList(List<Vegetation> vegetationList) {
        this.vegetationList = vegetationList;
        notifyPropertyChanged(BR.surveyDetail);
    }

    public String getExtraDetailJson() {
        return extraDetailJson;
    }

    public void setExtraDetailJson(String extraDetailJson) {
        this.extraDetailJson = extraDetailJson;
    }

    /* public class GeogFeature {
        String id;
        String name;
        int coordination;
        int type;

        public GeogFeature() {
            this.id = UUID.randomUUID().toString();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCoordination() {
            return coordination;
        }

        public void setCoordination(int coordination) {
            this.coordination = coordination;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }*/


}
