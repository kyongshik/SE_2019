package com.example.se_2019.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.se_2019.DBRequest.getIDRequest;
import com.example.se_2019.R;
import com.example.se_2019.DBRequest.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    EditText et_rid, et_rpass, et_name, et_add, et_dep2;
    ListView listView, listView2;
    private Button btn_register, btn_over;
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
        et_add = findViewById(R.id.et_add); //이메일
        et_dep2 = findViewById(R.id.et_dep2); //학과
        btn_register = findViewById(R.id.btn_register);
        btn_over = findViewById(R.id.btn_overlap);

        btn_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = et_rid.getText().toString();
                if (userID.length() == 0) {
                    Toast.makeText(getApplicationContext(), "ID를 입력하세요", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.i("WHY", "여기들어오나여");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { //회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(), "ID중복", Toast.LENGTH_LONG).show();
                            } else { //회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(), "ID중복아님", Toast.LENGTH_LONG).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                getIDRequest getIDRequest = new getIDRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(getIDRequest);
            }

        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit text에 현재 입력되어있는 값을 가져온다
                //학과는 입력안해도 넘어갈 수 있게 해놓음
                String userID = et_rid.getText().toString();
                String userPass = et_rpass.getText().toString();
                String userName = et_name.getText().toString();
                String userAdd = et_add.getText().toString();

                if (userID.length() == 0) {
                    Toast.makeText(getApplicationContext(), "ID를 입력하세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (userPass.length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (userName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (userAdd.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력하세요", Toast.LENGTH_LONG).show();
                    return;
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { //회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(), "회원등록에 성공하였습니다.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else { //회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(), "회원등록에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPass, userName, userAdd, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }

    public int check() {

        return 0;
    }
}