package com.visualstore.Model;

import java.util.ArrayList;

public class EmployeeModel {

    private String status;
    private String  message;
    private ArrayList<EmployeeDataModel> data;


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

    public ArrayList<EmployeeDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<EmployeeDataModel> data) {
        this.data = data;
    }
}
