package com.example.se_2019.DBRequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddVoteRequest extends StringRequest {
    //방을 추가시킬 때 해당 userID
    final static private String URL="http://deu04202.dothome.co.kr/VoteAdd.php";
    private Map<String, String> map;


    public AddVoteRequest(String roomID, int num, String chklist, String votenum, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("roomID", roomID);
        map.put("num", num+"");
        map.put("chklist", chklist);
        map.put("votenum", votenum);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}