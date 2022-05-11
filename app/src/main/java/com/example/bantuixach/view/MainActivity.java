package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bantuixach.R;
import com.example.bantuixach.model.Customer;

public class MainActivity extends AppCompatActivity {

    ImageButton btnInOut;
    SearchView svSearch;
    LinearLayout llHome, llCart, llIntroduce, llInfor;
    public static Customer customer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        customer= (Customer) getIntent().getSerializableExtra("customer");
        if(customer!=null){
            btnInOut.setImageResource(R.drawable.logoution);
            Toast.makeText(this, customer.getName(), Toast.LENGTH_SHORT).show();
        } else {
            btnInOut.setImageResource(R.drawable.loginicon);
        }
        setEvent();

    }

    private void setEvent(){
        btnInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                customer = null;
            }
        });
        llCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
        llIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IntroduceActivity.class));
            }
        });
        llInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
            }
        });
    }

    private void setControl(){
        btnInOut = findViewById(R.id.btnInOut);
        svSearch = findViewById(R.id.svSearch);
        llHome = findViewById(R.id.llHome);
        llCart = findViewById(R.id.llCart);
        llIntroduce = findViewById(R.id.llIntroduce);
        llInfor = findViewById(R.id.llInfor);
    }
}