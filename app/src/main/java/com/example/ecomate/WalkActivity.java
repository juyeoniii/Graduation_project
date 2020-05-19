package com.example.ecomate;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecomate.R;


public class WalkActivity extends Activity {

    Intent manboService;
    BroadcastReceiver receiver;

    boolean flag = true;
    String serviceData;
    TextView countText,pointview;
    Button StartBtn, ResetBtn,pointbtn;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

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

        ResetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try {

                    Toast toast = Toast.makeText(getApplicationContext(),"      리셋되었습니다.\n시작버튼을 눌러주세요. ",Toast.LENGTH_LONG);
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

        pointbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try {

                    Toast toast = Toast.makeText(getApplicationContext(),"포인트 지급 완료 ! 리셋되었습니다. \n        시작버튼을 눌러주세요.",Toast.LENGTH_LONG);
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

    }



    class PlayingReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("PlayignReceiver", "IN");
            serviceData = intent.getStringExtra("stepService");
            int pp = Integer.parseInt(serviceData);
            int ppp = pp/10;
            String point = Integer.toString(ppp);
            countText.setText(serviceData);
            pointview.setText(point);




            //Toast.makeText(getApplicationContext(), "헛둘헛둘", Toast.LENGTH_SHORT).show();

        }
    }

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
