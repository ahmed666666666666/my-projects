package com.example.divingcenter.Model;

import java.security.PublicKey;

public class Courses  {

    public String j_level ;
    public   String title ;
    public   String avilability ;
    public   int  memebers ;
    public   String price  ;
    public String   description;
    public String id;
    public  String user_id;

    public Courses() {
    }

    public Courses(String j_level, String title, String avilability, int memebers, String price, String description, String id,String user_id) {
        this.j_level = j_level;
        this.title = title;
        this.avilability = avilability;
        this.memebers = memebers;
        this.price = price;
        this.description = description;
        this.id = id;
        this.user_id = user_id;
    }


    public Courses(String j_level, String title, String avilability, int memebers, String price, String description, String id) {
        this.j_level = j_level;
        this.title = title;
        this.avilability = avilability;
        this.memebers = memebers;
        this.price = price;
        this.description = description;
        this.id = id;
    }



    public String getUser_id() {
        return user_id;
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

    public String getId() {
        return id;
    }
}
