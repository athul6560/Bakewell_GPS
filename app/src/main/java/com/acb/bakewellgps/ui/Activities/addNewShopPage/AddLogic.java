package com.acb.bakewellgps.ui.Activities.addNewShopPage;

import android.content.Context;
import android.widget.Toast;

import com.acb.bakewellgps.API.APIClient;
import com.acb.bakewellgps.API.APIInterface;
import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.modell.allCurrencies;
import com.acb.bakewellgps.modell.areaList;
import com.acb.bakewellgps.modell.countryList;
import com.acb.bakewellgps.modell.parentCompany.basePArent;
import com.acb.bakewellgps.modell.responseSimple;
import com.acb.bakewellgps.modell.sentShopAddDetails;
import com.acb.bakewellgps.modell.shopCategories;
import com.acb.bakewellgps.ui.Activities.EditPage.IEditLogic;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLogic implements IAddLogic.logic {
    private Context context;
    private IAddLogic.view view;

    public AddLogic(Context context, IAddLogic.view view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void addNewShop(sentShopAddDetails sentShopAddDetails) {
        Dialogues.show(context);
        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Call<responseSimple> call = service.createShop(sentShopAddDetails);
        call.enqueue(new Callback<responseSimple>() {
            @Override
            public void onResponse(Call<responseSimple> call, Response<responseSimple> response) {
                Dialogues.dismiss();

                if (response.isSuccessful()) {
                    view.addSuccessCallback(true,"Success");
                } else {
                    view.addSuccessCallback(false,response.body().getMessage());                }

            }

            @Override
            public void onFailure(Call<responseSimple> call, Throwable t) {
                Dialogues.dismiss();
                view.addSuccessCallback(false,"Failed"+t.getMessage());

            }
        });
    }

    @Override
    public void getAllCountries() {

        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Call<List<countryList>> call = service.getAllCountries();
        call.enqueue(new Callback<List<countryList>>() {
            @Override
            public void onResponse(Call<List<countryList>> call, Response<List<countryList>> response) {


                if (response.isSuccessful()) {
                    view.countryCallback(true, "Sucess", response.body());
                } else {
                    view.countryCallback(false, "Server Error", null);
                }

            }

            @Override
            public void onFailure(Call<List<countryList>> call, Throwable t) {

                view.countryCallback(false, "No Network", null);

            }
        });
    }

    @Override
    public void getAllArea() {

        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Call<List<areaList>> call = service.getAllArea();
        call.enqueue(new Callback<List<areaList>>() {
            @Override
            public void onResponse(Call<List<areaList>> call, Response<List<areaList>> response) {


                if (response.isSuccessful()) {
                    view.areaCallback(true, "Sucess", response.body());
                } else {
                    view.areaCallback(false, "Server Error", null);
                }

            }

            @Override
            public void onFailure(Call<List<areaList>> call, Throwable t) {

                view.areaCallback(false, "No Network", null);

            }
        });

    }

    @Override
    public void getAllCurrencies() {

        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Call<List<allCurrencies>> call = service.getAllCurrencies();
        call.enqueue(new Callback<List<allCurrencies>>() {
            @Override
            public void onResponse(Call<List<allCurrencies>> call, Response<List<allCurrencies>> response) {
                Dialogues.dismiss();

                if (response.isSuccessful()) {
                    view.currencyCallback(true, "Sucess", response.body());
                } else {
                    view.currencyCallback(false, "Server Error", null);
                }

            }

            @Override
            public void onFailure(Call<List<allCurrencies>> call, Throwable t) {

                view.currencyCallback(false, "No Network", null);

            }
        });
    }

    @Override
    public void getShopCategory() {

        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Call<shopCategories> call = service.getShopCategories();
        call.enqueue(new Callback<shopCategories>() {
            @Override
            public void onResponse(Call<shopCategories> call, Response<shopCategories> response) {
                Dialogues.dismiss();

                if (response.isSuccessful()) {
                    view.shopCategoryCallBack(true, "Sucess", response.body());
                } else {
                    view.shopCategoryCallBack(false, "Server Error", null);
                }

            }

            @Override
            public void onFailure(Call<shopCategories> call, Throwable t) {

                view.shopCategoryCallBack(false, "No Network", null);

            }
        });
    }

    @Override
    public void getallParentCompanies() {
        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Call<basePArent> call = service.getParentCompanies();
        call.enqueue(new Callback<basePArent>() {
            @Override
            public void onResponse(Call<basePArent> call, Response<basePArent> response) {
                Dialogues.dismiss();

                if (response.isSuccessful()) {
                    view.parentCompanyCallBack(true, "Sucess", response.body().getData());
                } else {
                    view.parentCompanyCallBack(false, "Server Error", null);
                }

            }

            @Override
            public void onFailure(Call<basePArent> call, Throwable t) {

                view.parentCompanyCallBack(false, "No Network", null);

            }
        });
    }
}
