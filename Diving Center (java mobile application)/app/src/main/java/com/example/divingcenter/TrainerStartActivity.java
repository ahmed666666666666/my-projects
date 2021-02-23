package com.example.divingcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.divingcenter.View.HomeActivityTrainer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FederatedAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class TrainerStartActivity extends AppCompatActivity {
    ImageView logout;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_start);
        logout  = findViewById(R.id.log);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.trainer_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.trainer_home :

                    case R.id.trainer_curses:
                        startActivity(new Intent(getApplicationContext(), TrainerCourses.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.mas:
                        startActivity(new Intent(getApplicationContext(), TrainerOredersActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.trainer_profile:
                        startActivity(new Intent(getApplicationContext(), HomeActivityTrainer.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(TrainerStartActivity.this,LoginActivity.class));
            }
        });
    }
}