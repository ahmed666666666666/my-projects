package com.example.divingcenter.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.divingcenter.Model.Pending;
import com.example.divingcenter.Orders;
import com.example.divingcenter.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class Licenes_Order_Adapter extends RecyclerView.Adapter<Licenes_Order_Adapter.Holder> {
    UserController controller;
    private List<OrderPending> list;
    FirebaseAuth auth;
    public void setList(List<OrderPending> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        controller = new UserController();
        auth = FirebaseAuth.getInstance();
        return new Licenes_Order_Adapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lisense_orders_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.textView.setText(list.get(position).getOrder());
        if(list.get(position).getLicencePending().equals("accepted") || list.get(position).getLicencePending().equals("rejected")) {
            holder.accept.setVisibility(View.INVISIBLE);
            holder.reject.setVisibility(View.INVISIBLE);
        }

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.update("LicenceOrders",list.get(position).getKey(),"licencePending","accepted");
                holder.accept.setVisibility(View.INVISIBLE);
                holder.reject.setVisibility(View.INVISIBLE);
                Toast.makeText(holder.itemView.getContext(),"Accepted",Toast.LENGTH_SHORT).show();
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.update("LicenceOrders",list.get(position).getKey(),"licencePending","rejected");
                holder.accept.setVisibility(View.VISIBLE);
                holder.reject.setVisibility(View.VISIBLE);
                Toast.makeText(holder.itemView.getContext(),"Rejected",Toast.LENGTH_SHORT).show();
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
        Button accept;
        Button reject;
        public Holder(@NonNull View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.text_o_l);
            accept = itemView.findViewById(R.id.yes);
            reject = itemView.findViewById(R.id.no);
        }
    }

}