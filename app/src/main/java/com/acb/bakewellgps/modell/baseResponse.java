package com.acb.bakewellgps.modell;

import java.util.List;

public class baseResponse {
    public boolean status;
    public String message;
    public List<User> data;

    public boolean isStatus() {
        return status;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
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
}
