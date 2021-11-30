package com.acb.bakewellgps.modell;

public class Shop {
    public int shop_id;
    public String shop_name;
    public String area;
    public String outlet_type;
    public String billing_type;
    public boolean is_lat_long;

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOutlet_type() {
        return outlet_type;
    }

    public void setOutlet_type(String outlet_type) {
        this.outlet_type = outlet_type;
    }

    public String getBilling_type() {
        return billing_type;
    }

    public void setBilling_type(String billing_type) {
        this.billing_type = billing_type;
    }

    public boolean isIs_lat_long() {
        return is_lat_long;
    }

    public void setIs_lat_long(boolean is_lat_long) {
        this.is_lat_long = is_lat_long;
    }
}
