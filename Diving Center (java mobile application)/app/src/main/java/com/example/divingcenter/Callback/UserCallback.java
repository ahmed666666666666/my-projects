package com.example.divingcenter.Callback;

import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.Trainer;
import com.example.divingcenter.Model.User;

import java.util.ArrayList;
import java.util.List;

public  interface UserCallback {
void OnSuccess(String msg);
void onError(String msg);
void OnSuccess(List<User> users) ;
void OnCSuccess(List<User> users) ;

void onSuccess(List<Trainer> trainer);
void OnSuccessL(List<String> users);

    void OnSuccess(ArrayList<User> users);

    void OnSuccess(User user) ;
}
