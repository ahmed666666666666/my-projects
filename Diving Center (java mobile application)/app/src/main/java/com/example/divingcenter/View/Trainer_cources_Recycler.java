package com.example.divingcenter.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.example.divingcenter.Journey_recylcer;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Orders;
import com.example.divingcenter.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Trainer_cources_Recycler extends RecyclerView.Adapter<Trainer_cources_Recycler.Holder> implements AdapterView.OnItemSelectedListener {
    String  levelSelected;
    private List<Courses> list;
    Spinner spinner;
    UserController controller;
    FirebaseAuth auth;
    public void setList(List<Courses> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        return new Trainer_cources_Recycler.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_courses_layout, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        controller = new UserController();
        holder.title.setText(list.get(position).getTitle());
        holder.member.setText(String.valueOf(list.get(position).getMemebers()));
        holder.price.setText(list.get(position).getPrice());
        holder.level.setText(list.get(position).getJ_level());
        holder.description.setText(list.get(position).getDescription());
        holder.avilability.setText(list.get(position).getAvilability());

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
                                    controller.delete("courses", list.get(position).getId());
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
        }


    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        else
            return list.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.c_levels){
            levelSelected = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    static class Holder extends RecyclerView.ViewHolder {
    TextView title;
    TextView description;
    TextView avilability;
    TextView price;
    TextView level;
    TextView member;
    ImageView remove;
    public Holder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.jj_title);
        description = itemView.findViewById(R.id.content_des);
        avilability = itemView.findViewById(R.id.avilability);
        price = itemView.findViewById(R.id.price);
        level = itemView.findViewById(R.id.jj_level);
        member = itemView.findViewById(R.id.member);
        remove = itemView.findViewById(R.id.remove_journey);

    }
}
    private void alert_dialog(View v,int posistion){
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.courses_alert);
        dialog.show();
        spinner = dialog.findViewById(R.id.c_levels);
        spinner.setOnItemSelectedListener(this);
        f_spinner(v);
        Button finish = dialog.findViewById(R.id.finish);
        Button cancel = dialog.findViewById(R.id.cancel);
        EditText title = dialog.findViewById(R.id.c_title);
        EditText memebers = dialog.findViewById(R.id.c_mem);
        EditText price = dialog.findViewById(R.id.c_price);
        EditText description = dialog.findViewById(R.id.c_des);
        title.setText(list.get(posistion).getTitle());
        memebers.setText(String.valueOf(list.get(posistion).getMemebers()));
        price.setText(list.get(posistion).getPrice());
        description.setText(list.get(posistion).getDescription());
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup av = dialog.findViewById(R.id.c_avilab);
                int i = av.getCheckedRadioButtonId();
                RadioButton check = dialog.findViewById(i);
                Courses info = new Courses(levelSelected, title.getText().toString(), check.getText().toString(),
                            Integer.parseInt(memebers.getText().toString()),
                        price.getText().toString(), description.getText().toString(), list.get(posistion).getId(),auth.getCurrentUser().getUid());
                    controller = new UserController();
                    controller.update("courses", list.get(posistion).id, info);
                    Toast.makeText(v.getContext(), "success", Toast.LENGTH_SHORT).show();
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

  private void   update_list(){
        UserController userController  = new UserController();
        userController.get(new InfoCallback() {
            @Override
            public void onSuccess(List<Info> info) {

            }

            @Override
            public void onOSuccess(List<Orders> info) {

            }

            @Override
            public void onCSuccess(List<Courses> courses)
            {
                setList(courses);
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
    }

