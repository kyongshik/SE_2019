package com.example.se_2019;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText editText, editText2;
    DBHelper dbHelper;
    SQLiteDatabase db;
    //Cusor cursor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void register_Click(View view){
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }
    protected void onClick(View v) {


        editText = findViewById(R.id.et_lid);
        editText2 = findViewById(R.id.et_lpass);

        dbHelper = new DBHelper(this, 3);
        db = dbHelper.getWritableDatabase();    // 읽기/쓰기 모드로 데이터베이스를 오픈


    }

}