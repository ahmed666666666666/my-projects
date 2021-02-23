package com.example.divingcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.divingcenter.View.HomeActivityTrainer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_order);
        bottomNavigationView.setSelectedItemId(R.id.j);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.j:
                        startActivity(new Intent(getApplicationContext(), JourneyActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;

                    case R.id.e:
                        startActivity(new Intent(getApplicationContext(), EquipmentActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;

                    case R.id.l:
                        startActivity(new Intent(getApplicationContext(), LicenceActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;

                }

            }
        });

    }
}