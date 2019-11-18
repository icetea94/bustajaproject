package com.example.bustaja;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MessageboardMain extends AppCompatActivity {
    TextView board_nickname,board_title,board_contents,board_time;

    RecyclerView board_listview;
    MessageboardAdapter boardAdapter2;
    ArrayList<MessageboardItem> boardItem = new ArrayList<>();
    ArrayList<MessageboardItem> searchboardItem = new ArrayList<>();

    SearchView searchView;
    MenuItem searchItem;
    CardView results;


    public FirebaseAuth firebaseAuth;
    InputMethodManager imm;



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
        boardAdapter2 = new MessageboardAdapter(boardItem,this);

        board_listview.setAdapter(boardAdapter2);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        searchboardItem.addAll(boardItem);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        setTitle("게시판");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String title = data.getStringExtra("title");
                    String contents = data.getStringExtra("contents");
                    String date= data.getStringExtra("date");
                    String emailid= data.getStringExtra("nick");

                    boardItem.add(0,new MessageboardItem(""+title,""+contents,""+date,""+emailid));

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


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                for (int i = 0; i < boardItem.size(); i++) {
                    if (boardItem.get(i).getBoardTitle().contains(query)||boardItem.get(i).getBoardContents().contains(query)) {

                        searchView.setQuery("", false);
                        searchView.setIconified(true);

                        Toast.makeText(MessageboardMain.this, "검색이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        board_listview.scrollToPosition(i);
                        boardAdapter2.notifyDataSetChanged();
                        return false;
                    }
                }
                Toast.makeText(getApplicationContext(), "일치하는 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                return false;
            }


            @Override
            public boolean onQueryTextChange(String query) {
                boardItem.addAll(searchboardItem);
                if (query.length() == 0) {

                }  else
                {
                    // 리스트의 모든 데이터를 검색한다.
                    for(int i = 0;i < searchboardItem.size(); i++)
                    {
                        // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                        if (searchboardItem.get(i).getBoardTitle().contains(query)||searchboardItem.get(i).getBoardContents().contains(query))
                        {
                            // 검색된 데이터를 리스트에 추가한다.
                            boardItem.add(searchboardItem.get(i));
                        }
                    }
                }
                // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
                boardAdapter2.notifyDataSetChanged();



                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        switch(id){
            case R.id.board_add:
                //SecondActivity 실행!
                if(firebaseAuth.getCurrentUser() == null) {
                    AlertDialog dialog= new AlertDialog.Builder(this).setMessage("로그인 하셔야 합니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create();

                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    int checkedPermission= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);//READ는 WRITE를 주면 같이 권한이 주어짐

                    if(checkedPermission== PackageManager.PERMISSION_DENIED){//퍼미션이 허가되어 있지 않다면
                        //사용자에게 퍼미션 허용 여부를 물어보는 다이얼로그 보여주기!
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);

                    }else {
                        Intent intent = new Intent(this, MessageboardNew.class);
                        //세컨드 액티비티로 가는 인텐트가 돌아오도록
                        startActivityForResult(intent, 1);

                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
