package com.example.bantuixach.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bantuixach.R;
import com.example.bantuixach.model.Cart;
import com.example.bantuixach.view.CartActivity;
import com.example.bantuixach.view.InfoActivity;
import com.example.bantuixach.view.MainActivity;
import com.example.bantuixach.view.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends ArrayAdapter<Cart>{
    private Context context;
    ArrayList<Cart> carts;
    int resource;

    public CartAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Cart> carts) {
        super(context, resource, carts);
        this.context = context;
        this.carts = carts;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return carts.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(resource, null);

        ImageView ivProductImage = (ImageView) view.findViewById(R.id.ivProductImage);
        TextView tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        TextView tvProductCost = (TextView) view.findViewById(R.id.tvProductCost);
        Spinner spNumber = (Spinner) view.findViewById(R.id.spNumber);
        TextView tvProductColor = (TextView) view.findViewById(R.id.tvProductColor);
        ImageButton btnDelete = (ImageButton) view.findViewById(R.id.btnDelete);

        ArrayList<Integer> numbers = new ArrayList<>();
        for(int k =1; k<10;k++){
            numbers.add(k);
        }
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, numbers);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spNumber.setAdapter(adapter);

        Cart cart= carts.get(position);
        ivProductImage.setImageResource(R.drawable.tuixachmau);
        spNumber.setSelection(cart.getQuantity()-1);

        RequestQueue requestQueue = Volley.newRequestQueue((CartActivity) context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://192.168.100.180:3000/api/product/" + cart.getIdProduct(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject k = response.getJSONObject(0);
                            tvProductName.setText(k.getString("title"));
                            tvProductCost.setText(k.getDouble("price")+"");
                            JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Request.Method.GET, "http://192.168.100.180:3000/api/color/"+k.getInt("idColor"), null,
                                    new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            try {
                                                JSONObject k = response.getJSONObject(0);
                                                tvProductColor.setText(k.getString("nameColor"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(context, "Lỗi lấy tên màu trong giỏ hàng", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            requestQueue.add(jsonArrayRequest1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Lỗi lấy thông tin sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("idUser", cart.getIdUser());
                    jsonObject.put("idProduct", cart.getIdProduct());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String requestBody = jsonObject.toString();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, "http://192.168.100.180:3000/api/cart", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getInt("data")>0){
                                        Toast.makeText(context, "Xóa sản phẩm trong giỏ thành công", Toast.LENGTH_SHORT).show();
                                    }
                                    ((CartActivity)context).getCartByIDUser();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Lỗi xóa sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    public byte[] getBody() {
                        return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
                    }
                };

                Toast.makeText(context, requestBody.toString(), Toast.LENGTH_LONG).show();
                RequestQueue requestQueue = Volley.newRequestQueue((CartActivity) context);
                requestQueue.add(jsonObjectRequest);
            }
        });

        return view;
    }
}
