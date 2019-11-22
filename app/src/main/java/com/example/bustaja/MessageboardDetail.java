package com.example.bustaja;

import android.content.Context;
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
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MessageboardDetail extends AppCompatActivity {

     public FirebaseAuth firebaseAuth;
     Button btn_replaceOk ,btn_replace;
     EditText detail_title_tv;
     TextView detail_nickid_tv;
     EditText detail_contents_tv;
     TextView detail_date_tv;
     KeyListener originalKeyListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_board_detail);
        btn_replaceOk=findViewById(R.id.btn_replaceOk);
        btn_replace=findViewById(R.id.btn_replace);
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String gettemail = user.getEmail();
        detail_title_tv = (EditText) findViewById(R.id.detail_title_tv);
        detail_nickid_tv = (TextView) findViewById(R.id.detail_nickid_tv);
        detail_contents_tv = (EditText) findViewById(R.id.detail_contents_tv);
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
        btn_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restore key listener - this will make the field editable again.
                detail_title_tv.setKeyListener(originalKeyListener);
                // Focus the field.
                detail_title_tv.requestFocus();
                // Show soft keyboard for the user to enter the value.
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(detail_title_tv, InputMethodManager.SHOW_IMPLICIT);

                btn_replace.setVisibility(View.GONE);
                btn_replaceOk.setVisibility(View.VISIBLE);
            }
        });

        detail_title_tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // If it loses focus...
                if (!hasFocus) {
                    // Hide soft keyboard.
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(detail_title_tv.getWindowToken(), 0);
                    // Make it non-editable again.
                    detail_title_tv.setKeyListener(null);
                }
            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickBack(View view) {



        finish();
    }

    public void clickReplace(View view) {

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


