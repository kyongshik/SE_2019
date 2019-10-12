package com.example.se_2019;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    EditText et_rid, et_rpass, et_name, et_add, et_dep2;
    ListView listView, listView2;
    private Button btn_register;
//    DBHelper dbHelper;//
//    SQLiteDatabase db = null;
//    Cursor cursor; //DB에서 가져온 데이터를 쉽게 처리하기 위해 제공하는 2차원 테이블을 가지는 인터페이스이다.
//    ArrayAdapter adapter, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_rid = findViewById(R.id.et_rid);
        et_rpass = findViewById(R.id.et_rpass);
        et_name = findViewById(R.id.et_name);
        et_add = findViewById(R.id.et_add);
        et_dep2 = findViewById(R.id.et_dep2);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //edit text에 현재 입력되어있는 값을 가져온다
                String userID = et_rid.getText().toString();
                String userPass = et_rpass.getText().toString();
                String userName= et_name.getText().toString();
                int userAge = 1;

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if(success){ //회원등록에 성공한 경우
                            Toast.makeText(getApplicationContext(), "회원등록에 성공하였습니다.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        }
                        else{ //회원등록에 실패한 경우
                            Toast.makeText(getApplicationContext(), "회원등록에 실패하였습니다.", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            RegisterRequest registerRequest = new RegisterRequest(userID, userPass, userName, userAge, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Register.this);
            queue.add(registerRequest);


        }

    });
    }
}