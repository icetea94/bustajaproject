package com.example.bustaja;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MessageboardNew extends AppCompatActivity {

    TextView reque,tv_wordnum;
    EditText et_title,et_introduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_board_new);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("작성하기");

        tv_wordnum=findViewById(R.id.tv_wordnum);
        et_introduce= findViewById(R.id.et_introduce);
        et_title=findViewById(R.id.et_title);
        et_introduce=findViewById(R.id.et_introduce);

        et_introduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_wordnum.setText("글자수 : "+s.length()+"/300");
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    public void clickOkbtn(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //건축가에게 원하는 작업요청
        builder.setTitle("재확인");
        LayoutInflater inflater = getLayoutInflater();

        View v = inflater.inflate(R.layout.message_board_dialog,null);

        reque = v.findViewById(R.id.reque);
        builder.setView(v);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                String str1=et_title.getText().toString();
                String str2=et_introduce.getText().toString();

                //복귀한 인텐트에 추가
                Intent intent= getIntent();
                intent.putExtra("title",str1);
                intent.putExtra("contents",str2);

                //액티비티의 결과라고 설정
                setResult(RESULT_OK,intent);
                //액티비티종료
                finish();
            }
        }).create();



        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create();
        AlertDialog dialog = builder.create();
        //다이얼로그의 바깥쪽을 터치하였을 때 다이얼로그가 꺼지지 않도록..
        dialog.setCanceledOnTouchOutside(false);
        //뒤로가기 버튼을 클릭해도 꺼지지 않도록 하려면..
        dialog.setCancelable(false);
        //다이얼로그를 화면에 보이기!!
        dialog.show();

    }


    public void clickCancelbtn(View view) {


        AlertDialog dialog= new AlertDialog.Builder(this).setMessage("정말로 취소하시겠습니까?").setNegativeButton("아니오", null).setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
        finish();
            }
        }).create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
