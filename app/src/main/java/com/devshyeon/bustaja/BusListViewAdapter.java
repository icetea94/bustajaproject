package com.devshyeon.bustaja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BusListViewAdapter extends RecyclerView.Adapter<BusListViewAdapter.MyViewHolder> {

    private ArrayList<Stopmember> members;
    private LayoutInflater mInflate;
    private Context mContext;

    public BusListViewAdapter(Context context, ArrayList<Stopmember> items) {
        this.members = items;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }


    @NonNull
    @Override
    public BusListViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.listview_item, parent, false);
        BusListViewAdapter.MyViewHolder viewHolder = new BusListViewAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusListViewAdapter.MyViewHolder holder, int position) {
        //binding
        holder.stationName.setText(members.get(position).stationName);
        holder.stationSeq.setText(members.get(position).stationSeq);
        holder.stationId.setText(members.get(position).stationId);
        holder.stationx.setText(members.get(position).stationx);
        holder.stationy.setText(members.get(position).stationy);

        //Click event
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    //ViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView stationName;
        public TextView stationSeq;
        public TextView stationId;
        public TextView stationx;
        public TextView stationy;

        public MyViewHolder(View itemView) {
            super(itemView);

            stationName = itemView.findViewById(R.id.item_tv_name);
            stationSeq = itemView.findViewById(R.id.item_tv_num);
            stationId = itemView.findViewById(R.id.item_tv_Id);
            stationx = itemView.findViewById(R.id.item_tv_x);
            stationy = itemView.findViewById(R.id.item_tv_y);
        }
    }

}