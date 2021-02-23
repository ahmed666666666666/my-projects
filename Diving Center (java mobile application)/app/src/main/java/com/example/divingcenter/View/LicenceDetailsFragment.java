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

import com.example.divingcenter.Callback.CardData;
import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.View.CardModel;

import java.util.List;

public class LicenceDetailsFragment extends Fragment {
        LicenseDetailsAdapter licenseDetailsAdapter ;
        RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        licenseDetailsAdapter = new LicenseDetailsAdapter();
        recyclerView = view.findViewById(R.id.L_D);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(licenseDetailsAdapter);
        recyclerView.setLayoutManager(layoutManager);
        get();
    }
    private  void get(){

        UserController controller = new UserController();
        controller.get_card_licence_u(new CardData() {
            @Override
            public void onSuccess(List<CardModel> cardModel) {
                licenseDetailsAdapter.setList(cardModel);
            }

            @Override
            public void onError(String msg) {

            }
        });

    }

}