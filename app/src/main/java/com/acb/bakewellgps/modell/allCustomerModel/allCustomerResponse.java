package com.acb.bakewellgps.modell.allCustomerModel;

import java.util.ArrayList;

public class allCustomerResponse {
    public boolean status;
    public String message;
    public ArrayList<allCustomers> data;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<allCustomers> getData() {
        return data;
    }
}
