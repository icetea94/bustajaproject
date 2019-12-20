package com.devshyeon.bustaja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class BusstopLocation extends AppCompatActivity  implements OnMapReadyCallback {

    public String dataKey = "HPhv7q1fispfruW2r4aROp%2FvBjNa6M0jg%2F1FVE4BliwYYTY4oWQ54%2Fs10n8Ny2eFJcTKZ21NEbFaC%2BSs%2BGWNSQ%3D%3D";
    private String requestUrl;
    GoogleMap gMap;
    String stationId,stationx,stationy,stationseq,stationname;
    TextView stopname,stop_empty_tv;
    RecyclerView recyclerView;
    ParsingAdapter adapter;

    ArrayList<ParsingItem> list = null;
    ParsingItem bus = null;

    private FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onStart() {
        super.onStart();

        MyAsyncTask4 myAsyncTask4 = new MyAsyncTask4();
        myAsyncTask4.execute();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busstop_location);
        stopname=findViewById(R.id.stopname);
        getWindow().setWindowAnimations(android.R.style.Animation_Toast);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        stop_empty_tv=findViewById(R.id.stop_empty_tv);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //프레그먼트 관리자 객체 소환
        FragmentManager fragmentManager = getSupportFragmentManager();

        //관리자에게 xml에 있는 프레그먼트를 찾아와달라고 요청
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.frag_map);

        //프레그먼트에게 맵 가져오도록
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        stationId = intent.getExtras().getString("stationId");
        stationx = intent.getExtras().getString("stationx");
        stationy = intent.getExtras().getString("stationy");
        stationseq = intent.getExtras().getString("stationseq");
        stationname = intent.getExtras().getString("stationname");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("정류소 정보");

        stopname.setText(stationseq+stationname);

    }

    //구글 맵 로딩이 완료되면 실행되는 콜백메소드
    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;

        Double locationy=Double.parseDouble(stationy);
        Double locationx=Double.parseDouble(stationx);
        LatLng Station = new LatLng(locationy, locationx);

        //마커객체 생성 및 설정
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Station);
        markerOptions.title(stationname);
        markerOptions.snippet("버스정류장");

        //마커 맵에 추가하기
        gMap.addMarker(markerOptions);

        //지도 위치 지정 및 줌
        gMap.moveCamera(CameraUpdateFactory.newLatLng(Station));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Station, 16));
        //대표적인 맵 설정

        UiSettings settings = gMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        markerOptions.draggable(false);
    }

    public class MyAsyncTask4 extends AsyncTask<String, Void, String> {



        @Override
        protected String doInBackground(String... strings) {

            requestUrl = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice/station?serviceKey=" + dataKey + "&stationId=" + stationId;

            try {
                boolean b_locationNo1 = false;
                boolean b_predictTime1 = false;
                boolean b_plateNo1 = false;
                boolean b_routeId = false;

                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is, "UTF-8"));

                String tag;
                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<ParsingItem>();
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("busArrivalList") && bus != null) {
                                list.add(bus);
                            }
                            break;
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("busArrivalList")) {
                                bus = new ParsingItem();
                            }
                            if (parser.getName().equals("locationNo1")) b_locationNo1 = true; //버스위치
                            if (parser.getName().equals("plateNo1")) b_plateNo1 = true;  //차량번호
                            if (parser.getName().equals("predictTime1")) b_predictTime1 = true;  //버스도착예정시간
                            if (parser.getName().equals("routeId")) b_routeId = true;   //노선 ID

                            break;
                        case XmlPullParser.TEXT:
                            if (b_locationNo1) {
                                bus.setLocationNo1(parser.getText());
                                b_locationNo1 = false;
                            }
                            else if (b_plateNo1) {
                                bus.setPlateNo1(parser.getText());
                                b_plateNo1 = false;
                            }else if(b_predictTime1){
                                bus.setPredictTime1(parser.getText());
                                b_predictTime1 = false;
                            }
                            else if (b_routeId) {
                                bus.setRouteId(parser.getText());
                                b_routeId = false;
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //어답터 연결
            ParsingAdapter adapter = new ParsingAdapter(getApplicationContext(), list);
            recyclerView.setAdapter(adapter);

            if (list.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                stop_empty_tv.setVisibility(View.VISIBLE);
            }
            else {
                recyclerView.setVisibility(View.VISIBLE);
                stop_empty_tv.setVisibility(View.GONE);
            }
        }
    }
}
