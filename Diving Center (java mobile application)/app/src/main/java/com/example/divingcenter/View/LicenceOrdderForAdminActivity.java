package com.example.divingcenter.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.divingcenter.Callback.CardData;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.EquipmentActivity;
import com.example.divingcenter.JourneyActivity;
import com.example.divingcenter.LicenceActivity;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.OderedCorsesforAdminActivity;
import com.example.divingcenter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class LicenceOrdderForAdminActivity extends AppCompatActivity {
    UserController controller;
    List<Courses> list_info;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth ;
    Trainer_Order_AtAdmin_Adapter trainer_recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence_ordder_for_admin);
        firebaseAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.l_o_a);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.j:
                        startActivity(new Intent(getApplicationContext(), JourneyActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.e:
                        startActivity(new Intent(getApplicationContext(), EquipmentActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.l:
                        startActivity(new Intent(getApplicationContext(), LicenceActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case  R.id.c_o:
                        startActivity(new Intent(getApplicationContext(), OderedCorsesforAdminActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.l_o_a:
                        break;
                }
                return false;
            }
        });

        controller =new UserController();
        recyclerView = findViewById(R.id.l_f_a);
        trainer_recycler_view = new Trainer_Order_AtAdmin_Adapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(LicenceOrdderForAdminActivity.this, RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(trainer_recycler_view);
        get_licences();
    }

    private void  get_licences(){
        controller.get_card_licence(new CardData() {
            @Override
            public void onSuccess(List<CardModel> cardModel) {
                trainer_recycler_view.setList(cardModel);
            }
            @Override
            public void onError(String msg) {
            }
        });
    }


}