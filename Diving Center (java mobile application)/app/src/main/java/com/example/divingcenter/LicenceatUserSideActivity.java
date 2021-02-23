package com.example.divingcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Callback.MyCall;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.Pending;
import com.example.divingcenter.View.Licenes_Order_Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class LicenceatUserSideActivity extends AppCompatActivity {
    LicenceAtJUserAdapter equipment_recycler;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licenceat_user_side);
        recyclerView = findViewById(R.id.licence_pending);
        equipment_recycler = new LicenceAtJUserAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(LicenceatUserSideActivity.this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(equipment_recycler);
        retrieve();
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation2);
        bottomNavigationView.setSelectedItemId(R.id.Licences_order);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.courses_order:
                        startActivity(new Intent(getApplicationContext(), CoursesUserActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.courses_paid:
                        startActivity(new Intent(getApplicationContext(), CousesPaidActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
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
    }
    public void retrieve(){
        UserController controller = new UserController();
        controller.getatuser("LicenceOrders", new MyCall() {
            @Override
            public void ondSuccess(List<Pending> p) {
                equipment_recycler.setList(p);
            }
        });
    }
}