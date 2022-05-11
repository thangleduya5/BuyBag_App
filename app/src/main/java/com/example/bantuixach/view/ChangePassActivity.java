package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bantuixach.R;

import org.json.JSONException;
import org.json.JSONObject;

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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });
        btnDestroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void changePass(){
        RequestQueue requestQueue = Volley.newRequestQueue(ChangePassActivity.this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", MainActivity.customer.getUsername());
            jsonObject.put("password", etNewPass.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestBody = jsonObject.toString();

        String url ="http://192.168.100.180:3000/api/customer//change_pass";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getInt("affectedRows")==1){
                                Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            public byte[] getBody() {
                return requestBody.getBytes();
            }
        };

        requestQueue.add(jsonObjectRequest);
    }


    private void setControl(){
        etPassword = findViewById(R.id.etPassword);
        etNewPass = findViewById(R.id.etNewPass);
        etConfirmPass = findViewById(R.id.etConfirmPass);
        btnSave = findViewById(R.id.btnSave);
        btnDestroy = findViewById(R.id.btnDestroy);
    }
}