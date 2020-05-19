package com.example.ecomate;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



public class TumrcpActivity extends AppCompatActivity {


    private static final int MY_PERMISSION_STORAGE = 1111 ;
    private static final int CAPTURE_IMAGE =2222 ;
    public static final int PICK_IMAGE = 1001;

    TessBaseAPI tess;
    String dataPath="";
    ImageButton btn_capture;
    Bitmap imageBitmap;
    Button button,combtn;
    Bitmap image;
    String OCRresult = null;
    TextView OCRTextView;
    String newString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumrcp);


        btn_capture = (ImageButton) findViewById(R.id.recieptview);
        button = (Button) findViewById(R.id.ocrbtn);
        OCRTextView =(TextView) findViewById(R.id.ocrview);
        combtn = (Button) findViewById(R.id.combtn);


        // 영수증 사진 불러오기

        btn_capture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkPermission();
                photoDialogRadio();

            }
        });


        // 영수증 인식하기

        String lang = "kor+eng";
        dataPath = getFilesDir()+"/tesseract/";
        tess = new TessBaseAPI();

        checkFile(new File(dataPath + "tessdata/"),"kor");
        checkFile(new File(dataPath + "tessdata/"),"eng");

        tess.init(dataPath,lang);



        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                image = ((BitmapDrawable)btn_capture.getDrawable()).getBitmap();
                processImage(image);

            }
        });

        // 영수증 날짜 인식하고 오늘 날짜랑 비교하기

        final Date today = new Date();
        final SimpleDateFormat sdf0,sdf1,sdf2,sdf3;

        sdf0 = new SimpleDateFormat("yyyy/MM/dd");
        sdf1 = new SimpleDateFormat("yyyy.MM.dd");
        sdf2 = new SimpleDateFormat("yyyy MM dd");
        sdf3 = new SimpleDateFormat("yyyy-MM-dd");

        //System.out.println(sdf0.format(today)); - 오늘
        // newString 이 인식 날짜


        //@@@ 애뮬레이터 시간 변경해줘야 날짜 맞는지 확인 가능 @@@
        combtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if((newString == sdf0.format(today))||(newString == sdf1.format(today))||(newString == sdf2.format(today))||(newString == sdf3.format(today))){
                    Toast.makeText(getApplicationContext(), "인증 성공!", Toast.LENGTH_LONG).show();

                } else Toast.makeText(getApplicationContext(), "인증 실패!", Toast.LENGTH_LONG).show();

            }
        });

/*

/*

*/

    }


    public void processImage(Bitmap bitmap){


        TextView OCRTextView;
        tess.setImage(bitmap);
        OCRresult = tess.getUTF8Text();
        OCRTextView =(TextView) findViewById(R.id.ocrview);


        SpannableStringBuilder sb = new SpannableStringBuilder(OCRresult);

        Pattern pattern =  Pattern.compile("(19|20)\\d{2}[- /.]*(0[1-9]|1[012])[- /.]*(0[1-9]|[12][0-9]|3[01])");
        Matcher matcher = pattern.matcher(OCRresult);

        if (matcher.find()){

            newString = matcher.group();
            sb.setSpan(new ForegroundColorSpan(Color.rgb(255, 0, 0)), matcher.start(), matcher.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            OCRTextView.setText(sb);

        } else OCRTextView.setText("날짜를 인식하지 못하였습니다.");



        /*
        if(OCRresult.contains("할인")){
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#5F00FF")),OCRresult.indexOf("할인"),OCRresult.indexOf("할인")+2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            OCRTextView.setText(ssb);
        }else OCRTextView.setText(OCRresult);

        */

    }





    private void copyFiles(String lang){
        try {
            String filepath = dataPath + "/tessdata/" + lang + ".traineddata";
            AssetManager assetManager = getAssets();

            InputStream inStream = assetManager.open("tessdata/"+lang+".traineddata");
            OutputStream outStream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while((read=inStream.read(buffer))!= -1){
                outStream.write(buffer,0,read);

            }
            outStream.flush();
            outStream.close();
            inStream.close();




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void checkFile(File dir, String lang){
        if(!dir.exists()&& dir.mkdirs()){
            copyFiles(lang);
        }

        if(dir.exists()){
            String datafilePath = dataPath + "/tessdata/" + lang +".traineddata";
            File datafile = new File(datafilePath);
            if(!datafile.exists()){
                copyFiles(lang);
            }

        }
    }









    public void photoDialogRadio(){
        final CharSequence[] PhotoModels = {"갤러리에서 가져오기","직접 찍어서 가져오기"};

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setTitle("사진 가져오기");
        alt_bld.setSingleChoiceItems(PhotoModels, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(item==0) { //갤러리
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PICK_IMAGE);

                }
                else { //사진 찍어서 가져오기
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAPTURE_IMAGE);
                }
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==PICK_IMAGE && resultCode ==RESULT_OK){
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                imageBitmap = BitmapFactory.decodeStream(in);
                in.close();
                btn_capture.setImageBitmap(imageBitmap);


            }catch(Exception e){

            }

        }else if(requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageBitmap = rotateImage(imageBitmap, 90);
            btn_capture.setImageBitmap(imageBitmap);

        }
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public static Bitmap gallerymark(Bitmap src, String watermark) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        paint.setAntiAlias(true);
        paint.setUnderlineText(false);
        canvas.drawText(watermark, 100 ,350 , paint);


        return result;
    }
    public static Bitmap cameramark(Bitmap src, String watermark) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(13);
        paint.setAntiAlias(true);
        paint.setUnderlineText(false);
        canvas.drawText(watermark, 33 ,78 , paint);


        return result;
    }











    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 다시 보지 않기 버튼을 만드려면 이 부분에 바로 요청을 하도록 하면 됨 (아래 else{..} 부분 제거)
            // ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_CAMERA);

            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_STORAGE:
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(TumrcpActivity.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..

                break;
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    } // BiteMapToString

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    } // StringToBitMap




/*
    public void onButtontumbler(View v){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap bitmap = ((BitmapDrawable)btn_capture.getDrawable()).getBitmap();

        float scale = (float) (2048/(float)bitmap.getWidth());
        int image_w = (int) (bitmap.getWidth() * scale);
        int image_h = (int) (bitmap.getHeight() * scale);
        Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
        resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();


        Intent intent = new Intent(TumrcpActivity.this, TumblerActivity.class);
        intent.putExtra("image", byteArray);
        startActivity(intent);

    }

 */


    public void hmbtn(View v) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
    }

    public void cmrbtn(View v) {
        Intent myIntent = new Intent(getApplicationContext(), CertificationActivity.class);
        startActivity(myIntent);
    }

    public void scbtn(View v) {
        Intent myIntent = new Intent(getApplicationContext(), CertificationActivity.class);
        startActivity(myIntent);
    }

    public void mpbtn(View v) {
        Intent myIntent = new Intent(getApplicationContext(), MypageActivity.class);
        startActivity(myIntent);
    }

}
