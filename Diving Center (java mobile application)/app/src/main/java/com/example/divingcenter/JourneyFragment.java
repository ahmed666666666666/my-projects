package com.example.divingcenter;

import android.app.Dialog;
import android.graphics.ImageFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;;
import com.example.divingcenter.Model.Levels;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JourneyFragment extends Fragment implements AdapterView.OnItemSelectedListener  {
FloatingActionButton j_add;
UserController controller;
List<Info> list_info;
String levelSelected;
Spinner spinner;
RecyclerView recyclerView;
FirebaseAuth firebaseAuth ;
Journey_recylcer journey_recylcer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_journey, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        j_add = view.findViewById(R.id.j_add);
        if(firebaseAuth.getCurrentUser().getEmail().equals("admin@gmail.com"))
            j_add.setVisibility(View.VISIBLE);
        else
            j_add.setVisibility(View.GONE);
            alert_dialog();
            recyclerView = view.findViewById(R.id.journey_layout);
        journey_recylcer = new Journey_recylcer();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(journey_recylcer);
        retrieve_journey();
    }
    private void alert_dialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.journy_alert);
        spinner = dialog.findViewById(R.id.j_level);
        spinner.setOnItemSelectedListener(this);
        f_spinner();
        list_info = new ArrayList<>();
        controller = new UserController();
        Button finish = dialog.findViewById(R.id._finish);
        Button cancel = dialog.findViewById(R.id.cancel);
        EditText title = dialog.findViewById(R.id.j_title);
        EditText memebers = dialog.findViewById(R.id.j_mem);
        EditText price = dialog.findViewById(R.id.j_price);
        EditText description = dialog.findViewById(R.id.j_des);

        j_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup av = dialog.findViewById(R.id.j_avilab);
                int i = av.getCheckedRadioButtonId();
                RadioButton check = dialog.findViewById(i);
                if(!title.getText().toString().isEmpty() && !memebers.getText().toString().isEmpty() &&  !description.getText().toString().isEmpty()
               && !price.getText().toString().isEmpty()) {
                    final String uuid = UUID.randomUUID().toString();
                    Info info = new Info(levelSelected, title.getText().toString(),
                            check.getText().toString(), Integer.parseInt(memebers.getText().toString()),
                            price.getText().toString() + " k" + "L.E", description.getText().toString(), 0, "ss", uuid);
                    controller.add("journey", info);
                    list_info.add(info);
                    Toast.makeText(getContext(), "add success", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    title.setText("");
                    price.setText("");
                    description.setText("");
                    memebers.setText("");
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
    private void retrieve_journey(){
        controller.get_journey(new InfoCallback() {
            @Override
            public void onSuccess(List<Info> info) {
                journey_recylcer.setList(info);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.j_level){
            levelSelected = parent.getItemAtPosition(position).toString();
        }
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