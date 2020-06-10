package com.example.ecomate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login,btn_register;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id=findViewById(R.id.et_id);
        et_pass=findViewById(R.id.et_pass);
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        checkBox = (CheckBox)findViewById(R.id.checkBox);

        SharedPreferences sf = getSharedPreferences("File",MODE_PRIVATE);
        String text1= sf.getString("text1","");
        String text2= sf.getString("text2","");

        if(!(text1.equals("")))
            checkBox.setChecked(true);

        et_id.setText(text1);
        et_pass.setText(text2);


        btn_register.setOnClickListener(new View.OnClickListener() {//회원가입 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID=et_id.getText().toString();
                String userPass=et_pass.getText().toString();


                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject=new JSONObject(response);
                            boolean success=jasonObject.getBoolean("success");
                            if (success) {//회원등록 성공한 경우
                                String userID = jasonObject.getString("userID");
                                String userPass = jasonObject.getString("userPassword");
                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("log", "User");
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPass);
                                startActivity(intent);
                            }


                            else{//회원등록 실패한 경우
                                Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest=new LoginRequest(userID,userPass,responseListener);
                RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences("File",MODE_PRIVATE);
        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //체크 박스에 체크가 됬다면 아이디를 저장한다.
        if(checkBox.isChecked()) {
            String text1 = et_id.getText().toString();
            String text2 = et_pass.getText().toString();
            editor.putString("text1", text1);
            editor.putString("text2", text2);
        }
        else
        {
            editor.putString("text1", "");
            editor.putString("text2", "");
        }

        // 값을 다 넣었으면 commit으로 완료한다.
        editor.commit();
    }

}