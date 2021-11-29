package com.acb.bakewellgps.ui.Activities.ShopViewPage;

import android.content.Context;

import com.acb.bakewellgps.API.APIClient;
import com.acb.bakewellgps.API.APIInterface;
import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.modell.Root;
import com.acb.bakewellgps.modell.RootList;
import com.acb.bakewellgps.modell.baseResponse;
import com.acb.bakewellgps.modell.shopDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopViewLogic implements IShopViewLogic.logic{
    private Context context;
    private IShopViewLogic.view view;

    public ShopViewLogic(Context context, IShopViewLogic.view view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void updateGpsApi(double longi, double lanti, int shopId) {
        Dialogues.show(context);
        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Call<baseResponse> call = service.updateLocation(lanti,longi,shopId);
        call.enqueue(new Callback<baseResponse>() {
            @Override
            public void onResponse(Call<baseResponse> call, Response<baseResponse> response) {
                Dialogues.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        view.updateGpsCallback(response.body().status);
                    }else{
                        view.updateGpsCallback(response.body().status);

                    }
                } else {
                    view.updateGpsCallback(false);
                }

            }

            @Override
            public void onFailure(Call<baseResponse> call, Throwable t) {
                Dialogues.dismiss();
                view.updateGpsCallback(false);

            }
        });
    }

    @Override
    public void getShopDetailsAPI(int shopId) {
        Dialogues.show(context);
        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Call<Root<shopDetails>> call = service.getShopDetails(shopId);
        call.enqueue(new Callback<Root<shopDetails>>() {
            @Override
            public void onResponse(Call<Root<shopDetails>> call, Response<Root<shopDetails>> response) {
                Dialogues.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        view.shopDetailsCallback(response.body().status,response.body().getData());
                    }else{
                        view.shopDetailsCallback(response.body().status,response.body().getData());

                    }
                } else {
                    view.shopDetailsCallback(false,null);
                }

            }

            @Override
            public void onFailure(Call<Root<shopDetails>> call, Throwable t) {
                Dialogues.dismiss();
                view.shopDetailsCallback(false,null);

            }
        });
    }
}
