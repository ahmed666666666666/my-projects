package com.example.divingcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.divingcenter.Callback.CardData;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.View.CardModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class CousesPaidActivity extends AppCompatActivity {
RecyclerView recyclerView;
CoursesPaidAdapter coursesPaidAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couses_paid);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation2);
        bottomNavigationView.setSelectedItemId(R.id.courses_paid);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Licences_order:
                        startActivity(new Intent(getApplicationContext(), LicenceatUserSideActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.courses_order:
                        startActivity(new Intent(getApplicationContext(), CoursesUserActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                        case R.id.courses_paid:
                            break;
                    case R.id.Equipments_order:
                        startActivity(new Intent(getApplicationContext(), EquipmentOfUser.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.journey_order:
                        startActivity(new Intent(getApplicationContext(), JourneyofUser.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;

                }

                return false;
            }
        });

        recyclerView = findViewById(R.id.PPP);
        coursesPaidAdapter = new CoursesPaidAdapter();
        recyclerView.setAdapter(coursesPaidAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CousesPaidActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        get();
    }

    private  void get(){

        UserController controller = new UserController();
        controller.get_card_courses(new CardData() {
            @Override
            public void onSuccess(List<CardModel> cardModel) {
                coursesPaidAdapter.setList(cardModel);
            }

            @Override
            public void onError(String msg) {

            }
        });

    }



}