package com.devshyeon.bustaja;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BusListViewAdapter extends BaseAdapter {
    //멤버변수
    ArrayList<Stopmember> members;
    LayoutInflater inflater;
    //생성자메소드
    public BusListViewAdapter(ArrayList<Stopmember> members, LayoutInflater inflater) {
        this.members = members;
        this.inflater=inflater;
    }

    //어댑터 객체가 만들어낼 뷰 객체의 개수를 리턴
    @Override
    public int getCount() {
        return members.size();
    }

    //
    @Override
    public Object getItem(int position) {
        return members.get(position);
    }

    //
    @Override
    public long getItemId(int position) {
        return position;
    }

    //리스트 뷰에 보여질 항목 하나의 뷰를 만들어내는 메소드//
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        //xml로 뷰모양을 설계하고 이를 객체로만들어서 리턴함
        //listview_item.xml문서를 읽어와서
        //뷰 객체로 만들어 주는 (inflate 부풀리는 기능) 객체를 이용하여 뷰 객체 생성
        //XML->View객체로 부풀려주는 LayoutInflater라는 직원이 필요함
        //1.new View
        //재활용 할 뷰 가 없는가?
        if(view==null) {
            view  = inflater.inflate(R.layout.listview_item,null);
        }

        //2.bind view: 만들어진 뷰와 데이터를 연결
        //현재 번(position)째의 뷰
        //데이터를 얻어오기
        Stopmember member=members.get(position);

        ImageView iv=view.findViewById(R.id.item_iv);
        TextView tvName=view.findViewById(R.id.item_tv_name);


        iv.setImageResource(member.imgID);
        tvName.setText(member.name);



        //만들어진 뷰를 리턴하면 그 뷰가 화면에 보여짐
        return view;

    }
}
