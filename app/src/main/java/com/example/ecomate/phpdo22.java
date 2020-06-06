package com.example.ecomate;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class phpdo22 extends AsyncTask<String,Void,String> {


    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {

        try {
            String userID = arg0[0];

            String link = "http://192.168.0.2/ehlsl3.php?userID=" + userID;
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
}