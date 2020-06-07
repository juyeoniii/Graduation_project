package com.example.ecomate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView pointpic;
    TextView et_name, et_id , pointview1, pointview2, pointview3;
    phpdo task;
    phpdo2 task2;
    phpdo3 task3;
    int point=7900;




    static final int REQ = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        String userID = intent.getStringExtra("userID");
        String userName = intent.getStringExtra("userName");
        String userPassword = intent.getStringExtra("userPassword");

        task = new phpdo();
        task2 = new phpdo2();
        task3 = new phpdo3();
        et_name = (TextView)findViewById(R.id.et_name);
        et_id = (TextView)findViewById(R.id.et_id);
        pointview1=(TextView)findViewById(R.id.pointview1);
        pointview2=(TextView)findViewById(R.id.pointview2);
        pointview3=(TextView)findViewById(R.id.pointview3);
        pointpic = (ImageView)findViewById(R.id.pointpic);
        task.execute(userID,userPassword);
        task2.execute(userID,userPassword);
        task3.execute(userID);


        et_id.setText(userID);
        et_name.setText(userName);

        ImageButton mpbtn = (ImageButton) findViewById(R.id.mpbtn);
        mpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MypageActivity.class);


                TextView et_name = (TextView)findViewById(R.id.et_name);
                TextView et_id = (TextView)findViewById(R.id.et_id);
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


                TextView et_name = (TextView)findViewById(R.id.et_name);
                TextView et_id = (TextView)findViewById(R.id.et_id);
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

                TextView et_name = (TextView)findViewById(R.id.et_name);
                TextView et_id = (TextView)findViewById(R.id.et_id);
                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                startActivityForResult(intent, REQ);
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
    public class phpdo3 extends AsyncTask<String,Void,String> {

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {

            try {
                String userID = arg0[0];

                String link = "http://192.168.0.2/ehlsl4.php?userID=" + userID;
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

        protected void onPostExecute(String result) {
            //txtview.setText("Login Successful");
            try{
                pointview1.setText("Point : "+result);


                int point = Integer.parseInt(result);

                if (point < 3000) {

                    pointpic.setImageResource(R.drawable.sprout_1);
                    pointview3.setText("1단계 새싹");
                    pointview2.setText("다음 단계까지 " + (3000 - point) + "P 남았습니다.");


                } else if ((point >= 3000) && (point < 7000)) {

                    pointpic.setImageResource(R.drawable.sprout_2);
                    pointview3.setText("2단계 새싹");
                    pointview2.setText("다음 단계까지 " + (7000 - point) + "P 남았습니다.");


                } else if ((point >= 7000) && (point < 10000)) {

                    pointpic.setImageResource(R.drawable.sprout_3);
                    pointview3.setText("3단계 새싹");
                    pointview2.setText("다음 단계까지 " + (10000 - point) + "P 남았습니다.");
                }

            }catch (NumberFormatException e){

            }catch (Exception e){

            }



        }
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