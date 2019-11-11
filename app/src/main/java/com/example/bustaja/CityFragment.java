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


public class CityFragment extends Fragment {

    TextView city_empty_tv;
    RecyclerView city_listview;
    FloatingActionButton city_refresh_fab;
    ArrayList<CityItem> cityItem=new ArrayList<>();
    CityAdapter cityAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CityData();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        city_listview = view.findViewById(R.id.city_listview);
        city_empty_tv = view.findViewById(R.id.city_empty_tv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        cityAdapter = new CityAdapter(cityItem,getContext());
        city_listview.setLayoutManager(mLayoutManager);
        city_listview.setAdapter(cityAdapter);
        city_refresh_fab = view.findViewById(R.id.city_refresh_fab);
        city_refresh_fab.setVisibility(View.VISIBLE);

        city_refresh_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "최신 정보를 불러오는 중입니다", Snackbar.LENGTH_SHORT).setAction("Refresh", null).show();
            }
        });
        if (cityItem.isEmpty()) {
            city_listview.setVisibility(View.GONE);
            city_empty_tv.setVisibility(View.VISIBLE);
        }
        else {
            city_listview.setVisibility(View.VISIBLE);
            city_empty_tv.setVisibility(View.GONE);
        }

        return view;
    }

    private void CityData() {
        cityItem.add(new CityItem("1","공용차고지-공용차고지"));
        cityItem.add(new CityItem("2","공용차고지-공용차고지"));
        cityItem.add(new CityItem("03","공용차고지-공용차고지"));
        cityItem.add(new CityItem("5","공용차고지-공용차고지"));
        cityItem.add(new CityItem("06","공용차고지-공용차고지"));
        cityItem.add(new CityItem("6-1","공용차고지-공용차고지"));
        cityItem.add(new CityItem("6-2","공용차고지-공용차고지"));
        cityItem.add(new CityItem("7","공용차고지-공용차고지"));
        cityItem.add(new CityItem("8","공용차고지-공용차고지"));

    }


}