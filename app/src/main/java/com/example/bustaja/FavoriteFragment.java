package com.example.bustaja;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    RecyclerView favor_listview;
    TextView favor_empty_tv;
    ArrayList<FavorItem> favorItem = new ArrayList<>();
    FavorAdapter favorAdapter;
    FloatingActionButton favor_refresh_fab;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference boardRef2;
    ProgressDialog progressDialog;
    SharedPreferences cityFavor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseDatabase=FirebaseDatabase.getInstance();

        boardRef2=firebaseDatabase.getReference("Favors");
        progressDialog = new ProgressDialog(getContext());
        boardRef2.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // 새로 추가된 데이터(값: MessageItem객체)
                FavorItem favorItem2=dataSnapshot.getValue(FavorItem.class);
                //새로운 메세지를 리스트뷰에 추가하기 ArrayList에 추가하기



                favorItem.add(0,favorItem2);
//                //리스트뷰 갱신
                favorAdapter.notifyDataSetChanged();
                favor_listview.setAdapter(favorAdapter);
                // board_listview.setSelection(boardItem.size()-1);//화면 포커스 이동
                if (favorItem.isEmpty()) {
                    favor_listview.setVisibility(View.GONE);
                    favor_empty_tv.setVisibility(View.VISIBLE);
                }
                else {
                    favor_listview.setVisibility(View.VISIBLE);
                    favor_empty_tv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                favorAdapter.notifyDataSetChanged();
                favor_listview.setAdapter(favorAdapter);
                // board_listview.setSelection(boardItem.size()-1);//화면 포커스 이동
                if (favorItem.isEmpty()) {
                    favor_listview.setVisibility(View.GONE);
                    favor_empty_tv.setVisibility(View.VISIBLE);
                }
                else {
                    favor_listview.setVisibility(View.VISIBLE);
                    favor_empty_tv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                
                favorAdapter.notifyDataSetChanged();
                favor_listview.setAdapter(favorAdapter);
                // board_listview.setSelection(boardItem.size()-1);//화면 포커스 이동
                if (favorItem.isEmpty()) {
                    favor_listview.setVisibility(View.GONE);
                    favor_empty_tv.setVisibility(View.VISIBLE);
                }
                else {
                    favor_listview.setVisibility(View.VISIBLE);
                    favor_empty_tv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);


        favorAdapter = new FavorAdapter(favorItem,getContext());
        swipeRefreshLayout=view.findViewById(R.id.favor_refresh);
        favor_listview = view.findViewById(R.id.favor_listview);
        favor_empty_tv = view.findViewById(R.id.favor_empty_tv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        favor_listview.setLayoutManager(mLayoutManager);
        favor_listview.setAdapter(favorAdapter);
        favor_refresh_fab=view.findViewById(R.id.favor_refresh_fab);



//





        favor_listview.addOnItemTouchListener(new FavoriteFragment.RecyclerTouchListener(getContext(), favor_listview, new MessageboardMain.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FavorItem dict = favorItem.get(position);

                Intent intent = new Intent(getContext(), BusWebinfo.class);

                intent.putExtra("busnum", dict.getBusNum());
                intent.putExtra("busstop", dict.getBusstopFF());
                intent.putExtra("routeid", dict.getFavorRouteId());
                progressDialog.setMessage("경기 버스정보 웹 화면으로 이동중입니다. \n잠시 기다려 주세요...");
                progressDialog.show();

                startActivity(intent);

                progressDialog.dismiss();


            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                favorAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        favor_refresh_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
                Snackbar.make(view, "최신 정보를 불러오는 중입니다", Snackbar.LENGTH_SHORT).setAction("Refresh", null).show();
                favorAdapter.notifyDataSetChanged();
            }
        });
        if (favorItem.isEmpty()) {
            favor_listview.setVisibility(View.GONE);
            favor_empty_tv.setVisibility(View.VISIBLE);
        }
        else {
            favor_listview.setVisibility(View.VISIBLE);
            favor_empty_tv.setVisibility(View.GONE);
        }


        return view;



    }
    private void refresh() {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }





    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MessageboardMain.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MessageboardMain.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }


    }




}

