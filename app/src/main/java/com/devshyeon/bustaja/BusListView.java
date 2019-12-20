package com.devshyeon.bustaja;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class BusListView extends AppCompatActivity {
    FloatingActionButton stop_fab;
    RecyclerView listView;
    BusListViewAdapter adapter;
    //대량의 데이터 참조변수
    ArrayList<Stopmember> members= null;
    Stopmember stopmember =null;
    public String dataKey = "HPhv7q1fispfruW2r4aROp%2FvBjNa6M0jg%2F1FVE4BliwYYTY4oWQ54%2Fs10n8Ny2eFJcTKZ21NEbFaC%2BSs%2BGWNSQ%3D%3D";
    private String requestUrl;
    String urls;
    TextView item_tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buslistview_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setWindowAnimations(android.R.style.Animation_Toast);
        listView=findViewById(R.id.listView);
        stop_fab=findViewById(R.id.stop_refresh_fab);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        item_tv_name=findViewById(R.id.item_tv_name);
        listView.setAdapter(adapter);

        stop_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "최신 정보를 불러오는 중입니다", Snackbar.LENGTH_SHORT).setAction("Refresh", null).show();

            }
        });

        Intent intent = getIntent();

        urls = intent.getExtras().getString("routeid");
        String num = intent.getExtras().getString("busnum");
        setTitle(num+"번 버스 실시간 정보");

        listView.addOnItemTouchListener(new CityFragment.RecyclerTouchListener(getApplicationContext(), listView, new MessageboardMain.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Stopmember dict = members.get(position);

                Intent intent = new Intent(getApplicationContext(), BusstopLocation.class);
                intent.putExtra("stationname", dict.getStationName());
                intent.putExtra("stationseq", dict.getStationSeq());
                intent.putExtra("stationId", dict.getStationId());
                intent.putExtra("stationx", dict.getStationx());
                intent.putExtra("stationy", dict.getStationy());

                startActivity(intent);



            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));

    }

    @Override
    protected void onStart() {
        super.onStart();
        MyAsyncTask3 myAsyncTask3 = new MyAsyncTask3();
        myAsyncTask3.execute();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    public class MyAsyncTask3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            requestUrl = "http://openapi.gbis.go.kr/ws/rest/busrouteservice/station?serviceKey=" + dataKey + "&routeId=" + urls;
            try {
                boolean b_stationId = false;
                boolean b_stationName = false;
                boolean b_x = false;
                boolean b_y = false;
                boolean b_stationSeq = false;
                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is, "UTF-8"));


                int eventType = parser.getEventType();


                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            members = new ArrayList<Stopmember>();
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("busRouteStationList") && stopmember != null) {
                                members.add(stopmember);
                            }
                            break;
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("busRouteStationList")) {
                                stopmember = new Stopmember();
                            }
                            if (parser.getName().equals("stationId"))
                                b_stationId = true;  //
                            if (parser.getName().equals("stationName"))
                                b_stationName = true;  //
                            if (parser.getName().equals("x"))
                                b_x = true;  //
                            if (parser.getName().equals("y"))
                                b_y = true;  //
                            if (parser.getName().equals("stationSeq"))
                                b_stationSeq = true;  //
                            break;
                        case XmlPullParser.TEXT:
                            if (b_stationId) {
                                stopmember.setStationId(parser.getText());
                                b_stationId = false;
                            }
                            else if (b_stationName) {
                                stopmember.setStationName("["+parser.getText()+"]");
                                b_stationName = false;
                            }
                            else if (b_x) {
                                stopmember.setStationx(parser.getText());
                                b_x = false;
                            }
                            else if (b_y) {
                                stopmember.setStationy(parser.getText());
                                b_y = false;
                            }
                            else if (b_stationSeq) {
                                stopmember.setStationSeq("No."+parser.getText());
                                b_stationSeq = false;
                            }

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
            BusListViewAdapter adapter = new BusListViewAdapter(getApplicationContext(), members);
            listView.setAdapter(adapter);
        }

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
