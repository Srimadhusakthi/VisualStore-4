package com.visualstore.Model;

import java.util.ArrayList;

public class LoginResultData {
    private String status;
    private String message;
    private Data data;

    public static  LoginResultData instance  = new LoginResultData();

    public static LoginResultData get(){
        return  instance;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

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


    public class Data {
        private String id;
        private String auth_id;
        private LoginChildData data;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuth_id() {
            return auth_id;
        }

        public void setAuth_id(String auth_id) {
            this.auth_id = auth_id;
        }

        public LoginChildData getData() {
            return data;
        }

        public void setData(LoginChildData data) {
            this.data = data;
        }
    }

    public class LoginChildData{
        private String id;
        private String name;
        private String email;
        private String phone;
        private String username;
        private String login_code;
        private String logged_in;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getLogin_code() {
            return login_code;
        }

        public void setLogin_code(String login_code) {
            this.login_code = login_code;
        }

        public String getLogged_in() {
            return logged_in;
        }

        public void setLogged_in(String logged_in) {
            this.logged_in = logged_in;
        }
    }

}


