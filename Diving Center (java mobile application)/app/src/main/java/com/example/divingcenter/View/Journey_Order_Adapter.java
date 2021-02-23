package com.example.divingcenter.View;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.divingcenter.CardResulstActivity;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Orders;
import com.example.divingcenter.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class Journey_Order_Adapter extends RecyclerView.Adapter<Journey_Order_Adapter.Holder> {

    UserController controller;
    private List<CardModel> list;
    FirebaseAuth auth;
    public void setList(List<CardModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Journey_Order_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        controller = new UserController();
        auth = FirebaseAuth.getInstance();
        return new Journey_Order_Adapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Journey_Order_Adapter.Holder holder, int position) {
            holder.textView.setText(list.get(position).getKey());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), CardResulstActivity.class);
                    intent.putExtra("name",list.get(position).getKey());
                    intent.putExtra("expmonth",list.get(position).getCardExpMonth());
                    intent.putExtra("expyear",list.get(position).getCardExpYear());
                    intent.putExtra("cardtype",list.get(position).getCardType());
                    intent.putExtra("cardnumber",list.get(position).getCardNumber());
                    intent.putExtra("cvv",list.get(position).getCvv2());
                    intent.putExtra("cardonname",list.get(position).getNameOnCard());
                    holder.itemView.getContext().startActivity(intent);

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

    static class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_o);
        }

    }

}