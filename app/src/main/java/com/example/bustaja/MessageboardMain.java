package com.example.bustaja;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;


public class MessageboardMain extends AppCompatActivity {
    TextView board_nickname, board_title, board_contents, board_time;

    RecyclerView board_listview;
    MessageboardAdapter boardAdapter2;
    ArrayList<MessageboardItem> boardItem = new ArrayList<>();
    ArrayList<MessageboardItem> searchboardItem = new ArrayList<>();

    SearchView searchView;
    MenuItem searchItem;
    CardView results;

    public FirebaseAuth firebaseAuth;
    InputMethodManager imm;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference boardRef;
    DatabaseReference rootRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_board_main);
        results = findViewById(R.id.results);
        board_nickname = findViewById(R.id.board_nickname);
        board_title = findViewById(R.id.board_title);
        board_contents = findViewById(R.id.board_contents);
        board_time = findViewById(R.id.board_time);
        board_listview = findViewById(R.id.board_listview);
        boardAdapter2 = new MessageboardAdapter(boardItem, this);
        board_listview.setAdapter(boardAdapter2);
        boardAdapter2.notifyDataSetChanged();
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        boardRef=firebaseDatabase.getReference("Boards");
        boardRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // 새로 추가된 데이터(값: MessageItem객체)
                MessageboardItem messageItem=dataSnapshot.getValue(MessageboardItem.class);
                //새로운 메세지를 리스트뷰에 추가하기 ArrayList에 추가하기
                boardItem.add(0,messageItem);
                //리스트뷰 갱신
                boardAdapter2.notifyDataSetChanged();
                // board_listview.setSelection(boardItem.size()-1);//화면 포커스 이동
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

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
                    Log.i("moya",title);
//                    boardItem.add(0, new MessageboardItem("" + title, "" + contents, "" + date, "" + emailid));
//                    boardAdapter2 = new MessageboardAdapter(boardItem, this);
//
//                    board_listview.setAdapter(boardAdapter2);
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    rootRef2=firebaseDatabase.getReference();//괄호 안이 비어있으면 최상위 노드를 뜻함
                    MessageboardItem boardItem = new MessageboardItem(title,contents,date,emailid);
                    boardRef= rootRef2.child("Boards");
                    boardRef.push().setValue(boardItem);
                    boardAdapter2.notifyDataSetChanged();

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


                for (int i = 0; i < searchboardItem.size(); i++) {
                    if (searchboardItem.get(i).getBoardTitle().contains(query) || searchboardItem.get(i).getBoardContents().contains(query)) {

                        searchView.setQuery("", false);
                        searchView.setIconified(true);

                        Toast.makeText(MessageboardMain.this, "검색이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        board_listview.scrollToPosition(i);
                        boardItem.addAll(searchboardItem);
                        boardAdapter2.notifyDataSetChanged();
                        return false;
                    }

                }

                Toast.makeText(getApplicationContext(), "일치하는 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                boardAdapter2.notifyDataSetChanged();
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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