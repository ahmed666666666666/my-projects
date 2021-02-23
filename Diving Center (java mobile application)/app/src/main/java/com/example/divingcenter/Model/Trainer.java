package com.example.divingcenter.Model;

public class Trainer {

   public String key ;
    String name ;
    String age;
    String email ;
    String password ;
    String sex;
    String level;
    String type;
    String confirmation;
    long created_at ;

    public Trainer() {

    }

    public String getConfirmation() {
        return confirmation;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public String getLevel() {
        return level;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Trainer(String key,String name, String age, String email, String password, String sex, String level, String type , String confirmation, long created_at) {

        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.level = level;
        this.created_at = created_at;
        this.type = type;
        this.confirmation = confirmation;

    }

}
