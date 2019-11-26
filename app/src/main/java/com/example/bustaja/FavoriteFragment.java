package com.example.bustaja;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    RecyclerView favor_listview;
    TextView favor_empty_tv;
    ArrayList<FavorItem> favorItem = new ArrayList<>();
    FavorAdapter favorAdapter;
    FloatingActionButton favor_refresh_fab;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FavorData();




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);


        favorAdapter = new FavorAdapter(favorItem,getContext());
swipeRefreshLayout=view.findViewById(R.id.favor_refresh);
        favor_listview = view.findViewById(R.id.favor_listview);
        favor_empty_tv = view.findViewById(R.id.favor_empty_tv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        favor_listview.setLayoutManager(mLayoutManager);
        favor_listview.setAdapter(favorAdapter);
        favor_refresh_fab=view.findViewById(R.id.favor_refresh_fab);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                favorAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        favor_refresh_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "최신 정보를 불러오는 중입니다", Snackbar.LENGTH_SHORT).setAction("Refresh", null).show();
                favorAdapter.notifyDataSetChanged();
            }
        });
        if (favorItem.isEmpty()) {
            favor_listview.setVisibility(View.GONE);
            favor_empty_tv.setVisibility(View.VISIBLE);
        }
        else {
            favor_listview.setVisibility(View.VISIBLE);
            favor_empty_tv.setVisibility(View.GONE);
        }
        return view;
    }

    private void FavorData() {

        favorItem.add(new FavorItem("01","관동교장 -> 관동교장"));
    }


}

