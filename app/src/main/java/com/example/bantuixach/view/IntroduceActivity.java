package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bantuixach.R;

public class IntroduceActivity extends AppCompatActivity {

    TextView tvIntroduce;
    LinearLayout llHome, llCart, llIntroduce, llInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        setControl();
        tvIntroduce.setMovementMethod(new ScrollingMovementMethod());
        setEvent();
    }

    private void setEvent(){
        llCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.customer==null){
                    startActivity(new Intent(IntroduceActivity.this, LoginActivity.class));
                } else{
                    startActivity(new Intent(IntroduceActivity.this, CartActivity.class));
                }
            }
        });
        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroduceActivity.this, MainActivity.class);
                intent.putExtra("customer", MainActivity.customer);
                startActivity(intent);
            }
        });
        llInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.customer==null){
                    startActivity(new Intent(IntroduceActivity.this, LoginActivity.class));
                } else{
                    startActivity(new Intent(IntroduceActivity.this, InfoActivity.class));
                }
            }
        });
    }

    private void setControl(){
        llHome = findViewById(R.id.llHome);
        llCart = findViewById(R.id.llCart);
        llIntroduce = findViewById(R.id.llIntroduce);
        llInfor = findViewById(R.id.llInfor);
        tvIntroduce = findViewById(R.id.tvIntroduce);
    }
}