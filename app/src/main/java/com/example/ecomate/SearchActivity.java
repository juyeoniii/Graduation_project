package com.example.ecomate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecomate.Product;
import com.example.ecomate.ProductsAdapter;
import com.example.ecomate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS = "http://192.168.0.2/MyApi/api2.php";

    //a list to store all the products
    ArrayList<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;
    ProductsAdapter adapter;
    GridLayoutManager layoutManager;
    TextView et_id, et_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView2);

        Intent intent = getIntent();

        final TextView et_id = (TextView)findViewById(R.id.et_id);
        String userID = intent.getStringExtra("userID");
        et_id.setText("아이디 : " +userID);

        final TextView et_name = (TextView)findViewById(R.id.et_name);
        String userName = intent.getStringExtra("userName");
        et_name.setText("닉네임 : " +userName);


        //initializing the productlist
        productList = new ArrayList<>();

        loadProducts();

        adapter = new ProductsAdapter(getApplicationContext(), productList);
        layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ImageButton cmrbtn = (ImageButton) findViewById(R.id.cmrbtn);
        cmrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, CertificationActivity.class);


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
                Intent intent = new Intent(SearchActivity.this, MypageActivity.class);


                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);


                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        ImageButton hmbtn = (ImageButton) findViewById(R.id.hmbtn);
        hmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);


                String userID = et_id.getText().toString();
                intent.putExtra("userID", userID);


                String userName = et_name.getText().toString();
                intent.putExtra("userName", userName);

                setResult(RESULT_OK, intent);
                finish();
            }
        });




        //this method will fetch and parse json
        //to display it in recyclerview

    }

    private void loadProducts() {

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
                            ProductsAdapter adapter = new ProductsAdapter(SearchActivity.this, productList);
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
}