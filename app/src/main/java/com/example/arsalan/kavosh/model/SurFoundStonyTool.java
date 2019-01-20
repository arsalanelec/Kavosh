package com.example.arsalan.kavosh.model;

import com.example.arsalan.kavosh.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class SurFoundStonyTool extends BaseObservable {
    String id;
    String condition;
    String SampleQuantity;
    boolean[] coordinationDispersion = new boolean[10];
    boolean[] coordinationDensity = new boolean[10];
    String age;
    String quantity;
    String material;
    int type;

    public SurFoundStonyTool() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        if (this.condition == null || !this.condition.equals(condition)) {
            this.condition = condition;
            notifyPropertyChanged(BR.condition);
        }
    }

    @Bindable
    public String getSampleQuantity() {
        return SampleQuantity;
    }

    public void setSampleQuantity(String sampleQuantity) {
        if (this.SampleQuantity == null || !this.SampleQuantity.equals(sampleQuantity)) {
            this.SampleQuantity = sampleQuantity;
            notifyPropertyChanged(BR.material);
        }
    }

    public boolean[] getCoordinationDispersion() {
        return coordinationDispersion;
    }

    public void setCoordinationDispersion(boolean[] coordinationDispersion) {
        this.coordinationDispersion = coordinationDispersion;
    }

    public boolean[] getCoordinationDensity() {
        return coordinationDensity;
    }

    public void setCoordinationDensity(boolean[] coordinationDensity) {
        this.coordinationDensity = coordinationDensity;
    }

    @Bindable
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        if (this.age == null || !this.age.equals(age)) {
            this.age = age;
            notifyPropertyChanged(BR.age);
        }
    }

    @Bindable
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        if (this.quantity == null || !this.quantity.equals(quantity)) {
            this.quantity = quantity;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDispersionN() {
        return coordinationDispersion[0];
    }


    public void setDispersionN(boolean b) {
        if (this.coordinationDispersion[0] != b) {
            this.coordinationDispersion[0] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDispersionS() {
        return coordinationDispersion[1];
    }


    public void setDispersionS(boolean b) {
        if (this.coordinationDispersion[1] != b) {
            this.coordinationDispersion[1] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDispersionNW() {
        return coordinationDispersion[2];
    }


    public void setDispersionNW(boolean b) {
        if (this.coordinationDispersion[2] != b) {
            this.coordinationDispersion[2] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDispersionNE() {
        return coordinationDispersion[3];
    }

    public void setDispersionNE(boolean b) {
        if (this.coordinationDispersion[3] != b) {
            this.coordinationDispersion[3] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDispersionSW() {
        return coordinationDispersion[4];
    }

    public void setDispersionSW(boolean b) {
        if (this.coordinationDispersion[4] != b) {
            this.coordinationDispersion[4] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDispersionSE() {
        return coordinationDispersion[4];
    }

    public void setDispersionSE(boolean b) {
        if (this.coordinationDispersion[5] != b) {
            this.coordinationDispersion[5] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDispersionW() {
        return coordinationDispersion[6];
    }

    public void setDispersionW(boolean b) {
        if (this.coordinationDispersion[6] != b) {
            this.coordinationDispersion[6] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDispersionE() {
        return coordinationDispersion[7];
    }

    public void setDispersionE(boolean b) {
        if (this.coordinationDispersion[7] != b) {
            this.coordinationDispersion[7] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDispersionAll() {
        return coordinationDispersion[8];
    }

    public void setDispersionAll(boolean b) {
        if (this.coordinationDispersion[8] != b) {
            this.coordinationDispersion[8] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDispersionCenter() {
        return coordinationDispersion[9];
    }

    public void setDispersionCenter(boolean b) {
        if (this.coordinationDispersion[9] != b) {
            this.coordinationDispersion[9] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDensityN() {
        return coordinationDensity[0];
    }

    /*        <item id="1">شمال غربی</item>
        <item id="2">شمالی شرقی</item>
        <item id="3">جنوب غربی</item>
        <item id="4">جنوب شرقی</item>
        <item id="5">غرب</item>
        <item id="6">شرق</item>
        <item id="7">سطح</item>
        <item id="8">مرکز</item>*/

    public void setDensityN(boolean b) {
        if (this.coordinationDensity[0] != b) {
            this.coordinationDensity[0] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDensityS() {
        return coordinationDensity[1];
    }

    /*        <item id="1">شمال غربی</item>
        <item id="2">شمالی شرقی</item>
        <item id="3">جنوب غربی</item>
        <item id="4">جنوب شرقی</item>
        <item id="5">غرب</item>
        <item id="6">شرق</item>
        <item id="7">سطح</item>
        <item id="8">مرکز</item>*/

    public void setDensityS(boolean b) {
        if (this.coordinationDensity[1] != b) {
            this.coordinationDensity[1] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDensityNW() {
        return coordinationDensity[2];
    }

    /*        <item id="1">شمال غربی</item>
        <item id="2">شمالی شرقی</item>
        <item id="3">جنوب غربی</item>
        <item id="4">جنوب شرقی</item>
        <item id="5">غرب</item>
        <item id="6">شرق</item>
        <item id="7">سطح</item>
        <item id="8">مرکز</item>*/

    public void setDensityNW(boolean b) {
        if (this.coordinationDensity[2] != b) {
            this.coordinationDensity[2] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDensityNE() {
        return coordinationDensity[3];
    }

    public void setDensityNE(boolean b) {
        if (this.coordinationDensity[3] != b) {
            this.coordinationDensity[3] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDensitySW() {
        return coordinationDensity[4];
    }

    public void setDensitySW(boolean b) {
        if (this.coordinationDensity[4] != b) {
            this.coordinationDensity[4] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDensitySE() {
        return coordinationDensity[5];
    }

    public void setDensitySE(boolean b) {
        if (this.coordinationDensity[5] != b) {
            this.coordinationDensity[5] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDensityW() {
        return coordinationDensity[6];
    }

    public void setDensityW(boolean b) {
        if (this.coordinationDensity[6] != b) {
            this.coordinationDensity[6] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDensityE() {
        return coordinationDensity[7];
    }

    public void setDensityE(boolean b) {
        if (this.coordinationDensity[7] != b) {
            this.coordinationDensity[7] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDensityAll() {
        return coordinationDensity[8];
    }

    public void setDensityAll(boolean b) {
        if (this.coordinationDensity[8] != b) {
            this.coordinationDensity[8] = b;
            notifyChange();
        }
    }

    @Bindable
    public boolean getDensityCenter() {
        return coordinationDensity[9];
    }

    public void setDensityCenter(boolean b) {
        if (this.coordinationDensity[9] != b) {
            this.coordinationDensity[9] = b;
            notifyChange();
        }
    }

    @Bindable
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        if (this.material == null || this.material.equals(material)) {
            this.material = material;
            notifyChange();
        }
    }

    @Bindable
    public int getType() {
        return type;
    }


    public void setType(int type) {
        if (this.type != type) {
            this.type = type;
            notifyChange();
        }
    }
}
