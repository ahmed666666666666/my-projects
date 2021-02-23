package com.example.divingcenter;

import com.example.divingcenter.Model.User;

public  class SharedData {
 private static User user ;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        SharedData.user = user;
    }
}
