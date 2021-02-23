package com.example.divingcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button normalUser;
    Button Trainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        normalUser = findViewById(R.id.normal_user);
        Trainer = findViewById(R.id.trainer);
        Button button = findViewById(R.id.bu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                HomeActivity.this.finish();
            }
        });
    }

    public  void  to_sign_up(View view){
        switch (view.getId()){
            case  R.id.normal_user:{
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                intent.putExtra("type","normalUser");
                intent.putExtra("confirm","accepted");
                startActivity(intent);
                HomeActivity.this.finish();
                break;
            }
            case R.id.trainer :
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                intent.putExtra("type","trainer");
                intent.putExtra("confirm","pending");
                startActivity(intent);
                HomeActivity.this.finish();
        }

    }

}