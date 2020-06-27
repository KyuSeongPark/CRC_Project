package com.example.crc_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;



public class Fragment1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;




    public Fragment1() {

    }


    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }


    // 이중 프래그 먼트
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_1,container,false); // View 를 이용해 아이디를 가져온다


       // 처음 자식 프래그먼트를 지정
       getChildFragmentManager().beginTransaction().add(R.id.child_fragment,new ChildFragment1()).commit();

       // 버튼을 눌러 자식 프래그먼트 호출
        Button btn_child1 = (Button) v.findViewById(R.id.btn_child1);
        Button btn_child2 = (Button) v.findViewById(R.id.btn_child2);
        Button btn_child3 = (Button) v.findViewById(R.id.btn_child3);


        btn_child1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getChildFragmentManager().beginTransaction().replace(R.id.child_fragment, new ChildFragment1()).commit();
            }
        });

        btn_child2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getChildFragmentManager().beginTransaction().replace(R.id.child_fragment, new ChildFragment2()).commit();
            }
        });

        btn_child3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.child_fragment, new ChildFragment3()).commit();
            }
        });

        return v;
    }



}
