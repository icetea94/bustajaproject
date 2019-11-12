package com.example.bustaja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {

    ArrayList<Boards> boardDatas;
    Context context;
    TextView nickname_textView;
    TextView title_textView;
    TextView date_textView;
    TextView content_textView;

    public BoardAdapter(ArrayList<Boards> boardDatas, Context context) {
        this.boardDatas = boardDatas;
        this.context = context;
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


        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_board_form, null);
            nickname_textView = (TextView) convertView.findViewById(R.id.board_nickname);
            content_textView = (TextView) convertView.findViewById(R.id.board_contents);
            date_textView = (TextView) convertView.findViewById(R.id.board_time);
            title_textView = (TextView) convertView.findViewById(R.id.board_title);
        }

        nickname_textView.setText(boardDatas.get(position).getNickname());
        title_textView.setText(boardDatas.get(position).getTitle());
        content_textView.setText(boardDatas.get(position).getContents());
        date_textView.setText(boardDatas.get(position).getDate());

        return convertView;
    }
}
