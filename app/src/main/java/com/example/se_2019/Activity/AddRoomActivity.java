package com.example.se_2019.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.se_2019.DBRequest.*;
import com.example.se_2019.R;
import com.example.se_2019.Room;
import com.example.se_2019.content_notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddRoomActivity extends AppCompatActivity {
    EditText room_name,sub_name;
    String room_code="";
    Room room;
    String userid="";
    String text;
    String userID; //intent로 넘어오는 id
    Button btn_search;
    ArrayList<Room> roomlist = new ArrayList<Room>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        /////상단바
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //왼쪽에 home버튼 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /////
        room_name = (EditText)findViewById(R.id.etname);
        sub_name = (EditText)findViewById(R.id.subject_name);

        final Intent intent_Add = getIntent();
        userID = intent_Add.getExtras().getString("userID");

        btn_search = findViewById(R.id.search_btn);

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


        //방 정보들을 가져오기 //검색
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String roomID = jsonObject.getString("roomID");
                        String subName = jsonObject.getString("subName");
                        String roomName = jsonObject.getString("roomName");
                        Room room = new Room(roomName);
                        room.setCode(roomID);
                        room.setUserID(userID);
                        room.setRoomName(roomName);
                        room.setSubName(subName);
                        roomlist.add(room);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        SearchRoomRequest searchroomRequest = new SearchRoomRequest( responseListener); //여기서 userID로 보내는거 같음
        RequestQueue queue = Volley.newRequestQueue(AddRoomActivity.this);
        queue.add(searchroomRequest);


        //검색 버튼을 눌렀을 때 팝업창
        Bundle bundle = getIntent().getExtras();
        userid = bundle.getString("userID");
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddRoomActivity.this);
                View view = LayoutInflater.from(AddRoomActivity.this).inflate(R.layout.content_search_room, null, false);
                SearchView search = (SearchView)findViewById(R.id.searchView);
                text = search.getQuery().toString();
                //여기 text가 사용자가 입력한 방코드

                builder.setView(view);
                final AlertDialog dialog = builder.create();
                final TextView textRoomcode = (TextView) view.findViewById(R.id.et_roomcode) ;
                final Button btn_roomSearch = (Button) view.findViewById(R.id.btn_add_room_search);
                final Button btn_roomCancel = (Button) view.findViewById(R.id.btn_cancel);
                textRoomcode.setText(text);

                //확인 버튼을 눌렀을때
                btn_roomSearch.setOnClickListener(new View.OnClickListener() {
                    //디비에서 해당 룸 코드를 찾아 있으면 사용자 아이디에 추가시키고 아니면 해당 룸코드가 없다고 toast 메시지 띄워야힘
                    @Override
                    public void onClick(View v) {
                        String searchroomID="";
                        String searchsubName="";
                        String searchroomName="";
                        for(int i=0; i<roomlist.size();i++){
                            if(text.equals(roomlist.get(i).getRoomID())){ //만약 같은 방이 있다면 방에 해당되는 데이터 저장
                                searchroomID= roomlist.get(i).getRoomID();
                                searchsubName= roomlist.get(i).getSubName();
                                searchroomName= roomlist.get(i).getRoomName();
                                break;
                            }
                        }
                        if(searchroomID!=""){
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject2 = new JSONObject(response);
                                        boolean successadd= jsonObject2.getBoolean("successadd");
                                        if (successadd) { //회원등록에 성공한 경우
                                            Intent intent = new Intent(AddRoomActivity.this, MainActivity.class);
                                            intent.putExtra("userID", userID);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "서버에 올라가지 않았어요", Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            AddSearchRoomRequest addsearchroomRequest = new AddSearchRoomRequest(searchroomID, userID, searchroomName, searchsubName, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(AddRoomActivity.this);
                            queue.add(addsearchroomRequest);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "입력하신 방이 존재하지않습니다. 다시 입력해주세요:)", Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }
                });
                //취소 버튼을 눌렀을때
                btn_roomCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
        //방에 대한 정보를 리스트에 저장
        {
            //new room만들어서 사용자가 입력한 내용을 set함
            String roomID  =room_code;
            String userID =userid; //여기 바꿔줘야함
            String roomName = room_name.getText().toString();
            String subName = sub_name.getText().toString();

            room = new Room(room_name.getText().toString());
            room.setCode(roomID);
            room.setUserID(userID);
            room.setRoomName(roomName);
            room.setSubName(subName);

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
        getMenuInflater().inflate(R.menu.menu_main, menu);//여기서 menu/menu_main UI를 가져옴
        return true;
    }

    //상단바에 있는 애들
    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        int id = item.getItemId();
        if (id == android.R.id.home) {
            Toast.makeText(this, "홈버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        }
        if (id == R.id.toolbar_alarm) {
            Toast.makeText(this, "알람버튼을 눌렀습니다++", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, content_notice.class);
            intent.putExtra("userID",userID);
            startActivity(intent);
        }
        if (id == R.id.toolbar_profile) {
            Toast.makeText(this, "프로필버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
            // SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
            Intent intent = new Intent(this, Preferences.class);
            intent.putExtra("userID",userID);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
