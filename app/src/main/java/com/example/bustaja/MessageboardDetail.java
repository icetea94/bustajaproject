package com.example.bustaja;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MessageboardDetail extends AppCompatActivity {


     TextView detail_title_tv;
     TextView detail_nickid_tv;
     TextView detail_contents_tv;
     TextView detail_date_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_board_detail);

        detail_title_tv = (TextView) findViewById(R.id.detail_title_tv);
        detail_nickid_tv = (TextView) findViewById(R.id.detail_nickid_tv);
        detail_contents_tv = (TextView) findViewById(R.id.detail_contents_tv);
        detail_date_tv=(TextView) findViewById(R.id.detail_date_tv);

        Intent intent=getIntent();

        detail_title_tv.setText( intent.getStringExtra("title"));
        detail_contents_tv.setText( intent.getStringExtra("contents"));
        detail_nickid_tv.setText( intent.getStringExtra("nick"));
        detail_date_tv.setText( intent.getStringExtra("date"));
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

                    detail_title_tv.setText(title);
                    detail_contents_tv.setText(contents);
                    detail_date_tv.setText(date);
                    detail_nickid_tv.setText(emailid);

                    break;
                }
        }
    }

    public void clickBack(View view) {
        Intent intent2=new Intent(MessageboardDetail.this,MessageboardMain.class);
        startActivity(intent2);
    }

    public void clickReplace(View view) {


    }
}


