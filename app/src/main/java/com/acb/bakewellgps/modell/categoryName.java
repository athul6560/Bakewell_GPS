package com.acb.bakewellgps.modell;

public class categoryName {
    public int id;
    public String category_name;
    public boolean is_active;

    public categoryName(int id, String category_name) {
        this.id = id;
        this.category_name = category_name;
    }

    @Override
    public String toString() {
        return category_name;
    }

    public int getId() {
        return id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public boolean isIs_active() {
        return is_active;
    }
}
