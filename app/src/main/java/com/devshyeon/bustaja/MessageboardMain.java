package com.devshyeon.bustaja;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.view.GestureDetector;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.util.ArrayList;


public class MessageboardMain extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    TextView board_nickname, board_title, board_contents, board_time,board_empty_tv;


    RecyclerView board_listview;
    MessageboardAdapter boardAdapter2;
    ArrayList<MessageboardItem> boardItem = new ArrayList<>();

    static ArrayList<String> boardKeys= new ArrayList<>();
    CardView results;
    SearchView searchView;
    MenuItem searchItem;
    public FirebaseAuth firebaseAuth;
    InputMethodManager imm;


    DatabaseReference boardRef;

    @Override
    protected void onStart() {
        boardAdapter2.notifyDataSetChanged();
        board_listview.setAdapter(boardAdapter2);

        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_board_main);
        results = findViewById(R.id.results);
        board_nickname = findViewById(R.id.board_nickname);
        board_title = findViewById(R.id.board_title);
        board_contents = findViewById(R.id.board_contents);
        board_empty_tv=findViewById(R.id.board_empty_tv);
        board_time = findViewById(R.id.board_time);
        board_listview = findViewById(R.id.board_listview);
        boardAdapter2 = new MessageboardAdapter(boardItem, this);
        boardAdapter2.notifyDataSetChanged();
        board_listview.setAdapter(boardAdapter2);



        if (boardItem.isEmpty()) {
            board_listview.setVisibility(View.GONE);
            board_empty_tv.setVisibility(View.VISIBLE);
        }
        else {
            board_listview.setVisibility(View.VISIBLE);
            board_empty_tv.setVisibility(View.GONE);
        }

        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout=findViewById(R.id.board_refresh);








        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
                boardAdapter2.notifyDataSetChanged();
                board_listview.setAdapter(boardAdapter2);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        boardRef=firebaseDatabase.getReference("Boards");
        boardRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // 새로 추가된 데이터(값: MessageItem객체)

                MessageboardItem messageItem=dataSnapshot.getValue(MessageboardItem.class);
                //새로운 메세지를 리스트뷰에 추가하기 ArrayList에 추가하기
                boardItem.add(0,messageItem);
                boardKeys.add(0,dataSnapshot.getKey());
//                //리스트뷰 갱신
                boardAdapter2.notifyDataSetChanged();
                // board_listview.setSelection(boardItem.size()-1);//화면 포커스 이동
                board_listview.setAdapter(boardAdapter2);
                if (boardItem.isEmpty()) {
                    board_listview.setVisibility(View.GONE);
                    board_empty_tv.setVisibility(View.VISIBLE);
                }
                else {
                    board_listview.setVisibility(View.VISIBLE);
                    board_empty_tv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                boardAdapter2.notifyDataSetChanged();
                // board_listview.setSelection(boardItem.size()-1);//화면 포커스 이동
                board_listview.setAdapter(boardAdapter2);


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                boardAdapter2.notifyDataSetChanged();
                // board_listview.setSelection(boardItem.size()-1);//화면 포커스 이동
                board_listview.setAdapter(boardAdapter2);
                if (boardItem.isEmpty()) {
                    board_listview.setVisibility(View.GONE);
                    board_empty_tv.setVisibility(View.VISIBLE);
                }
                else {
                    board_listview.setVisibility(View.VISIBLE);
                    board_empty_tv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        setTitle("게시판");


        ///////////////////


        board_listview.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), board_listview, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MessageboardItem dict = boardItem.get(position);

                Intent intent = new Intent(getBaseContext(), MessageboardDetail.class);

                intent.putExtra("title", dict.getBoardTitle());
                intent.putExtra("date", dict.getBoardDate());
                intent.putExtra("contents", dict.getBoardContents());
                intent.putExtra("nick", dict.getBoardNick());

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));
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
///////////


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {

                    String title = data.getStringExtra("title");
                    String contents = data.getStringExtra("contents");
                    String date = data.getStringExtra("date");
                    String emailid = data.getStringExtra("nick");

                    break;
                }


        }
    }
    //옵션메뉴 만들어주는 메소드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.board_option, menu);
        searchItem = menu.findItem(R.id.board_option_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("검색할 내용 입력");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {


                for (int i = 0; i < boardItem.size(); i++) {
                    if (boardItem.get(i).getBoardTitle().contains(query) || boardItem.get(i).getBoardContents().contains(query)) {

                        searchView.setQuery("", false);
                        searchView.setIconified(true);

                        Toast.makeText(MessageboardMain.this, "검색 내용과 일치하는 게시글이 존재합니다.", Toast.LENGTH_SHORT).show();
                        board_listview.scrollToPosition(i);

                        boardAdapter2.notifyDataSetChanged();
                        return true;
                    }

                }

                Toast.makeText(getApplicationContext(), "검색 내용과 일치하는 게시글이 없습니다.", Toast.LENGTH_SHORT).show();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                boardAdapter2.notifyDataSetChanged();
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {




                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {

        boardAdapter2.notifyDataSetChanged();
        board_listview.setAdapter(boardAdapter2);


        super.onResume();

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.board_add:
                //SecondActivity 실행!
                if (firebaseAuth.getCurrentUser() == null) {
                    AlertDialog dialog = new AlertDialog.Builder(this).setMessage("로그인 하셔야 합니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create();

                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int checkedPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);//READ는 WRITE를 주면 같이 권한이 주어짐

                    if (checkedPermission == PackageManager.PERMISSION_DENIED) {//퍼미션이 허가되어 있지 않다면
                        //사용자에게 퍼미션 허용 여부를 물어보는 다이얼로그 보여주기!
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);

                    } else {
                        Intent intent = new Intent(this, MessageboardNew.class);
                        //세컨드 액티비티로 가는 인텐트가 돌아오도록
                        startActivityForResult(intent, 1);

//hometest//
                    }
                }
                break;

            case android.R.id.home: {
                onBackPressed();

            }

        }
        return super.onOptionsItemSelected(item);
    }


}