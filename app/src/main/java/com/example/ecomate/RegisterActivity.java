package com.example.ecomate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecomate.R;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;


public class RegisterActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextId;
    private EditText mEditTextpw;
    private EditText mEditTextpwck;
    private EditText mEditTextemail;
    private TextView mTextViewresult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEditTextName = (EditText)findViewById(R.id.nameregister);
        mEditTextId = (EditText)findViewById(R.id.idregister);
        mEditTextpw = (EditText)findViewById(R.id.pwregister);
        mEditTextpwck = (EditText)findViewById(R.id.pwcheck);
        mEditTextemail = (EditText)findViewById(R.id.emailregister);
        mTextViewresult = (TextView) findViewById(R.id.mainresult);


        mTextViewresult.setMovementMethod(new ScrollingMovementMethod());


        Button buttonInsert = (Button)findViewById(R.id.bt_Join);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mEditTextName.getText().toString();
                String id = mEditTextId.getText().toString();
                String pw = mEditTextpw.getText().toString();
                String pwck = mEditTextpwck.getText().toString();
                String email = mEditTextemail.getText().toString();

                if(pw.equals(pwck))
                {
                    InsertData task = new InsertData();
                    task.execute("http://" + IP_ADDRESS + "/insert2.php", name,id,pw,pwck,email);
                }


                mEditTextName.setText("");
                mEditTextId.setText("");
                mEditTextpw.setText("");
                mEditTextpwck.setText("");
                mEditTextemail.setText("");

            }
        });

    }

    public void onClick(View view)
    {
        finish();
    }



    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(RegisterActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            mTextViewresult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String name = (String)params[1];
            String id = (String)params[2];
            String pw = (String)params[3];
            String email = (String)params[5];

            String serverURL = (String)params[0];
            String postParameters = "name=" + name + "&id=" + id + "&pw=" + pw + "&email=" + email;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }


}