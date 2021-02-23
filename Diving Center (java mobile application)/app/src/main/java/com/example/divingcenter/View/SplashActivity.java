package com.example.divingcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.divingcenter.Callback.UserCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Trainer;
import com.example.divingcenter.Model.User;
import com.example.divingcenter.View.Trainer_Recycler_view;
import com.felipecsl.gifimageview.library.GifImageView;
import com.google.android.gms.common.util.IOUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    GifImageView gifImageView;
    FirebaseAuth auth;
    UserController controller;
    private void findResources() {
        gifImageView = findViewById(R.id.gifimageview);
        gifImageView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        controller = new UserController();
        auth = FirebaseAuth.getInstance();
          findResources();
          loading();
    }

    private void loading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                        if(auth.getCurrentUser() != null){
                            controller.getUserData(auth.getCurrentUser().getUid(),
                                    new UserCallback() {
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
                                            if (SharedData.getUser().getType().equals("normalUser")) {
                                                gifImageView.setVisibility(View.GONE);
                                                startActivity(new Intent(SplashActivity.this, PagesActivity.class));
                                                SplashActivity.this.finish();
                                            }
                                            if (SharedData.getUser().getType().equals("trainer")) {
                                                gifImageView.setVisibility(View.GONE);
                                                startActivity(new Intent(SplashActivity.this, ConfirmationActivity.class));
                                                SplashActivity.this.finish();
                                            }
                                        }
                                    });

                        }else {
                        gifImageView.setVisibility(View.GONE);
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        SplashActivity.this.finish();
                    }
                }
        }, 3000);
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("loading.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

