package com.example.se_2019;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRoom extends MainActivity {
    EditText etname,subject_name,prof_name,subject_time;
    String code="";
    Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        etname = (EditText)findViewById(R.id.etname);
        subject_name = (EditText)findViewById(R.id.subject_name);
        prof_name = (EditText)findViewById(R.id.prof_name);
        subject_time = (EditText)findViewById(R.id.subject_time);
        //코드를 여기서 미리 배정해놓음
        int count=8;
        while(count>0){
            int choose = (int)(Math.random()*3); //0,1,2난수 생성
            switch(choose){
                case(0):
                    int num=(int)(Math.random()*10);//0~9까지 난수 생성
                    code+=(int)num;
                    break;
                case(1):
                    char c1 = (char)((int)(Math.random()*26+65));//랜덤한 대문자
                    code+=c1;
                    break;
                case(2):
                    char c2= (char)((int)(Math.random()*26)+97); //랜덤한 소문자
                    code+=c2;
                    break;
            }
            count--;
        }
        Toast.makeText(this, "코드는 "+code+" 입니다.", Toast.LENGTH_LONG).show(); //확인용 나중에 지워야함
    }
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnCancel){
            finish();
        }
        else //추가 버튼을 눌렀을 때
        {
            room = new Room(etname.getText().toString());
            room.setMenu(subject_name.getText().toString());
            room.setMenu(prof_name.getText().toString());
            room.setMenu(subject_time.getText().toString());
            room.setDate(finddate());
            room.setCode(code);
            Intent intent = getIntent();
            intent.putExtra("newroom",room);  //Parcelable한 Restaurant를 첨부
            setResult(RESULT_OK,intent);
            finish();
        }
    }
    public void click_createbtn(View v){ //만들기 버튼 클릭시 보였다 안보였다하는 기능
        View view = findViewById(R.id.addroom);
        if(view.getVisibility()==View.VISIBLE)
            view.setVisibility(View.INVISIBLE);
        else
            view.setVisibility(View.VISIBLE);
    }
    public String finddate()
    {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String fmdate = sdf.format(date);
        return fmdate;
    }
}
