package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.bantuixach.R;

public class IntroduceActivity extends AppCompatActivity {

    TextView tvIntroduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        setControl();
        tvIntroduce.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setControl(){
        tvIntroduce = findViewById(R.id.tvIntroduce);
    }
}