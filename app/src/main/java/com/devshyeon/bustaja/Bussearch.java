package com.devshyeon.bustaja;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Bussearch extends AppCompatActivity {


    public String dataKey = "HPhv7q1fispfruW2r4aROp%2FvBjNa6M0jg%2F1FVE4BliwYYTY4oWQ54%2Fs10n8Ny2eFJcTKZ21NEbFaC%2BSs%2BGWNSQ%3D%3D";
    private String requestUrl;
    ArrayList<BussearchItem> list = null;
    BussearchItem bus2 = null;
    RecyclerView recyclerView;
    EditText search_edittext;
    MenuItem searchItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bussearch_main);

        search_edittext = findViewById(R.id.search_edittext);
        setTitle("버스 노선 별 정보 조회");
        recyclerView = findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void search(View view) {
        MyAsyncTask2 myAsyncTask2 = new MyAsyncTask2();
        myAsyncTask2.execute();
        String RouteID = search_edittext.getText().toString();
        if(RouteID.equals("")) {
            Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home: {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void newOnclick2(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search_edittext.getWindowToken(), 0);
    }

    public class MyAsyncTask2 extends AsyncTask<String, Void, String> {

        String RouteID = search_edittext.getText().toString();

        @Override
        protected String doInBackground(String... strings) {

            requestUrl = "http://openapi.gbis.go.kr/ws/rest/busrouteservice/info?serviceKey=" + dataKey + "&routeId=" + RouteID;
            try {
                boolean b_downFirstTime = false;
                boolean b_downLastTime = false;
                boolean b_endStationName = false;
                boolean b_regionName = false;
                boolean b_routeName = false;
                boolean b_startStationName = false;
                boolean b_upFirstTime = false;
                boolean b_upLastTime = false;

                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is, "UTF-8"));


                int eventType = parser.getEventType();


                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<BussearchItem>();
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("busRouteInfoItem") && bus2 != null) {
                                list.add(bus2);
                            }
                            break;
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("busRouteInfoItem")) {
                                bus2 = new BussearchItem();
                            }
                            if (parser.getName().equals("downFirstTime"))
                                b_downFirstTime = true;  //버스도착예정시간
                            if (parser.getName().equals("downLastTime"))
                                b_downLastTime = true; //버스위치
                            if (parser.getName().equals("endStationName"))
                                b_endStationName = true;  //차량번호
                            if (parser.getName().equals("regionName"))
                                b_regionName = true;   //노선 ID
                            if (parser.getName().equals("routeName"))
                                b_routeName = true;  //버스도착예정시간
                            if (parser.getName().equals("startStationName"))
                                b_startStationName = true; //버스위치
                            if (parser.getName().equals("upFirstTime"))
                                b_upFirstTime = true;  //차량번호
                            if (parser.getName().equals("upLastTime"))
                                b_upLastTime = true;   //노선 ID

                            break;
                        case XmlPullParser.TEXT:
                            if (b_downFirstTime) {
                                bus2.setDownFirstTime(parser.getText());
                                b_downFirstTime = false;
                            } else if (b_downLastTime) {
                                bus2.setDownLastTime(parser.getText());
                                b_downLastTime = false;
                            } else if (b_endStationName) {
                                bus2.setEndStationName(parser.getText());
                                b_endStationName = false;
                            } else if (b_regionName) {
                                bus2.setRegionName(parser.getText());
                                b_regionName = false;
                            }else if (b_routeName) {
                                bus2.setRouteName(parser.getText());
                                b_routeName = false;
                            } else if (b_startStationName) {
                                bus2.setStartStationName(parser.getText());
                                b_startStationName = false;
                            } else if (b_upFirstTime) {
                                bus2.setUpFirstTime(parser.getText());
                                b_upFirstTime = false;
                            }else if (b_upLastTime) {
                                bus2.setUpLastTime(parser.getText());
                                b_upLastTime = false;
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
            SearchAdapter adapter = new SearchAdapter(getApplicationContext(), list);
            recyclerView.setAdapter(adapter);
        }
    }


}