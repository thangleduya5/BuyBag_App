package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bantuixach.R;
import com.example.bantuixach.adapter.CartAdapter;
import com.example.bantuixach.model.Bill_detail;
import com.example.bantuixach.model.Cart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CartActivity extends AppCompatActivity {

    ListView lvCart;
    TextView tvSum;
    Button btnOrderBag;
    private ArrayList<Cart> carts;
    private CartAdapter cartAdapter;
    LinearLayout llHome, llCart, llIntroduce, llInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setControl();
        getCartByIDUser();
        setEvent();
    }

    private void setEvent(){
        btnOrderBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);


                JSONObject jsonObject0 = new JSONObject();
                try {
                    jsonObject0.put("idUser", MainActivity.customer.getIdUser());
                    jsonObject0.put("dateBill", Calendar.getInstance().getTime());
                    jsonObject0.put("status", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String requestBody0 = jsonObject0.toString();
                JsonObjectRequest jsonObjectRequest0 = new JsonObjectRequest(Request.Method.POST, "http://192.168.100.180:3000/api/bill", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

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
                        return requestBody0.getBytes();
                    }
                };

                requestQueue.add(jsonObjectRequest0);

                Bill_detail bill_detail;
                for(Cart k: carts){
                    bill_detail = new Bill_detail();

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("idBill", bill_detail.getIdBill());
                        jsonObject.put("idProduct", bill_detail.getIdProduct());
                        jsonObject.put("price", bill_detail.getPrice());
                        jsonObject.put("quantity", bill_detail.getQuantity());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String requestBody = jsonObject.toString();

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://192.168.100.180:3000/api/bill_detail", null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(CartActivity.this, "Lỗi tạo bill detail: ", Toast.LENGTH_SHORT).show();
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

                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.DELETE, "http://192.168.100.180:3000/api/cart/idUser/"+MainActivity.customer.getIdUser(), null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getBoolean("status")){
                                        startActivity(new Intent(CartActivity.this, NoticeActivity.class));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(CartActivity.this, "Xóa giỏ hàng bị lỗi", Toast.LENGTH_SHORT).show();
                            }
                        });

                requestQueue.add(jsonObjectRequest1);
            }
        });
        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                intent.putExtra("customer", MainActivity.customer);
                startActivity(intent);
            }
        });
        llIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, IntroduceActivity.class));
            }
        });
        llInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, InfoActivity.class));
            }
        });
    }

    private void setControl(){
        llHome = findViewById(R.id.llHome);
        llCart = findViewById(R.id.llCart);
        llIntroduce = findViewById(R.id.llIntroduce);
        llInfor = findViewById(R.id.llInfor);
        lvCart = findViewById(R.id.lvCart);
        tvSum = findViewById(R.id.tvSum);
        btnOrderBag = findViewById(R.id.btnOrderBag);
        carts = new ArrayList<>();
        cartAdapter = new CartAdapter(CartActivity.this, R.layout.bill_item_layout, carts);
        lvCart.setAdapter(cartAdapter);
    }

    public void getCartByIDUser(){
        carts.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://192.168.100.180:3000/api/cart/"+ MainActivity.customer.getIdUser(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i =0 ;i<response.length();i++){
                            try {
                                JSONObject k = response.getJSONObject(i);
                                carts.add(new Cart(k.getInt("idUser"), k.getInt("idProduct"), k.getInt("quantity")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        cartAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CartActivity.this, "Lỗi lấy sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}