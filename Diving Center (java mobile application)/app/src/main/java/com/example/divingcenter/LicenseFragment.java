package com.example.divingcenter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class LicenseFragment extends Fragment implements AdapterView.OnItemSelectedListener {
FloatingActionButton l_add;
    UserController controller;
    ArrayAdapter adapter;
    List<Info> list_info;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    Licence_recycler licence_recycler;
    Spinner level;
    String levelSelected;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_license, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        auth = FirebaseAuth.getInstance();
        l_add = view.findViewById(R.id.l_add);
        if(auth.getCurrentUser().getEmail().equals("admin@gmail.com")) {
            l_add.setVisibility(View.VISIBLE);

        }else {
            l_add.setVisibility(View.GONE);
        }
        alert_dialog();
        recyclerView = view.findViewById(R.id.l_layout);
        licence_recycler = new Licence_recycler();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(licence_recycler);
        retrieve_licenes();
    }
    private void alert_dialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.license_alert);
        level = dialog.findViewById(R.id.level_licences);
        level.setOnItemSelectedListener(this);
        f_spinner();
        list_info = new ArrayList<>();
        controller = new UserController();
        Button finish = dialog.findViewById(R.id.l_finish);
        Button cancel = dialog.findViewById(R.id.l_cancel);
        EditText title = dialog.findViewById(R.id.ll_title);
        EditText price = dialog.findViewById(R.id.ll_price);
       // EditText quantity = dialog.findViewById(R.id.ll_quantity);
        l_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!title.getText().toString().isEmpty()
                        && !price.getText().toString().isEmpty()) {

                    final String uuid = UUID.randomUUID().toString();
                    Info info = new Info(levelSelected, title.getText().toString(),
                            "null", 0,
                            price.getText().toString() + " " + "L.E", "", 0, "d", uuid);
                    list_info.add(info);
                    controller.add("licences", info);
                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    title.setText("");
                    price.setText("");

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
    private void retrieve_licenes(){
        controller.get_licence(new InfoCallback() {
            @Override
            public void onSuccess(List<Info> info) {
                list_info=info;
                licence_recycler.setList(info);
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
                adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,vals);
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