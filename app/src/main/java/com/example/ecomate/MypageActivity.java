package com.example.ecomate;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MypageActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
    }

    public void onButton2Clicked(View v){
        Intent myIntent = new Intent(getApplicationContext(),CertificationActivity.class);
        startActivity(myIntent);
    }

    public void onClick(View view)
    {
        finish();
    }




}
