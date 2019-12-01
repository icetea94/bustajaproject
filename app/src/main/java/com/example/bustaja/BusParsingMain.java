package com.example.bustaja;

import android.os.AsyncTask;
import android.os.Bundle;
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


public class BusParsingMain extends AppCompatActivity {

    public String dataKey = "HPhv7q1fispfruW2r4aROp%2FvBjNa6M0jg%2F1FVE4BliwYYTY4oWQ54%2Fs10n8Ny2eFJcTKZ21NEbFaC%2BSs%2BGWNSQ%3D%3D";
    private String requestUrl;
    ArrayList<ParsingItem> list = null;
    ParsingItem bus = null;
    RecyclerView recyclerView;
    EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busstopparsing_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        edittext = findViewById(R.id.edittext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("정류소별 실시간 도착정보 목록 조회");

    }

    public void clickse(View view) {
        String stationNum = edittext.getText().toString();

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

        if(stationNum.equals("")) {
            Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void newOnclick2(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {

        String stationNum = edittext.getText().toString();

        @Override
        protected String doInBackground(String... strings) {

            requestUrl = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice/station?serviceKey=" + dataKey + "&stationId=" + stationNum;

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
        }
    }
}