package com.example.bantuixach.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bantuixach.R;
import com.example.bantuixach.adapter.BrandAdapter;
import com.example.bantuixach.adapter.ProductAdapter;
import com.example.bantuixach.model.Customer;
import com.example.bantuixach.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton btnInOut;
    SearchView svSearch;
    GridView gridView,gvProduct;
    ArrayList<String> brand = new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();
    int[] hinh= {R.drawable.carticon,R.drawable.carticon,R.drawable.carticon,R.drawable.carticon,R.drawable.carticon,R.drawable.carticon};
    int[] hinhpd = {R.drawable.tuixachmau,R.drawable.tuixachmau};
    LinearLayout llHome, llCart, llIntroduce, llInfor;
    public static Customer customer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        getBrand();
        getProduct();
        customer= (Customer) getIntent().getSerializableExtra("customer");
        if(customer!=null){
            btnInOut.setImageResource(R.drawable.logoution);
            Toast.makeText(this, customer.getName(), Toast.LENGTH_SHORT).show();
        } else {
            btnInOut.setImageResource(R.drawable.loginicon);
        }
        setEvent();

    }


    private void getProduct() {
        //Toast.makeText(MainActivity.this,"a"+ brand, Toast.LENGTH_LONG).show();
        String url ="http://192.168.100.180:3000/api/product";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i< response.length();i++){
                    try {
                        JSONObject k = (JSONObject) response.get(i);
                        products.add(new Product(k.getInt("idProduct"), k.getInt("price"), k.getString("descr"),
                                k.getString("title"), k.getInt("idColor"), k.getInt("idBrand"), k.getString("image"),
                                k.getInt("quantity")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                ProductAdapter productAdapter = new ProductAdapter(MainActivity.this ,products,hinhpd, R.layout.product_item_layout);
                gvProduct.setAdapter(productAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue RequestQueue = Volley.newRequestQueue(this);
        RequestQueue.add(jsonArrayRequest);
    }

    private void getBrand() {
        String url ="http://192.168.100.180:3000/api/brand";
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                System.out.println(response);
//                getData(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i< response.length();i++){
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        String brandName = jsonObject.getString("nameBrand");

                        brand.add(brandName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                Toast.makeText(MainActivity.this,"a"+ brand, Toast.LENGTH_LONG).show();
                BrandAdapter brandAdapter = new BrandAdapter(MainActivity.this ,brand,hinh, R.layout.gridview_row);
                gridView.setAdapter(brandAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue RequestQueue = Volley.newRequestQueue(this);
        RequestQueue.add(jsonArrayRequest);


    }

    private void setEvent(){
        btnInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                customer = null;
            }
        });
        llCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.customer==null){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else{
                    startActivity(new Intent(MainActivity.this, CartActivity.class));
                }
            }
        });
        llIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IntroduceActivity.class));
            }
        });
        llInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.customer==null){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else{
                    startActivity(new Intent(MainActivity.this, InfoActivity.class));
                }
            }
        });
    }

    private void setControl(){
        btnInOut = findViewById(R.id.btnInOut);
        svSearch = findViewById(R.id.svSearch);
        llHome = findViewById(R.id.llHome);
        llCart = findViewById(R.id.llCart);
        llIntroduce = findViewById(R.id.llIntroduce);
        llInfor = findViewById(R.id.llInfor);
        gridView = findViewById(R.id.gvBrand);
        gvProduct = findViewById(R.id.gvProduct);
    }
}