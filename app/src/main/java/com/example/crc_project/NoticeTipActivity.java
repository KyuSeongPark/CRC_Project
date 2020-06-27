package com.example.crc_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NoticeTipActivity extends AppCompatActivity {

    private Button btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_tip);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //상태바 없애기


        ViewPager vp = findViewById(R.id.viewpager);
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        //탭과 뷰를 연동
        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.tip);
        images.add(R.drawable.notice);

        for(int i = 0 ; i < 2 ; i++ ){
            tab.getTabAt(i).setIcon(images.get(i));
        }


        // 홈 버튼
        btn_home = (Button)findViewById(R.id.btn_home);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(homeintent);
                finish();
            }
        });
    }


    // 뒤로가기 누르면 홈으로 이동
    @Override
    public void onBackPressed() {
        Intent homeintent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(homeintent);
        finish();
    }
}


