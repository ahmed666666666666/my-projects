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
import com.example.divingcenter.View.CardModel;
import com.example.divingcenter.View.LicenceOrdderForAdminActivity;
import com.example.divingcenter.View.Trainer_Order_Adapter;
import com.example.divingcenter.View.Trainer_Order_AtAdmin_Adapter;
import com.example.divingcenter.View.Trainer_cources_Recycler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class OderedCorsesforAdminActivity extends AppCompatActivity {

    UserController controller;
    List<Courses> list_info;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth ;
    Trainer_Order_AtAdmin_Adapter trainer_recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odered_corsesfor_admin);
        firebaseAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.c_o);
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
                    case R.id.l_o_a:
                        startActivity(new Intent(getApplicationContext(), LicenceOrdderForAdminActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });
        controller =new UserController();
        recyclerView = findViewById(R.id.c_f_a);
        trainer_recycler_view = new Trainer_Order_AtAdmin_Adapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(OderedCorsesforAdminActivity.this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(trainer_recycler_view);
        get_courses();
    }
    private void  get_courses(){
        controller.get_card_courses(new CardData() {
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