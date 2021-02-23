package com.example.divingcenter.Model;

public class Levels {
    public  String level;

    public String key;

    public Levels(String level,String key) {
        this.level = level;

        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Levels() {
    }

    public String getLevel() {
        return level;
    }
}
