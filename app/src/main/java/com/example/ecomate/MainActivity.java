package com.example.ecomate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onButton2Clicked(View v){
        Intent myIntent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(myIntent);
    }
}
