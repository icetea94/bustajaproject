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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FavorAdapter extends RecyclerView.Adapter implements Serializable {

    ArrayList<FavorItem> favorItem;
    Context context;
    RecyclerView favor_listview;
    TextView favor_empty_tv;
    FavorAdapter favorAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference boardRef2;
    DatabaseReference rootRef2;

    String dbName="Favor.db";//데이터베이스 파일명
    String tableName = "FavorList";//표 이름
    SQLiteDatabase db;

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnCreateContextMenuListener {

        public TextView bus_num, busstop_first_final,favor_route_id;


        public MyViewHolder(View view) {

            super(view);
            favor_listview = view.findViewById(R.id.favor_listview);
            favor_empty_tv = view.findViewById(R.id.favor_empty_tv);
            bus_num = view.findViewById(R.id.bus_num);
            busstop_first_final = view.findViewById(R.id.busstop_first_final);
            favor_route_id=view.findViewById(R.id.favor_route_id);
            view.setOnCreateContextMenuListener(this);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //클릭된 아이템뷰가 리사이클러 뷰에서 몇번째 index(position)인지 알아내기
//                    int position = getAdapterPosition();
//
//                    Toast.makeText(context, position + ":" + favorItem.get(position), Toast.LENGTH_SHORT).show();
                }
            });


        }



        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Delete = menu.add(Menu.NONE, 1001, 1, "삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {


            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case 1001:
                        int position=getAdapterPosition();
                        String ef =favorItem.get(position).getFavorRouteId();
                        db = context.openOrCreateDatabase(dbName,MODE_PRIVATE,null);
                        db.execSQL("DELETE FROM "+tableName+" WHERE ef=?", new String[]{ef});

                        favorItem.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), favorItem.size());
                        notifyDataSetChanged();




//                        firebaseDatabase = FirebaseDatabase.getInstance();
//                        rootRef2 = firebaseDatabase.getReference();//괄호 안이 비어있으면 최상위 노드를 뜻함
//                        boardRef2 = rootRef2.child("Favors");
//                        boardRef2.removeValue();




                        break;
                }

                return true;
            }
        };

    }


    public FavorAdapter(ArrayList<FavorItem> favorItem,Context context){

        this.favorItem = favorItem;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favor_list_item, parent, false);
        MyViewHolder holder=new FavorAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FavorAdapter.MyViewHolder holder2=(FavorAdapter.MyViewHolder)holder;


        if(favorItem.get(position).getBusNum()==null){
            holder2.bus_num.setText("");
        }else{
            holder2.bus_num.setText(favorItem.get(position).getBusNum());
        }

        if(favorItem.get(position).getBusstopFF()==null){
            holder2.busstop_first_final.setText("");
        }else{
            holder2.busstop_first_final.setText(favorItem.get(position).getBusstopFF());
        }
        if(favorItem.get(position).getFavorRouteId()==null){
            holder2.favor_route_id.setText("");
        }else{
            holder2.favor_route_id.setText(favorItem.get(position).getFavorRouteId());
        }

    }


    @Override
    public int getItemCount() {
        return favorItem.size();
    }

}