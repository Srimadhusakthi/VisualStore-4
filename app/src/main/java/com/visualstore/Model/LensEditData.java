package com.visualstore.Model;

public class LensEditData {

    private String status;
    private String message;
    private EditData  data;

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

    public EditData getData() {
        return data;
    }

    public void setData(EditData data) {
        this.data = data;
    }

    public class EditData {
        private String id;
        private String order_id;
        private String admin_id;
        private String consumer_name;
        private String status;
        private String order_date;
        private String  content;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getConsumer_name() {
            return consumer_name;
        }

        public void setConsumer_name(String consumer_name) {
            this.consumer_name = consumer_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }



}
