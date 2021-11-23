package com.acb.bakewellgps.ui.Activities.DashboardPage;

import android.content.Context;
import android.util.Log;

import com.acb.bakewellgps.API.APIClient;
import com.acb.bakewellgps.API.APIInterface;
import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.modell.Root;
import com.acb.bakewellgps.modell.RoutesData;


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
        Call<Root<RoutesData>> call = service.routesList(EmployeeId);
        call.enqueue(new Callback<Root<RoutesData>>() {
            @Override
            public void onResponse(Call<Root<RoutesData>> call, Response<Root<RoutesData>> response) {
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
            public void onFailure(Call<Root<RoutesData>> call, Throwable t) {
                Dialogues.dismiss();
                view.routeListApiCompleted(false, null, "No Network");

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
