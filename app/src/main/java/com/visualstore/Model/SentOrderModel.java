package com.visualstore.Model;

import java.util.ArrayList;

public class SentOrderModel {

    private String status;
    private String message;
    private ArrayList<SentOrderDataModel>  data;


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

    public ArrayList<SentOrderDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<SentOrderDataModel> data) {
        this.data = data;
    }
}
