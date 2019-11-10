package com.example.se_2019;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RoomRequest extends StringRequest {
    final static private String URL="http://deu04202.dothome.co.kr/Room.php";
    private Map<String, String> map;

    public RoomRequest(String roomID, String userID, String roomName, String subName, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("roomID", roomID);
        map.put("userID", userID);
        map.put("roomName", roomName);
        map.put("subName", subName);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
