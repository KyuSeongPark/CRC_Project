package com.example.crc_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ViewFlipper;

public class WarmCoolActivity extends AppCompatActivity {

    private Button btn_home;
    private ViewFlipper Viewlipper2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warm_cool);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //상태바 없애기

        Viewlipper2 = (ViewFlipper)findViewById(R.id.Viewlipper2);
        Viewlipper2.startFlipping();
        Viewlipper2.setFlipInterval(5000);

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
