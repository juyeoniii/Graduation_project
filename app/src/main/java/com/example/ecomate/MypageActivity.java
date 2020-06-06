package com.example.ecomate;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MypageActivity extends AppCompatActivity {

    phpdo task;

    TextView et_point;
   /* private static final String URL_PRODUCTS = "http://192.168.0.2/MyApi/api2.php" ;

    //a list to store all the products
    ArrayList<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;
    PageAdapter adapter;
    GridLayoutManager layoutManager;

*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Intent intent = getIntent();

        final TextView et_id = (TextView)findViewById(R.id.et_id);
        String userID = intent.getStringExtra("userID");
        et_id.setText("아이디 : " +userID);

        final TextView et_name = (TextView)findViewById(R.id.et_name);
        String userName = intent.getStringExtra("userName");
        et_name.setText("닉네임 : " +userName);

        task = new phpdo();
        et_point = (TextView)findViewById(R.id.et_point);
        task.execute(userID);

        //getting the recyclerview from xml
/*        recyclerView = findViewById(R.id.recylcerView3);

        loadProducts();

        adapter = new PageAdapter(getApplicationContext(), productList);
        layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

*/

        ImageButton hmbtn = (ImageButton) findViewById(R.id.hmbtn);
        hmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, MainActivity.class);

                TextView et_id = (TextView) findViewById(R.id.et_id);
                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                TextView et_name = (TextView) findViewById(R.id.et_name);
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

                TextView et_id = (TextView) findViewById(R.id.et_id);
                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);

                TextView et_name = (TextView) findViewById(R.id.et_name);
                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                setResult(RESULT_OK, intent);
                finish();
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

        protected void onPostExecute(String result){
            //txtview.setText("Login Successful");
            et_point.setText("포인트 : "+result);
        }
    }

  /*  private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Product(
                                        product.getString("image")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            ProductsAdapter adapter = new ProductsAdapter(MypageActivity.this, productList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    */

}


