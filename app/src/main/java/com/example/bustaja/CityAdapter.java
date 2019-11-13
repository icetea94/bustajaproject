package com.example.bustaja;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {

        ArrayList<CityItem> cityItem;
        Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView city_bus_num, city_bus_stop;


            public MyViewHolder(View view) {

                super(view);

                city_bus_num = view.findViewById(R.id.city_bus_num);
                city_bus_stop =view.findViewById(R.id.city_bus_stop);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //클릭된 아이템뷰가 리사이클러 뷰에서 몇번째 index(position)인지 알아내기
                        int position  = getAdapterPosition();
                        Toast.makeText(context,position+":"+cityItem.get(position),Toast.LENGTH_SHORT).show();


                    }
                });

            }



        }

        public CityAdapter(ArrayList<CityItem> cityItem,Context context){

            this.cityItem = cityItem;
            this.context = context;

        }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_list_item, parent, false);
        CityAdapter.MyViewHolder holder=new CityAdapter.MyViewHolder(view);

        return holder;
    }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            com.example.bustaja.CityAdapter.MyViewHolder holder3=(com.example.bustaja.CityAdapter.MyViewHolder)holder;

            holder3.city_bus_num.setText(cityItem.get(position).getCitybusnum());
            holder3.city_bus_stop.setText(cityItem.get(position).getCitybusstop());


        }


        @Override
        public int getItemCount() {
            return cityItem.size();
        }

    }
