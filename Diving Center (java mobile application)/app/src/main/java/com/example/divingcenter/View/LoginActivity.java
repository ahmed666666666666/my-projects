package com.example.divingcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.divingcenter.Callback.UserCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Trainer;
import com.example.divingcenter.Model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    UserController userController;
    TextView email;
    TextView password;
    Button logIn;
    TextView sign_up_text;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth  = FirebaseAuth.getInstance();
        userController = new UserController();
        email = findViewById(R.id.user_name);
        password = findViewById(R.id.pass_word);
        logIn = findViewById(R.id.sin_in);
        sign_up_text = findViewById(R.id.sin_up_text);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_in();
            }
        });
        sign_up_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                LoginActivity.this.finish();
            }
        });
    }
    private void log_in() {

        String _email = email.getText().toString().trim();
        String _password = password.getText().toString().trim();

        if (!_email.isEmpty() && !_password.isEmpty()) {
            userController.signIn(_email, _password, new UserCallback() {
                @Override
                public void OnSuccess(String msg) {
                    Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
                    email.setText("");
                    password.setText("");
                    userController.getUserData(auth.getCurrentUser().getUid(), new UserCallback() {
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
                            if(SharedData.getUser().getType().equals("normalUser") || auth.getCurrentUser().getEmail().equals("admin@gmail.com")){
                                startActivity(new Intent(LoginActivity.this,PagesActivity.class));
                                LoginActivity.this.finish();
                            }
                            if(SharedData.getUser().getType().equals("trainer")) {
                                startActivity(new Intent(LoginActivity.this,ConfirmationActivity.class));
                                LoginActivity.this.finish();
                            }
                        }
                    });
                }
                @Override
                public void onError(String msg) {
                    Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
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

                }
            });


        }
    }
}