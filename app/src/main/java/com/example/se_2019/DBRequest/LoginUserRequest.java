package com.example.se_2019.DBRequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginUserRequest extends StringRequest {
    //서버 설정//
    // 서버URL설정 (PHP파일 연동)
    final static private String URL="http://deu04202.dothome.co.kr/Login_userID.php";
    private Map<String, String> map;


    public LoginUserRequest(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
