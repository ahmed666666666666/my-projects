package com.example.divingcenter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.divingcenter.Callback.ImageUri;
import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Equipment_Recycler extends  RecyclerView.Adapter<Equipment_Recycler.Holder>implements   AdapterView.OnItemSelectedListener {
    private List<Info> list;
    UserController controller;
    FirebaseAuth auth ;
    String availability = "notReserved";
    String  levelSelected;
    Spinner spinner;

    public void setList(List<Info> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Equipment_Recycler.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            auth = FirebaseAuth.getInstance();
         controller = new UserController();
        return new Equipment_Recycler.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Equipment_Recycler.Holder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.price.setText(list.get(position).getPrice());
        holder.level.setText(list.get(position).getJ_level());
        holder.quantity.setText(String.valueOf(list.get(position).getQuantity()));
        holder.avilability.setText(list.get(position).getAvilability());
        Glide.with(holder.itemView.getContext()).load(Uri.parse(list.get(position).getUri())).into(holder.imageView_eq);
        if(auth.getCurrentUser().getEmail().equals("admin@gmail.com")){
            holder.rem.setVisibility(View.VISIBLE);
            holder.pay.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert_dialog(v,position);
                }
            });
            holder.rem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(holder.itemView.getContext()).setMessage("Are you sure")
                            .setTitle("Delete")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    controller = new UserController();
                                    controller.delete("equipments",list.get(position).getKey());
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
            holder.rem.setVisibility(View.GONE);
            holder.pay.setVisibility(View.VISIBLE);
        }

        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getQuantity() > 0) {

                    Intent intent = new Intent(holder.itemView.getContext(),VisaActivity.class);
                    intent.putExtra("key",list.get(position).getKey());
                    intent.putExtra("type","2");
                    intent.putExtra("id",list.get(position).getTitle());
                    intent.putExtra("number",list.get(position).getQuantity());
                    intent.putExtra("increment_id",list.get(position).getKey());
                    holder.itemView.getContext().startActivity(intent);
                                }else {
                    Toast.makeText(holder.itemView.getContext(),"not valid",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        if(parent.getId() == R.id.ee_level){
            levelSelected = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView title;
        TextView avilability;
        TextView price;
        TextView level;
        TextView quantity;
        ImageView rem;
        ImageView imageView_eq ;
        Button pay;
        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.eq_title);
            avilability = itemView.findViewById(R.id.av_eq);
            price = itemView.findViewById(R.id.eq_price);
            level = itemView.findViewById(R.id.eq_lev);
            quantity = itemView.findViewById(R.id.quantity);
            rem = itemView.findViewById(R.id.rej);
            imageView_eq = itemView.findViewById(R.id.i_e);
            pay = itemView.findViewById(R.id.e_pay);
        }
    }

    private void alert_dialog(View v,int posistion){
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.equipment_alert);
        dialog.show();
        spinner = dialog.findViewById(R.id.ee_level);
        spinner.setOnItemSelectedListener(this);
        f_spinner(v);
        FloatingActionButton upload = dialog.findViewById(R.id.upload_equip);
        Button finish = dialog.findViewById(R.id.ee_finish);
        Button cancel = dialog.findViewById(R.id.ee_cancel);
        EditText title = dialog.findViewById(R.id.ee_title);
        EditText price = dialog.findViewById(R.id.ee_price);
        EditText quantity = dialog.findViewById(R.id.e_quan);
        de.hdodenhof.circleimageview.CircleImageView img = dialog.findViewById(R.id.equip_pic);

        upload.setVisibility(View.GONE);
        title.setText(list.get(posistion).getTitle());
        price.setText(list.get(posistion).getPrice());
        quantity.setText(String.valueOf(list.get(posistion).getQuantity()));
        Glide.with(v.getContext()).load(Uri.parse(list.get(posistion).getUri())).into(img);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup av = dialog.findViewById(R.id.ee_avilab);
                int i = av.getCheckedRadioButtonId();
                RadioButton check = dialog.findViewById(i);
                if ( levelSelected != null && price.getText().toString() != null && quantity.getText().toString() != null) {
                        Info info = new Info(levelSelected, title.getText().toString(),
                            check.getText().toString(), 0,
                            price.getText().toString(), "null", Integer.parseInt(quantity.getText().toString()), list.get(posistion).getUri(), list.get(posistion).getKey());
                    controller = new UserController();
                    controller.update("equipments", list.get(posistion).getKey(), info);
                    Toast.makeText(v.getContext(), "success", Toast.LENGTH_SHORT).show();
                    Log.d("s", "onClick: sucesssssssssss ");
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
                List<String> vals = new ArrayList<>();
                for(Levels levels1 : levels){
                    vals.add(levels1.getLevel());
                }
                ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item,vals);
                spinner.setAdapter(adapter);
            }
        });
    }
    private void retrieve_equipments(){
        UserController controller = new UserController();
        controller.get_equipments(new InfoCallback() {
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

}