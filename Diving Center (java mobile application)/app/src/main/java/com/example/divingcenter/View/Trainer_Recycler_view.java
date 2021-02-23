package com.example.divingcenter.View;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.divingcenter.Callback.UserCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Trainer;
import com.example.divingcenter.Model.User;
import com.example.divingcenter.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Trainer_Recycler_view extends RecyclerView.Adapter<Trainer_Recycler_view.Holder> {
    private List<User> list;
    FirebaseAuth auth;
    public void setList(List<User> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            auth = FirebaseAuth.getInstance();
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.trainer_name.setText(list.get(position).getName());
        holder.trainer_age.setText(list.get(position).getAge());
        holder.trainer_lev.setText(list.get(position).getLevel());
        holder.status.setText(list.get(position).getConfirmation());
//        if(auth.getCurrentUser().getEmail().equals("admin@gmail.com")) {
        //  || list.get(position).getConfirmation().equals("rejected")
        if (auth.getCurrentUser().getEmail().equals("admin@gmail.com")) {
            if (list.get(position).getConfirmation().equals("accepted")) {
                holder.status.setTextColor(Color.GREEN);
                holder.accept.setVisibility(View.INVISIBLE);
                holder.reject.setVisibility(View.INVISIBLE);
            }
            if (list.get(position).getConfirmation().equals("rejected")) {
                holder.status.setTextColor(Color.RED);
                holder.accept.setVisibility(View.INVISIBLE);
                holder.reject.setVisibility(View.INVISIBLE);
            }
            if (list.get(position).getConfirmation().equals("pending")) {
                holder.status.setTextColor(Color.WHITE);
                holder.accept.setVisibility(View.VISIBLE);
                holder.reject.setVisibility(View.VISIBLE);
            }
//                else {
//                    holder.accept.setVisibility(View.VISIBLE);
//                    holder.reject.setVisibility(View.VISIBLE);
//                }
//
//            }else{
//
//                holder.accept.setVisibility(View.INVISIBLE);
//                holder.reject.setVisibility(View.INVISIBLE);
//            }

            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserController controller = new UserController();
                    controller.update("users", list.get(position).getKey(), "confirmation", "accepted");

                }
            });
            holder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserController controller = new UserController();
                    controller.update("users", list.get(position).getKey(), "confirmation", "rejected");
                    //controller.delete("users",list.get(position).getKey());
                    update_list();
                }
            });
        }else {
                    holder.accept.setVisibility(View.INVISIBLE);
                    holder.reject.setVisibility(View.INVISIBLE);
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
        TextView trainer_name;
        TextView trainer_age;
        TextView trainer_lev;
        TextView status;
        Button accept;
        Button reject;
        public Holder(@NonNull View itemView) {
            super(itemView);
            trainer_name= itemView.findViewById(R.id.t_name_text);
            trainer_age = itemView.findViewById(R.id.t_age_text);
            trainer_lev = itemView.findViewById(R.id.t_level_text);
            accept = itemView.findViewById(R.id.accepttt);
            reject = itemView.findViewById(R.id.reject);
            status = itemView.findViewById(R.id.status);
        }
    }

private void update_list(){
        UserController controller = new UserController();
        controller.getTrainers(new UserCallback() {
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
            setList(users);
        }

        @Override
        public void OnSuccess(User user) {

        }
    });
}
    }
