package com.devshyeon.bustaja;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter {

    ArrayList<NoticeItem> NoticeData;
    Context context;
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;


    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public TextView notice_title, notice_subtitle,notice_contents;
        private int position;
        private NoticeItem data;

        public MyViewHolder(View view) {

            super(view);

            notice_title = view.findViewById(R.id.notice_title);
            notice_subtitle =view.findViewById(R.id.notice_subtitle);
            notice_contents = view.findViewById(R.id.notice_contents);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //클릭된 아이템뷰가 리사이클러 뷰에서 몇번째 index(position)인지 알아내기
                    int position  = getAdapterPosition();
                    Toast.makeText(context,(position+1)+"번 째 공지사항입니다.",Toast.LENGTH_SHORT).show();
                }
            });

        }

        void onBind(NoticeItem data, int position) {
            this.data = data;
            this.position = position;

            notice_title.setText(data.getNoticeTitle());

            changeVisibility(selectedItems.get(position));

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.noticeItem:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
            }
        }
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경
                    notice_contents.getLayoutParams().height = value;
                    notice_contents.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    notice_contents.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
    }

    public NoticeAdapter(ArrayList<NoticeItem> NoticeData, Context context){
        this.NoticeData = NoticeData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice_item, parent, false);
        MyViewHolder holder=new MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder holder4= (MyViewHolder)holder;
        holder4.notice_title.setText(NoticeData.get(position).getNoticeTitle());
        holder4.notice_subtitle.setText(NoticeData.get(position).getNoticeSubTitle());
        holder4.notice_contents.setText(NoticeData.get(position).getNoticeContents());
        holder4.onBind(NoticeData.get(position), position);
    }


    @Override
    public int getItemCount() {
        return NoticeData.size();
    }
}
