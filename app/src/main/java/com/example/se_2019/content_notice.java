package com.example.se_2019;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.se_2019.Activity.*;
import com.example.se_2019.DBRequest.AddAlarm;
import com.example.se_2019.DBRequest.SearchRoomRequest;
import com.example.se_2019.DBRequest.getAlarmRequest;
import com.example.se_2019.DBRequest.getRoomUserRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class content_notice extends AppCompatActivity {
    String userID;
    String alarm_time;
    String alarm_title;
    Alarm alarm;
    String roomcode;
    List<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    String userRoom;
    ///////////////////////////////////////////////////////////////////////////////////////////////

    List<Alarm> userlist = new ArrayList<>();

    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_notice);

        final Intent intent = getIntent();
        userRoom = intent.getExtras().getString("roomID");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        checkAlarm();



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
                        Log.i("ALARM",alarm_title);
                        Alarm alarm = new Alarm(alarm_time, alarm_title,roomcode);
                        if(userRoom.equals(roomcode)){
                            list.add("time : " + alarm.getTime() + " title : " + alarm.getContent()+" code: "+alarm.getRoomcode());
                        }
                    }
                    isItNull(list);
                    adapter.notifyDataSetChanged();

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
                intent.putExtra("time",alarm_time);
                intent.putExtra("roomID", roomcode);
                startActivity(intent);
            }
        });


        //리스트뷰에 보여질 아이템을 추가
    }
    public void isItNull(List<String> list){
        if(list.size() == 0){
            Toast.makeText(this, "알림 내용이 없습니다.", Toast.LENGTH_SHORT).show();
        }
        else{

            //NotificationSomethings();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//여기서 e/menu_main UI를 가져옴
        return true;
    }
    /////////////소공-알림띄우기//////////////
    public void NotificationSomethings() {


        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, content_notice.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground)) //BitMap 이미지 요구
                .setContentTitle("상태바 드래그시 보이는 타이틀")
                .setContentText("상태바 드래그시 보이는 서브타이틀")
                // 더 많은 내용이라서 일부만 보여줘야 하는 경우 아래 주석을 제거하면 setContentText에 있는 문자열 대신 아래 문자열을 보여줌
                //.setStyle(new NotificationCompat.BigTextStyle().bigText("더 많은 내용을 보여줘야 하는 경우..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // 사용자가 노티피케이션을 탭시 ResultActivity로 이동하도록 설정
                .setAutoCancel(true);

        //OREO API 26 이상에서는 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남
            CharSequence channelName  = "노티페케이션 채널";
            String description = "오레오 이상을 위한 것임";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName , importance);
            channel.setDescription(description);

            // 노티피케이션 채널을 시스템에 등록
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

        }else builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

        assert notificationManager != null;
        notificationManager.notify(1234, builder.build()); // 고유숫자로 노티피케이션 동작시킴

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
    public void checkAlarm(){

        Toast.makeText(this, "따란.", Toast.LENGTH_SHORT).show();
        Response.Listener<String> responseListener_a = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String user = jsonObject.getString("userID");

                        //Alarm al = new Alarm(user,time);
//
//                        al.setUser(user);
//                        al.setTime(time);
                       // userlist.add(al);

                    }

                    timeAlarm(userlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        getRoomUserRequest roomUserRequest = new getRoomUserRequest(userRoom, responseListener_a);
        RequestQueue q = Volley.newRequestQueue(content_notice.this);
        q.add(roomUserRequest);
    }
    public void timeAlarm(List list){
        String currentTime = finddate();
        String[]temp;
        for(int i = 0; i<list.size(); i++){
            String text = list.get(i).toString();
            temp = alarm_time.toString().split("/");
            int day = Integer.parseInt(temp[2]);
            day--;
            if(day < 10){
                temp[2] = "0"+ String.valueOf(day);
            }else{
                temp[2] = String.valueOf(day);
            }
            String time = temp[0]+"/"+temp[1]+"/"+temp[2];
            if(currentTime.equals(time)){
                NotificationSomethings();
            }
        }
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
