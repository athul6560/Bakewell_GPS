package com.acb.bakewellgps.API;


import com.acb.bakewellgps.modell.Root;
import com.acb.bakewellgps.modell.RootList;
import com.acb.bakewellgps.modell.RoutesData;
import com.acb.bakewellgps.modell.allCurrencies;
import com.acb.bakewellgps.modell.allCustomerModel.allCustomerResponse;
import com.acb.bakewellgps.modell.areaList;
import com.acb.bakewellgps.modell.baseResponse;
import com.acb.bakewellgps.modell.countryList;
import com.acb.bakewellgps.modell.responseSimple;
import com.acb.bakewellgps.modell.sentShopAddDetails;
import com.acb.bakewellgps.modell.sentShopUpdateDetails;
import com.acb.bakewellgps.modell.shopCategories;
import com.acb.bakewellgps.modell.shopDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("Shop/salesman_route_shops")
    Call<RootList<RoutesData>> routesList(@Query("emp_id") int employeeId);

    @GET("Login/Logout")
    Call<Void> logOutApi(@Query("uname") String username);

    @GET("Login/Details_login_details")
    Call<baseResponse> loginApi(@Query("uname") String username, @Query("passwrd") String password, @Query("version_code") String version_code);

    @POST("Login/insert_geo")
    Call<baseResponse> updateLocation(@Query("lati") double latitude,@Query("longi") double longitude,@Query("shop_id") int shopId);

    @GET("Shop/shop_details")
    Call<Root<shopDetails>> getShopDetails(@Query("shop_id") int shopId);

    @POST("SaveUpdate/update_shop")
    Call<responseSimple> updateShopDetails(@Body sentShopUpdateDetails dataList);

    @POST("SaveUpdate/create_shop")
    Call<responseSimple> createShop(@Body sentShopAddDetails dataList);


    @POST("generalviews/getAllCountries")
    Call<List<countryList>> getAllCountries();

    @POST("generalviews/getAllArea")
    Call<List<areaList>> getAllArea();

    @POST("generalviews/getAllCurrencies")
    Call<List<allCurrencies>> getAllCurrencies();

    @POST("shop/shopCategories")
    Call<shopCategories> getShopCategories();

    @GET("Shop/get_all_shops")
    Call<allCustomerResponse> getAllCustomers(@Query("salesman_id") int EmployeeId);
}
