package com.example.arsalan.kavosh.model;

import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class SurHillExtraDetail extends BaseObservable {
    private String nsLenght;
    private String ewLenght;
    private String height;
    private String shape;
    private List<Hillside> hillsideList;

    public SurHillExtraDetail() {
    }

    @Bindable
    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        if (this.shape == null || this.shape != shape) {
            this.shape = shape;
            notifyChange();
        }
    }

    public List<Hillside> getHillsideList() {
        return hillsideList;
    }

    public void setHillsideList(List<Hillside> hillsideList) {
        this.hillsideList = hillsideList;
    }


    @Bindable
    public String getNsLenght() {
        return nsLenght;
    }

    public void setNsLenght(String nsLenght) {
        if (this.nsLenght == null || !this.nsLenght.equals(nsLenght)) {
            this.nsLenght = nsLenght;
            notifyChange();
        }
    }

    @Bindable
    public String getEwLenght() {
        return ewLenght;
    }

    public void setEwLenght(String ewLenght) {

        if (this.ewLenght == null || !this.ewLenght.equals(ewLenght)) {
            this.ewLenght = ewLenght;
            notifyChange();
        }
    }

    @Bindable
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        if (this.height == null || !this.height.equals(height)) {
            this.height = height;
            notifyChange();
        }
    }


}
