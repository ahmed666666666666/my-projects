package com.example.divingcenter.Callback;

import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.OrderPending;
import com.example.divingcenter.Orders;

import java.util.List;

public interface LisenseCallback {
    void  onOSuccess(List<OrderPending> info);
    void  onOSuccess(String msg );
    void  onError(String msg);

}
