package com.devshyeon.bustaja;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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


public class CityFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    TextView city_empty_tv;
    RecyclerView city_listview;
    FloatingActionButton city_refresh_fab;
    ArrayList<CityItem> cityItem=new ArrayList<>();
    CityAdapter cityAdapter;
    ProgressDialog progressDialog;
    String dbName="Favor.db";//데이터베이스 파일명
    String tableName = "FavorList";//표 이름
    SQLiteDatabase db;


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
        swipeRefreshLayout = view.findViewById(R.id.bus_refresh);
        cityAdapter = new CityAdapter(cityItem, getContext());
        city_listview.setLayoutManager(mLayoutManager);
        city_listview.setAdapter(cityAdapter);
        city_refresh_fab = view.findViewById(R.id.city_refresh_fab);
        city_refresh_fab.setVisibility(View.VISIBLE);
        view.setOnCreateContextMenuListener(this);
        progressDialog = new ProgressDialog(getContext());
//
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                cityAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
//







        city_listview.addOnItemTouchListener(new CityFragment.RecyclerTouchListener(getContext(), city_listview, new MessageboardMain.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CityItem dict = cityItem.get(position);

                Intent intent = new Intent(getContext(), BusWebinfo.class);

                intent.putExtra("busnum", dict.getCitybusnum());
                intent.putExtra("busstop", dict.getCitybusstop());
                intent.putExtra("routeid", dict.getCityRouteId());
                progressDialog.setMessage("경기 버스정보 웹 화면으로 이동중입니다. \n잠시 기다려 주세요...");
                progressDialog.show();

                startActivity(intent);

                progressDialog.dismiss();


            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));




///////////
        //옵션메뉴 만들어주는 메소드




        city_refresh_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "최신 정보를 불러오는 중입니다", Snackbar.LENGTH_SHORT).setAction("Refresh", null).show();
                cityAdapter.notifyDataSetChanged();

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





    public void CityData() {

        cityItem.add(new CityItem("01","관동교장 -> 석수역","241245006"));
        cityItem.add(new CityItem("1","관동교장 -> 안양역","241245002"));
        cityItem.add(new CityItem("1-1","KTX광명역 -> 관악역","241274002"));
        cityItem.add(new CityItem("2","안양예술공원종점.갈메산기도원입구\n -> 소곡마을.신성중학교","241253001"));
        cityItem.add(new CityItem("2-1","안양예술공원종점.갈메산기도원입구\n -> 법원검찰청.평촌역","241253002"));
        cityItem.add(new CityItem("03","모락산현대A -> 관악타운","241392001"));
        cityItem.add(new CityItem("3-1","임곡주공A -> 안양역","241247003"));
        cityItem.add(new CityItem("05","시티병원.구선병원(마을) -> 인덕원역4호선","241453015"));
        cityItem.add(new CityItem("05-1","시티병원.구선병원(마을) -> 인덕원역4호선","241453016"));
        cityItem.add(new CityItem("5","부안중학교 -> 관양고등학교","241245001"));
        cityItem.add(new CityItem("5-1","양명고등학교.대우아파트 -> 금강펜터리움IT타워","241250001"));
        cityItem.add(new CityItem("5-3","명학역 -> 무궁화코오롱아파트","241245004"));
        cityItem.add(new CityItem("5-5","자유공원 -> 관양고등학교","241245003"));
        cityItem.add(new CityItem("06","한가람한양아파트 -> 한가람한양아파트","241251002"));
        cityItem.add(new CityItem("6-1","한림대병원 -> 포도원.현대.대림아파트.유한양행","241252001"));
        cityItem.add(new CityItem("6-2","경인교대 -> 금정역","241252002"));
        cityItem.add(new CityItem("06-2","모락산안골식당 -> 인덕원역4호선","241394002"));
        cityItem.add(new CityItem("6-3","벽산아파트1단지정문 -> 안양예술공원","241252003"));
        cityItem.add(new CityItem("6-5","호계푸르지오A -> 호계푸르지오A","241252004"));
        cityItem.add(new CityItem("7","안양시자원회수시설 -> 임곡주공아파트","241247001"));
        cityItem.add(new CityItem("8","포도원.현대.대림아파트.유한양행 -> 문화공원","241245005"));
        cityItem.add(new CityItem("9","신안초등학교.우성아파트 -> 관악역","241248001"));
        cityItem.add(new CityItem("10","청계산주차장 -> 인덕원역4호선","241395002"));
        cityItem.add(new CityItem("10-1","평촌역 -> 성원샹떼빌","241249002"));
        cityItem.add(new CityItem("10-1","청계산주차장 -> 인덕원역4호선","241395001"));
        cityItem.add(new CityItem("10-2","무궁화진흥A -> 소곡마을.신성중학교","241249001"));
        cityItem.add(new CityItem("11","자유공원 -> 옥박골","241401001"));
        cityItem.add(new CityItem("12","숲속마을3.5단지 -> 농수산물도매시장","241401002"));
        cityItem.add(new CityItem("13","숲속마을3.5단지 -> 옥박골","241401003"));
        cityItem.add(new CityItem("70","명학역 -> 주공뜨란채A","241245007"));

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MessageboardMain.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MessageboardMain.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }


    }


}