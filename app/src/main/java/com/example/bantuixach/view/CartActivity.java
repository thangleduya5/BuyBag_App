package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bantuixach.R;

public class CartActivity extends AppCompatActivity {

    ListView lvCart;
    TextView tvSum;
    Button btnOrderBag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setControl();
        setEvent();
    }

    private void setEvent(){

    }

    private void setControl(){
        lvCart = findViewById(R.id.lvCart);
        tvSum = findViewById(R.id.tvSum);
        btnOrderBag = findViewById(R.id.btnOrderBag);
    }
}