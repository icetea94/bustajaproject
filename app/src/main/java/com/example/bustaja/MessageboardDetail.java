package com.example.bustaja;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

     TextView detail_title_tv;
     TextView detail_nickid_tv;
     TextView detail_contents_tv;
     TextView detail_date_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_board_detail);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String gettemail = user.getEmail();
        detail_title_tv = (TextView) findViewById(R.id.detail_title_tv);
        detail_nickid_tv = (TextView) findViewById(R.id.detail_nickid_tv);
        detail_contents_tv = (TextView) findViewById(R.id.detail_contents_tv);
        detail_date_tv=(TextView) findViewById(R.id.detail_date_tv);

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


    }

    public void clickRemove(View view) {


    }
}


