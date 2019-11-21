package com.example.bustaja;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MessageboardDetail extends AppCompatActivity {

    public FirebaseAuth firebaseAuth;
    Button btn_replaceOk ,btn_replace;
     TextView detail_title_tv;
     TextView detail_nickid_tv;
     TextView detail_contents_tv;
     TextView detail_date_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_board_detail);
        btn_replaceOk=findViewById(R.id.btn_replaceOk);
        btn_replace=findViewById(R.id.btn_replace);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String gettemail = user.getEmail();
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

        setTitle(boardtitle);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickBack(View view) {



        finish();
    }

    public void clickReplace(View view) {
        detail_title_tv.setEnabled(true);
        detail_contents_tv.setEnabled(true);

        detail_contents_tv.setFocusable(true);
        detail_title_tv.setFocusable(true);

        detail_contents_tv.requestFocus();

        btn_replace.setVisibility(View.GONE);
        btn_replaceOk.setVisibility(View.VISIBLE);

    }
    public void clickreplaceOk(View view) {
        detail_title_tv.setEnabled(false);
        detail_contents_tv.setEnabled(false);
        detail_contents_tv.setFocusable(false);
        detail_title_tv.setFocusable(false);

        btn_replace.setVisibility(View.VISIBLE);
        btn_replaceOk.setVisibility(View.GONE);
    }
}


