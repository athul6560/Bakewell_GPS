package com.acb.bakewellgps.ui.Activities.addNewShopPage;

import com.acb.bakewellgps.modell.allCurrencies;
import com.acb.bakewellgps.modell.areaList;
import com.acb.bakewellgps.modell.countryList;
import com.acb.bakewellgps.modell.parentCompany.parentCompany;
import com.acb.bakewellgps.modell.sentShopAddDetails;
import com.acb.bakewellgps.modell.sentShopUpdateDetails;
import com.acb.bakewellgps.modell.shopCategories;

import java.util.List;

public interface IAddLogic {
    interface view{
        void addSuccessCallback(Boolean status,String Message);
        void countryCallback(Boolean status, String Message, List<countryList> countryList);
        void areaCallback(Boolean status, String Message, List<areaList> areaLists);
        void currencyCallback(Boolean status, String Message, List<allCurrencies> allCurrencies);
        void shopCategoryCallBack(Boolean status, String Message, shopCategories shopCategories);
        void parentCompanyCallBack(Boolean status, String Message, List<parentCompany> parentCompany);
    }
    interface logic{
        void addNewShop(sentShopAddDetails sentShopAddDetails);
        void getAllCountries();
        void getAllArea();
        void getAllCurrencies();
        void getShopCategory();
        void getallParentCompanies();
    }
}
