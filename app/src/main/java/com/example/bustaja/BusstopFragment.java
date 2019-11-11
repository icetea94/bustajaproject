package com.example.bustaja;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class BusstopFragment extends Fragment {

    TextView busstop_empty_tv;
    RecyclerView busstop_listview;
    FloatingActionButton busstop_refresh_fab;
    ArrayList<BusstopItem> busstopItem=new ArrayList<>();
    BusstopAdapter busstopAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusstopData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_busstop, container, false);
        busstop_listview=view.findViewById(R.id.busstop_listview);
        busstop_empty_tv = view.findViewById(R.id.busstop_empty_tv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        busstopAdapter = new BusstopAdapter(busstopItem,getContext());

        busstop_refresh_fab=view.findViewById(R.id.busstop_refresh_fab);
        busstop_refresh_fab.setVisibility(View.VISIBLE);
        busstop_listview.setLayoutManager(mLayoutManager);
        busstop_listview.setAdapter(busstopAdapter);
        busstop_refresh_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "최신 정보를 불러오는 중입니다", Snackbar.LENGTH_SHORT).setAction("Refresh", null).show();
            }
        });
        if (busstopItem.isEmpty()) {
            busstop_listview.setVisibility(View.GONE);
            busstop_empty_tv.setVisibility(View.VISIBLE);
        }
        else {
            busstop_listview.setVisibility(View.VISIBLE);
            busstop_empty_tv.setVisibility(View.GONE);
        }

        return view;
    }

    private void BusstopData() {
        busstopItem.add(new BusstopItem("14112","공용차고지"));
        busstopItem.add(new BusstopItem("14113","공용차고지"));
    }

}