package com.visualstore.Model;

import java.util.ArrayList;

public class CoatingTintModel {
    private String status;
    private String message;
    private ArrayList<CoatingTintDataModel> data;

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

    public ArrayList<CoatingTintDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<CoatingTintDataModel> data) {
        this.data = data;
    }
}
