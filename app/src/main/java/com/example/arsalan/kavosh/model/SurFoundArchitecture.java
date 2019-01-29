package com.example.arsalan.kavosh.model;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class SurFoundArchitecture extends BaseObservable {
   private String id;
   private String condition;
   private String material;
   private boolean[] coordinationDispersion = new boolean[10];
   private boolean[] coordinationDensity = new boolean[10];
   private String age;
   private String usage;
   private int direction1;
   private int direction2;
   private String length1;
   private String lenght2;

    public SurFoundArchitecture() {
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
            notifyChange();
        }
    }

    @Bindable
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        if (this.material == null || !this.material.equals(material)) {
            this.material = material;
            notifyChange();
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
            notifyChange();
        }
    }

    @Bindable
    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        if (this.usage == null || !this.usage.equals(usage)) {
            this.usage = usage;
            notifyChange();
        }
    }

    @Bindable
    public int getDirection1() {
        return direction1;
    }

    public void setDirection1(int direction1) {
        if (this.direction1 != direction1) {
            this.direction1 = direction1;
            notifyChange();
        }
    }

    @Bindable
    public int getDirection2() {
        return direction2;
    }

    public void setDirection2(int direction2) {
        if (this.direction2 != direction2) {
            this.direction2 = direction2;
            notifyChange();
        }
    }

    @Bindable
    public String getLength1() {
        return length1;
    }

    public void setLength1(String length1) {
        if (this.length1 == null || !this.length1.equals(length1)) {
            this.length1 = length1;
            notifyChange();
        }
    }

    @Bindable
    public String getLenght2() {
        return lenght2;
    }

    public void setLenght2(String lenght2) {
        if (this.lenght2 == null || !this.lenght2.equals(lenght2)) {
            this.lenght2 = lenght2;
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
        return coordinationDispersion[5];
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
}
