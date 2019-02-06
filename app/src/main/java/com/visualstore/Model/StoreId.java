package com.visualstore.Model;

public class StoreId {

    private String auth_id;


    public String getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    public static StoreId instances = new  StoreId();

    public static StoreId get(){
        return  instances;
    }
}
