package com.devshyeon.bustaja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BusListView extends AppCompatActivity {

    ListView listView;
    BusListViewAdapter adapter;
    //대량의 데이터 참조변수
    ArrayList<Stopmember> members= new ArrayList<Stopmember>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        listView=findViewById(R.id.listView);
        listView.setAdapter(adapter);
//대량의 데이터를 보여줄 뷰 를만들어내는 작업을 수행하는 어댑터객체 생성
        LayoutInflater inflater=getLayoutInflater();
        adapter=new BusListViewAdapter(members, inflater);

        listView.setAdapter(adapter);

    }
}
