package com.acb.bakewellgps.modell.parentCompany;

import java.util.ArrayList;

public class basePArent {
    public boolean status;
    public String message;
    public ArrayList<parentCompany> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<parentCompany> getData() {
        return data;
    }

    public void setData(ArrayList<parentCompany> data) {
        this.data = data;
    }
}
