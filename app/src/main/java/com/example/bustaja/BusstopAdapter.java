package com.example.bustaja;

import android.content.Context;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;




import java.util.ArrayList;

public class BusstopAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> busItem;


    public BusstopAdapter(ArrayList<String> busItem, Context context) {
        this.busItem = busItem;
        this.context = context;
    }

    @Override
    public int getCount() {
        return busItem.size();
    }

    @Override
    public Object getItem(int position) {
        return busItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertview, ViewGroup viewGroup) {



        return convertview;
    }
}