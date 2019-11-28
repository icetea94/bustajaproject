package com.example.bustaja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> implements Serializable {

    ArrayList<CityItem> cityItem;
    Context context;


    RecyclerView city_listview;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference boardRef2;
    DatabaseReference rootRef2;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView city_bus_num, city_bus_stop, city_route_id;


        public MyViewHolder(View view) {

            super(view);
            city_listview = view.findViewById(R.id.city_listview);
            city_bus_num = view.findViewById(R.id.city_bus_num);
            city_bus_stop = view.findViewById(R.id.city_bus_stop);
            city_route_id = view.findViewById(R.id.city_route_id);
            view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    MenuItem Add = menu.add(Menu.NONE, 1002, 1, "즐겨찾기에 추가");
                    Add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case 1002:
//즐겨찾기 추가 코드

                                    int position = getAdapterPosition();
                                    String ab =cityItem.get(position).getCitybusnum();
                                    String cd = cityItem.get(position).getCitybusstop();
                                    String ef = cityItem.get(position).getCityRouteId();
                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                    rootRef2 = firebaseDatabase.getReference();//괄호 안이 비어있으면 최상위 노드를 뜻함
                                    FavorItem favorItem2 = new FavorItem(""+ab,""+cd,""+ef);
                                    boardRef2 = rootRef2.child("Favors");
                                    boardRef2.push().setValue(favorItem2);
                                    notifyDataSetChanged();

//                                    SharedPreferences cityFavor;
//
//                                    cityFavor = context.getSharedPreferences("FavorList" + i, 0);
//                                    SharedPreferences.Editor editor = cityFavor.edit();
//                                    editor.putString("FavorNum", ab);
//                                    editor.putString("FavorStop", cd);
//                                    editor.putString("FavorId", ef);
//                                    editor.commit();

                                    break;
                            }
                            return false;
                        }
                    });

                }
            });

        }
    }


    public CityAdapter(ArrayList<CityItem> cityItem, Context context) {
        this.cityItem = cityItem;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_list_item, parent, false);
        CityAdapter.MyViewHolder holder = new CityAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        com.example.bustaja.CityAdapter.MyViewHolder holder3 = (com.example.bustaja.CityAdapter.MyViewHolder) holder;

        holder3.city_bus_num.setText(cityItem.get(position).getCitybusnum());
        holder3.city_bus_stop.setText(cityItem.get(position).getCitybusstop());
        holder3.city_route_id.setText(cityItem.get(position).getCityRouteId());

    }


    @Override
    public int getItemCount() {
        return cityItem.size();
    }

}
