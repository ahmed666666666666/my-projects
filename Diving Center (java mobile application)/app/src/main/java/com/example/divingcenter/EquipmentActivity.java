package com.example.divingcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.divingcenter.Callback.CardData;
import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.View.CardModel;
import com.example.divingcenter.View.Equipment_Order_Adapter;
import com.example.divingcenter.View.Journey_Order_Adapter;
import com.example.divingcenter.View.LicenceOrdderForAdminActivity;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class EquipmentActivity extends AppCompatActivity {
    UserController controller;
    Equipment_Order_Adapter equipment_recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        controller = new  UserController();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.e);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.j:
                        startActivity(new Intent(getApplicationContext(), JourneyActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;
                    case  R.id.e:

                    case R.id.l:
                        startActivity(new Intent(getApplicationContext(), LicenceActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;
                    case R.id.c_o:
                        startActivity(new Intent(getApplicationContext(), OderedCorsesforAdminActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.l_o_a:
                        startActivity(new Intent(getApplicationContext(), LicenceOrdderForAdminActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                }

                return false;
            }
        });
        RecyclerView  recyclerView = findViewById(R.id.equip_or);
        equipment_recycler = new Equipment_Order_Adapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(EquipmentActivity.this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(equipment_recycler);
        get_eq();
    }
private void get_eq(){
        controller.get_card_equipments(new CardData() {
            @Override
            public void onSuccess(List<CardModel> cardModel) {
                equipment_recycler.setList(cardModel);
            }

            @Override
            public void onError(String msg) {

            }
        });
}
}
