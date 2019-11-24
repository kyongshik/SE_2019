package com.example.se_2019.DBRequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddPostRequest extends StringRequest {
    //방을 추가시킬 때 해당 userID
    final static private String URL="http://deu04202.dothome.co.kr/PostAdd.php";
    private Map<String, String> map;


    public AddPostRequest(String roomID, String name, String write_date, String title, String content, String chklist, String Dday, String posi, String num, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("roomID", roomID);  //필수
        map.put("name", name);//필수
        map.put("write_date", write_date);//필수
        map.put("title", title);//필수
        map.put("content", content);//필수
        //null나올수있음
        if(chklist==null)
            chklist="";
        map.put("chklist", chklist);
        //null나올수있음
        if(Dday==null)
            Dday="";
        map.put("Dday", Dday);
        map.put("posi", posi+"");
        map.put("num",num+"");


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
