package com.acb.bakewellgps.ui.Activities.EditPage;

import android.content.Context;

import com.acb.bakewellgps.API.APIClient;
import com.acb.bakewellgps.API.APIInterface;
import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.modell.Root;
import com.acb.bakewellgps.modell.responseSimple;
import com.acb.bakewellgps.modell.sentShopUpdateDetails;
import com.acb.bakewellgps.modell.shopDetails;
import com.acb.bakewellgps.ui.Activities.ShopViewPage.IShopViewLogic;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditLogic implements IEditLogic.logic {
    private Context context;
    private IEditLogic.view view;

    public EditLogic(Context context, IEditLogic.view view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void updateShopDetails(sentShopUpdateDetails shopUpdateDetails) {
        Dialogues.show(context);
        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Call<responseSimple> call = service.updateShopDetails(shopUpdateDetails);
        call.enqueue(new Callback<responseSimple>() {
            @Override
            public void onResponse(Call<responseSimple> call, Response<responseSimple> response) {
                Dialogues.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        view.updateSuccessCallback(response.body().status, response.body().getMessage());
                    } else {
                        view.updateSuccessCallback(response.body().status, response.body().getMessage());

                    }
                } else {
                    view.updateSuccessCallback(false, "Server Error");
                }

            }

            @Override
            public void onFailure(Call<responseSimple> call, Throwable t) {
                Dialogues.dismiss();
                view.updateSuccessCallback(false, "No Network");

            }
        });
    }
}
