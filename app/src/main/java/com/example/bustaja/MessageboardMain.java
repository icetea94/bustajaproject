package com.example.bustaja;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import static java.lang.Character.getName;

public class MessageboardMain extends AppCompatActivity {
    TextView tv2;
    ListView listView;
    ArrayAdapter boardAdapter2;
    Button addlistBtn;
    ArrayList<Boards> boardDatas = new ArrayList<>();
    SearchView searchView;
    MenuItem searchItem;

    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_board_main);
        tv2 = findViewById(R.id.results);
        listView = findViewById(R.id.listview);
        boardAdapter2 = new ArrayAdapter(MessageboardMain.this, R.layout.message_board_form, boardDatas);
        listView.setAdapter(boardAdapter2);
        addlistBtn=findViewById(R.id.addlistBtn);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setTitle("게시판");

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String str1 = data.getStringExtra("data1");
                    String str2 = data.getStringExtra("data2");

                    String str = str1 + "\n\n" + str2 + "\n";

                    boardAdapter2.notifyDataSetChanged();
                    break;
                }
        }
    }
    public void clickBtn (View view){
        //SecondActivity 실행!
        Intent intent = new Intent(this, MessageboardNew.class);
        //세컨드 액티비티로 가는 인텐트가 돌아오도록
        startActivityForResult(intent, 1);
    }

}
