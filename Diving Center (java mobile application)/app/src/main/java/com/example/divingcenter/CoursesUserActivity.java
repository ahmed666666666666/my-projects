package com.example.divingcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.View.Trainer_cources_Recycler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class CoursesUserActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    AdapterUserCourses adapterUserCourses;
    UserController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_user);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation2);
        bottomNavigationView.setSelectedItemId(R.id.courses_order);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Licences_order:
                        startActivity(new Intent(getApplicationContext(), LicenceatUserSideActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.Equipments_order:
                        startActivity(new Intent(getApplicationContext(),EquipmentOfUser.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.journey_order:
                        startActivity(new Intent(getApplicationContext(),JourneyofUser.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.courses_paid:
                        startActivity(new Intent(getApplicationContext(), CousesPaidActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
            }
                return false;
            }
        });
        controller = new UserController();
        recyclerView = findViewById(R.id.user_courses);
        adapterUserCourses = new AdapterUserCourses();
        LinearLayoutManager layoutManager = new LinearLayoutManager(CoursesUserActivity.this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterUserCourses);
        get_courses();
    }

    private void  get_courses(){
        controller.get_courses_for_user(new InfoCallback() {
            @Override
            public void onSuccess(List<Info> info) {

            }

            @Override
            public void onOSuccess(List<Orders> info) {

            }

            @Override
            public void onCSuccess(List<Courses> courses) {
                adapterUserCourses.setList(courses);

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