package com.example.crc_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.crc_project.LoadingActivity;
import com.example.crc_project.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private long backBtnTime = 0; // 한번 더 누르면 종료됩니다.

    private Button btn_NoticeTip, btn_camera, btn_tip, btn_warmcool, btn_gallery;
    private TextView tv_NoticeTip;
    private ViewFlipper viewFlipper1;

    private final int PICK_IMAGE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //상태바 없애기


        viewFlipper1 = (ViewFlipper)findViewById(R.id.viewFlipper1);
        viewFlipper1.startFlipping();
        viewFlipper1.setFlipInterval(5000);

        tv_NoticeTip = (TextView)findViewById(R.id.tv_NoticeTip); // 공지 및 팁 흐르는 애니메이션
        tv_NoticeTip.setSelected(true);

        tv_NoticeTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 팁 공지 액티비티 실행
                Intent intent = new Intent(getApplicationContext(),NoticeTipActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btn_NoticeTip = (Button)findViewById(R.id.btn_NoticeTip); // 버튼을 누르면 팁/공지사항 화면으로 넘어갑니다.

        btn_NoticeTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 팁 공지 액티비티 실행
                Intent intent = new Intent(getApplicationContext(),NoticeTipActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btn_tip = (Button)findViewById(R.id.btn_tip); // 버튼을 누르면 팁/공지사항 화면으로 넘어갑니다.

        btn_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 팁 공지 액티비티 실행
                Intent intent = new Intent(getApplicationContext(),NoticeTipActivity.class);
                startActivity(intent);
                finish();

            }
        });


        // 갤러리 버튼을 눌렀을때
        btn_gallery = (Button)findViewById(R.id.btn_gallery);
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,PICK_IMAGE);

            }
        });


        // 웜톤 쿨톤 구분하기 버튼 눌렀을때
        btn_warmcool = (Button)findViewById(R.id.btn_warmcool);
        btn_warmcool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent warmcoolintent = new Intent(getApplicationContext(),WarmCoolActivity.class);
                startActivity( warmcoolintent);
                finish();
            }
        });



        btn_camera = (Button)findViewById(R.id.btn_camera); // 버튼을 누르면 카메라 권한 실행

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 권한 체크
                TedPermission.with(getApplicationContext())
                        .setPermissionListener(permissionListener)
                        .setRationaleMessage("카메라 권한이 필요합니다.")
                        .setDeniedMessage("거부하셨습니다.")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                        .check();
            }
        });

    }


    // 권한 체크, 허용되면 카메라 액티비티 실행
    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(),"권한이 허용됨",Toast.LENGTH_SHORT).show();
           Intent intent = new Intent(getApplicationContext(),CaptureResultActivity.class); //카메라 액티비티 실행
           startActivity(intent);
           finish();

        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(),"권한이 거부됨",Toast.LENGTH_SHORT).show();

        }
    };



    // 뒤로가기 두번 눌러 종료
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(0<= gapTime && 2000 >= gapTime){
            super.onBackPressed();
        }
        else{
            backBtnTime = curTime;
            Toast.makeText(this,"한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 갤러리에서 사진 선택시
        if (requestCode == PICK_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    Intent intent = new Intent(getApplicationContext(),GalleryResultActivity.class); // GalleryResultActivity 실행
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image",byteArray);
                    startActivity(intent);
                    in.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        finish();
    }


}
