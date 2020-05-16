package com.example.ecomate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MypageActivity extends AppCompatActivity{

    TextView et_name;
    phpdo task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");

        task = new phpdo();
        et_name = (TextView)findViewById(R.id.et_name);
        task.execute(userID,userPassword);

    }

    private class phpdo extends AsyncTask<String,Void,String> {

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

        @Override
        protected void onPostExecute(String result){
            //txtview.setText("Login Successful");
            et_name.setText(result);
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

