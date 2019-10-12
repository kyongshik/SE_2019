package com.example.se_2019;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    EditText editText, editText2, editText3, editText4, editText5;
    ListView listView, listView2;

    DBHelper dbHelper;
    SQLiteDatabase db = null;
    Cursor cursor; //DB에서 가져온 데이터를 쉽게 처리하기 위해 제공하는 2차원 테이블을 가지는 인터페이스이다.
    ArrayAdapter adapter, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText = findViewById(R.id.et_rid);
        editText2 = findViewById(R.id.et_rpass);
        editText3 = findViewById(R.id.et_name);
        editText4 = findViewById(R.id.et_add);
        editText5 = findViewById(R.id.et_dep2);

        dbHelper = new DBHelper(this, 3);
        db = dbHelper.getWritableDatabase();    // 읽기/쓰기 모드로 데이터베이스를 오픈


    }

    public void insert(View v) {
        String name = editText.getText().toString();
        String info = editText2.getText().toString();
        db.execSQL("INSERT INTO tableName VALUES ('" + name + "', '" + info + "');"); //name 하고 info가 변수명이라서 작은 따옴표로 다시 한번 감싼거
//실질적으로 얘 한문장이 SQL문을 실행시켜주는 기능을 함.
        Toast.makeText(getApplicationContext(), "추가 성공", Toast.LENGTH_SHORT).show();

        editText.setText("");
        editText2.setText("");
    }

    /*public void delete(View v) {
        String name = editText.getText().toString();
        db.execSQL("DELETE FROM tableName WHERE name = '" + name + "';");
        //where은 if문 같은거 name과 같은 데이터가 있으면 삭제해주는 것.
    }*/
}