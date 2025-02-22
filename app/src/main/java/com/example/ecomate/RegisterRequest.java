package com.example.ecomate;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 url 설정(php파일 연동)
    final static  private String URL="http://192.168.0.2/Register2.php";
    private Map<String,String> map;

    public RegisterRequest(String usercode, String userID, String userPassword, String userName, String recommendID, String userPoint, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        map=new HashMap<>();
        map.put("usercode", usercode);
        map.put("userID",userID);
        map.put("userPassword",userPassword);
        map.put("userName",userName);
        map.put("recommendID", recommendID);
        map.put("userPoint", userPoint);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}