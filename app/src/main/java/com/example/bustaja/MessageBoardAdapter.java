package com.example.bustaja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageBoardAdapter extends BaseAdapter {

    ArrayList<MessageBoardItem> boardItem;
    Context context;
    TextView title_textView;
    TextView content_textView;

    public MessageBoardAdapter(ArrayList<MessageBoardItem> boardItem, Context context, TextView title_textView, TextView content_textView) {
        this.boardItem = boardItem;
        this.context = context;
        this.title_textView = title_textView;
        this.content_textView = content_textView;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}