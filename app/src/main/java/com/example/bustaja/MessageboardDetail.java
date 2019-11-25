package com.example.bustaja;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MessageboardDetail extends AppCompatActivity {



     public FirebaseAuth firebaseAuth;
     Button btn_replaceOk ,btn_replace,btn_back;
     TextView detail_title_tv;
     TextView detail_nickid_tv;
     TextView detail_contents_tv;
     TextView detail_date_tv;

     MessageReplaceFragment messageReplaceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_board_detail);
        btn_replaceOk=findViewById(R.id.btn_replaceOk);
        btn_replace=findViewById(R.id.btn_replace);
        btn_back=findViewById(R.id.btn_back);
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        detail_title_tv = (TextView) findViewById(R.id.detail_title_tv);
        detail_nickid_tv = (TextView) findViewById(R.id.detail_nickid_tv);
        detail_contents_tv = (TextView) findViewById(R.id.detail_contents_tv);
        detail_date_tv=(TextView) findViewById(R.id.detail_date_tv);
        detail_title_tv.setFocusable(false);
        detail_contents_tv.setFocusable(false);
        Bundle extras = getIntent().getExtras();

        String boardtitle= extras.getString("title");
        detail_title_tv.setText( boardtitle);
        String boardcontents = extras.getString("contents");
        detail_contents_tv.setText(boardcontents);
        String boardnick = extras.getString("nick");
        detail_nickid_tv.setText(boardnick);
        String boarddate= extras.getString("date");
        detail_date_tv.setText(boarddate);

        messageReplaceFragment=new MessageReplaceFragment();


        setTitle(boardtitle);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickBack(View view) {

        String title = detail_title_tv.getText().toString();
        String contents = detail_contents_tv.getText().toString();
        String emailid = detail_nickid_tv.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd ");
        String replacedate = sdf.format(new Date());
        detail_date_tv.setText(replacedate);
        String dates = detail_date_tv.getText().toString();

        //복귀한 인텐트에 추가
        Intent intent = getIntent();
        intent.putExtra("title", title);
        intent.putExtra("contents", contents);
        intent.putExtra("date", dates);
        intent.putExtra("nick", emailid);

        //액티비티의 결과라고 설정
        setResult(RESULT_OK, intent);

//        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//        DatabaseReference rootRef2=firebaseDatabase.getReference();//괄호 안이 비어있으면 최상위 노드를 뜻함
//        DatabaseReference boardRef= rootRef2.child("Boards");
//        MessageboardItem boardItem = new MessageboardItem(title,contents,dates,emailid);
//        boardRef.setValue(boardItem);

        finish();


    }

    public void clickReplace(View view) {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String gettemail = user.getEmail();

        if(!gettemail.equals(detail_nickid_tv.getText().toString())){
            AlertDialog dialog = new AlertDialog.Builder(this).setMessage("본인만 수정할 수 있습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
                }
            }).create();

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, messageReplaceFragment).commit();
            btn_replace.setVisibility(view.GONE);
            btn_back.setVisibility(view.GONE);
        }
    }


    @Override
    protected void onResume() {


        firebaseAuth = FirebaseAuth.getInstance();


        super.onResume();
    }
}


