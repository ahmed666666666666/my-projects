package com.example.divingcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CardResulstActivity extends AppCompatActivity {
    TextView Tname;
    TextView eyear;
    TextView cardNu;
    TextView cardT;
    TextView cvv;
    TextView nameOn;
    TextView eM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        Tname = findViewById(R.id.product_name);
        cardNu = findViewById(R.id.card_number);
        eyear = findViewById(R.id.cardexyear);
        eM =  findViewById(R.id.cardexmonth);
        nameOn = findViewById(R.id.name_on_c);
        cvv = findViewById(R.id.cvv2);
        cardT = findViewById(R.id.card_type);
        String year = getIntent().getStringExtra("expyear");
        String month = getIntent().getStringExtra("expmonth");
        String name = getIntent().getStringExtra("name");
        String cardType = getIntent().getStringExtra("cardtype");
        String cardNumber = getIntent().getStringExtra("cardnumber");
        String nameOnCard = getIntent().getStringExtra("cardonname");
        String cvv2 = getIntent().getStringExtra("cvv");
        Tname.setText(name);
        cardNu.setText(cardNumber);
        cardT.setText(cardType);
        nameOn.setText(nameOnCard);
        eM.setText(month);
        cvv.setText(cvv2);
        eyear.setText(year);
    }
}