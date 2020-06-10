package com.example.ecomate;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MypageActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_STORAGE = 1111;
    private static final int CAPTURE_IMAGE = 2222;
    public static final int PICK_IMAGE = 1001;

    ImageButton profile_capture;
    Bitmap imageBitmap, bitbit;

    Bitmap getBlob;

    private String mJsonSting;


    phpdo task;
    back task4;
    //load_image_task tt;

    TextView et_point, textViewid;


    //a list to store all the products
    ArrayList<Image> productList;

    //the recyclerview
    RecyclerView recyclerView;
    ImageAdapter adapter;
    GridLayoutManager layoutManager;
    static final int REQ = 1;

    final String imgUri = "http:\\/\\/192.168.18.1\\/MyApi\\/uploads\\/1591543737432.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Intent intent = getIntent();

        final TextView et_id = (TextView) findViewById(R.id.et_id);
        String userID = intent.getStringExtra("userID");
        et_id.setText(userID);

        final TextView et_name = (TextView) findViewById(R.id.et_name);
        String userName = intent.getStringExtra("userName");
        et_name.setText(userName);

        textViewid = (TextView) findViewById(R.id.et_id);


        task = new phpdo();
        task4 = new back();

        et_point = (TextView) findViewById(R.id.et_point);
        task.execute(userID);
        task4.execute(imgUri);

        // tt = new load_image_task();
        // tt.execute(userID);


        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView2);

        productList = new ArrayList<>();

        loadProducts();

        adapter = new ImageAdapter(getApplicationContext(), productList);
        layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        ImageButton hmbtn = (ImageButton) findViewById(R.id.hmbtn);
        hmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, MainActivity.class);

                TextView et_id = (TextView) findViewById(R.id.et_id);
                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                TextView et_name = (TextView) findViewById(R.id.et_name);
                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        ImageButton scbtn = (ImageButton) findViewById(R.id.scbtn);
        scbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, SearchActivity.class);

                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                startActivityForResult(intent, REQ);
            }
        });


        ImageButton cmrbtn = (ImageButton) findViewById(R.id.cmrbtn);
        cmrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, CertificationActivity.class);

                TextView et_id = (TextView) findViewById(R.id.et_id);
                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                TextView et_name = (TextView) findViewById(R.id.et_name);
                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        profile_capture = (ImageButton) findViewById(R.id.profile_capture);

        profile_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                photoDialogRadio();
            }
        });


    }

    public class phpdo extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {

            try {
                String userID = arg0[0];

                String link = "http://192.168.0.2/ehlsl4.php?userID=" + userID;
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return sb.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        protected void onPostExecute(String result) {
            //txtview.setText("Login Successful");
            et_point.setText("포인트 : " + result);
        }
    }

    public void photoDialogRadio() {
        final CharSequence[] PhotoModels = {"갤러리에서 가져오기", "직접 찍어서 가져오기"};

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setTitle("사진 가져오기");
        alt_bld.setSingleChoiceItems(PhotoModels, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) { //갤러리
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PICK_IMAGE);

                } else { //사진 찍어서 가져오기
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAPTURE_IMAGE);
                }
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            try {
                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");

                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                profile_capture.setBackground(new ShapeDrawable(new OvalShape()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    profile_capture.setClipToOutline(true);
                }
                profile_capture.setImageBitmap(imageBitmap);

                uploadBitmap(imageBitmap);

            } catch (Exception e) {

                e.printStackTrace();

            }

        } else if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK) {
            Intent intent = getIntent();
            String userID = intent.getStringExtra("userID");

            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");


            // imageBitmap = rotateImage(imageBitmap, 90);
            profile_capture.setBackground(new ShapeDrawable(new OvalShape()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                profile_capture.setClipToOutline(true);
            }
            profile_capture.setImageBitmap(imageBitmap);

            uploadBitmap(imageBitmap);
        }
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void checkPermission() {
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
                        Toast.makeText(MypageActivity.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..

                break;
        }
    }

    private class back extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {

                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoOutput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                imageBitmap = BitmapFactory.decodeStream(is);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return imageBitmap;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            profile_capture.setBackground(new ShapeDrawable(new OvalShape()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                profile_capture.setClipToOutline(true);
            }
            profile_capture.setImageBitmap(bitmap);
        }
    }



    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext

        final String userID = textViewid.getText().toString().trim();


        //our custom volley request
        VolleyMultipartRequest2 volleyMultipartRequest = new VolleyMultipartRequest2(Request.Method.POST, EndPoints4.ROOT_URL,
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
            protected Map<String, String> getID() throws AuthFailureError {
                Map<String, String> user = new HashMap<>();
                user.put("userID", userID);
                return user;
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
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void loadProducts() {

        TextView et_id = (TextView)findViewById(R.id.et_id);
        String userID = et_id.getText().toString();
        final String URL_PRODUCTS = "http://192.168.0.2/MyApi/api6.php?userID=" +userID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Image(
                                        product.getString("image")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            ImageAdapter adapter = new ImageAdapter(MypageActivity.this, productList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }




}


