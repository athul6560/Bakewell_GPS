package com.acb.bakewellgps.modell;

import java.util.List;

public class shopCategories {
    public boolean status;
    public String message;
    public List<categoryName> data;

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

    public List<categoryName> getData() {
        return data;
    }

    public void setData(List<categoryName> data) {
        this.data = data;
    }
}
