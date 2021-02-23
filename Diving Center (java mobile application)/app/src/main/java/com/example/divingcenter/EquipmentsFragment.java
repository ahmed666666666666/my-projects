package com.example.divingcenter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
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
import java.util.UUID;

public class EquipmentsFragment extends Fragment  implements AdapterView.OnItemSelectedListener {
    List<Info> list_eq;
    private StorageReference mStorageRef;
    UserController controller;
    FloatingActionButton e_add;
    String levelSelected;
    RecyclerView recyclerView;
    FirebaseAuth auth;
    Spinner spinner;
    Equipment_Recycler equipment_recycler;
    Uri imguri;
    Context context = getContext();
    de.hdodenhof.circleimageview.CircleImageView eq_pic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_equipments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.eq_layout);
        equipment_recycler = new Equipment_Recycler();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(equipment_recycler);
        retrieve_equipments();
        auth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        e_add = view.findViewById(R.id.e_add);
        if(auth.getCurrentUser().getEmail().equals("admin@gmail.com")){
            e_add.setVisibility(View.VISIBLE);
        }
        else {
            e_add.setVisibility(View.GONE);
        }
        alert_dialog();

    }

    private void alert_dialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.equipment_alert);
        list_eq= new ArrayList<>();
        controller = new UserController();
        Button finish = dialog.findViewById(R.id.ee_finish);
        Button cancel = dialog.findViewById(R.id.ee_cancel);
        spinner = dialog.findViewById(R.id.ee_level);
        spinner.setOnItemSelectedListener(this);
        f_spinner();
        EditText title = dialog.findViewById(R.id.ee_title);
        EditText price = dialog.findViewById(R.id.ee_price);
        EditText quantity = dialog.findViewById(R.id.e_quan);
        FloatingActionButton upload_eq = dialog.findViewById(R.id.upload_equip);
        upload_eq.setVisibility(View.VISIBLE);
         eq_pic = dialog.findViewById(R.id.equip_pic);
        upload_eq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        e_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup av = dialog.findViewById(R.id.ee_avilab);
                int i = av.getCheckedRadioButtonId();
                RadioButton check = dialog.findViewById(i);

                if (!title.getText().toString().isEmpty() && !price.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty()
                        && !price.getText().toString().isEmpty() && imguri != null) {

                    final String uuid = UUID.randomUUID().toString();
                    Info info = new Info(levelSelected, title.getText().toString(),
                            check.getText().toString(), 0,
                            price.getText().toString() + "s" + "L.E", "null", Integer.parseInt(quantity.getText().toString()),
                            imguri.toString(), uuid);
                    list_eq.add(info);
                    controller.add("equipments", info);
                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    upload_picture(eq_pic);
                    title.setText("");
                    price.setText("");
                    quantity.setText("");
                    eq_pic.setImageResource(R.drawable.profile_pic);
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
    private void retrieve_equipments(){
        UserController controller = new UserController();
        controller.get_equipments(new InfoCallback() {
            @Override
            public void onSuccess(List<Info> info) {
                equipment_recycler.setList(info);
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

            }
        });
    }
    private void upload_picture(de.hdodenhof.circleimageview.CircleImageView img) {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setTitle("Uploading Image....");
        final String uuid = UUID.randomUUID().toString();
        dialog.show();
        StorageReference riversRef = mStorageRef.child("equipment_pic/"+uuid);
        riversRef.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imguri = uri;
                                Glide.with(getContext()).load(imguri).into(eq_pic);
                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        dialog.dismiss();
                        Toast.makeText(getContext(),"Unable to upload  ",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null) {
            Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();
            imguri = data.getData();
            eq_pic.setImageURI(imguri);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        levelSelected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,vals);
                spinner.setAdapter(adapter);
            }
        });
    }

}
