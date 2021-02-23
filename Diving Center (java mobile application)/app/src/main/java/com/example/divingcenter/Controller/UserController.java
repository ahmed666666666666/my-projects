package com.example.divingcenter.Controller;
import android.content.Context;
import android.graphics.CornerPathEffect;
import android.telephony.SignalStrength;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.divingcenter.Callback.CardData;
import com.example.divingcenter.Callback.InfoCallback;
import com.example.divingcenter.Callback.LisenseCallback;
import com.example.divingcenter.Callback.MyCall;
import com.example.divingcenter.Callback.UserCallback;
import com.example.divingcenter.Model.Available;
import com.example.divingcenter.Model.Courses;
import com.example.divingcenter.Model.Info;
import com.example.divingcenter.Model.Levels;
import com.example.divingcenter.Model.OrderPending;
import com.example.divingcenter.Model.Pending;
import com.example.divingcenter.Model.Trainer;
import com.example.divingcenter.Model.User;
import com.example.divingcenter.Orders;
import com.example.divingcenter.SharedData;
import com.example.divingcenter.View.CardModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.security.auth.callback.Callback;

public class UserController {
    private FirebaseAuth mAuth;
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    List<User> users;
    List<User>trainers;
    List<Levels> levels;
    List<Info> j_info;
    public UserController() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public void Register(final User  user, final UserCallback callback ){

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.setKey(mAuth.getCurrentUser().getUid());
                            callback.OnSuccess("Authentication Successful");
                            SharedData.setUser(user);
                            myRef.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                           // myRef.child("waiting").child(trainer.getKey()).setValue(trainer);
                        } else {
                            // If sign in fails, display a message to the user.
                            callback.onError("Authentication Failed");
                        }
                    }
                });
    }
    public  void  signIn(String email,String password, UserCallback callback){

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.OnSuccess("sign In Succeeded");
                        } else {
                            callback.onError("Sign In Failed");
                        }
                    }
                });
    }
    public void  getUserData(String id , final UserCallback callback) {

        myRef.child("users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                SharedData.setUser(user);
                callback.OnSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void  getAllUserData( final UserCallback callback) {
        List<User> normal = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if(user.getType().equals("normalUser")){
                        normal.add(user);
                    }
                    callback.OnCSuccess(normal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void  getTrainers(final UserCallback callback) {
        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainers = new ArrayList<>();
                for( DataSnapshot ds :snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if(user.getType().equals("trainer")) {
                        trainers.add(user);
                    }
                }
                callback.OnSuccess(trainers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void  getlevels(final InfoCallback callback) {

        myRef.child("levels").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                levels = new ArrayList<>();
                for( DataSnapshot ds :snapshot.getChildren()) {
                    Levels user = ds.getValue(Levels.class);
                        levels.add(user);
                }
                callback.success(levels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void  get_journey(final InfoCallback callback) {

        myRef.child("journey").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                j_info = new ArrayList<>();
                for( DataSnapshot ds :snapshot.getChildren()) {
                    Info d = ds.getValue(Info.class);
                    j_info.add(d);
                }
                callback.onSuccess(j_info);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void  get_licence(final InfoCallback callback) {

        myRef.child("licences").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                j_info = new ArrayList<>();
                for( DataSnapshot ds :snapshot.getChildren()) {
                    Info d = ds.getValue(Info.class);
                    j_info.add(d);
                }
                callback.onSuccess(j_info);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void  get_equipments(final InfoCallback callback) {

        myRef.child("equipments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                j_info = new ArrayList<>();
                for( DataSnapshot ds :snapshot.getChildren()) {
                    Info d = ds.getValue(Info.class);
                    j_info.add(d);
                }
                callback.onSuccess(j_info);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void  get(final InfoCallback callback) {

        myRef.child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               List<Courses>  courses = new ArrayList<>();
                for( DataSnapshot ds :snapshot.getChildren()) {
                    Courses d = ds.getValue(Courses.class);
                    if(d.user_id.equals(mAuth.getCurrentUser().getUid())) {
                        courses.add(d);
                    }
                }
                callback.onCSuccess(courses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void  get_courses_for_admin(final InfoCallback callback) {

        myRef.child("orderedCourses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Orders>  courses = new ArrayList<>();
                for( DataSnapshot ds :snapshot.getChildren()) {
                    Orders d = ds.getValue(Orders.class);
                        courses.add(d);
                }
                callback.onOSuccess(courses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void  get_courses_for_user(final InfoCallback callback) {

        myRef.child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Courses>  courses = new ArrayList<>();
                for( DataSnapshot ds :snapshot.getChildren()) {
                    Courses d = ds.getValue(Courses.class);
                        courses.add(d);
                }
                callback.onCSuccess(courses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void  get(String ref,String child,final InfoCallback callback) {
        myRef.child(ref).child(child).child("isAvailable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String d = snapshot.getValue(String.class);
                    callback.onSuccess(d);
                    }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void  get(String ref,String child,String child2,final InfoCallback callback) {
        myRef.child(ref).child(child).child(child2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String d = snapshot.getValue(String.class);
                callback.onSuccess(d);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void get_licences_status(String child, final LisenseCallback callback){

        myRef.child("LicenceOrders").child(child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrderPending d = snapshot.getValue(OrderPending.class);
                callback.onOSuccess(d.getLicencePending());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void  get(String ref,final InfoCallback callback) {

        myRef.child(ref).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Orders>  courses = new ArrayList<>();
                for( DataSnapshot ds :snapshot.getChildren()) {
                    Orders d = ds.getValue(Orders.class);
                    courses.add(d);
                }
                callback.onOSuccess(courses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void  get_orrder_status(String ref,final LisenseCallback callback) {

        myRef.child(ref).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<OrderPending>  courses = new ArrayList<>();
                for( DataSnapshot ds :snapshot.getChildren()) {
                    OrderPending d = ds.getValue(OrderPending.class);
                    courses.add(d);
                }
                callback.onOSuccess(courses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void  getatuser(String ref,final MyCall callback) {

        myRef.child(ref).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Pending>  courses = new ArrayList<>();
                for( DataSnapshot ds :snapshot.getChildren()) {
                    Pending d = ds.getValue(Pending.class);
                    mAuth = FirebaseAuth.getInstance();
                        if(d.getEmail().equals(mAuth.getCurrentUser().getUid())) {
                        courses.add(d);

                    }
                }
                callback.ondSuccess(courses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void get_card_jurney(final CardData callBack){
        myRef.child("CardView").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CardModel> list = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    CardModel model = snapshot1.getValue(CardModel.class);
                    if(model.getType().equals("1")){
                        list.add(model);
                    }
                }
                callBack.onSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void get_card_jurney_u(final CardData callBack){
        myRef.child("CardView").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CardModel> list = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    CardModel model = snapshot1.getValue(CardModel.class);
                    if(model.getType().equals("1") && model.getCurrentUserId().equals(mAuth.getCurrentUser().getUid())){
                        list.add(model);
                    }
                }
                callBack.onSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void get_card_equipments(final CardData callBack){
        myRef.child("CardView").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CardModel> list = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    CardModel model = snapshot1.getValue(CardModel.class);
                    if(model.getType().equals("2")){
                        list.add(model);
                    }

                }
                callBack.onSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void get_card_equipments_u(final CardData callBack){
        myRef.child("CardView").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CardModel> list = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    CardModel model = snapshot1.getValue(CardModel.class);
                    if(model.getType().equals("2") && model.getCurrentUserId().equals(mAuth.getCurrentUser().getUid())){
                        list.add(model);
                    }
                }
                callBack.onSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }





    public void get_card_licence(final CardData callBack){
        myRef.child("CardView").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CardModel> list = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    CardModel model = snapshot1.getValue(CardModel.class);
                    if(model.getType().equals("3")){
                        list.add(model);
                    }

                }
                callBack.onSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void get_card_licence_u(final CardData callBack){
        myRef.child("CardView").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CardModel> list = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    CardModel model = snapshot1.getValue(CardModel.class);
                    if(model.getType().equals("3") &&  model.getCurrentUserId().equals(mAuth.getCurrentUser().getUid())){
                        list.add(model);
                    }

                }
                callBack.onSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void get_card_courses(final CardData callBack){
        myRef.child("CardView").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CardModel> list = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    CardModel model = snapshot1.getValue(CardModel.class);
                    if(model.getType().equals("4")){
                        list.add(model);
                    }
                }
                callBack.onSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void get_card_courses_u(final CardData callBack){
        myRef.child("CardView").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CardModel> list = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    CardModel model = snapshot1.getValue(CardModel.class);
                    if(model.getType().equals("4") && model.getCurrentUserId().equals(mAuth.getCurrentUser().getUid())){
                        list.add(model);
                    }
                }
                callBack.onSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void  get_for_trainer_orders(String ref,final InfoCallback callback) {

        myRef.child(ref).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Orders>  courses = new ArrayList<>();
                for( DataSnapshot ds :snapshot.getChildren()) {
                    Orders d = ds.getValue(Orders.class);
                    if(d.getTrainer_id().equals(mAuth.getCurrentUser().getUid()))
                    courses.add(d);
                }
                callback.onOSuccess(courses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void update(String ref,String id,String child,String value){
        myRef.child(ref).child(id).child(child).setValue(value);
}

    public void update(String ref,int child,Info info){
        myRef.child(ref).child(String.valueOf(child)).setValue(info);
    }

    public void update(String ref,String child,String value){
        myRef.child(ref).child(child).setValue(value);
    }
    public void update(String ref,String key,Courses  info){
        myRef.child(ref).child(key).setValue(info);
    }

    public void update(String ref,String key,Info  info){
        myRef.child(ref).child(key).setValue(info);
    }
    public void update(String ref,String key,Levels  info){
        myRef.child(ref).child(key).setValue(info);
    }
    public void delete(String ref,int child){
        myRef.child(ref).child(String.valueOf(child)).removeValue();
    }

    public void delete(String ref,String key){
        myRef.child(ref).child(key).removeValue();
    }
    public void update(String ref,int child,Levels info){
        myRef.child(ref).child(String.valueOf(child)).setValue(info);
    }
//    public void add(String ref, Map<String,String> value){
//        myRef.child(ref).push().setValue(value);
//    }
    public void add(String ref,Courses courses){
        myRef.child(ref).child(courses.getId()).setValue(courses);
    }
    public void add(String ref, String child,CardModel model){
        myRef.child(ref).child(child).setValue(model);
    }
    public void add(String ref,OrderPending courses){
        myRef.child(ref).child(courses.getKey()).setValue(courses);
    }
    public void add(String p, String ref, Orders courses){
        myRef.child(p).child(ref).child(courses.getKey()).setValue(courses);
    }
    public void insert_to_user(List<User>users){
        myRef.child("orderedCourses").setValue(users);
    }
    public void add(String ref,Levels courses){
        myRef.child(ref).child(courses.getKey()).setValue(courses);
    }
    public void add(String ref, Orders courses){
        myRef.child(ref).child(courses.getKey()).setValue(courses);

    }

    public void add(String ref,Info courses){
        myRef.child(ref).child(courses.getKey()).setValue(courses);
    }

    public void add_journey(String ref, List<Info> info) {
        myRef.child(ref).setValue(info);
    }
    public void add_level(String ref, List<Levels> info) {
        myRef.child(ref).setValue(info);
    }

    public void update(String ref, String child,String id, int newCount) {
        myRef.child(ref).child(id).child(child).setValue(newCount);
    }

}