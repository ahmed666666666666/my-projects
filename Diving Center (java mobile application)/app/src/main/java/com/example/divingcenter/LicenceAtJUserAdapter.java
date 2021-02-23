package com.example.divingcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Callback.MyCall;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.Pending;
import com.example.divingcenter.View.Licenes_Order_Adapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class LicenceAtJUserAdapter extends RecyclerView.Adapter<LicenceAtJUserAdapter.Holder> {
    UserController controller;
    private List<Pending> list;
    FirebaseAuth auth;
    public void setList(List<Pending> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LicenceAtJUserAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        controller = new UserController();
        auth = FirebaseAuth.getInstance();
        return new LicenceAtJUserAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.licence_user_pending_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LicenceAtJUserAdapter.Holder holder, int position) {
        holder.textView.setText("("+list.get(position).getLicencePending()+")"+" "+list.get(position).getOrder());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),VisaActivity.class);
                intent.putExtra("key",list.get(position).key);
                intent.putExtra("type","3");
                intent.putExtra("id",list.get(position).getOrder());
                holder.itemView.getContext().startActivity(intent);
            }
        });

        if(list.get(position).getLicencePending().equals("accepted")){
        holder.button.setVisibility(View.VISIBLE);
        // retrieve();
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
       Button button;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.stat);
            button = itemView.findViewById(R.id.p);
        }
    }
    public void retrieve(){
        UserController controller = new UserController();
        controller.getatuser("LicenceOrders", new MyCall() {
            @Override
            public void ondSuccess(List<Pending> p) {
                setList(p);
            }
        });
    }
}