package com.acb.bakewellgps.API;


import com.acb.bakewellgps.modell.Root;
import com.acb.bakewellgps.modell.RoutesData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("salesman_route_shops")
    Call<Root<RoutesData>> routesList(@Query("emp_id") int employeeId);


}
