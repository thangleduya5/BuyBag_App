package com.example.bantuixach.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bantuixach.R;
import com.example.bantuixach.model.Product;
import com.example.bantuixach.view.MainActivity;
import com.example.bantuixach.view.ProductDetailActivity;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Product> products ;
    private int [] hinhProduct;
    private int resource;

    public ProductAdapter(Context context, ArrayList<Product> products, int[] hinhProduct, int resource) {
        this.context = context;
        this.products = products;
        this.hinhProduct = hinhProduct;
        this.resource = resource;
    }
    @Override
    public int getCount() {
        return products.toArray().length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(resource, null);
        TextView textView = (TextView) view.findViewById(R.id.tvProductName);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivProductImage);
        TextView textViewPrice = (TextView) view.findViewById(R.id.tvProductCost);
        textView.setText(products.get(i).getTitle());
        imageView.setImageResource(R.drawable.tuixachmau);
        textViewPrice.setText(products.get(i).getPrice()+"");
        Product product = products.get(i);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("item", product);
                ((MainActivity)context).startActivity(intent);
            }
        });
        return view;
    }
}
