package com.example.arsalan.kavosh.model;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class SurHillDestructionDetail extends BaseObservable {
    private String nsLenght;
    private String ewLenght;
    private String height;
    private String shape;
    private List<Hillside> hillsideList;
    private boolean[] intactCoordinations = new boolean[10];
    private List<Destruction> destructionList = new ArrayList<>();

    public SurHillDestructionDetail() {
    }

    @Bindable
    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        if (this.shape == null || !this.shape.equals(shape)) {
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

    public boolean[] getIntactCoordinations() {
        return intactCoordinations;
    }

    public void setIntactCoordinations(boolean[] intactCoordinations) {
        this.intactCoordinations = intactCoordinations;
    }

    public List<Destruction> getDestructionList() {
        return destructionList;
    }

    public void setDestructionList(List<Destruction> destructionList) {
        this.destructionList = destructionList;
    }

    @Bindable
    public boolean getIntactN() {
        return intactCoordinations[0];
    }


    public void setIntactN(boolean b) {
        if (this.intactCoordinations[0] != b) {
            this.intactCoordinations[0] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getIntactS() {
        return intactCoordinations[1];
    }


    public void setIntactS(boolean b) {
        if (this.intactCoordinations[1] != b) {
            this.intactCoordinations[1] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getIntactNW() {
        return intactCoordinations[2];
    }


    public void setIntactNW(boolean b) {
        if (this.intactCoordinations[2] != b) {
            this.intactCoordinations[2] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getIntactNE() {
        return intactCoordinations[3];
    }

    public void setIntactNE(boolean b) {
        if (this.intactCoordinations[3] != b) {
            this.intactCoordinations[3] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getIntactSW() {
        return intactCoordinations[4];
    }

    public void setIntactSW(boolean b) {
        if (this.intactCoordinations[4] != b) {
            this.intactCoordinations[4] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getIntactSE() {
        return intactCoordinations[5];
    }

    public void setIntactSE(boolean b) {
        if (this.intactCoordinations[5] != b) {
            this.intactCoordinations[5] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getIntactW() {
        return intactCoordinations[6];
    }

    public void setIntactW(boolean b) {
        if (this.intactCoordinations[6] != b) {
            this.intactCoordinations[6] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getIntactE() {
        return intactCoordinations[7];
    }

    public void setIntactE(boolean b) {
        if (this.intactCoordinations[7] != b) {
            this.intactCoordinations[7] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getIntactAll() {
        return intactCoordinations[8];
    }

    public void setIntactAll(boolean b) {
        if (this.intactCoordinations[8] != b) {
            this.intactCoordinations[8] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getIntactCenter() {
        return intactCoordinations[9];
    }

    public void setIntactCenter(boolean b) {
        if (this.intactCoordinations[9] != b) {
            this.intactCoordinations[9] = b;
            notifyChange();
        }
    }


}
