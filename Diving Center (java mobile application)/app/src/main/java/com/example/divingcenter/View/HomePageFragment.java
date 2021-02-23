package com.example.divingcenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomePageFragment extends Fragment {

    FloatingActionButton go;
    FloatingActionButton orders;

    FirebaseAuth auth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        go = view.findViewById(R.id.gotocourses);
        orders = view.findViewById(R.id.orders);
        if(auth.getCurrentUser().getEmail().equals("admin@gmail.com")){
            go.setVisibility(View.GONE);
            orders.setVisibility(View.VISIBLE);

        }else{
            go.setVisibility(View.VISIBLE);
            orders.setVisibility(View.GONE);
        }


        go.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            startActivity(new  Intent(getContext(),CoursesUserActivity.class));
        }
    });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(),JourneyActivity.class));
            }
        });

    }
}