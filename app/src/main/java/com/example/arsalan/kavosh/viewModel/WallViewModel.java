package com.example.arsalan.kavosh.viewModel;

import android.util.Log;

import com.example.arsalan.kavosh.BR;
import com.example.arsalan.kavosh.model.WallFeature;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


public class WallViewModel extends BaseObservable {
    private static final String TAG = "WallViewModel";
    WallFeature wall;

    public WallViewModel(WallFeature wall) {
        this.wall = wall;
    }

    public WallFeature getWall() {
        return wall;
    }

    @Bindable
    public int getSelectedWallType() {
        Log.d(TAG, "getSelectedWallType: ");
        return wall.getWallType();
    }

    public void setSelectedWallType(int index) {
        Log.d(TAG, "setSelectedWallType: ");
        if (wall.getWallType() != index) {
            wall.setWallType(index);
            notifyPropertyChanged(BR.wall);
        }
    }

    @Bindable
    public int getWallContext() {

        return wall.getContexture();
    }

    public void setWallContext(int position) {
        if (position != wall.getWallStruct()) {
            wall.setContexture(position);
            notifyPropertyChanged(BR.wall);
        }
    }

    @Bindable
    public int getWallStructure() {

        return wall.getWallStruct();
    }

    public void setWallStructure(int position) {
        if (position != wall.getWallStruct()) {
            wall.setWallStruct(position);
            notifyPropertyChanged(BR.wall);
        }
    }

    @Bindable
    public String getSoilColor() {
        return wall.getWallColor();
    }

    public void setSoilColor(String color) {
        if (color != wall.getWallColor()) {
            wall.setWallColor(color);
            notifyPropertyChanged(BR.wall);
        }
    }

    @Bindable
    public String getMortarColor() {
        return wall.getMortarColor();
    }

    public void setMortarColor(String color) {
        if (color != wall.getWallColor()) {
            wall.setMortarColor(color);
            notifyPropertyChanged(BR.wall);
        }
    }


    @Bindable
    public String getMortarThickness() {

        return String.valueOf(wall.getMortarThickness());
    }

    public void setMortarThickness(String value) {
        if (!value.isEmpty() && Double.valueOf(value) != wall.getMortarThickness()) {
            try {
                wall.setMortarThickness(Double.parseDouble(value));
                notifyPropertyChanged(BR.wall);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Bindable
    public String getMortarName() {

        return wall.getMortarName();
    }

    public void setMortarName(String value) {
        if (!value.equals(wall.getMortarName())) {

            wall.setMortarName(value);
            notifyPropertyChanged(BR.wall);
        }
    }


    @Bindable
    public String getCoatingName() {

        return wall.getCoatingName();
    }

    public void setCoatingName(String value) {
        if (!value.equals(wall.getCoatingName())) {

            wall.setCoatingName(value);
            notifyPropertyChanged(BR.wall);
        }
    }

    @Bindable
    public String getCoatingColor() {
        return wall.getCoatingColor();
    }

    public void setCoatingColor(String color) {
        if (color != wall.getCoatingColor()) {
            wall.setCoatingColor(color);
            notifyPropertyChanged(BR.wall);
        }
    }

    @Bindable
    public String getCoatingThickness() {

        return String.valueOf(wall.getCoatingThickness());
    }

    public void setCoatingThickness(String value) {
        if (!value.isEmpty() && Double.valueOf(value) != wall.getCoatingThickness()) {
            try {
                wall.setCoatingThickness(Double.parseDouble(value));
                notifyPropertyChanged(BR.wall);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Bindable
    public String getDimX() {

        return String.valueOf(wall.getDimX());
    }

    public void setDimX(String value) {
        if (!value.isEmpty() && Double.valueOf(value) != wall.getDimX()) {
            try {
                wall.setDimX(Double.parseDouble(value));
                notifyPropertyChanged(BR.wall);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Bindable
    public String getDimY() {

        return String.valueOf(wall.getDimY());
    }

    public void setDimY(String value) {
        if (!value.isEmpty() && Double.valueOf(value) != wall.getDimY()) {
            try {
                wall.setDimY(Double.parseDouble(value));
                notifyPropertyChanged(BR.wall);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Bindable
    public String getDimZ() {

        return String.valueOf(wall.getDimZ());
    }

    public void setDimZ(String value) {
        if (!value.isEmpty() && Double.valueOf(value) != wall.getDimZ()) {
            try {
                wall.setDimZ(Double.parseDouble(value));
                notifyPropertyChanged(BR.wall);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Bindable
    public String getDepth() {

        return String.valueOf(wall.getDepth());
    }

    public void setDepth(String value) {
        if (!value.isEmpty() && Double.valueOf(value) != wall.getDepth()) {
            try {
                wall.setDepth(Double.parseDouble(value));
                notifyPropertyChanged(BR.wall);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Bindable
    public String getDescription() {

        return wall.getDescription();
    }

    public void setDescription(String value) {
        if (!value.equals(wall.getDescription())) {

            wall.setDescription(value);
            notifyPropertyChanged(BR.wall);
        }
    }


    @Bindable
    public String getWallComposition() {
        return wall.getCompositionJson();
    }

    public void setWallComposition(String jsonStr) {
        if (!jsonStr.equals(wall.getCompositionJson())) {
            wall.setCompositionJson(jsonStr);
            notifyPropertyChanged(BR.wall);
        }
    }


}
