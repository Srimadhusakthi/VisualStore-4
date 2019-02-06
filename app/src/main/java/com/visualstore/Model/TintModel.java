package com.visualstore.Model;

import java.util.ArrayList;

public class TintModel {
    private String status;
    private String message;
    private ArrayList<TintDataModel> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<TintDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<TintDataModel> data) {
        this.data = data;
    }
}
