package com.example.se_2019;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRoomActivity extends AppCompatActivity {
    EditText room_name,sub_name; //userid 넣어야됨ㅠㅠ
    String room_code="";
    Room room;
    String userid="";

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

        room_name = (EditText)findViewById(R.id.etname);
        sub_name = (EditText)findViewById(R.id.subject_name);

        //방코드는 방 생성시 미리 정해줌
        int count=8;
        while(count>0){
            int choose = (int)(Math.random()*3); //0,1,2난수 생성
            switch(choose){
                case(0):
                    int num=(int)(Math.random()*10);//0~9까지 난수 생성
                    room_code+=(int)num;
                    break;
                case(1):
                    char c1 = (char)((int)(Math.random()*26+65));//랜덤한 대문자
                    room_code+=c1;
                    break;
                case(2):
                    char c2= (char)((int)(Math.random()*26)+97); //랜덤한 소문자
                    room_code+=c2;
                    break;
            }
            count--;
        }
        Toast.makeText(this, "방 코드는 "+room_code+" 입니다.", Toast.LENGTH_LONG).show(); //확인용 나중에 지워야함

        Bundle bundle = getIntent().getExtras();
        userid = bundle.getString("userID");


    }
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnCancel){
            finish();
        }
        else //추가 버튼을 눌렀을 때
        //방에 대한 정보를 리스트에 저장
        {
            //new room만들어서 사용자가 입력한 내용을 set함
            room = new Room(room_name.getText().toString());
            room.setMenu(sub_name.getText().toString());

            room.setDate(finddate());
            room.setCode(room_code);
            //변수 받아오기
            String roomID  =room_code;
            String userID =userid; //여기 바꿔줘야함
            String roomName = room_name.getText().toString();
            String subName = sub_name.getText().toString();
            //서버에 추가하는 부분
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if(success){ //방등록에 성공한 경우
                            //intent로 다른 창에 뜨게 함
                            Intent intent = getIntent();
                            intent.putExtra("newroom",room);  //Parcelable한 room을 첨부
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                        else{ //등록에 실패한 경우
                            Toast.makeText(getApplicationContext(), "서버등록에 실패하였습니다.", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            AddRoomRequest addRoomRequest = new AddRoomRequest(roomID, userID, roomName, subName, responseListener);
            RequestQueue queue = Volley.newRequestQueue(AddRoomActivity.this);
            queue.add(addRoomRequest);

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

    //상단바에 있는 애들
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
