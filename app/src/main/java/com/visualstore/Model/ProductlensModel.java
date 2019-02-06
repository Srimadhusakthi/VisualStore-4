package com.visualstore.Model;

import java.util.ArrayList;

public class ProductlensModel {
    private String status;
    private String message;
    private ArrayList<ProductLensDataModel> data;

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

    public ArrayList<ProductLensDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<ProductLensDataModel> data) {
        this.data = data;
    }


}
