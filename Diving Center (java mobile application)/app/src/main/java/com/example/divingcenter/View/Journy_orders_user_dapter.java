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
import com.example.divingcenter.Orders;
import com.example.divingcenter.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class Journy_orders_user_dapter extends RecyclerView.Adapter<Journy_orders_user_dapter.Holder> {

    UserController controller;
    private List<CardModel> list;
    FirebaseAuth auth;
    public void setList(List<CardModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Journy_orders_user_dapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        return new Journy_orders_user_dapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Journy_orders_user_dapter.Holder holder, int position) {

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
