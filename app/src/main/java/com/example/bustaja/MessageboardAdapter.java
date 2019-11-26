package com.example.bustaja;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageboardAdapter extends RecyclerView.Adapter implements Serializable {

    ArrayList<MessageboardItem> boardItem;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView board_nickname, board_title, board_contents, board_time;


        public MyViewHolder(View view) {

            super(view);

            board_nickname = view.findViewById(R.id.board_nickname);
            board_title = view.findViewById(R.id.board_title);
            board_contents = view.findViewById(R.id.board_contents);
            board_time = view.findViewById(R.id.board_time);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //클릭된 아이템뷰가 리사이클러 뷰에서 몇번째 index(position)인지 알아내기
                    int position = getAdapterPosition();

//                    Intent intent = new Intent(context, MessageboardDetail.class);
//
//
//
//                    context.startActivity(intent);


                }
            });

        }
    }

    public MessageboardAdapter(ArrayList<MessageboardItem> boardItem, Context context) {
        this.boardItem = boardItem;
        this.context = context;

    }

    @NonNull
    @Override
    public MessageboardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_board_form, parent, false);
        MessageboardAdapter.MyViewHolder holder = new MessageboardAdapter.MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageboardAdapter.MyViewHolder holder5 = (MessageboardAdapter.MyViewHolder) holder;

        holder5.board_title.setText(boardItem.get(position).getBoardTitle());
        holder5.board_nickname.setText(boardItem.get(position).getBoardNick());
        holder5.board_time.setText(boardItem.get(position).getBoardDate());
        holder5.board_contents.setText(boardItem.get(position).getBoardContents());

    }

    @Override
    public int getItemCount() {
        return boardItem.size();
    }
}
