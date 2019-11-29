package com.example.se_2019.DBRequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getPostListRequest extends StringRequest {
    final static private String URL="http://deu04202.dothome.co.kr/getPostlist.php";
    private Map<String, String> map;

    public getPostListRequest(String roomID, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("roomID", roomID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
