package com.devshyeon.bustaja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {

    ArrayList<CityItem> cityItem = new ArrayList<>();
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    TabLayout tabLayout;
    ViewPager pager;
    BusMainAdapter adapter;
    MenuItem searchItem;
    InputMethodManager im;
    BackPressCloseHandler backPressCloseHandler;
    public FirebaseAuth firebaseAuth;
    RecyclerView favor_listview;
    RecyclerView city_listview;
    TextView header_id;
    FirebaseAuth mAuth;
    MenuItem menu_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        backPressCloseHandler = new BackPressCloseHandler(this);
        city_listview = findViewById(R.id.city_listview);
        favor_listview = findViewById(R.id.favor_listview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);
        drawerLayout = findViewById(R.id.layout_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle); //레이아웃에 토글 붙이기
        drawerToggle.syncState(); //토글에 삼선모양 나타나게하기
//        drawerToggle.setDrawerIndicatorEnabled(); 토글지우기

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser googleuser = mAuth.getCurrentUser();

        menu_login = navigationView.getMenu().findItem(R.id.menu_login);


        if (user != null || googleuser != null) {
            View nav_header_view = navigationView.getHeaderView(0);
            header_id = nav_header_view.findViewById(R.id.header_id);
            String headerid = user.getEmail();
            header_id.setText(headerid);
            menu_login.setTitle("프로필");
        }else if(user==null||googleuser==null){
            menu_login.setTitle("로그인");
        }




        tabLayout = findViewById(R.id.layout_tab);
        pager = findViewById(R.id.pager);
        adapter = new BusMainAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        tabLayout.setupWithViewPager(pager);
        getSupportActionBar().setSubtitle("안양시 버스 정보");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getSupportActionBar().setSubtitle(tab.getText()); //서브타이틀을 갖고와서 액션바에 붙이기
                int numTab = tab.getPosition();
                if(numTab==1){
                    searchItem.setVisible(false);
                }else if(numTab==0){
                    searchItem.setVisible(true);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //네비게이션 뷰에 아이템 선택 리스너 추가
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {

                    case R.id.menu_login:
                        Intent intent = new Intent(MainActivity.this, LoginMain.class);
                        startActivity(intent);

                        break;
                    case R.id.menu_favor:

                        int index = pager.getCurrentItem();

                        if (index == 0) {
                            //특정페이지로 이동
                            pager.setCurrentItem(index +1, true);
                        }
                        break;
                    case R.id.menu_location:

                        intent = new Intent(MainActivity.this, MyLocationMain.class);
                        startActivity(intent);

                        break;

                    case R.id.menu_notice:
                        intent = new Intent(MainActivity.this, NoticeMain.class);
                        startActivity(intent);

                        break;
                    case R.id.menu_messageboard:

                        intent = new Intent(MainActivity.this, MessageboardMain.class);
                        startActivity(intent);

                        break;
                    case R.id.menu_access:

                        intent = new Intent(MainActivity.this, AccessMain.class);
                        startActivity(intent);

                        break;

                    case R.id.menu_information:

                        intent = new Intent(MainActivity.this, InformationMain.class);
                        startActivity(intent);

                        break;


                }


                //drawer nav 닫기
                drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
                drawerLayout.closeDrawer(navigationView);

                return false;
            }
        });

    }//oncreate method


    //뒤로가기 두번 메소드
    @Override
    public void onBackPressed() {

        backPressCloseHandler.onBackPressed();
    }
    //옵션메뉴 만들어주는 메소드

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        searchItem = menu.findItem(R.id.option_search);
        menu_login = navigationView.getMenu().findItem(R.id.menu_login);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser googleuser = mAuth.getCurrentUser();

        if (user != null || googleuser != null) {
            menu_login.setTitle("프로필");
        } else if(user==null||googleuser==null){
            menu_login.setTitle("로그인");
        }




        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        drawerToggle.onOptionsItemSelected(item);
            int id = item.getItemId();

            switch (id) {
                case R.id.option_search:

                            Intent intent = new Intent(this, Bussearch.class);
                            //세컨드 액티비티로 가는 인텐트가 돌아오도록
                            startActivity(intent);

                        break;
           }
        // 스위치 케이스 로 토스트 달아놓기
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser googleuser = mAuth.getCurrentUser();

        menu_login = navigationView.getMenu().findItem(R.id.menu_login);


        if (user != null || googleuser != null) {
            View nav_header_view = navigationView.getHeaderView(0);
            header_id = nav_header_view.findViewById(R.id.header_id);
            String headerid = user.getEmail();
            header_id.setText(headerid);
            menu_login.setTitle("프로필");
        }else if(user==null||googleuser==null){
            menu_login.setTitle("로그인");
            View nav_header_view = navigationView.getHeaderView(0);
            header_id = nav_header_view.findViewById(R.id.header_id);
            String noheaderid ="로그인해주세요";
            header_id.setText(noheaderid);
        }

        super.onResume();
    }


}//main activity