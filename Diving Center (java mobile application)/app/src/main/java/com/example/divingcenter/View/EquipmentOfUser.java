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
import com.example.divingcenter.View.Equipments_orders_user_dapter;
import com.example.divingcenter.View.Journy_orders_user_dapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class EquipmentOfUser extends AppCompatActivity {
    Equipments_orders_user_dapter equipments_orders_user_dapter;
    RecyclerView recyclerView;
    FirebaseAuth auth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_of_user);
        auth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation2);
        bottomNavigationView.setSelectedItemId(R.id.Equipments_order);
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
                    case R.id.Licences_order:
                        startActivity(new Intent(getApplicationContext(), LicenceatUserSideActivity.class));
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
        recyclerView = findViewById(R.id.e);
        equipments_orders_user_dapter = new Equipments_orders_user_dapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(EquipmentOfUser.this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(equipments_orders_user_dapter);
        get_Equipments();
    }

    private void get_Equipments() {
        UserController controller =new UserController();
        controller.get_card_equipments_u(new CardData() {
            @Override
            public void onSuccess(List<CardModel> cardModel) {
                equipments_orders_user_dapter.setList(cardModel);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}



