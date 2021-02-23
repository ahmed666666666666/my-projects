package com.example.divingcenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Callback.UserCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.Trainer;
import com.example.divingcenter.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {
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
    List<String> vals;
    ArrayAdapter adapter;
    de.hdodenhof.circleimageview.CircleImageView profile;
    String  levelSelected;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new ArrayAdapter (container.getContext(), android.R.layout.simple_spinner_dropdown_item,vals);
        vals = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        level = view.findViewById(R.id.level_pro);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        userController =new UserController();
        auth = FirebaseAuth.getInstance();
    name = view.findViewById(R.id._name);
    age = view.findViewById(R.id._age);
    email = view.findViewById(R.id._email);
    save = view.findViewById(R.id.save);
    identity = view.findViewById(R.id._identityy);
    profile = view.findViewById(R.id.profile_picture);
    upload = view.findViewById(R.id.upload);
        TextView textView = view.findViewById(R.id.textView9);
        if(auth.getCurrentUser().getEmail().equals("admin@gmail.com")){
            level.setVisibility(View.INVISIBLE);
            identity.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        } else {
            level.setVisibility(View.VISIBLE);
            identity.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
        level.setOnItemSelectedListener(this);
        f_spinner(view);
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
            level.setSelection(getIndex(level,SharedData.getUser().getLevel()));
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
                   level.setSelection(getIndex(level,user.getLevel()));
               }
           });
           if(imguri != null) {
               upload_picture();
           }
            Toast.makeText(view.getContext(),"Saved Successfully",Toast.LENGTH_SHORT).show();
        }
    });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null && data.getData()!=null ){
            imguri = data.getData();
            profile.setImageURI(imguri);
        }
    }

    private void upload_picture() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setTitle("Uploading Image....");
        dialog.show();
        StorageReference riversRef = mStorageRef.child("profile/"+SharedData.getUser().getKey());
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
                        Toast.makeText(getContext(),"Unable to upload  ",Toast.LENGTH_SHORT).show();
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
     mStorageRef.child("profile/"+SharedData.getUser().getKey()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
         @Override
         public void onSuccess(Uri uri) {
             Glide.with(getContext()).load(uri).into(profile);
         }
     });
    }

    @Override
    public void onStart() {
        super.onStart();
    download_pic();
    }
    private void f_spinner(View view){
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
                vals = new ArrayList<>();
                for(Levels levels1 : levels){
                    vals.add(levels1.getLevel());
                }
                adapter = new ArrayAdapter (view.getContext(), android.R.layout.simple_spinner_dropdown_item,vals);
                level.setAdapter(adapter);
                level.setSelection(adapter.getPosition(levelSelected));
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

    private int getIndex(Spinner spinner ,String s) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)) {
                return i;
            }
        }
        return  0;
    }
}