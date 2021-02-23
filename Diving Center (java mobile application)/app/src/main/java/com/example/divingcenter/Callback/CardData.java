package com.example.divingcenter.Callback;

import com.example.divingcenter.View.CardModel;

import java.util.List;

public interface CardData {
public void onSuccess(List<CardModel> cardModel);
public void onError(String msg);
}