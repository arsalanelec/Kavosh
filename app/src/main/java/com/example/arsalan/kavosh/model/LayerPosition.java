package com.example.arsalan.kavosh.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class LayerPosition implements Parcelable {
    public static final Creator<LayerPosition> CREATOR = new Creator<LayerPosition>() {
        @Override
        public LayerPosition createFromParcel(Parcel in) {
            return new LayerPosition(in);
        }

        @Override
        public LayerPosition[] newArray(int size) {
            return new LayerPosition[size];
        }
    };
    private List<String> underLayer;
    private List<String> similarLayer;
    private List<LayerCoordination> besideLayer;
    private List<LayerCoordination> topOfLayer;
    private List<LayerCoordination> cuttingLayers;
    private List<LayerCoordination> cuttedByLayers;
    private List<LayerCoordination> insideLayers;
    private List<LayerCoordination> aroundlayers;

    public LayerPosition() {
        besideLayer = new ArrayList<>();
        topOfLayer = new ArrayList<>();
        cuttingLayers = new ArrayList<>();
        cuttedByLayers = new ArrayList<>();
        insideLayers = new ArrayList<>();
        aroundlayers = new ArrayList<>();
        underLayer = new ArrayList<>();
        similarLayer = new ArrayList<>();
    }

    protected LayerPosition(Parcel in) {
        underLayer = in.createStringArrayList();
        similarLayer = in.createStringArrayList();
        besideLayer = in.createTypedArrayList(LayerCoordination.CREATOR);
        topOfLayer = in.createTypedArrayList(LayerCoordination.CREATOR);
        cuttingLayers = in.createTypedArrayList(LayerCoordination.CREATOR);
        cuttedByLayers = in.createTypedArrayList(LayerCoordination.CREATOR);
        insideLayers = in.createTypedArrayList(LayerCoordination.CREATOR);
        aroundlayers = in.createTypedArrayList(LayerCoordination.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(underLayer);
        dest.writeStringList(similarLayer);
        dest.writeTypedList(besideLayer);
        dest.writeTypedList(topOfLayer);
        dest.writeTypedList(cuttingLayers);
        dest.writeTypedList(cuttedByLayers);
        dest.writeTypedList(insideLayers);
        dest.writeTypedList(aroundlayers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<String> getUnderLayer() {
        return underLayer;
    }

    public void setUnderLayer(List<String> underLayer) {
        this.underLayer = underLayer;
    }

    public List<String> getSimilarLayer() {
        return similarLayer;
    }

    public void setSimilarLayer(List<String> similarLayer) {
        this.similarLayer = similarLayer;
    }

    public List<LayerCoordination> getCuttingLayers() {
        return cuttingLayers;
    }

    public void setCuttingLayers(List<LayerCoordination> cuttingLayers) {
        this.cuttingLayers = cuttingLayers;
    }

    public List<LayerCoordination> getCuttedByLayers() {
        return cuttedByLayers;
    }

    public void setCuttedByLayers(List<LayerCoordination> cuttedByLayers) {
        this.cuttedByLayers = cuttedByLayers;
    }

    public List<LayerCoordination> getInsideLayers() {
        return insideLayers;
    }

    public void setInsideLayers(List<LayerCoordination> insideLayers) {
        this.insideLayers = insideLayers;
    }

    public List<LayerCoordination> getAroundlayers() {
        return aroundlayers;
    }

    public void setAroundlayers(List<LayerCoordination> aroundlayers) {
        this.aroundlayers = aroundlayers;
    }


    public List<LayerCoordination> getBesideLayer() {
        return besideLayer;
    }

    public void setBesideLayer(List<LayerCoordination> besideLayer) {
        this.besideLayer = besideLayer;
    }

    public List<LayerCoordination> getTopOfLayer() {
        return topOfLayer;
    }

    public void setTopOfLayer(List<LayerCoordination> topOfLayer) {
        this.topOfLayer = topOfLayer;
    }
}
