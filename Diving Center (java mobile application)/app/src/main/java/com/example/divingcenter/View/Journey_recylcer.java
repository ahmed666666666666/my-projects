package com.example.divingcenter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.style.IconMarginSpan;
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

import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.User;
import com.example.divingcenter.R;
import com.example.divingcenter.View.Levels_recycler;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Journey_recylcer extends RecyclerView.Adapter<Journey_recylcer.Holder> implements   AdapterView.OnItemSelectedListener{
    String  levelSelected;
    Spinner spinner;
    List<String> vals;
    ArrayAdapter adapter;
    private List<Info> list;
    String availability = "notReserved";
    UserController controller;
    FirebaseAuth auth;
    public void setList(List<Info> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        adapter = new ArrayAdapter(parent.getContext(), android.R.layout.simple_spinner_dropdown_item,vals);
            auth = FirebaseAuth.getInstance();
        return new Journey_recylcer.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.journey_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
                    holder.title.setText(list.get(position).getTitle());
                    holder.member.setText(String.valueOf(list.get(position).getMemebers()));
                    holder.price.setText(list.get(position).getPrice());
                    holder.level.setText(list.get(position).getJ_level());
                    holder.description.setText(list.get(position).getDescription());
                    holder.avilability.setText(list.get(position).getAvilability());
        if(auth.getCurrentUser().getEmail().equals("admin@gmail.com")) {
                        holder.remove.setVisibility(View.VISIBLE);
                        holder.pay.setVisibility(View.GONE);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alert_dialog(v, position);
                            }
                        });
                        holder.remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                              new AlertDialog.Builder(holder.itemView.getContext()).setMessage("Are you sure")
                                      .setTitle("Delete")
                                      .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {
                                      controller = new UserController();
                                      controller.delete("journey",list.get(position).getKey());
                                      retrieve_journey();
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
                        holder.remove.setVisibility(View.GONE);
                        holder.pay.setVisibility(View.VISIBLE);
                    }
                    holder.pay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           UserController controller = new UserController();
                            controller.get("journeyOf" + auth.getCurrentUser().getUid(),list.get(position).getKey(), new InfoCallback() {
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
                                    if (list.get(position).getMemebers() > 0 && availability.equals("notReserved")) {
                                        Intent intent = new Intent(holder.itemView.getContext(),VisaActivity.class);
                                        intent.putExtra("key",list.get(position).getKey());
                                        intent.putExtra("type","1");
                                        intent.putExtra("id",list.get(position).getTitle());
                                        intent.putExtra("number",list.get(position).getMemebers());
                                        holder.itemView.getContext().startActivity(intent);
                                    }else {
                                        Toast.makeText(holder.itemView.getContext(),"not valid",Toast.LENGTH_SHORT).show();
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

    }

    private void get(String key) {
        UserController controller = new UserController();

    }


    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        else
            return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView avilability;
        TextView price;
        TextView level;
        TextView member;
        ImageView remove;
        Button pay;
        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.jj_title);
            description = itemView.findViewById(R.id.content_des);
            avilability = itemView.findViewById(R.id.avilability);
            price = itemView.findViewById(R.id.price);
            level = itemView.findViewById(R.id.jj_level);
            member = itemView.findViewById(R.id.member);
            remove = itemView.findViewById(R.id.remove_journey);
            pay = itemView.findViewById(R.id.j_pay);
        }
    }
    private void alert_dialog(View v,int posistion){
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.journy_alert);
        dialog.show();
        spinner = dialog.findViewById(R.id.j_level);
        spinner.setOnItemSelectedListener(this);
        f_spinner(v);
        Button finish = dialog.findViewById(R.id._finish);
        Button cancel = dialog.findViewById(R.id.cancel);
        EditText title = dialog.findViewById(R.id.j_title);
        EditText memebers = dialog.findViewById(R.id.j_mem);
        EditText price = dialog.findViewById(R.id.j_price);
        EditText description = dialog.findViewById(R.id.j_des);
        title.setText(list.get(posistion).getTitle());
        memebers.setText(String.valueOf(list.get(posistion).getMemebers()));
        price.setText(list.get(posistion).getPrice());
        description.setText(list.get(posistion).getDescription());
        spinner.setSelection(getIndex(spinner,list.get(posistion).getJ_level()));
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup av = dialog.findViewById(R.id.j_avilab);
                int i = av.getCheckedRadioButtonId();
                RadioButton check = dialog.findViewById(i);
                Info info = new Info(levelSelected,title.getText().toString(),
                        check.getText().toString(),Integer.parseInt(memebers.getText().toString()),
                        price.getText().toString(),description.getText().toString(),0,"s",list.get(posistion).getKey());
                         controller = new UserController();
                         controller.update("journey",list.get(posistion).getKey(),info);
                         Toast.makeText(v.getContext(),"success",Toast.LENGTH_SHORT).show();
                             Log.d("s", "onClick: sucesssssssssss ");
                         dialog.dismiss();
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.j_level){
            levelSelected = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                 adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item,vals);
                spinner.setAdapter(adapter);

            }
        });
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

