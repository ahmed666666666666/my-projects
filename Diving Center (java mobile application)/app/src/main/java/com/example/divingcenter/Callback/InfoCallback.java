package com.example.divingcenter.Callback;

import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Orders;

import java.security.PublicKey;
import java.util.List;

public interface InfoCallback {
    void  onSuccess(List<Info> info);
    void  onOSuccess(List<Orders> info);
    void  onCSuccess(List<Courses> courses);
    void  onSuccess(String msg);
    void  onError(String msg);
    void success(List<Levels> levels);
}
