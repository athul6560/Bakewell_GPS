package com.acb.bakewellgps.ui.Activities.ShopViewPage;

public interface IShopViewLogic {
    interface logic{
        void updateGpsApi(double longi,double lanti,int shopId);
    }
    interface view{
        void updateGpsCallback(boolean status);
    }
}
