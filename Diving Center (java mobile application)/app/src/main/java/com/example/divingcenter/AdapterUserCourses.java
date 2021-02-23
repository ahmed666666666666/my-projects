package com.example.divingcenter;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.divingcenter.Model.User;
import com.example.divingcenter.View.Trainer_Recycler_view;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.UUID;

public class AdapterUserCourses extends RecyclerView.Adapter<AdapterUserCourses.Holder> {
    private List<Courses> list;
    FirebaseAuth auth;
    String availability = "notReserved";
    public void setList(List<Courses> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterUserCourses.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        return new AdapterUserCourses.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_courses, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterUserCourses.Holder holder, int position) {
        holder.price.setText(list.get(position).getPrice()+" "+"L.E");
        holder.name.setText(list.get(position).getTitle());
        holder.level.setText(list.get(position).getJ_level());
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserController controller = new UserController();
                controller.get("coursesOf" + auth.getCurrentUser().getUid(), list.get(position).getId(), new InfoCallback() {
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
                        }else{
                            availability = "notReserved";
                        }


                        if(list.get(position).getMemebers() > 0 && availability.equals("notReserved") ) {
//                            final String uuid = UUID.randomUUID().toString();
//                            UserController controller = new UserController();
//                            Orders orders = new Orders(list.get(position).getTitle()+" "+"Is Ordered",uuid,auth.getCurrentUser().getEmail(),
//                                    "resss",list.get(position).getUser_id()
//                                    ,auth.getCurrentUser().getUid());
//                            controller.add("orderedCourses",orders);
//                            controller.update("courses","memebers" ,list.get(position).getId(),list.get(position).getMemebers() -1);
//                            Orders orders1 = new Orders(list.get(position).getTitle(),list.get(position).getId(),auth.getCurrentUser().getEmail(),"Reserved"
//                                    ,list.get(position).getUser_id(),auth.getCurrentUser().getUid());
//                            controller.add("coursesOf"+auth.getCurrentUser().getUid(),orders1);
                            Intent intent = new Intent(holder.itemView.getContext(),VisaActivity.class);
                            intent.putExtra("key",list.get(position).getId());
                            intent.putExtra("type","4");
                            intent.putExtra("id",list.get(position).getTitle());
                            intent.putExtra("number",list.get(position).getMemebers());
                            intent.putExtra("uid",list.get(position).getUser_id());
                            intent.putExtra("increment_id",list.get(position).getId());
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

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        else
            return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView name ;
        TextView price;
        TextView level;
        Button pay;
        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Name);
            price  = itemView.findViewById(R.id.price);
            level = itemView.findViewById(R.id.level);
            pay = itemView.findViewById(R.id.pay);
        }
    }
    private void get(String courseId){

    }
}