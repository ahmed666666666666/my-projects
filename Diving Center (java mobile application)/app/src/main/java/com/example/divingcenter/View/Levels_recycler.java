package com.example.divingcenter.View;


import android.app.Dialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.User;
import com.example.divingcenter.Orders;
import com.example.divingcenter.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Levels_recycler  extends RecyclerView.Adapter<Levels_recycler.Holder> {
    UserController controller;
    private List<Levels> list;
    FirebaseAuth auth;
    public void setList(List<Levels> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.levels_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.textView.setText(list.get(position).getLevel());
        if(auth.getCurrentUser().getEmail().equals("admin@gmail.com")) {
            holder.rem.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert_dialog(v, position);
                }
            });
            holder.rem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(holder.itemView.getContext());
                    dialog.setContentView(R.layout.confirm_delete);
                    dialog.show();
                    Button ok = dialog.findViewById(R.id._Yes);
                    Button no  = dialog.findViewById(R.id._no);
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            controller = new UserController();
                            controller.delete("levels", list.get(position).getKey());
                            retrieve_levels();
                            dialog.dismiss();
                            Toast.makeText(holder.textView.getContext(),"Deleted",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else {
            holder.rem.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (list == null)
        return 0;
            else
            return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView textView ;
        ImageView rem;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.level_text);
            rem = itemView.findViewById(R.id.lev_del);
        }
    }
    private void alert_dialog(View v,int posistion){
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.level_alert);
        dialog.show();
        Button finish = dialog.findViewById(R.id.l_finish);
        Button cancel = dialog.findViewById(R.id.l_cancel);
        EditText newLevel  = dialog.findViewById(R.id.newLev);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller = new UserController();
                if(!newLevel.getText().toString().isEmpty()) {
                    Levels lev = new Levels(newLevel.getText().toString(), list.get(posistion).getKey());
                    controller.update("levels", list.get(posistion).getKey(), lev);
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
    private void retrieve_levels() {
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
                setList(levels);
            }
        });
    }
}