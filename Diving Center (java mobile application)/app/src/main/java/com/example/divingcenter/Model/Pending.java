package com.example.divingcenter.Model;

import android.net.wifi.aware.PublishConfig;
public class Pending {
public String key;
public String email;
public String licencePending;
public String order;
    public Pending() {
    }

    public String getKey() {
        return key;
    }

    public String getOrder() {
        return order;
    }

    public String getLicencePending() {
        return licencePending;
    }

    public Pending(String key, String order, String licencePending,String email) {
        this.key = key;
        this.order = order;
        this.licencePending = licencePending;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
