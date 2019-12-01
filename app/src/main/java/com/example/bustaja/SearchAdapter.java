package com.example.bustaja;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private ArrayList<BussearchItem> mList;
    private LayoutInflater mInflate;
    private Context mContext;



    public SearchAdapter(Context context, ArrayList<BussearchItem> items) {
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.bussearch_item, parent, false);
        SearchAdapter.MyViewHolder viewHolder = new SearchAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        //binding

        holder.downFirstTime.setText(mList.get(position).downFirstTime);
        holder.downLastTime.setText(mList.get(position).downLastTime);
        holder.endStationName.setText(mList.get(position).endStationName);
        holder.regionName.setText(mList.get(position).regionName);
        holder.routeName.setText(mList.get(position).routeName);
        holder.startStationName.setText(mList.get(position).startStationName);
        holder.upFirstTime.setText(mList.get(position).upFirstTime);
        holder.upLastTime.setText(mList.get(position).upLastTime);

        //Click event
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    //ViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView downFirstTime;
        public TextView downLastTime;
        public TextView endStationName;
        public TextView regionName;
        public TextView routeName;
        public TextView startStationName;
        public TextView upFirstTime;
        public TextView upLastTime;




        public MyViewHolder(View itemView) {
            super(itemView);
            downFirstTime = itemView.findViewById(R.id.tv_downFirstTime);
            downLastTime = itemView.findViewById(R.id.tv_downLastTime);
            endStationName = itemView.findViewById(R.id.tv_endStationName);
            regionName = itemView.findViewById(R.id.tv_regionName);
            routeName = itemView.findViewById(R.id.tv_routeName);
            startStationName = itemView.findViewById(R.id.tv_startStationName);
            upFirstTime = itemView.findViewById(R.id.tv_upFirstTime);
            upLastTime = itemView.findViewById(R.id.tv_upLastTime);

        }
    }

}