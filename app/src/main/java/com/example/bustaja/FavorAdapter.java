package com.example.bustaja;


import android.content.Context;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class FavorAdapter extends RecyclerView.Adapter {

    ArrayList<FavorItem> favorItem;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnCreateContextMenuListener {

        public TextView bus_num, busstop_first_final;


        public MyViewHolder(View view) {

            super(view);

            bus_num = view.findViewById(R.id.bus_num);
            busstop_first_final = view.findViewById(R.id.busstop_first_final);
            view.setOnCreateContextMenuListener(this);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //클릭된 아이템뷰가 리사이클러 뷰에서 몇번째 index(position)인지 알아내기
                    int position = getAdapterPosition();

                    Toast.makeText(context, position + ":" + favorItem.get(position), Toast.LENGTH_SHORT).show();
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
                        favorItem.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), favorItem.size());

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
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder holder2=(MyViewHolder)holder;

        holder2.bus_num.setText(favorItem.get(position).getBusNum());
        holder2.busstop_first_final.setText(favorItem.get(position).getBusstopFF());


    }


    @Override
    public int getItemCount() {
        return favorItem.size();
    }

}
