package com.example.bustaja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<BusstopItem> numDatas=new ArrayList<>();
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    TabLayout tabLayout;
    ViewPager pager;
    BusMainAdapter adapter;
    SearchView searchView;
    MenuItem searchItem;
    InputMethodManager im;

    private BackPressCloseHandler backPressCloseHandler;

    ListView favor_listview;
    ListView city_listview;
    ListView busstop_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        city_listview=findViewById(R.id.city_listview);
        busstop_listview=findViewById(R.id.busstop_listview);
        favor_listview=findViewById(R.id.favor_listview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);

        drawerLayout = findViewById(R.id.layout_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle); //레이아웃에 토글 붙이기
        drawerToggle.syncState(); //토글에 삼선모양 나타나게하기

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
                        Intent intent=new Intent(MainActivity.this,LoginMain.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_favor:
                        break;
                    case R.id.menu_notice:
                        break;
                    case R.id.menu_messageboard:

                        intent=new Intent(MainActivity.this,MessageboardMain.class);
                        startActivity(intent);

                        break;
                    case R.id.menu_access:
                        break;
                    case R.id.menu_version:
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
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("버스 번호, 정류소번호, 정류소명 입력");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){



            @Override
            public boolean onQueryTextSubmit(String query) {

                for (int i = 0; i < numDatas.size(); i++) {
                    if (numDatas.get(i).equals(query)) {
//.getBusnum()
                        searchView.setQuery("", false);
                        searchView.setIconified(true);

                        Toast.makeText(MainActivity.this, "검색이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                        busstop_listview.setSelection(i);

                        return false;
                    }
                }



                Toast.makeText(getApplicationContext(), "일치하는 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
//                loadHistory(query);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        drawerToggle.onOptionsItemSelected(item);

    // 스위치 케이스 로 토스트 달아놓기
        return super.onOptionsItemSelected(item);
}


}//main activity