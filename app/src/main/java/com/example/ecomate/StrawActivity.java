package com.example.ecomate;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class StrawActivity extends AppCompatActivity {


    private static final int MY_PERMISSION_STORAGE = 1111 ;
    private static final int CAPTURE_IMAGE =2222 ;
    public static final int PICK_IMAGE = 1001;


    static final int REQ = 1;

    ImageButton Straw_capture;
    Bitmap imageBitmap;
    Button upload;

    TextView textViewid;
    TextView point;
    TextView textViewname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_straw);

        Button upload = (Button)findViewById(R.id.upload);

        Intent intent = getIntent();

        final TextView et_id = (TextView)findViewById(R.id.et_id);
        String userID = intent.getStringExtra("userID");
        et_id.setText(userID);

        final TextView et_name = (TextView)findViewById(R.id.et_name);
        String userName = intent.getStringExtra("userName");
        et_name.setText(userName);

        Straw_capture = (ImageButton)findViewById(R.id.straw_capture);
        textViewid = (TextView) findViewById(R.id.et_id);
        point = (TextView) findViewById(R.id.et_point);
        textViewname = (TextView) findViewById(R.id.et_name);

        Straw_capture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkPermission();
                photoDialogRadio();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "인증 성공! 20P가 적립되었습니다.", Toast.LENGTH_LONG).show();
                Intent myintent = new Intent(StrawActivity.this,MypageActivity.class);

                String userID = et_id.getText().toString();
                myintent.putExtra("userID", userID);

                String userName = et_name.getText().toString();
                myintent.putExtra("userName", userName);

                setResult(RESULT_OK, myintent);
                finish();

            }
        });



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

            Uri imageUri = data.getData();
            try {

                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");

                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageBitmap = gallerymark(imageBitmap,userID);

                Straw_capture.setImageBitmap(imageBitmap);

                uploadBitmap(imageBitmap);

            }catch(Exception e){
                e.printStackTrace();
            }

        }else if(requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK){

            Intent intent = getIntent();
            String userID = intent.getStringExtra("userID");

            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");


            imageBitmap = rotateImage(imageBitmap, 90);
            imageBitmap = cameramark(imageBitmap,userID);
            Straw_capture.setImageBitmap(imageBitmap);

            uploadBitmap(imageBitmap);

        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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
                        Toast.makeText(StrawActivity.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..

                break;
        }
    }

    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext

        final String userID = textViewid.getText().toString().trim();
        final String userPoint = point.getText().toString().trim();
        final String userName = textViewname.getText().toString().trim();




        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */

            @Override
            protected Map<String, String> getPoint() throws AuthFailureError {
                Map<String, String> pt = new HashMap<>();
                pt.put("userPoint", userPoint);
                return pt;
            }

            @Override
            protected Map<String, String> getID() throws AuthFailureError {
                Map<String, String> user = new HashMap<>();
                user.put("userID", userID);
                return user;
            }

            @Override
            protected Map<String, String> getName() throws AuthFailureError {
                Map<String, String> name = new HashMap<>();
                name.put("userName", userName);
                return name;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }


        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);


        VolleyMultipartRequest volleyMultiRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints2.UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */

            @Override
            protected Map<String, String> getPoint() throws AuthFailureError {
                Map<String, String> pt = new HashMap<>();
                pt.put("userPoint", userPoint);
                return pt;
            }

            @Override
            protected Map<String, String> getID() throws AuthFailureError {
                Map<String, String> user = new HashMap<>();
                user.put("userID", userID);
                return user;
            }

            @Override
            protected Map<String, String> getName() throws AuthFailureError {
                Map<String, String> name = new HashMap<>();
                name.put("userName", userName);
                return name;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
            /*
             * Here we are passing image by renaming it with a unique name
             * */



        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultiRequest);

        VolleyMultipartRequest volleyMulti = new VolleyMultipartRequest(Request.Method.POST, EndPoints3.UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */

            @Override
            protected Map<String, String> getPoint() throws AuthFailureError {
                Map<String, String> pt = new HashMap<>();
                pt.put("userPoint", userPoint);
                return pt;
            }

            @Override
            protected Map<String, String> getID() throws AuthFailureError {
                Map<String, String> user = new HashMap<>();
                user.put("userID", userID);
                return user;
            }

            @Override
            protected Map<String, String> getName() throws AuthFailureError {
                Map<String, String> name = new HashMap<>();
                name.put("userName", userName);
                return name;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
            /*
             * Here we are passing image by renaming it with a unique name
             * */



        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMulti);
    }



}