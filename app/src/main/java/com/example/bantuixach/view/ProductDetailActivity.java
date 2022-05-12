package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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