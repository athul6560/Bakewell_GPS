package com.acb.bakewellgps.modell;

import java.util.List;

public class RoutesData {
    public int route_id;
    public String route_name;
    public List<Shop> shops;

    public int getRoute_id() {
        return route_id;
    }

    public String getRoute_name() {
        return route_name;
    }

    public List<Shop> getShops() {
        return shops;
    }
}
