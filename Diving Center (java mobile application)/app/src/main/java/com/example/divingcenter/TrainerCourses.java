package com.example.divingcenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Callback.UserCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Available;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.Trainer;
import com.example.divingcenter.Model.User;
import com.example.divingcenter.View.HomeActivityTrainer;
import com.example.divingcenter.View.Trainer_Recycler_view;
import com.example.divingcenter.View.Trainer_cources_Recycler;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TrainerCourses extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    FloatingActionButton j_add;
    UserController controller;
    List<Courses> list_info;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth ;
    String levelSelected;
    Trainer_cources_Recycler trainer_recycler_view;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_courses);
        controller =new UserController();
        recyclerView = findViewById(R.id.trainer_courses_layout);
        trainer_recycler_view = new Trainer_cources_Recycler();
        LinearLayoutManager layoutManager = new LinearLayoutManager(TrainerCourses.this,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(trainer_recycler_view);
        get_courses();
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.trainer_curses);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.trainer_home:
                        startActivity(new Intent(getApplicationContext(), TrainerStartActivity.class));
                        finish();
                        overridePendingTransition(0,0);

                    case R.id.trainer_profile:
                        startActivity(new Intent(getApplicationContext(), HomeActivityTrainer.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;

                    case R.id.trainer_curses:
                    case R.id.mas:
                        startActivity(new Intent(getApplicationContext(), TrainerOredersActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        j_add = findViewById(R.id.j_add_trainer);
        alert_dialog();

    }
    private void alert_dialog(){
        Dialog dialog = new Dialog(TrainerCourses.this);
        dialog.setContentView(R.layout.courses_alert);
        list_info = new ArrayList<>();
        spinner = dialog.findViewById(R.id.c_levels);
        spinner.setOnItemSelectedListener(this);
        f_spinner();
        controller = new UserController();
        Button finish = dialog.findViewById(R.id.finish);
        Button cancel = dialog.findViewById(R.id.cancel);
        EditText title = dialog.findViewById(R.id.c_title);
        EditText memebers = dialog.findViewById(R.id.c_mem);
        EditText price = dialog.findViewById(R.id.c_price);
        EditText description = dialog.findViewById(R.id.c_des);

        j_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup av = dialog.findViewById(R.id.c_avilab);
                int i = av.getCheckedRadioButtonId();
                RadioButton check = dialog.findViewById(i);
                final String uuid = UUID.randomUUID().toString();
                if(!title.getText().toString().isEmpty() && !memebers.getText().toString().isEmpty()
                        && !price.getText().toString().isEmpty() && !description.getText().toString().isEmpty() ) {
                    Courses info = new Courses(levelSelected, title.getText().toString(), check.getText().toString(),
                            Integer.parseInt(memebers.getText().toString()), price.getText().toString(), description.getText().toString(), uuid,
                            firebaseAuth.getCurrentUser().getUid());

                    //retrieve_journey();
                    list_info.add(info);
                    controller.add("courses", info);
                    Toast.makeText(TrainerCourses.this, "add success", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

    }
private void  get_courses(){
        controller.get(new InfoCallback() {
            @Override
            public void onSuccess(List<Info> info) {

            }

            @Override
            public void onOSuccess(List<Orders> info) {

            }

            @Override
            public void onCSuccess(List<Courses> courses) {
                list_info = courses;
                trainer_recycler_view.setList(list_info);
            }

            @Override
            public void onSuccess(String msg) {

            }

            @Override
            public void onError(String msg) {

            }

            @Override
            public void success(List<Levels> levels) {

            }
        });
}
    private void f_spinner(){
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
                for(Levels levels1 : levels){
                    vals.add(levels1.getLevel());
                }
                ArrayAdapter adapter = new ArrayAdapter(TrainerCourses.this, android.R.layout.simple_spinner_dropdown_item,vals);
                spinner.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             levelSelected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}