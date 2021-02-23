package com.example.divingcenter.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Callback.UserCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.HomeActivity;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.Trainer;
import com.example.divingcenter.Model.User;
import com.example.divingcenter.Orders;
import com.example.divingcenter.R;
import com.example.divingcenter.SharedData;
import com.example.divingcenter.TrainerCourses;
import com.example.divingcenter.TrainerOredersActivity;
import com.example.divingcenter.TrainerStartActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class HomeActivityTrainer extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private StorageReference mStorageRef;
    FirebaseAuth auth;
    EditText name;
    EditText age;
    EditText email;
    Spinner level;
    EditText identity;
    ImageView save;
    UserController userController;
    FloatingActionButton upload;
    Uri imguri;
    ArrayAdapter adapter;
    de.hdodenhof.circleimageview.CircleImageView profile;
    String  levelSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_trainer);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.trainer_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.trainer_curses:
                        startActivity(new Intent(getApplicationContext(), TrainerCourses.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case  R.id.trainer_profile :

                    case R.id.mas:
                        startActivity(new Intent(getApplicationContext(), TrainerOredersActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case  R.id.trainer_home:
                        startActivity(new Intent(getApplicationContext(), TrainerStartActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });

        level = findViewById(R.id.level_pro);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        userController =new UserController();
        auth = FirebaseAuth.getInstance();
        download_pic();
        name = findViewById(R.id._name);
        age = findViewById(R.id._age);
        email = findViewById(R.id._email);
        save = findViewById(R.id.save);
        identity = findViewById(R.id._identity);
        profile = findViewById(R.id.profile_picture);
        upload = findViewById(R.id.upload);
        level.setOnItemSelectedListener(this);
        f_spinner();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

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
                name.setText(SharedData.getUser().getName());
                age.setText(SharedData.getUser().getAge());
                email.setText(SharedData.getUser().getEmail());
                identity.setText(SharedData.getUser().getType());
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userController.update("users",auth.getCurrentUser().getUid(),"name",name.getText().toString());
                userController.update("users",auth.getCurrentUser().getUid(),"age",age.getText().toString());
                userController.update("users",auth.getCurrentUser().getUid(),"email",email.getText().toString());
                userController.update("users",auth.getCurrentUser().getUid(),"type",identity.getText().toString());
                userController.update("users",auth.getCurrentUser().getUid(),"level",levelSelected);
                Toast.makeText(HomeActivityTrainer.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null && data.getData()!=null ){
            imguri = data.getData();

            upload_picture();
        }
    }

    private void upload_picture() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading Image....");
        dialog.show();
        StorageReference riversRef = mStorageRef.child("trainer_pic/"+SharedData.getUser().getKey());
        riversRef.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        download_pic();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        dialog.dismiss();
                        Toast.makeText(HomeActivityTrainer.this,"Unable to upload  ",Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double percentage = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                dialog.setMessage("progress : "+ (int)percentage+"%" );
            }
        });
    }
    private void download_pic(){
        mStorageRef.child("trainer_pic/"+auth.getCurrentUser().getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(HomeActivityTrainer.this).load(uri).into(profile);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
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
                adapter = new ArrayAdapter (HomeActivityTrainer.this, android.R.layout.simple_spinner_dropdown_item,vals);
                level.setAdapter(adapter);
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