package com.acb.bakewellgps.modell;

public class customerGroup {
    public int id;
    public String group_name;
    public String group_type;

    public customerGroup(int id, String group_name) {
        this.id = id;
        this.group_name = group_name;
    }

    public int getId() {
        return id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getGroup_type() {
        return group_type;
    }
}
