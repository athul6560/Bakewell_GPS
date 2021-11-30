package com.acb.bakewellgps.ui.Activities.EditPage;

import com.acb.bakewellgps.modell.sentShopUpdateDetails;

public interface IEditLogic {
    interface logic{
        void updateShopDetails(sentShopUpdateDetails shopUpdateDetails);
    }
    interface  view{
        void updateSuccessCallback(Boolean status,String Message);
    }
}
