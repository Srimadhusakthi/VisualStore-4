package com.visualstore.Model;

import java.util.ArrayList;

public class GetOrderModel {
    private String status;
    private String message;
    private ArrayList<GetOrderData> data;

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

    public ArrayList<GetOrderData> getData() {
        return data;
    }

    public void setData(ArrayList<GetOrderData> data) {
        this.data = data;
    }


}
