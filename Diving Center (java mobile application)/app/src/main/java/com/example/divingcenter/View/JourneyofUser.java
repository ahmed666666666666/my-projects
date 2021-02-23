package com.example.divingcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.divingcenter.Callback.CardData;
import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.User;
import com.example.divingcenter.View.CardModel;
import com.example.divingcenter.View.Journey_Order_Adapter;
import com.example.divingcenter.View.Journy_orders_user_dapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;

import java.util.List;

public class JourneyofUser extends AppCompatActivity {
    Journy_orders_user_dapter journy_orders_user_dapter;
    RecyclerView recyclerView;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journeyof_user);
        auth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation2);
        bottomNavigationView.setSelectedItemId(R.id.journey_order);
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
                        startActivity(new Intent(getApplicationContext(), EquipmentOfUser.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
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

                }
                return false;
            }
        });
        recyclerView = findViewById(R.id.order_journey);
        journy_orders_user_dapter = new Journy_orders_user_dapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(JourneyofUser.this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(journy_orders_user_dapter);
        get_journies();
    }

    private void get_journies() {
        UserController controller =new UserController();
        controller.get_card_jurney(new CardData() {
            @Override
            public void onSuccess(List<CardModel> cardModel) {
                journy_orders_user_dapter.setList(cardModel);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}