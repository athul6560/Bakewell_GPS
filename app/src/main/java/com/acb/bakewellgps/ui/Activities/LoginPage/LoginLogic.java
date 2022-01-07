package com.acb.bakewellgps.ui.Activities.LoginPage;


import android.content.Context;
import android.util.Log;

import com.acb.bakewellgps.API.APIClient;
import com.acb.bakewellgps.API.APIInterface;
import com.acb.bakewellgps.SharedPref.SharedData;
import com.acb.bakewellgps.Tools.NetworkConstants;
import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.modell.baseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginLogic implements ILoginLogic.logic {
    private Context context;
    private ILoginLogic.view view;

    public LoginLogic(Context context, ILoginLogic.view view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void loginFunction(String username, String password) {
        if (validateCredentials(username, password)) {
            Dialogues.show(context);
            APIInterface service = APIClient.getClient().create(APIInterface.class);
            Call<baseResponse> call = service.loginApi(username, password, NetworkConstants.VERSION_CODE);
            call.enqueue(new Callback<baseResponse>() {
                @Override
                public void onResponse(Call<baseResponse> call, Response<baseResponse> response) {
                    Dialogues.dismiss();
                    if (response.body().status) {
                        SharedData.setUserName(context,response.body().getData().get(0).getUsername());
                        SharedData.setId(context,response.body().getData().get(0).getEmployee_id());
                        view.loginResponse(response.body().message, response.body().status);
                    }else
                        view.loginResponse(response.body().message, response.body().status);

                }

                @Override
                public void onFailure(Call<baseResponse> call, Throwable t) {
                    Dialogues.dismiss();
                    view.loginResponse("No network", false);
                }
            });
        }
    }

    @Override
    public boolean validateCredentials(String username, String password) {
        if (username.equals("")) {
            view.loginResponse("Please Enter Username", false);
            return false;
        } else if (password.equals("")) {
            view.loginResponse("Please Enter Password", false);
            return false;
        } else
            return true;
    }
}
