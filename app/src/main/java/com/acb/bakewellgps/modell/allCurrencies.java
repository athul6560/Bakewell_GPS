package com.acb.bakewellgps.modell;

public class allCurrencies {
    public int id;
    public String currency_name;
    public String code;
    public String symbol;
    public boolean is_default;
    public boolean is_active;

    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return currency_name;
    }
    public String getCurrency_name() {
        return currency_name;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isIs_default() {
        return is_default;
    }

    public boolean isIs_active() {
        return is_active;
    }
}
