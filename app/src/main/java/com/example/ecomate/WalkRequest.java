package com.example.ecomate;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WalkRequest extends StringRequest {

    //서버 url 설정(php파일 연동)
    final static  private String URL="http://192.168.0.2/Walk.php";
    private Map<String,String> map;

    public WalkRequest(String userID, String userPoint, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        map=new HashMap<>();
        map.put("userID",userID);
        map.put("userPoint", userPoint);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}