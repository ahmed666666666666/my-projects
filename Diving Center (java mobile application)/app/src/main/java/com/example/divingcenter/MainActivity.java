package com.example.divingcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Callback.UserCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.Trainer;
import com.example.divingcenter.Model.User;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    UserController userController;
    User user;
    Trainer trainer;
    TextView name;
    TextView age;
    TextView email;
    TextView password;
    TextView confirmPassword;
    Date date;
    Button sigup;
    TextView sing_in_text;
    RadioGroup sex;
    RadioButton sex_check;
    Spinner level;
    String levelSelected;
    ArrayAdapter adapter;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth = FirebaseAuth.getInstance();
        name = findViewById(R.id.firstName);
        age = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        sing_in_text = findViewById(R.id.login_text);
        sex = findViewById(R.id.radioGroup2);
       level = findViewById(R.id.level);
       level.setOnItemSelectedListener(this);
       date = new Date();
        f_spinner();
        level.setAdapter(adapter);
        sigup = findViewById(R.id.signUp);
        sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        sing_in_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                MainActivity.this.finish();
            }
        });

    }
    private void register() {
        int sex_checked = sex.getCheckedRadioButtonId();
        sex_check = findViewById(sex_checked);
        String _name = name.getText().toString().trim();
        String _age = age.getText().toString().trim();
        String user_email = email.getText().toString();
        String user_password = password.getText().toString();
        String user_confirmPassword = confirmPassword.getText().toString();
        String gender = sex_check.getText().toString();
        if(    _name != null &&
                _age != null  &&
                user_email != null &&
                user_password != null &&
                user_confirmPassword != null &&
                gender != null &&
               // levelSelected != null &&
                emailValidation(user_email) &&
                confirmPassword(user_password, user_confirmPassword)) {
            user = new User(
                    name.getText().toString(),
                    age.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString(),gender
                    ,levelSelected,get_status(),get_confirmation(),date.getTime()

            );
            userController = new UserController();
            userController.Register( user, new UserCallback()                                                                                                                                                               {
                @Override
                public void OnSuccess(String msg) {
                    SharedData.setUser(user);
                    name.setText("");
                    age.setText("");
                    email.setText("");
                    password.setText("");
                    confirmPassword.setText("");
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    if(get_status().equals("trainer")){
                        startActivity(new Intent(MainActivity.this,ConfirmationActivity.class));
                        MainActivity.this.finish();
                    }else {
                        startActivity(new Intent(MainActivity.this, PagesActivity.class));
                        MainActivity.this.finish();
                    }
                }

                @Override
                public void onError(String msg) {
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
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
    private boolean emailValidation(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private boolean confirmPassword(String password, String confirm) {
        return password.equals(confirm);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.level) {

             levelSelected = parent.getItemAtPosition(position).toString();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    private  String get_status(){
        return getIntent().getStringExtra("type");
    }
    private  String get_confirmation(){
        return getIntent().getStringExtra("confirm");
    }
    private void f_spinner() {
        UserController userController = new UserController();
        userController.getlevels(new InfoCallback() {
            @Override
            public void onSuccess(List<Info> info) {

            }

            @Override
            public void onOSuccess(List<Orders> info) {

            }

            @Override
            public void onCSuccess(List<Courses> courses) {

            }

            @Override
            public void onSuccess(String msg) {

            }

            @Override
            public void onError(String msg) {

            }

            @Override
            public void success(List<Levels> levels) {
                List<String> vals = new ArrayList<>();
                for (Levels levels1 : levels) {
                    vals.add(levels1.getLevel());
                }
                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, vals);
                level.setAdapter(adapter);
            }
        });
    }
}