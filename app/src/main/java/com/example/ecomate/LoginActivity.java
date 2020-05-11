package com.example.ecomate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecomate.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    String id, pw, pwck, email;

    private EditText mEditTextName;
    private EditText mEditTextId;
    private EditText mEditTextpw;
    private EditText mEditTextpwck;
    private EditText mEditTextemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void lgbtn(View view)
    {
        try{
            id = mEditTextName.getText().toString();
            pw = mEditTextpw.getText().toString();
        }catch (NullPointerException e)
        {
            Log.e("err",e.getMessage());
        }

        loginDB lDB = new loginDB();
        lDB.execute();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void rgbtn(View view)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    public class loginDB extends AsyncTask<Void, Integer, Void> {
        String data ="";
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Dialog alertBuilder;
            if(data.equals("1"))
            {
                Log.e("RESULT","성공적으로 처리되었습니다!");
                alertBuilder
                        .setTitle("알림")
                        .setMessage("성공적으로 등록되었습니다!")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
               // AlertDialog dialog = alertBuilder.create();
               // dialog.show();
            }
            else if(data.equals("0"))
            {
                Log.e("RESULT","비밀번호가 일치하지 않습니다.");
                alertBuilder
                        .setTitle("알림")
                        .setMessage("비밀번호가 일치하지 않습니다.")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                            }
                        });
               // AlertDialog dialog = alertBuilder.create();
                // dialog.show();
            }
            else
            {
                Log.e("RESULT","에러 발생! ERRCODE = " + data);
                alertBuilder
                        .setTitle("알림")
                        .setMessage("등록중 에러가 발생했습니다! errcode : "+ data)
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }
        }

   //     @Override
   //     protected Void doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
       //     String param = "id=" + id + "&pw=" + pw + "";
     //       Log.e("POST",param);
         //   try {
                /* 서버연결 */
           //     URL url = new URL(
         //               "http://10.0.2.2/insert3.php");
           //     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             //   conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
               // conn.setRequestMethod("POST");
        //        conn.setDoInput(true);
          //      conn.connect();

                /* 안드로이드 -> 서버 파라메터값 전달 */
            //    OutputStream outs = conn.getOutputStream();
              //  outs.write(param.getBytes("UTF-8"));
                //outs.flush();
              //  outs.close();

                /* 서버 -> 안드로이드 파라메터값 전달 */
              //  InputStream is = null;
              //  BufferedReader in = null;
              //  String data = "";

           //     is = conn.getInputStream();
             //   in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
           //     String line = null;
             //   StringBuffer buff = new StringBuffer();
               // while ( ( line = in.readLine() ) != null )
             //   {
               //     buff.append(line + "\n");
            //    }
              //  data = buff.toString().trim();

                /* 서버에서 응답 */
//                Log.e("RECV DATA",data);

         //       if(data.equals("0"))
           //     {
             //       Log.e("RESULT","성공적으로 처리되었습니다!");
               // }
               // else
               // {
                 //   Log.e("RESULT","에러 발생! ERRCODE = " + data);
               // }

           // } catch (MalformedURLException e) {
            //    e.printStackTrace();
           // } catch (IOException e) {
             //   e.printStackTrace();
           // }

        //    return null;
      //  }

  //  }

}
