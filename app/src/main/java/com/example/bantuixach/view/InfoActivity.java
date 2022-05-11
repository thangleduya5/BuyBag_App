package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.bantuixach.R;

public class InfoActivity extends AppCompatActivity {

    Button btnSave, btnChangePass, btnLogout;
    ImageButton btnBack, btnMenu;
    EditText etFullname, etUsername, etPhoneNumber, etEmail, etAddress;
    Spinner spSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setControl();
        setEvent();
    }

    private void setEvent(){

    }

    private void setControl(){
        btnSave = findViewById(R.id.btnSave);
        btnChangePass = findViewById(R.id.btnChangePass);
        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);
        btnMenu = findViewById(R.id.btnMenu);
        etFullname = findViewById(R.id.etFullname);
        etUsername = findViewById(R.id.etUsername);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        spSex = findViewById(R.id.spSex);
    }

}