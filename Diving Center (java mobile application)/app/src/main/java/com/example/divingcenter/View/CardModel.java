package com.example.divingcenter.View;

import com.google.gson.internal.$Gson$Types;

import java.util.Date;

public class CardModel {
   private String currentUserId;
    private String nameOnCard;
    private String cardType;
    private String cardNumber ;
    private String cardExpYear ;
    private String cardExpMonth ;
    private String cvv2;
    private Date created_date ;
    private String key;
    private String type;

    public String getCurrentUserId() {
        return currentUserId;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpYear() {
        return cardExpYear;
    }

    public String getCardExpMonth() {
        return cardExpMonth;
    }

    public String getCvv2() {
        return cvv2;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public CardModel() {
    }

    public CardModel(String nameOnCard, String cardType, String cardNumber, String cardExpYear, String cardExpMonth, String cvv2, String key, String type,String currentUserId) {
        this.nameOnCard = nameOnCard;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardExpYear = cardExpYear;
        this.cardExpMonth = cardExpMonth;
        this.cvv2 = cvv2;
        this.key = key;
        this.type = type;
        this.currentUserId = currentUserId;
    }
}
