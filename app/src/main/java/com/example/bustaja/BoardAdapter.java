package com.example.bustaja;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {

    ArrayList<Boards> boardDatas;
    LayoutInflater inflater;

    public BoardAdapter(ArrayList<Boards> boardDatas, LayoutInflater inflater) {
        this.boardDatas = boardDatas;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return boardDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return boardDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView= inflater.inflate(R.layout.message_board_form, null);
        }



        return convertView;
    }
}
