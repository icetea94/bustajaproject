package com.example.bustaja;


import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InformationMain extends AppCompatActivity {


    TextView information_title, information_subtitle, information_first, information_first_link, information_second, information_second_link, information_third, information_third_link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_main);
        setTitle("정보 제공처");
        information_title = findViewById(R.id.information_title);
        information_subtitle = findViewById(R.id.information_subtitle);
        information_first = findViewById(R.id.information_first);
        information_first_link = findViewById(R.id.information_first_link);
        information_second = findViewById(R.id.information_second);
        information_second_link = findViewById(R.id.information_second_link);
        information_third = findViewById(R.id.information_third);
        information_third_link = findViewById(R.id.information_third_link);
    }

}