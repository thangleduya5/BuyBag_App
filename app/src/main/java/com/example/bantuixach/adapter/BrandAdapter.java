package com.example.bantuixach.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bantuixach.R;

import java.util.ArrayList;

public class BrandAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> brand ;
    private int [] hinhLogo;
    private int resource;

    public BrandAdapter(Context context, ArrayList<String> brand, int[] hinhLogo, int resource) {
        this.context = context;
        this.brand = brand;
        this.hinhLogo = hinhLogo;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return brand.toArray().length;
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
        TextView textView = (TextView) view.findViewById(R.id.brandName);
        ImageView imageView = (ImageView) view.findViewById(R.id.brandImage);
        textView.setText(brand.get(i));
        imageView.setImageResource(hinhLogo[i]);
        return view;
    }
}
