package com.visualstore.Model;

import java.util.ArrayList;

public class DiameterModel {
    private String status;
    private String message;
    private ArrayList<DiameterModel> data;

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

    public ArrayList<DiameterModel> getData() {
        return data;
    }

    public void setData(ArrayList<DiameterModel> data) {
        this.data = data;
    }

    class DiamterData {

        private String lenscode;
        private String diameter;
        private String diameter_text;


        public String getLenscode() {
            return lenscode;
        }

        public void setLenscode(String lenscode) {
            this.lenscode = lenscode;
        }

        public String getDiameter() {
            return diameter;
        }

        public void setDiameter(String diameter) {
            this.diameter = diameter;
        }

        public String getDiameter_text() {
            return diameter_text;
        }

        public void setDiameter_text(String diameter_text) {
            this.diameter_text = diameter_text;
        }
    }
}
