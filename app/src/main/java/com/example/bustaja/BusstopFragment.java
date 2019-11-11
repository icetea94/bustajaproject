package com.example.bustaja;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class BusstopFragment extends Fragment {

    TextView busstop_empty_tv;
    ListView busstop_listview;
    FloatingActionButton busstop_refresh_fab;
    ArrayList<String> busItem=new ArrayList<>();
    BusstopAdapter busstopAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_busstop, container, false);
        busstop_listview=view.findViewById(R.id.busstop_listview);
        busstop_empty_tv = view.findViewById(R.id.busstop_empty_tv);
        busstop_listview.setEmptyView(busstop_listview);
        busstop_refresh_fab=view.findViewById(R.id.busstop_refresh_fab);
        busstop_refresh_fab.setVisibility(View.VISIBLE);

        busstop_refresh_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "최신 정보를 불러오는 중입니다", Snackbar.LENGTH_SHORT).setAction("Refresh", null).show();
            }
        });
//        if (favorItem.isEmpty()) {
//            favor_listview.setVisibility(View.GONE);
//            favor_empty_tv.setVisibility(View.VISIBLE);
//        }
//        else {
//            favor_listview.setVisibility(View.VISIBLE);
//            favor_empty_tv.setVisibility(View.GONE);
//        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        busstop_listview= view.findViewById(R.id.busstop_listview);


        //리스트뷰의 아이템을 클릭하면...
        busstop_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), busItem.get(position), Toast.LENGTH_SHORT).show();

            }
        });
    }
}