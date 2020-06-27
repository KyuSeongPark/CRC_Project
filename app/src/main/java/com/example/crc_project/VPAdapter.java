package com.example.crc_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VPAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> items;
    private ArrayList<String> itext = new ArrayList<String>();


    public VPAdapter(FragmentManager fm){
        super(fm);
        items = new ArrayList<Fragment>();
        items.add(new Fragment1());
        items.add(new Fragment2());

        itext.add("코디 팁");
        itext.add("공지");

    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return itext.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       return items.get(position);
    }

    @Override
    public int getCount() {
      return items.size();
    }
}
