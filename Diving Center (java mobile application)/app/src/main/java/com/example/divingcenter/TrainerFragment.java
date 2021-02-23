package com.example.divingcenter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.divingcenter.Callback.UserCallback;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.Model.Trainer;
import com.example.divingcenter.Model.User;
import com.example.divingcenter.View.Trainer_Recycler_view;

import java.util.ArrayList;
import java.util.List;

public class TrainerFragment extends Fragment {
    RecyclerView recyclerView ;
    Trainer_Recycler_view trainer_recycler_view;
    UserController controller;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainer, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new UserController();
        recyclerView = view.findViewById(R.id.recycler_trainer);
        trainer_recycler_view  = new Trainer_Recycler_view();
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(trainer_recycler_view);
        controller.getTrainers(new UserCallback() {
            @Override
            public void OnSuccess(String msg) {

            }

            @Override
            public void onError(String msg) {

            }

            @Override
            public void OnSuccess(List<User> users) {
                trainer_recycler_view.setList(users);

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
                trainer_recycler_view.setList(users);

            }

            @Override
            public void OnSuccess(User user) {

            }
        });
    }
}