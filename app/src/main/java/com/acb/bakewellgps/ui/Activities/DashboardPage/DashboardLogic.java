package com.acb.bakewellgps.ui.Activities.DashboardPage;

import android.content.Context;
import android.util.Log;

import com.acb.bakewellgps.API.APIClient;
import com.acb.bakewellgps.API.APIInterface;
import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.modell.RootList;
import com.acb.bakewellgps.modell.RoutesData;
import com.acb.bakewellgps.modell.allCustomerModel.allCustomerResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardLogic implements IDashboardLogic.logic {
    private IDashboardLogic.view view;
    private Context context;
    private static final String TAG = "Dashboad Logic";

    public DashboardLogic(IDashboardLogic.view view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void callRoutesApi(int EmployeeId) {
        Dialogues.show(context);
        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Call<RootList<RoutesData>> call = service.routesList(EmployeeId);
        call.enqueue(new Callback<RootList<RoutesData>>() {
            @Override
            public void onResponse(Call<RootList<RoutesData>> call, Response<RootList<RoutesData>> response) {
                Dialogues.dismiss();
                Log.d(TAG, response.body().message);
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        view.routeListApiCompleted(response.body().status, response.body().data, response.body().message);
                    } else {
                        view.routeListApiCompleted(response.body().status, null, response.body().message);

                    }
                } else {
                    view.routeListApiCompleted(false, null, "Server Error");
                }

            }

            @Override
            public void onFailure(Call<RootList<RoutesData>> call, Throwable t) {
                Dialogues.dismiss();
                view.routeListApiCompleted(false, null, "No Network");

            }
        });
    }

    @Override
    public void callAllCustomerApi(int EmployeeId) {
        Dialogues.show(context);
        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Call<allCustomerResponse> call = service.getAllCustomers(EmployeeId);
        call.enqueue(new Callback<allCustomerResponse>() {
            @Override
            public void onResponse(Call<allCustomerResponse> call, Response<allCustomerResponse> response) {
                Dialogues.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        view.allCustomerApiCallBack(response.body().status, response.body().data, response.body().message);
                    } else {
                        view.allCustomerApiCallBack(response.body().status, null, response.body().message);

                    }
                } else {
                    view.allCustomerApiCallBack(false, null, "Server Error");
                }

            }

            @Override
            public void onFailure(Call<allCustomerResponse> call, Throwable t) {
                Dialogues.dismiss();
                view.allCustomerApiCallBack(false, null, t.getMessage());

            }
        });
    }

    @Override
    public void callLogoutApi(String username) {
        Dialogues.show(context);
        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Call<Void> call = service.logOutApi(username);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Dialogues.dismiss();
                view.logoutCallback(true, "");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Dialogues.dismiss();
                view.logoutCallback(false, "");

            }
        });
    }
}
