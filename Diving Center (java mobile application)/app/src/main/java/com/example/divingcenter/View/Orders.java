package com.example.divingcenter;

public class Orders {
 private    String trainer_id;
    public  String order;
    private  String key;
    private  String email;
    private String isAvailable ;
    private String account_id;
    private  String licencePending;

    public Orders() {
    }

    public Orders(String order,String key,String email) {
        this.order = order;
        this.key = key;
        this.email = email;
    }


    public String getIsAvailable() {
        return isAvailable;
    }

    public Orders(String order, String key, String email, String IsAvailable) {
        this.order = order;
        this.key = key;
        this.email = email;
        this.isAvailable = IsAvailable ;

    }

    public String getTrainer_id() {
        return trainer_id;
    }

    public Orders(String order, String key, String email, String IsAvailable, String trainer_id,String account_id) {
        this.order = order;
        this.trainer_id = trainer_id;
        this.key = key;
        this.email = email;
        this.isAvailable = IsAvailable ;
        this.account_id  = account_id;
    }
    public String getKey() {
        return key;
    }

    public String getOrder() {
        return order;
    }

    public String getEmail() {
        return email;
    }
}
