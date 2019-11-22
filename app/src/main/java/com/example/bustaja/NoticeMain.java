package com.example.bustaja;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeMain extends AppCompatActivity {

    RecyclerView notice_recyclerview;
    NoticeAdapter noticeAdapter;
    ArrayList<NoticeItem> NoticeData=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_main);
        setTitle("공지사항");
        NoticeData.add(new NoticeItem("01.첫번째 공지사항입니다","공지사항"," 안녕하세요. 안양 마을버스 정보 앱 '버스타자' 입니다.\n\n 빠르고 정확한 정보를 제공합니다. \n\n 많은 이용 부탁드립니다. \n\n 자세한 사항은 구글 플레이스토어에서 확인하세요."));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        notice_recyclerview = findViewById(R.id.notice_recyclerview);
        noticeAdapter = new NoticeAdapter(NoticeData,this);
        notice_recyclerview.setAdapter(noticeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        notice_recyclerview.setLayoutManager(linearLayoutManager);



    }

}
