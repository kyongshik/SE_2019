package com.example.se_2019.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.se_2019.DBRequest.*;
import com.example.se_2019.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText et_lid, et_lpass;
    private Button btn_login, btn_register;
    String userID,userPass;
    ////////////////////////////////
    Boolean state;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_lid = findViewById(R.id.et_lid);
        et_lpass = findViewById(R.id.et_lpass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener(){ //회원가입 버튼을 눌렀을 때 회원가입 창으로 가도록
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //edit text에 현재 입력되어있는 값을 가져온다
                userID = et_lid.getText().toString();
                userPass = et_lpass.getText().toString();
                if(userID.length()==0){
                    Toast.makeText(getApplicationContext(), "ID를 입력하세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if(userPass.length()==0){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
                    return;
                }
                check_userID();


                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (state == false ){
                                Toast.makeText(getApplicationContext(), "유효한 아이디가 아닙니다.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userID", userID);
                                startActivity(intent);
                            }
                            else{

                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            et_lid.setText("");
                            et_lpass.setText("");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    public void pracbtnClick(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void check_userID(){

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        state = true;
                    }
                    else{
                        state = false;
                    }
                    et_lid.setText("");
                    et_lpass.setText("");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginUserRequest loginRequest = new LoginUserRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);

    }


}


