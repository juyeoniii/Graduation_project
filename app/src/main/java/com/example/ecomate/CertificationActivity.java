package com.example.ecomate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CertificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
    }

    public void onButtonTumbler(View v){
        Intent myIntent = new Intent(getApplicationContext(),TumtumActivity.class);
        startActivity(myIntent);
    }

    public void onButtonStraw(View v){
        Intent myIntent = new Intent(getApplicationContext(),StrawActivity.class);
        startActivity(myIntent);
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
