package com.example.bustaja;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MessageboardMain extends AppCompatActivity {
    TextView board_nickname,board_title,board_contents,board_time;
    ListView listView;
    ArrayAdapter boardAdapter2;
    ArrayList<MessageBoardItem> boardDatas = new ArrayList<>();
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
        listView = findViewById(R.id.listview);

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String date= sdf.format(new Date());
        boardAdapter2 = new ArrayAdapter(MessageboardMain.this, R.layout.message_board_form, boardDatas);
        listView.setAdapter(boardAdapter2);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        setTitle("게시판");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String str1 = data.getStringExtra("title");
                    String str2 = data.getStringExtra("contents");


                    boardDatas.add(new MessageBoardItem(str1,str2));
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
                for (int i = 0; i < boardDatas.size(); i++) {
                    if (boardDatas.get(i).equals(query)) {
//.getBoard()
                        searchView.setQuery("", false);
                        searchView.setIconified(true);

                        Toast.makeText(MessageboardMain.this, "검색이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        listView.setSelection(i);

                        return false;
                    }
                }
                Toast.makeText(getApplicationContext(), "일치하는 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                return false;
            }


            @Override
            public boolean onQueryTextChange(String query) {

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
                }else {
                    Intent intent = new Intent(this, MessageboardNew.class);
                    //세컨드 액티비티로 가는 인텐트가 돌아오도록
                    startActivityForResult(intent, 1);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
