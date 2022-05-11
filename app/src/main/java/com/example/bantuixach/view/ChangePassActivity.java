package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.bantuixach.R;

public class ChangePassActivity extends AppCompatActivity {

    EditText etPassword, etNewPass, etConfirmPass;
    Button btnSave, btnDestroy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        setControl();
        setEvent();
    }

    private void setEvent(){

    }

    private void setControl(){
        etPassword = findViewById(R.id.etPassword);
        etNewPass = findViewById(R.id.etNewPass);
        etConfirmPass = findViewById(R.id.etConfirmPass);
        btnSave = findViewById(R.id.btnSave);
        btnDestroy = findViewById(R.id.btnDestroy);
    }
}