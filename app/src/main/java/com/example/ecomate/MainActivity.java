package com.example.ecomate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
