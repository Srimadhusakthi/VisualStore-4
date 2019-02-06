package com.visualstore.Model;

public class LensOrderModel {
    private String mName;
    private String id;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LensOrderModel(String mName, String id) {
        this.mName = mName;
        this.id = id;
    }
}
