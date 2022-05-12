package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bantuixach.R;
import com.example.bantuixach.model.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnRegister;
    Customer customer=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();
        setEvent();
    }

    private void setEvent(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(etUsername.getText().toString(), etPassword.getText().toString(), "http://192.168.100.180:3000/api/customer/login");
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void login(String username, String password, String url){
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("hinhanh",  R.drawable.tuixachmau);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestBody = jsonObject.toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length()==0){
                            Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra lại mật khẩu và tên đăng nhập", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            JSONObject jsonObject1 = response.getJSONObject(0);
                            customer = new Customer(jsonObject1.getInt("idUser"), jsonObject1.getInt("idRole"),
                                    jsonObject1.getString("username"), jsonObject1.getString("password"),
                                    jsonObject1.getString("addressCustomer"), jsonObject1.getString("email"),
                                    jsonObject1.getString("phone"), jsonObject1.getJSONObject("sex").getJSONArray("data").getInt(0),
                                    jsonObject1.getString("name"));
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("customer", customer);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Loi nghiem trong: "+error, Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            public byte[] getBody() {
                return requestBody.getBytes();
            }
        };

        requestQueue.add(jsonArrayRequest);
    }

    private void setControl(){
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
    }
}