package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.bantuixach.model.Customer;
import com.example.bantuixach.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView ivProduct;
    Button btnAddCart;
    TextView tvProductName, tvProductCost, tvProductID, tvProductDescr, tvProductColor, tvProductBrand, tvProductNumber;
    Product product;
    LinearLayout llHome, llCart, llIntroduce, llInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        product= (Product) getIntent().getSerializableExtra("item");
        setControl();
        setViewValue();
        setEvent();
    }

    private void setEvent(){
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.customer==null){
                    startActivity(new Intent(ProductDetailActivity.this, LoginActivity.class));
                } else{

                    RequestQueue requestQueue = Volley.newRequestQueue(ProductDetailActivity.this);

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("idUser", MainActivity.customer.getIdUser());
                        jsonObject.put("idProduct", Integer.parseInt(tvProductID.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String requestBody = jsonObject.toString();

                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://192.168.100.180:3000/api/cart", null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Toast.makeText(ProductDetailActivity.this,"tvProductID: " + Integer.parseInt(tvProductID.getText().toString()), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(ProductDetailActivity.this,"idUser: " + MainActivity.customer.getIdUser(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(ProductDetailActivity.this,"Chiều dài của response: " + response.length(), Toast.LENGTH_SHORT).show();
//                                    if(response.length()==0){
//                                        JSONObject jsonObject = new JSONObject();
//                                        try {
//                                            jsonObject.put("idUser", MainActivity.customer.getIdUser());
//                                            jsonObject.put("idProduct", Integer.parseInt(tvProductID.getText().toString()));
//                                            jsonObject.put("quantity", 1);
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                        String requestBody = jsonObject.toString();
//
//                                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://192.168.100.180:3000/api/cart", null,
//                                                new Response.Listener<JSONObject>() {
//                                                    @Override
//                                                    public void onResponse(JSONObject response) {
//                                                        try {
//                                                            if(response.getBoolean("status")){
//                                                                Toast.makeText(ProductDetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//                                                                startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
//                                                            }
//                                                        } catch (JSONException e) {
//                                                            e.printStackTrace();
//                                                        }
//                                                    }
//                                                },
//                                                new Response.ErrorListener() {
//                                                    @Override
//                                                    public void onErrorResponse(VolleyError error) {
//                                                        Toast.makeText(ProductDetailActivity.this, "Thêm vào giỏ hàng lỗi", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                })
//                                        {
//                                            @Override
//                                            public byte[] getBody() {
//                                                return requestBody.getBytes();
//                                            }
//                                        };
//                                        requestQueue.add(jsonObjectRequest);
//                                    } else {
//                                        JSONObject jsonObject = new JSONObject();
//                                        try {
//                                            jsonObject.put("idUser", MainActivity.customer.getIdUser());
//                                            jsonObject.put("idProduct", Integer.parseInt(tvProductID.getText().toString()));
//                                            jsonObject.put("quantity", response.getJSONObject(0).getInt("quantity")+1);
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                        String requestBody = jsonObject.toString();
//
//                                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, "http://192.168.100.180:3000/api/cart", null,
//                                                new Response.Listener<JSONObject>() {
//                                                    @Override
//                                                    public void onResponse(JSONObject response) {
//                                                        try {
//                                                            if(response.getBoolean("status")){
//                                                                Toast.makeText(ProductDetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//                                                                startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
//                                                            }
//                                                        } catch (JSONException e) {
//                                                            e.printStackTrace();
//                                                        }
//                                                    }
//                                                },
//                                                new Response.ErrorListener() {
//                                                    @Override
//                                                    public void onErrorResponse(VolleyError error) {
//                                                        Toast.makeText(ProductDetailActivity.this, "Thêm vào giỏ hàng lỗi", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                })
//                                        {
//                                            @Override
//                                            public byte[] getBody() {
//                                                return requestBody.getBytes();
//                                            }
//                                        };
//                                        requestQueue.add(jsonObjectRequest);
//                                    }
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
                    requestQueue.add(jsonArrayRequest);
                }
            }
        });
        llCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.customer==null){
                    startActivity(new Intent(ProductDetailActivity.this, LoginActivity.class));
                } else{
                    startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
                }
            }
        });
        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
                intent.putExtra("customer", MainActivity.customer);
                startActivity(intent);
            }
        });
        llIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, IntroduceActivity.class));
            }
        });
        llInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.customer==null){
                    startActivity(new Intent(ProductDetailActivity.this, LoginActivity.class));
                } else{
                    startActivity(new Intent(ProductDetailActivity.this, InfoActivity.class));
                }
            }
        });
    }

    private void setViewValue(){
        ivProduct.setImageResource(R.drawable.tuixachmau);
        tvProductName.setText(product.getTitle());
        tvProductCost.setText(product.getPrice()+"");
        tvProductID.setText(product.getIdProduct()+"");
        tvProductDescr.setText(product.getDescr());
        tvProductNumber.setText(product.getQuantity()+"");

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, "http://192.168.100.180:3000/api/color/"+product.getIdColor(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            tvProductColor.setText(response.getJSONObject(0).getString("nameColor"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductDetailActivity.this, "Lỗi lấy tên color", Toast.LENGTH_SHORT).show();
                    }
                });

        JsonArrayRequest jsonObjectRequest1 = new JsonArrayRequest(Request.Method.GET, "http://192.168.100.180:3000/api/brand/"+product.getIdBrand(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            tvProductBrand.setText(response.getJSONObject(0).getString("nameBrand"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductDetailActivity.this, "Lỗi lấy tên brand", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ProductDetailActivity.this);
        requestQueue.add(jsonObjectRequest);
        requestQueue.add(jsonObjectRequest1);
    }

    private void setControl(){
        llHome = findViewById(R.id.llHome);
        llCart = findViewById(R.id.llCart);
        llIntroduce = findViewById(R.id.llIntroduce);
        llInfor = findViewById(R.id.llInfor);
        ivProduct = findViewById(R.id.ivProduct);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductCost = findViewById(R.id.tvProductCost);
        tvProductID = findViewById(R.id.tvProductID);
        tvProductDescr = findViewById(R.id.tvProductDescr);
        tvProductColor = findViewById(R.id.tvProductColor);
        tvProductBrand = findViewById(R.id.tvProductBrand);
        tvProductNumber = findViewById(R.id.tvProductNumber);
        btnAddCart = findViewById(R.id.btnAddCart);
    }
}