package com.acb.bakewellgps.modell;

public class countryList {
    public int id;
    public String country_name;
    public String code;
    public int currency_id;
    public boolean is_active;
    public String identifier;

    public int getId() {
        return id;
    }

    public String getCountry_name() {
        return country_name;
    }
    @Override
    public String toString() {
        return country_name;
    }
    public String getCode() {
        return code;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public String getIdentifier() {
        return identifier;
    }
}
