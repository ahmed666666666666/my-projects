package com.example.divingcenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.divingcenter.Controller.UserController;
import com.example.divingcenter.View.CardModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VisaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    FirebaseAuth auth;
    EditText name;
    Spinner type;
    Spinner expirationYear;
    Spinner expirationMonth;
    EditText cardNumber;
    EditText cvv2;
    TextView confirmationPay;
    String type_card;
    String year_card;
    String month_card;
    UserController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        auth = FirebaseAuth.getInstance();
        controller = new UserController();
        name =findViewById(R.id.txtNameOnCard);
        type =findViewById(R.id.SpType);
        expirationYear =findViewById(R.id.SpYear);
        expirationMonth =findViewById(R.id.SpMonth);
        cardNumber =findViewById(R.id.txtCardNumber);
        cvv2 =findViewById(R.id.txttCVV2);
        confirmationPay =findViewById(R.id.btn_enter);
        type.setOnItemSelectedListener(this);
        expirationYear.setOnItemSelectedListener(this);
        expirationMonth.setOnItemSelectedListener(this);
        expirationYear.setAdapter(fill_year());
        expirationMonth.setAdapter(fill_month());
        type.setAdapter(fill_type());


       confirmationPay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!name.getText().toString().isEmpty() && !cardNumber.getText().toString().isEmpty()
               && !cvv2.getText().toString().isEmpty() ) {
                   if(cardNumber.length() == 12) {
                       if (cvv2.length() == 4) {
                           final String uuid = UUID.randomUUID().toString();
                           String key = getIntent().getStringExtra("key");
                           String product_type = getIntent().getStringExtra("type");
                           String id = getIntent().getStringExtra("id");
                           int amount = getIntent().getIntExtra("number",0);
                           String location = getIntent().getStringExtra("increment_id");
                           CardModel cardModel = new CardModel(name.getText().toString(), type_card, cardNumber.getText().toString()
                                   , year_card, month_card, cvv2.getText().toString(), id,
                                   product_type,auth.getCurrentUser().getUid());
                           controller.add("CardView", uuid, cardModel);

                           if(product_type.equals("1")){
                               UserController controller = new UserController();
                               final String uid = UUID.randomUUID().toString();
                               Orders orders = new Orders(id + " " + "Is Ordered", uid, auth.getCurrentUser().getEmail()
                                       ,"a","t",auth.getCurrentUser().getUid());
                               controller.add("journeyOrders", orders);
                               controller.update("journey","memebers" ,key,amount - 1);
                               Orders orders1 = new Orders(id,key,auth.getCurrentUser().getEmail(),"Reserved");
                               controller.add("journeyOf"+auth.getCurrentUser().getUid(),orders1);
                           }
                            if(product_type.equals("2")){
                                final String uuiid = UUID.randomUUID().toString();
                                Orders orders = new Orders(id + " " + "Is Ordered", uuiid,
                                        auth.getCurrentUser().getDisplayName(),"a","s", auth.getCurrentUser().getUid());
                                controller.add("equipmentOrders", orders);
                                controller.add("equipmentsOf"+auth.getCurrentUser().getUid(),orders);
                                controller.update("equipments","quantity" ,location,amount - 1);
                            }
                           if(product_type.equals("3")){
                               controller.update("LicenceOrders", key, "licencePending", "paid");
                           }

                            if(product_type.equals("4")){
                                String userId  = getIntent().getStringExtra("uid");
                                final String uuidd = UUID.randomUUID().toString();
                                UserController controller = new UserController();
                                Orders orders = new Orders(id + " "+"Is Ordered",uuidd,auth.getCurrentUser().getEmail(),
                                        "resss",userId
                                        ,auth.getCurrentUser().getUid());
                                controller.add("orderedCourses",orders);
                                controller.update("courses","memebers" ,location,amount -1);
                                Orders orders1 = new Orders(id,key,auth.getCurrentUser().getEmail(),"Reserved"
                                        ,userId,auth.getCurrentUser().getUid());
                                controller.add("coursesOf"+auth.getCurrentUser().getUid(),orders1);
                            }
                           name.setText("");
                           cardNumber.setText("");
                           cvv2.setText("");
                           Toast.makeText(VisaActivity.this,"Congratulation",Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(VisaActivity.this,PagesActivity.class));
                       }else {
                           Toast.makeText(VisaActivity.this,"it must be 4 digits",Toast.LENGTH_SHORT).show();
                       }
                   }else {
                       Toast.makeText(VisaActivity.this,"it must be 12 digits",Toast.LENGTH_SHORT).show();
                   }
                   }
           }
       });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.SpType){

            type_card = parent.getItemAtPosition(position).toString();

        }
        if(parent.getId() == R.id.SpYear){

                year_card = parent.getItemAtPosition(position).toString();
        }
        if(parent.getId() == R.id.SpMonth){

            month_card = parent.getItemAtPosition(position).toString();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private ArrayAdapter fill_type(){
        List<String> type = new ArrayList<>();
        type.add("Visa");
        type.add("card");
        ArrayAdapter adapter = new ArrayAdapter(VisaActivity.this, android.R.layout.simple_spinner_dropdown_item, type);
        return adapter;
    }
    private ArrayAdapter fill_year(){
        List<Integer> year = new ArrayList<>();
        for(int i =2010;i<2030;i++) {
                year.add(i);
        }
            ArrayAdapter adapter = new ArrayAdapter(VisaActivity.this, android.R.layout.simple_spinner_dropdown_item, year);
            return adapter;

    }
    private ArrayAdapter fill_month(){
        List<String> month = new ArrayList<>();
        month.add("January");
        month.add("February");
        month.add("March");
        month.add("April");
        month.add("May");
        month.add("June");
        month.add("July");
        month.add("August");
        month.add("Septemper");
        month.add("October");
        month.add("November");
        month.add("December");
        ArrayAdapter adapter = new ArrayAdapter(VisaActivity.this, android.R.layout.simple_spinner_dropdown_item, month);
        return adapter;
    }
}
