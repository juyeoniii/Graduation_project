package com.example.ecomate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CertificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
    }

    public void onButton1Clicked(View v){
        Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(myIntent);
    }


    public void onButtonTumbler(View v){
        Intent myIntent = new Intent(getApplicationContext(),TumtumActivity.class);
        startActivity(myIntent);
    }

    public void onButtonStraw(View v){
        Intent myIntent = new Intent(getApplicationContext(),StrawActivity.class);
        startActivity(myIntent);
    }





}
