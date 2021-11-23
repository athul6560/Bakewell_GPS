package com.acb.bakewellgps.ui.Activities.LoginPage;



public interface ILoginLogic {


    interface view {
        void loginResponse(String message, Boolean status);

        void requestPermissions();
    }

    interface logic {
        void loginFunction(String username, String password);

        boolean validateCredentials(String username, String password);


    }


}
