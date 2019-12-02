package com.devshyeon.bustaja;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NoticeMain extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView notice_recyclerview;
    NoticeAdapter noticeAdapter;
    ArrayList<NoticeItem> NoticeData=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_main);
        setTitle("공지사항");

        swipeRefreshLayout=findViewById(R.id.notice_refresh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        notice_recyclerview = findViewById(R.id.notice_recyclerview);
        noticeAdapter = new NoticeAdapter(NoticeData, this);
        notice_recyclerview.setAdapter(noticeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        notice_recyclerview.setLayoutManager(linearLayoutManager);

        //서버의 loadDBtoJson.php파일에
        //접속하여 (DB데이터들)결과 받기
        //Volley+ 라이브러리 사용
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                noticeAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        //서버주소
        String serverUrl="http://icetea94.dothome.co.kr/BusWeb/noticeData.php";

        //결과를 JsonArray받을 것이므로...
        //StringRequest가 아니라...
        //JsonArrayRequest를 이용할 것임
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                //파라미터로 응답받은 결과 JsonArray를 분석

                NoticeData.clear();
                noticeAdapter.notifyDataSetChanged();

                try {

                    for(int i=0; i<response.length(); i++){
                        JSONObject jsonObject= response.getJSONObject(i);

                        String title= jsonObject.getString("title");
                        String content= jsonObject.getString("content");
                        String id= jsonObject.getString("id");




                        NoticeData.add( 0 , new NoticeItem(""+title, ""+id,""+content) );
                        noticeAdapter.notifyItemInserted(0);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NoticeMain.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //실제 요청작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        //요청큐에 요청객체 추가
        requestQueue.add(jsonArrayRequest);


    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onResume() {
        super.onResume();
        noticeAdapter.notifyDataSetChanged();
    }
}
