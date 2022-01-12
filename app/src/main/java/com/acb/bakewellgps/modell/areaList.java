package com.acb.bakewellgps.modell;

public class areaList {
    public int id;
    public String code;
    public String area_name;
    public int province_id;
    public boolean is_active;
    public String identifier;

    public areaList(int id, String area_name) {
        this.id = id;

        this.area_name = area_name;
    }

    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return area_name;
    }
    public String getCode() {
        return code;
    }

    public String getArea_name() {
        return area_name;
    }

    public int getProvince_id() {
        return province_id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public String getIdentifier() {
        return identifier;
    }
}
