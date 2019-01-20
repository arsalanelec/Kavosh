package com.example.arsalan.kavosh.viewModel;

import android.util.Log;

import com.example.arsalan.kavosh.BR;
import com.example.arsalan.kavosh.model.FloorFeature;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


public class FloorViewModel extends BaseObservable {
    private static final String TAG = "FloorViewModel";
    FloorFeature floor;

    public FloorViewModel(FloorFeature floor) {
        this.floor = floor;
    }

    public FloorFeature getFloor() {
        return floor;
    }

    @Bindable
    public int getSelectedFloorType() {
        Log.d(TAG, "getSelectedFloorType: ");
        return floor.getFloorType();
    }

    public void setSelectedFloorType(int index) {
        Log.d(TAG, "setSelectedFloorType: ");
        if (floor.getFloorType() != index) {
            floor.setFloorType(index);
            notifyPropertyChanged(BR.floor);
        }
    }

    @Bindable
    public int getFloorContext() {

        return floor.getContexture();
    }

    public void setFloorContext(int position) {
        if (position != floor.getFloorStruct()) {
            floor.setContexture(position);
            notifyPropertyChanged(BR.floor);
        }
    }

    @Bindable
    public int getFloorStructure() {

        return floor.getFloorStruct();
    }

    public void setFloorStructure(int position) {
        if (position != floor.getFloorStruct()) {
            floor.setFloorStruct(position);
            notifyPropertyChanged(BR.floor);
        }
    }

    @Bindable
    public String getSoilColor() {
        return floor.getFloorColor();
    }

    public void setSoilColor(String color) {
        if (!color.equals(floor.getFloorColor())) {
            floor.setFloorColor(color);
            notifyPropertyChanged(BR.floor);
        }
    }


    @Bindable
    public String getSurface() {
        return floor.getSurface();
    }

    public void setSurface(String value) {
        if (!value.equals(floor.getSurface())) {

            floor.setSurface(value);
            notifyPropertyChanged(BR.floor);
        }
    }

    @Bindable
    public String getDimX() {

        return String.valueOf(floor.getDimX());
    }

    public void setDimX(String value) {
        if (Double.valueOf(value) != floor.getDimX()) {
            try {
                floor.setDimX(Double.parseDouble(value));
                notifyPropertyChanged(BR.floor);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Bindable
    public String getDimY() {

        return String.valueOf(floor.getDimY());
    }

    public void setDimY(String value) {
        if (Double.valueOf(value) != floor.getDimY()) {
            try {
                floor.setDimY(Double.parseDouble(value));
                notifyPropertyChanged(BR.floor);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Bindable
    public String getDimZ() {

        return String.valueOf(floor.getDimZ());
    }

    public void setDimZ(String value) {
        if (Double.valueOf(value) != floor.getDimZ()) {
            try {
                floor.setDimZ(Double.parseDouble(value));
                notifyPropertyChanged(BR.floor);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Bindable
    public String getDepth() {

        return String.valueOf(floor.getDepth());
    }

    public void setDepth(String value) {
        if (Double.valueOf(value) != floor.getDepth()) {
            try {
                floor.setDepth(Double.parseDouble(value));
                notifyPropertyChanged(BR.floor);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Bindable
    public String getDescription() {

        return floor.getDescription();
    }

    public void setDescription(String value) {
        if (!value.equals(floor.getDescription())) {

            floor.setDescription(value);
            notifyPropertyChanged(BR.floor);
        }
    }


    @Bindable
    public String getFloorComposition() {
        return floor.getCompositionJson();
    }

    public void setFloorComposition(String jsonStr) {
        if (!jsonStr.equals(floor.getCompositionJson())) {
            floor.setCompositionJson(jsonStr);
            notifyPropertyChanged(BR.floor);
        }
    }


}
