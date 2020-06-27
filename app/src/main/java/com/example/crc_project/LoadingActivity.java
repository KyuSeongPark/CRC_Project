package com.example.crc_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.crc_project.R;

public class LoadingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //상태바 없애기
        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Mainintent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(Mainintent);
                finish();
            }
        },2000);
    }


}
