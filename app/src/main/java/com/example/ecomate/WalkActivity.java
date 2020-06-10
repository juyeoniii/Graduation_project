package com.example.ecomate;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ecomate.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class WalkActivity extends Activity {

    Intent manboService;
    BroadcastReceiver receiver;

    String serviceData;
    TextView countText,pointview;
    Button StartBtn, ResetBtn,pointbtn;

    TextView textViewid;
    TextView point;
    TextView textViewname;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        Intent intent = getIntent();

        final TextView et_id = (TextView) findViewById(R.id.et_id);
        String userID = intent.getStringExtra("userID");
        et_id.setText(userID);

        final TextView et_name = (TextView) findViewById(R.id.et_name);
        String userName = intent.getStringExtra("userName");
        et_name.setText(userName);

        textViewid = (TextView) findViewById(R.id.et_id);
        point = (TextView) findViewById(R.id.et_point);
        textViewname = (TextView) findViewById(R.id.et_name);


        manboService = new Intent(this, StepCheckService.class);
        receiver = new PlayingReceiver();

        countText = (TextView) findViewById(R.id.stepText);
        StartBtn = (Button) findViewById(R.id.startbtn);
        ResetBtn = (Button) findViewById(R.id.resetbtn);
        pointview = (TextView) findViewById(R.id.pointview);
        pointbtn = (Button) findViewById(R.id.pointbtn);


        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    IntentFilter mainFilter = new IntentFilter("make.a.yong.manbo");
                    registerReceiver(receiver, mainFilter);
                    startService(manboService);

                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        ResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Toast toast = Toast.makeText(getApplicationContext(), "      리셋되었습니다.\n시작버튼을 눌러주세요. ", Toast.LENGTH_LONG);
                    toast.show();

                    unregisterReceiver(receiver);
                    stopService(manboService);

                    // txtMsg.setText("After stoping Service:\n"+service.getClassName());
                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        pointbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = textViewid.getText().toString();
                String userPoint = pointview.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {//volley
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jasonObject = new JSONObject(response);//Register2 php에 response
                            boolean success = jasonObject.getBoolean("success");//Register2 php에 suces

                            if (success) {
                                Toast toast = Toast.makeText(getApplicationContext(), "포인트 지급 완료 ! 리셋되었습니다. \n        시작버튼을 눌러주세요.", Toast.LENGTH_LONG);
                                toast.show();
                                unregisterReceiver(receiver);
                                stopService(manboService);

                                Intent myIntent = new Intent(getApplicationContext(), CertificationActivity.class);
                                String userID = et_id.getText().toString();
                                myIntent.putExtra("userID", userID);

                                String userName = et_name.getText().toString();
                                myIntent.putExtra("userName", userName);

                                setResult(RESULT_OK, myIntent);
                                finish();
                                startActivity(myIntent);
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                            Toast.makeText(getApplicationContext(), e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                };
                WalkRequest registerRequest = new WalkRequest(userID, userPoint, responseListener);
                RequestQueue queue = Volley.newRequestQueue(WalkActivity.this);
                queue.add(registerRequest);
            }
        });
    }


    class PlayingReceiver extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("PlayingReceiver", "IN");
                serviceData = intent.getStringExtra("stepService");
                int pp = Integer.parseInt(serviceData);
                final int ppp = pp / 10;
                final String point = Integer.toString(ppp);
                countText.setText(serviceData);
                pointview.setText(point);


            }
        }

}


