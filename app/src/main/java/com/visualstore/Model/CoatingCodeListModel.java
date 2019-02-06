package com.visualstore.Model;

import java.util.ArrayList;

public class CoatingCodeListModel {
    private String status;
    private String message;
    private ArrayList<CoatingCodeData> data;

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

    public ArrayList<CoatingCodeData> getData() {
        return data;
    }

    public void setData(ArrayList<CoatingCodeData> data) {
        this.data = data;
    }



    private  class CoatingCodeData{
        private String id;
        private String code;
        private String name;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
