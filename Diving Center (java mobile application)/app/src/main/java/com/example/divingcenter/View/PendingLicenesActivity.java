package com.example.divingcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Callback.LisenseCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.OrderPending;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class PendingLicenesActivity extends AppCompatActivity {

String status = "pending" ;
String key;
String key2;
String quan;
UserController controller;
FirebaseAuth auth;
String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_licenes);
        auth = FirebaseAuth.getInstance();
        controller = new UserController();
         key = getIntent().getStringExtra("key");
         key2 = getIntent().getStringExtra("key2");
         quan = getIntent().getStringExtra("q");
        title = getIntent().getStringExtra("t");
        get(key);
         if(status.equals("accepted") ) {
            controller.update("licences","quantity" ,key2,quan);
             Orders orders1 = new Orders(title,key2,auth.getCurrentUser().getEmail(),"Reserved");
             controller.add("licencesOf"+auth.getCurrentUser().getUid(),orders1);
             startActivity(new Intent(PendingLicenesActivity.this,PaymentActivity.class));
        }
        if(status.equals("rejected")) {
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
        }
        }
  private void get(String key){
      UserController controller = new UserController();
      controller.get_licences_status(key, new LisenseCallback() {
          @Override
          public void onOSuccess(List<OrderPending> info) {

          }

          @Override
          public void onOSuccess(String msg) {
              if(msg.length() > 0) {
                  status = msg;
              }
          }

          @Override
          public void onError(String msg) {

          }
      });
  }

}