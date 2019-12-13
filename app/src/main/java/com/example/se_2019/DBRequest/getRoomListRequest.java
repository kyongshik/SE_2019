package com.example.se_2019.DBRequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getRoomListRequest extends StringRequest {
    final static private String URL="http://deu04202.dothome.co.kr/getRoomlist.php";
    private Map<String, String> map;

    public getRoomListRequest(String userID, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
<<<<<<< HEAD
=======

>>>>>>> a83b6b86d477b58174a39afd5eabf038fc17ab90

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
