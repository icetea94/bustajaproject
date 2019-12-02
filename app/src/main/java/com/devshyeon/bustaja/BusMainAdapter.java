package com.devshyeon.bustaja;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BusMainAdapter extends FragmentPagerAdapter {

    Fragment[] fragments = new Fragment[2];
    String [] pageTitles=new String[]{"버스정보","즐겨찾기"};


    public BusMainAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragments[0] = new CityFragment();
        fragments[1] = new FavoriteFragment();

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return pageTitles[position];
    }
}
