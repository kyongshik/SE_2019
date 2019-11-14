package com.example.se_2019;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRoom extends AppCompatActivity {
    EditText etname,subject_name,prof_name,subject_time;
    String code="";
    Room room;
    Button btn_search ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        //상단바
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //왼쪽에 home버튼 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


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
        Toast.makeText(this, "방 코드는 "+code+" 입니다.", Toast.LENGTH_LONG).show(); //확인용 나중에 지워야함
        btn_search = findViewById(R.id.btn_searchFor_add);
        Log.i("STORE","이거 이상하다");
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddRoom.this);

                View view = LayoutInflater.from(AddRoom.this).inflate(R.layout.content_search_room, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();


                final TextView textRoomcode = (TextView) view.findViewById(R.id.et_roomcode) ;
                final Button btn_roomSearch = (Button) view.findViewById(R.id.btn_add_room_search);
                final Button btn_roomCancel = (Button) view.findViewById(R.id.btn_cancel);
                textRoomcode.setText(code);


                btn_roomSearch.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });
                btn_roomCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                    Log.i("WHY",code);
                        dialog.dismiss();
                    }
                });

                dialog.show();


            }
        });


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//여기서 menu/menu_main UI를 가져옴
        return true;
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        int id = item.getItemId();
        if (id == android.R.id.home) {
            Toast.makeText(this, "홈버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.toolbar_alarm) {
            Toast.makeText(this, "알람버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.toolbar_profile) {
            Toast.makeText(this, "프로필버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
            // SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
            Intent intent = new Intent(this, Preferences.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
