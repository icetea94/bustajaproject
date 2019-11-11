package com.example.bustaja;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BusstopAdapter  extends RecyclerView.Adapter<BusstopAdapter.MyViewHolder> {

    Context context;
    ArrayList<BusstopItem> busstopItem;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView busstop_num, busstop_name;


        public MyViewHolder(View view) {

            super(view);

            busstop_num = view.findViewById(R.id.busstop_num);
            busstop_name =view.findViewById(R.id.busstop_name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //클릭된 아이템뷰가 리사이클러 뷰에서 몇번째 index(position)인지 알아내기
                    int position  = getAdapterPosition();


                    Toast.makeText(context,position+":"+busstopItem.get(position),Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    public BusstopAdapter(ArrayList<BusstopItem> busstopItem,Context context){

        this.busstopItem = busstopItem;
        this.context = context;

    }

    @NonNull
    @Override
    public BusstopAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.busstop_list_item, parent, false);
        BusstopAdapter.MyViewHolder holder=new BusstopAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusstopAdapter.MyViewHolder holder, int position) {
        com.example.bustaja.BusstopAdapter.MyViewHolder holder4=(com.example.bustaja.BusstopAdapter.MyViewHolder)holder;

        holder4.busstop_num.setText(busstopItem.get(position).getBusstopnum());
        holder4.busstop_name.setText(busstopItem.get(position).getBusstopname());


    }


    @Override
    public int getItemCount() {
        return busstopItem.size();
    }

}
