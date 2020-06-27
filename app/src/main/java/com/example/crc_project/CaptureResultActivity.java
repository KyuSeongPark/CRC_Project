package com.example.crc_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureResultActivity extends AppCompatActivity {

    private Button btn_Recamera, btn_home , btn_cool , btn_warm, btn_Regallery;
    private String imageFilePath;
    private Uri photoUri;
    private LinearLayout test,test2;

    private final int PICK_IMAGE = 200;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_result);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //상태바 없애기


        btn_home = (Button)findViewById(R.id.btn_home); // 홈버튼을 눌렀을때
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(homeintent);
                finish();
            }
        });


        //상단 갤러리버튼 눌렀을때
        btn_Regallery = (Button)findViewById(R.id.btn_Regallery);
        btn_Regallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,PICK_IMAGE);
            }
        });


        // 상단의 카메라 버튼을 눌렀을때
        btn_Recamera = (Button)findViewById(R.id.btn_Recamera);
        btn_Recamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ReCameraintent = new Intent(getApplicationContext(),CaptureResultActivity.class);
                startActivity(ReCameraintent);
                finish();
            }
        });


        // 카메라를 실행하는 액티비티
        Intent takePictureintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureintent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }catch (IOException ex){
                // Error occurred while creating the File
            }

            if(photoFile != null){
                photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                takePictureintent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureintent, REQUEST_IMAGE_CAPTURE);
            }
        }

        // 피부색 버튼
        btn_cool = (Button)findViewById(R.id.btn_cool);
        btn_warm = (Button)findViewById(R.id.btn_warm);

        test = (LinearLayout)findViewById(R.id.test);
        test2 = (LinearLayout)findViewById(R.id.test2);

        btn_cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            test.setVisibility(View.VISIBLE);
            test2.setVisibility(View.GONE);

            }
        });

        btn_warm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test2.setVisibility(View.VISIBLE);
                test.setVisibility(View.GONE);
            }
        });

    }


    // 저장될 이미지 파일 생성 메소드
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }


    //촬영 결과를 띄우는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;

            try {
                exif = new ExifInterface(imageFilePath);
            }catch (IOException e){
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if(exif != null){
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            }else{
                exifDegree = 0;
            }

            ((ImageView)findViewById(R.id.camera_result)).setImageBitmap(rotate(bitmap, exifDegree));

        }


        // 갤러리에서 사진 선택시
        if (requestCode == PICK_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지뷰에 세팅
                    ((ImageView)findViewById(R.id.camera_result)).setImageBitmap(img);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ((TextView)findViewById(R.id.captured)).setText("갤러리 사진");
        }


    }

    // 상수를 받아 각도로 변환 시켜주는 메소드
    private int exifOrientationToDegrees(int exifOrientation){
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return 0;
    }


    // 비트맵을 각도대로 회전시켜 결과를 반환 시키는 메소드
    private Bitmap rotate(Bitmap bitmap , float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return  Bitmap.createBitmap(bitmap , 0 , 0 , bitmap.getWidth(),
                bitmap.getHeight() , matrix, true);
    }

    // 백 버튼 눌렀을때 홈으로 돌아감
    @Override
    public void onBackPressed() {
        Intent Mainintent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(Mainintent);
        finish();
        super.onBackPressed();
    }


    //캡쳐버튼클릭
    public void mOnCaptureClick(View v){
        //전체화면
        View rootView = getWindow().getDecorView();

        File screenShot = ScreenShot(rootView);
        if(screenShot!=null){
            //갤러리에 추가
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(screenShot)));
            Toast.makeText(getApplicationContext(),"저장 완료!",Toast.LENGTH_SHORT).show();
        }

    }

    //화면 캡쳐하기
    public File ScreenShot(View view){
        view.setDrawingCacheEnabled(true);  //화면에 뿌릴때 캐시를 사용하게 한다

        Bitmap screenBitmap = view.getDrawingCache();   //캐시를 비트맵으로 변환

        String filename = "screenshot.png";

        // CRC 파일 생성
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CRC/";
        File testfile = new File(dirPath);

        if(!testfile.exists()){
            testfile.mkdir();
        }


        File file = new File(Environment.getExternalStorageDirectory()+"/CRC", filename);  //CRC폴더 screenshot.png 파일
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(file);
            screenBitmap.compress(Bitmap.CompressFormat.PNG, 90, os);   //비트맵을 PNG파일로 변환
            os.close();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        view.setDrawingCacheEnabled(false);
        return file;


    }


}
