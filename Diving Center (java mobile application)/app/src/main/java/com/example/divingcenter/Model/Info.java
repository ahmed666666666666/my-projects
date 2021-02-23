package com.example.divingcenter.Model;

import android.net.Uri;

public class Info {

    public String j_level ;
    public   String title ;
    public   String avilability ;
    public   int  memebers ;
    public   String price  ;
    public String   description;
    public int quantity;
    public String uri;
    public String key;
    public Info() {

    }

    public String getKey() {
        return key;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUri() {
        return uri;
    }

    public Info(String j_level, String title, String avilability, int memebers, String price, String description,
                int quantity,String uri,String key) {
        this.j_level = j_level;
        this.title = title;
        this.avilability = avilability;
        this.memebers = memebers;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.uri= uri;
        this.key = key;
    }


    public String getJ_level() {
        return j_level;
    }

    public String getTitle() {
        return title;
    }

    public String getAvilability() {
        return avilability;
    }

    public int getMemebers() {
        return memebers;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

}
