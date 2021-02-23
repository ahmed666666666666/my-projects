package com.example.divingcenter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.OrderPending;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Licence_recycler extends  RecyclerView.Adapter<Licence_recycler.Holder> implements AdapterView.OnItemSelectedListener  {
    private List<Info> list;
    UserController controller;
    Spinner level;
    String levelSelected;
    String availability = "notReserved";
    FirebaseAuth auth;
    public void setList(List<Info> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Licence_recycler.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        controller = new UserController();
        auth = FirebaseAuth.getInstance();
        return new Licence_recycler.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.licence_layot, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Licence_recycler.Holder holder, int position) {
        get(list.get(position).getKey());
        get(list.get(position).getKey());
        holder.title.setText(list.get(position).getTitle());
        holder.price.setText(list.get(position).getPrice());
        holder.level.setText(list.get(position).getJ_level());
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserController controller = new UserController();
                controller.get("licencesOf" + auth.getCurrentUser().getUid(), list.get(position).getKey(), new InfoCallback() {
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
                        if(msg != null){
                            availability = msg;
                        }else {
                            availability = "notReserved";
                        }
                        if (availability.equals("notReserved") || availability.equals("rejected") ){
                            final String uuid = UUID.randomUUID().toString();
                            OrderPending orders = new OrderPending(list.get(position).getTitle(),uuid,auth.getCurrentUser().getUid(),
                                    "pending",auth.getCurrentUser().getUid());
                            controller.add("LicenceOrders",orders);
                            Orders orders1 = new Orders(list.get(position).getTitle(),list.get(position).getKey(),auth.getCurrentUser().getEmail(),"Reserved");
                            controller.add("licencesOf"+auth.getCurrentUser().getUid(),orders1);

                        }else {
                            Toast.makeText(holder.itemView.getContext(), "not valid", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(String msg) {

                    }

                    @Override
                    public void success(List<Levels> levels) {

                    }
                });
            }
        });
        if(auth.getCurrentUser().getEmail().equals("admin@gmail.com")) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.pay.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert_dialog(v, position);
                }
            });
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    new AlertDialog.Builder(holder.itemView.getContext()).setMessage("Are you sure")
                            .setTitle("Delete")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    controller = new UserController();
                                    controller.delete("licences", list.get(position).getKey());
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            });
        }else {
            holder.imageView.setVisibility(View.GONE);
            holder.pay.setVisibility(View.VISIBLE);
        }

        }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        else
            return list.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        levelSelected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView title;
        TextView price;
        TextView level;
        ImageView imageView;
        Button pay;
        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.l_name);
            price = itemView.findViewById(R.id.l_price);
            level = itemView.findViewById(R.id.l_av);
            imageView = itemView.findViewById(R.id.rem);
            pay = itemView.findViewById(R.id.l_pay);
        }
    }
    private void alert_dialog(View v,int position){
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.license_alert);
        dialog.show();
        level = dialog.findViewById(R.id.level_licences);
        level.setOnItemSelectedListener(this);
        f_spinner(v);
        Button finish = dialog.findViewById(R.id.l_finish);
        Button cancel = dialog.findViewById(R.id.l_cancel);
       // EditText level = dialog.findViewById(R.id.ll_level);
        EditText title = dialog.findViewById(R.id.ll_title);
        EditText price = dialog.findViewById(R.id.ll_price);
       // EditText quantity = dialog.findViewById(R.id.ll_quantity);
        title.setText(list.get(position).getTitle());
        price.setText(list.get(position).getPrice());
      //  level.setText(list.get(position).getJ_level());
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!title.getText().toString().isEmpty() && !price.getText().toString().isEmpty()
                        ) {
                    Info info = new Info(levelSelected, title.getText().toString(),
                            "null", 0,
                            price.getText().toString(), "k",0, "s",
                            list.get(position).getKey());
                    controller = new UserController();
                    controller.update("licences", list.get(position).getKey(), info);
                    Toast.makeText(v.getContext(), "success", Toast.LENGTH_SHORT).show();
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
    private void f_spinner(View v){
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
               ArrayAdapter adapter1 = new ArrayAdapter(v.getContext(), android.R.layout.simple_spinner_dropdown_item,vals);
                level.setAdapter(adapter1);
            }
        });


    }

    private void retrieve_licenes(){
        controller.get_licence(new InfoCallback() {
            @Override
            public void onSuccess(List<Info> info) {
                setList(info);
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

    private void get(String key) {
    }
}