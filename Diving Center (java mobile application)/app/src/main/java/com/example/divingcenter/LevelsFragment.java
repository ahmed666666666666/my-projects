package com.example.divingcenter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Callback.UserCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.User;
import com.example.divingcenter.View.Levels_recycler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.installations.RandomFidGenerator;
import com.google.j2objc.annotations.ReflectionSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LevelsFragment extends Fragment {
FloatingActionButton add_level;
EditText level_input;
UserController controller ;
List<Levels> a_level;
FirebaseAuth auth;
Levels_recycler levels_recycler;
RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_levels, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        add_level = view.findViewById(R.id.add_level);
        controller = new UserController();
        if(auth.getCurrentUser().getEmail().equals("admin@gmail.com")) {
            add_level.setVisibility(View.VISIBLE);
        }else {
            add_level.setVisibility(View.GONE);
        }
        alert_dialog();
        recyclerView = view.findViewById(R.id.recycler_levels);
        levels_recycler = new Levels_recycler();
        level_input = view.findViewById(R.id.newLev);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(levels_recycler);
        recyclerView.setLayoutManager(layoutManager);
        retrieve_journey();
    }
    private void alert_dialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.level_alert);
        a_level = new ArrayList<>();
        controller = new UserController();
        Button finish = dialog.findViewById(R.id.l_finish);
        Button cancel = dialog.findViewById(R.id.l_cancel);
        EditText newLevel  = dialog.findViewById(R.id.newLev);
        add_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!newLevel.getText().toString().isEmpty()) {
                    final String uuid = UUID.randomUUID().toString();
                    Levels lev = new Levels(newLevel.getText().toString(), uuid);
                    retrieve_journey();
                    a_level.add(lev);
                    controller.add("levels", lev);
                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    newLevel.setText("");
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
    private void retrieve_journey() {
        controller.getlevels(new InfoCallback() {
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
                levels_recycler.setList(levels);
            }
        });
    }
}