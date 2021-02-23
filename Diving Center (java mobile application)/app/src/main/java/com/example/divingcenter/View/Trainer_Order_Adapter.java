package com.example.divingcenter.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Orders;
import com.example.divingcenter.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class Trainer_Order_Adapter extends RecyclerView.Adapter<Trainer_Order_Adapter.Holder> {

    UserController controller;
    private List<Orders> list;
    FirebaseAuth auth;
    public void setList(List<Orders> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        return new Trainer_Order_Adapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.textView.setText(list.get(position).getOrder());
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
        public Holder(@NonNull View itemView) {
            super(itemView);
        textView = itemView.findViewById(R.id.text_o);

        }

    }
}
