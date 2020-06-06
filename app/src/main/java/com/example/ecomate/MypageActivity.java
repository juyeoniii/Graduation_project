package com.example.ecomate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MypageActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Intent intent = getIntent();

        TextView et_id = (TextView)findViewById(R.id.et_id);
        String userID = intent.getStringExtra("userID");
        et_id.setText(userID);

        TextView et_name = (TextView)findViewById(R.id.et_name);
        String userName = intent.getStringExtra("userName");
        et_name.setText(userName);


        ImageButton hmbtn = (ImageButton) findViewById(R.id.hmbtn);
        hmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, MainActivity.class);

                TextView et_id = (TextView)findViewById(R.id.et_id);
                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                TextView et_name = (TextView)findViewById(R.id.et_name);
                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                setResult(RESULT_OK, intent);
                finish();
            }
        });


        ImageButton cmrbtn = (ImageButton) findViewById(R.id.cmrbtn);
        cmrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, CertificationActivity.class);

                TextView et_id = (TextView)findViewById(R.id.et_id);
                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                TextView et_name = (TextView)findViewById(R.id.et_name);
                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

}

