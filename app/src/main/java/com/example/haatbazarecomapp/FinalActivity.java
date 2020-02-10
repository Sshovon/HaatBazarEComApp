package com.example.haatbazarecomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FinalActivity extends AppCompatActivity {
    private Button pickUpBtn;
    private Button homeDeliveryBtn;
    private Button confirmBtn;
    private TextView setText;
    private EditText getText;
    private TextView shop;
    private TextView shopname;
    private TextView shopAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        pickUpBtn = findViewById(R.id.button2);
        homeDeliveryBtn = findViewById(R.id.button);
        confirmBtn = findViewById(R.id.button3);
        setText = findViewById(R.id.pick_up_time);
        getText = findViewById(R.id.pick_up_time_input);
        shop =findViewById(R.id.textView3);
        shopname= findViewById(R.id.textView4);
        shopAddress = findViewById(R.id.textView6);

        pickUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUpBtn.setBackgroundColor(Color.parseColor("#FF4A4A"));
                homeDeliveryBtn.setBackgroundColor(Color.parseColor("#50000000"));
                setText.setText("PickUp Time");
                setText.setVisibility(View.VISIBLE);
                getText.setVisibility(View.VISIBLE);
                shop.setVisibility(View.VISIBLE);
                shopname.setVisibility(View.VISIBLE);
                shopAddress.setVisibility(View.VISIBLE);

            }
        });

        homeDeliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeDeliveryBtn.setBackgroundColor(Color.parseColor("#FF4A4A"));
                pickUpBtn.setBackgroundColor(Color.parseColor("#50000000"));
                setText.setText("Address");
                setText.setVisibility(View.VISIBLE);
                getText.setVisibility(View.VISIBLE);
                shop.setVisibility(View.INVISIBLE);
                shopname.setVisibility(View.INVISIBLE);
                shopAddress.setVisibility(View.INVISIBLE);

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getText.getText().toString().trim().length()==0)
                    Toast.makeText(FinalActivity.this, "Please Fill up required information", Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(FinalActivity.this, "Your Order is successfully placed.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FinalActivity.this,MainActivity.class);
                    startActivity(intent);
                }


            }
        });

    }
}
