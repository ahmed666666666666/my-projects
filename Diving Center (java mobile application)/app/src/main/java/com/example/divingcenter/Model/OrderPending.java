package com.example.divingcenter.Model;

public class OrderPending {

    public  String order;
    private  String key;
    private  String email;
    private  String licencePending;
    private String account_id;

    public OrderPending(String order, String key, String email, String licencePending,String account_id) {
        this.order = order;
        this.key = key;
        this.email = email;
        this.licencePending = licencePending;
        this.account_id = account_id;
    }

    public OrderPending() {

    }

    public String getOrder() {
        return order;
    }

    public String getKey() {
        return key;
    }

    public String getEmail() {
        return email;
    }

    public String getLicencePending() {
        return licencePending;
    }
}