package com.example.ecomate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TumblerActivity extends AppCompatActivity {
    ImageButton ib;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbler);

        ib =(ImageButton)findViewById(R.id.tumblerimage);

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ib.setImageBitmap(bitmap);



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
