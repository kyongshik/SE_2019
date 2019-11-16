package com.example.se_2019.DBRequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getIDRequest extends StringRequest {

        final static private String URL="http://deu04202.dothome.co.kr/getID.php";
        private Map<String, String> map;

        public getIDRequest(String userID, Response.Listener<String> listener){
           super(Request.Method.POST,URL,listener,null);
            // super(Request.Method.POST, URL, listener, null);

            map = new HashMap<>();
            map.put("userID", userID);

        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return map;
        }


}
