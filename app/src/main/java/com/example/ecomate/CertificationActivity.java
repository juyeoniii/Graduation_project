package com.example.ecomate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CertificationActivity extends AppCompatActivity {

    static final int REQ = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);

        Intent intent = getIntent();

        final TextView et_id = (TextView)findViewById(R.id.et_id);
        String userID = intent.getStringExtra("userID");
        et_id.setText(userID);

        final TextView et_name = (TextView)findViewById(R.id.et_name);
        String userName = intent.getStringExtra("userName");
        et_name.setText(userName);


        ImageButton hmbtn = (ImageButton) findViewById(R.id.hmbtn);
        hmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CertificationActivity.this, MainActivity.class);


                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);


                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        ImageButton mpbtn = (ImageButton) findViewById(R.id.mpbtn);
        mpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CertificationActivity.this, MypageActivity.class);

                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                startActivityForResult(intent, REQ);
            }
        });



        ImageButton strawbtn = (ImageButton) findViewById(R.id.strawbtn);
        strawbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CertificationActivity.this, StrawActivity.class);

                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                startActivity(intent);
            }
        });

        ImageButton mealbtn = (ImageButton) findViewById(R.id.mealbtn);
        mealbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CertificationActivity.this, VegiActivity.class);

                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                startActivity(intent);
            }
        });


        ImageButton walkbtn = (ImageButton) findViewById(R.id.walkbtn);
        walkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CertificationActivity.this, WalkActivity.class);

                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                startActivity(intent);
            }
        });

        ImageButton tumblerbtn = (ImageButton) findViewById(R.id.tumblerbtn);
        tumblerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CertificationActivity.this, TumrcpActivity.class);

                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                startActivity(intent);
            }
        });

        ImageButton scbtn = (ImageButton) findViewById(R.id.scbtn);
        scbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CertificationActivity.this, SearchActivity.class);

                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                startActivityForResult(intent, REQ);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (requestCode == REQ){


            TextView et_name = (TextView)findViewById(R.id.et_name);
            String userName = et_name.getText().toString();
            intent.getStringExtra("userName");
            et_name.setText(userName);

            TextView et_id = (TextView)findViewById(R.id.et_id);
            String userID = et_id.getText().toString();
            intent.getStringExtra("userID");
            et_id.setText(userID);


        }
    }
}


