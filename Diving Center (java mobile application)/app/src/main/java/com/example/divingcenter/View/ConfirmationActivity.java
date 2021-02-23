package com.example.divingcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.divingcenter.Callback.UserCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Trainer;
import com.example.divingcenter.Model.User;
import com.example.divingcenter.View.HomeActivityTrainer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.util.GAuthToken;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    ImageView logout;
    ImageView status_pic ;
    Button sign ;
    Button home;
    TextView status_text;
    UserController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        controller = new UserController();
        status_text = findViewById(R.id.t_t_pending);
        status_pic = findViewById(R.id.imageView4);
        sign = findViewById(R.id.t_sign_in);
        home = findViewById(R.id.t_home);
        auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.log_out);
       sign.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(ConfirmationActivity.this, HomeActivityTrainer.class));
               ConfirmationActivity.this.finish();
           }
       });

       home.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(ConfirmationActivity.this,HomeActivity.class));
               ConfirmationActivity.this.finish();
                auth.signOut();
           }
       });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(ConfirmationActivity.this,LoginActivity.class));
                ConfirmationActivity.this.finish();
            }
        });
        controller.getUserData(auth.getCurrentUser().getUid(), new UserCallback() {
            @Override
            public void OnSuccess(String msg) {

            }

            @Override
            public void onError(String msg) {

            }

            @Override
            public void OnSuccess(List<User> users) {

            }

            @Override
            public void OnCSuccess(List<User> users) {

            }

            @Override
            public void onSuccess(List<Trainer> trainer) {

            }

            @Override
            public void OnSuccessL(List<String> users) {

            }

            @Override
            public void OnSuccess(ArrayList<User> users) {

            }

            @Override
            public void OnSuccess(User user) {
                SharedData.setUser(user);
                if(SharedData.getUser().getConfirmation().equals("accepted") && !auth.getCurrentUser().getEmail().equals("admin@gmail.com")){
                    status_pic.setImageResource(R.drawable.accepted);
                    status_text.setVisibility(View.GONE);
                    sign.setVisibility(View.GONE);
                    home.setVisibility(View.GONE);
                    startActivity(new Intent(ConfirmationActivity.this, TrainerStartActivity.class));
                    ConfirmationActivity.this.finish();
                }
                if(SharedData.getUser().getConfirmation().equals("rejected")){
                    status_pic.setImageResource(R.drawable.reject);
                    status_text.setVisibility(View.GONE);
                    sign.setVisibility(View.INVISIBLE);
                    home.setVisibility(View.VISIBLE);
                    ConfirmationActivity.this.finish();
                }
            }
        });
    }
}