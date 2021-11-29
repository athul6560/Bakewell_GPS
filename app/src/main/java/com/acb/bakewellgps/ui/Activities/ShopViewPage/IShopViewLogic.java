package com.acb.bakewellgps.ui.Activities.ShopViewPage;

import com.acb.bakewellgps.modell.shopDetails;

public interface IShopViewLogic {
    interface logic{
        void updateGpsApi(double longi,double lanti,int shopId);
        void getShopDetailsAPI(int shopId);
    }
    interface view{
        void updateGpsCallback(boolean status);
        void shopDetailsCallback(boolean status, shopDetails shopDetails);
    }
}
