package com.example.bustaja;


import android.os.Bundle;
import android.view.LayoutInflater;
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


public class CityFragment extends Fragment {
SwipeRefreshLayout swipeRefreshLayout;
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
        swipeRefreshLayout=view.findViewById(R.id.bus_refresh);
        cityAdapter = new CityAdapter(cityItem,getContext());
        city_listview.setLayoutManager(mLayoutManager);
        city_listview.setAdapter(cityAdapter);
        city_refresh_fab = view.findViewById(R.id.city_refresh_fab);
        city_refresh_fab.setVisibility(View.VISIBLE);
//
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                swipeRefreshLayout.setRefreshing(false);
            }
        });
//



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

        cityItem.add(new CityItem("01","관동교장 -> 관동교장"));
        cityItem.add(new CityItem("1","관동교장 -> 안양역"));
        cityItem.add(new CityItem("1-1","고속철도광명역 -> 관악역"));
        cityItem.add(new CityItem("2","안양예술공원종점.갈메산기도원입구\n -> 소곡마을.신성중학교"));
        cityItem.add(new CityItem("2-1","안양예술공원종점.갈메산기도원입구\n -> 안양예술공원종점.갈메산기도원입구"));
        cityItem.add(new CityItem("03","모락산현대A -> 관악타운"));
        cityItem.add(new CityItem("3","임곡주공A -> 안양역"));
        cityItem.add(new CityItem("3-1","임곡주공A -> 안양역"));
        cityItem.add(new CityItem("05","시티병원.구선병원(마을) -> 인덕원역4호선"));
        cityItem.add(new CityItem("05-1","시티병원.구선병원(마을) -> 인덕원역4호선"));
        cityItem.add(new CityItem("5","부안중학교 -> 관양고등학교"));
        cityItem.add(new CityItem("5-1","양명고등학교.대우아파트 -> 금강펜터리움IT타워"));
        cityItem.add(new CityItem("5-3","명학역 -> 무궁화코오롱아파트"));
        cityItem.add(new CityItem("5-5","자유공원 -> 관양고등학교"));
        cityItem.add(new CityItem("06","한가람한양아파트 -> 한가람한양아파트"));
        cityItem.add(new CityItem("06","한가람한양아파트 -> 한가람한양아파트"));
        cityItem.add(new CityItem("6","학현삼거리 -> 인덕원역4호선"));
        cityItem.add(new CityItem("6-1","한림대병원 -> 포도원.현대.대림아파트.유한양행"));
        cityItem.add(new CityItem("6-1","새터마을종점 -> 인덕원역4호선"));
        cityItem.add(new CityItem("6-1","한림대병원 -> 한림대병원"));
        cityItem.add(new CityItem("6-2","경인교대 -> 금정역"));
        cityItem.add(new CityItem("06-2","모락산안골식당 -> 인덕원역4호선"));
        cityItem.add(new CityItem("6-3","벽산아파트1단지정문 -> 안양예술공원"));
        cityItem.add(new CityItem("6-5","호계푸르지오A -> 호계푸르지오A"));
        cityItem.add(new CityItem("7","스마트베이 -> 임곡주공아파트"));
        cityItem.add(new CityItem("7","공작럭키아파트 -> 임곡주공아파트"));
        cityItem.add(new CityItem("8","포도원.현대.대림아파트.유한양행 -> 문화공원"));
        cityItem.add(new CityItem("9","신안초등학교.우성아파트 -> 관악역"));
        cityItem.add(new CityItem("10","청계산주차장 -> 인덕원역4호선"));
        cityItem.add(new CityItem("10-1","평촌역 -> 성원샹떼빌"));
        cityItem.add(new CityItem("10-1","청계산주차장 -> 인덕원역4호선"));
        cityItem.add(new CityItem("10-2","무궁화진흥A -> 소곡마을.신성중학교"));
        cityItem.add(new CityItem("11","자유공원 -> 옥박골"));
        cityItem.add(new CityItem("12","숲속마을3.5단지 -> 농수산물도매시장"));
        cityItem.add(new CityItem("13","숲속마을3.5단지 -> 옥박골"));
        cityItem.add(new CityItem("70","명학역 -> 주공뜨란채A"));

    }


}