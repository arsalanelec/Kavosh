package com.example.arsalan.kavosh.model;

import java.util.Date;

public class ExcavationProject {
    /*'id','name', 'type','license_num','license_date','license_duration','easting','northing','elevation','el_ref_id','status'*/
    private String id;
    private String name;
    private String licenseNum;
    private String licenseDuration;
    private Date licenseDate;
    private double easting;
    private double northing;
    private double elevation;
    private int elRefId;
    private int status;

    public ExcavationProject() {
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

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    public double getEasting() {
        return easting;
    }

    public void setEasting(double easting) {
        this.easting = easting;
    }

    public double getNorthing() {
        return northing;
    }

    public void setNorthing(double northing) {
        this.northing = northing;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public int getElRefId() {
        return elRefId;
    }

    public void setElRefId(int elRefId) {
        this.elRefId = elRefId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLicenseDuration() {
        return licenseDuration;
    }

    public void setLicenseDuration(String licenseDuration) {
        this.licenseDuration = licenseDuration;
    }
}
