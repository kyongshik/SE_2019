package com.example.se_2019.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.se_2019.Alarm;
import com.example.se_2019.DBRequest.getAlarmRequest;
import com.example.se_2019.DBRequest.getRoomListRequest;
import com.example.se_2019.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class content_notice extends AppCompatActivity {
    String userID;
    Alarm alarm;
    String alarm_time;
    String alarm_title;
    String roomcode;
    String userID_alarm;
    List<String> list = new ArrayList<>();
    List<String> ALLuserRoomID = new ArrayList<>();
    List<String> userRoomID = new ArrayList<>();
    List<String> userRoomCode = new ArrayList<>();
    List<String> userlist = new ArrayList<>();
    ArrayAdapter<String> adapter;

    String userRoom;
    ///////////////////////////////////////////////////////////////////////////////////////////////



    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_notice);

        final Intent intent = getIntent();
        userID = intent.getExtras().getString("userID");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        checkRoomList();

       // checkAlarm();


        ListView listView = (ListView) findViewById(R.id.listview_notice);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        alarm_time = jsonObject.getString("time");
                        alarm_title = jsonObject.getString("title");
                        roomcode = jsonObject.getString("roomcode");
                        userID_alarm = jsonObject.getString("userID");
                        Alarm alarm = new Alarm(alarm_time, alarm_title, roomcode,userID_alarm);

                        for (int j = 0; j < ALLuserRoomID.size(); j++) {
                            if (ALLuserRoomID.get(j).equals(roomcode)){
                                list.add("time : " + alarm.getTime() + " title : " + alarm.getContent() + " code: " + alarm.getRoomcode());
                                userRoomID.add(alarm.getTime());
                                userRoomCode.add(alarm.getRoomcode());
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    //timeAlarm(userRoomID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        getAlarmRequest alarmRequest = new getAlarmRequest(responseListener); //여기서 userID로 보내는거 같음
        RequestQueue queue = Volley.newRequestQueue(content_notice.this);
        queue.add(alarmRequest);


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);
        //상단바
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(content_notice.this, GetRoomInfo.class);
                intent.putExtra("time", alarm_time);
                intent.putExtra("roomID", roomcode);
                startActivity(intent);
            }
        });


        //리스트뷰에 보여질 아이템을 추가
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//여기서 e/menu_main UI를 가져옴
        return true;
    }
    /////////////소공-알림띄우기//////////////

    public void checkRoomList() {

        Response.Listener<String> responseListener_a = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String room = jsonObject.getString("roomID");

                        ALLuserRoomID.add(room);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        getRoomListRequest roomUserRequest = new getRoomListRequest(userID, responseListener_a);
        RequestQueue q = Volley.newRequestQueue(content_notice.this);
        q.add(roomUserRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            Toast.makeText(this, "홈버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        }
        if (id == R.id.toolbar_alarm) {
            Toast.makeText(this, "알람버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.toolbar_profile) {
            Toast.makeText(this, "프로필버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
            // SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
            Intent intent = new Intent(this, Preferences.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
