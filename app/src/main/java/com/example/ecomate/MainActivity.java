package com.example.ecomate;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView et_name, et_id;
    phpdo task;
    phpdo2 task2;

    static final int REQ = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");

        task = new phpdo();
        task2 = new phpdo2();
        et_name = (TextView)findViewById(R.id.et_name);
        et_id = (TextView)findViewById(R.id.et_id);
        task.execute(userID,userPassword);
        task2.execute(userID,userPassword);


        ImageButton mpbtn = (ImageButton) findViewById(R.id.mpbtn);
        mpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MypageActivity.class);

                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                startActivityForResult(intent, REQ);
            }
        });

       ImageButton cmrbtn = (ImageButton) findViewById(R.id.cmrbtn);
        cmrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CertificationActivity.class);

                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                startActivityForResult(intent, REQ);
            }
        });

        ImageButton scbtn = (ImageButton) findViewById(R.id.scbtn);
        scbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


    }

    public class phpdo extends AsyncTask<String,Void,String> {

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {

            try {
                String userID = arg0[0];
                String userPassword = arg0[1];

                String link = "http://192.168.0.2/ehlsl.php?userID=" + userID + "&userPassword=" + userPassword;
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return sb.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        protected void onPostExecute(String result){
            //txtview.setText("Login Successful");
            et_name.setText(result);
        }
    }

    public class phpdo2 extends AsyncTask<String,Void,String> {


        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {

            try {
                String userID = arg0[0];
                String userPassword = arg0[1];

                String link = "http://192.168.0.2/ehlsl2.php?userID=" + userID + "&userPassword=" + userPassword;
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return sb.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        protected void onPostExecute(String result){
            //txtview.setText("Login Successful");
            et_id.setText(result);
        }
    }


    public void cmrbtn(View v) {
        Intent myIntent = new Intent(getApplicationContext(), CertificationActivity.class);
        startActivity(myIntent);
    }

    public void scbtn(View v) {
        Intent myIntent = new Intent(getApplicationContext(), CertificationActivity.class);
        startActivity(myIntent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (requestCode == REQ){
            if (resultCode == RESULT_OK){

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


}
