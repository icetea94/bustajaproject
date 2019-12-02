package com.devshyeon.bustaja;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> implements Serializable {

    ArrayList<CityItem> cityItem;
    Context context;
    String dbName="Favor.db";//데이터베이스 파일명
    String tableName = "FavorList";//표 이름
    SQLiteDatabase db;

    RecyclerView city_listview;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView city_bus_num, city_bus_stop, city_route_id;


        public MyViewHolder(View view) {

            super(view);

            db = context.openOrCreateDatabase(dbName,MODE_PRIVATE,null);

            db.execSQL("CREATE TABLE IF NOT EXISTS " +  tableName  + "(num integer primary key autoincrement,ab text,cd text,ef text not null)");

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


                                    db.execSQL("INSERT INTO " + tableName + "(ab, cd, ef) VALUES('"+ab+"','"+cd+"','"+ef+"')");



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
        com.devshyeon.bustaja.CityAdapter.MyViewHolder holder3 = (com.devshyeon.bustaja.CityAdapter.MyViewHolder) holder;

        holder3.city_bus_num.setText(cityItem.get(position).getCitybusnum());
        holder3.city_bus_stop.setText(cityItem.get(position).getCitybusstop());
        holder3.city_route_id.setText(cityItem.get(position).getCityRouteId());

    }


    @Override
    public int getItemCount() {
        return cityItem.size();
    }

}
