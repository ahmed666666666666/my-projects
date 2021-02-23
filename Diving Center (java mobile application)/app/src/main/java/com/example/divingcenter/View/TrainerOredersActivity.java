package com.example.divingcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.Trainer;
import com.example.divingcenter.View.HomeActivityTrainer;
import com.example.divingcenter.View.Journey_Order_Adapter;
import com.example.divingcenter.View.Trainer_Order_Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class TrainerOredersActivity extends AppCompatActivity {
UserController controller;
    RecyclerView recyclerView;
    Trainer_Order_Adapter equipment_recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_oreders);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.mas);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.trainer_profile:
                        startActivity(new Intent(getApplicationContext(), HomeActivityTrainer.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;
                    case R.id.trainer_home:
                        startActivity(new Intent(getApplicationContext(), TrainerStartActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;
                    case R.id.trainer_curses:
                        startActivity(new Intent(getApplicationContext(), TrainerCourses.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;
                    case R.id.mas:
                }
                return false;
            }
        });
        controller = new UserController();
        recyclerView = findViewById(R.id.trainer_order);
        equipment_recycler = new Trainer_Order_Adapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(TrainerOredersActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(equipment_recycler);
        get_eq();
    }
        private void get_eq(){
        controller.get_for_trainer_orders("orderedCourses", new InfoCallback() {
            @Override
            public void onSuccess(List<Info> info) {

            }

            @Override
            public void onOSuccess(List<Orders> info)
            {

                equipment_recycler.setList(info);
            }

            @Override
            public void onCSuccess(List<Courses> courses) {


            }

            @Override
            public void onSuccess(String msg) {

            }

            @Override
            public void onError(String msg) {

            }

            @Override
            public void success(List<Levels> levels) {

            }
        });
    }
}