package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bantuixach.R;
import com.example.bantuixach.model.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    Button btnSave, btnChangePass, btnLogout;
    ImageButton btnBack, btnMenu;
    EditText etFullname, etUsername, etPhoneNumber, etEmail, etAddress;
    Spinner spSex;
    Customer customerUpdate;
    LinearLayout llHome, llCart, llIntroduce, llInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setControl();
        initSpinner();
        if(MainActivity.customer!=null){
            setInfor();
        }
        setEvent();
    }

    private void initSpinner(){
        ArrayList<String> sexs = new ArrayList<>();
        sexs.add("Nam");
        sexs.add("Nữ");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sexs);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spSex.setAdapter(adapter);
        spSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(InfoActivity.this, spSex.getSelectedItemPosition()+"", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void setEvent(){

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerUpdate= new Customer();
                if(validateCustomer()){
                    updateInfor(customerUpdate);
                }
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoActivity.this, ChangePassActivity.class));
            }
        });

        llCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoActivity.this, CartActivity.class));
            }
        });
        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                intent.putExtra("customer", MainActivity.customer);
                startActivity(intent);
            }
        });
        llIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoActivity.this, IntroduceActivity.class));
            }
        });
    }

    private Boolean validateCustomer(){
        String fullname = etFullname.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String username = etUsername.getText().toString().trim();

        if(fullname.equals("")){
            Toast.makeText(this, "Họ tên không được để trống !!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(phoneNumber.equals("")){
            Toast.makeText(this, "Số điện thoại không được để trống !!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(email.equals("")){
            Toast.makeText(this, "Email không được để trống !!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(address.equals("")){
            Toast.makeText(this, "Địa chỉ không được để trống !!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(username.equals("")){
            Toast.makeText(this, "Tên tài khoản không được để trống !!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(phoneNumber.length()!=10){
            Toast.makeText(this, "Số điện thoại phải có 10 số!!", Toast.LENGTH_SHORT).show();
            return false;
        }

        customerUpdate = new Customer(MainActivity.customer.getIdUser(), 2, username, MainActivity.customer.getPassword(), address, email, phoneNumber, spSex.getSelectedItemPosition(),fullname);
        return true;
    }


    private void updateInfor(Customer customer){
        RequestQueue requestQueue = Volley.newRequestQueue(InfoActivity.this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("idUser", null);
            jsonObject.put("idRole", 2);
            jsonObject.put("username", customer.getUsername());
            jsonObject.put("password", customer.getPassword());
            jsonObject.put("addressCustomer", customer.getAddressCustommer());
            jsonObject.put("email", customer.getEmail());
            jsonObject.put("phone", customer.getPhone());
            jsonObject.put("sex", customer.getSex());
            jsonObject.put("name", customer.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestBody = jsonObject.toString();

        String urlCheckUsername = "http://192.168.100.180:3000/api/customer/username/"+ customer.getUsername();
        String urlModify ="http://192.168.100.180:3000/api/customer/info/" + customer.getIdUser();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlCheckUsername, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length()>0 && !customer.getUsername().equals(MainActivity.customer.getUsername())){
                            Toast.makeText(InfoActivity.this, "username exist:" + customer.getUsername(), Toast.LENGTH_SHORT).show();
                        } else {
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, urlModify, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                if(response.getBoolean("status")){
                                                    MainActivity.customer = customerUpdate;
                                                    setInfor();
                                                    Toast.makeText(InfoActivity.this, "Modify account successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(InfoActivity.this, "Error modify customer", Toast.LENGTH_SHORT).show();
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InfoActivity.this, "Error check username exist", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }


    private void setInfor(){
        etFullname.setText(MainActivity.customer.getName());
        etUsername.setText(MainActivity.customer.getUsername());
        etPhoneNumber.setText(MainActivity.customer.getPhone());
        etEmail.setText(MainActivity.customer.getEmail());
        etAddress.setText(MainActivity.customer.getAddressCustommer());
        spSex.setSelection(MainActivity.customer.getSex());
    }

    private void setControl(){
        llHome = findViewById(R.id.llHome);
        llCart = findViewById(R.id.llCart);
        llIntroduce = findViewById(R.id.llIntroduce);
        llInfor = findViewById(R.id.llInfor);
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