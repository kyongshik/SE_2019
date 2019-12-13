package com.example.se_2019.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.se_2019.Alarm;
import com.example.se_2019.R;
import com.example.se_2019.Room;
import com.example.se_2019.DBRequest.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items = new ArrayList<String>(); //이게 방 이름
    ArrayAdapter<String> adapter;
    ArrayList<Room> roomlist = new ArrayList<Room>(); //방 리스트
    ListView listView;
    final int NEW_ROOM = 22;
    private TextView tv_id;
    String userID = "";
    String roomID = "";
    String subName = "";
    String roomName = "";
    private Button btn_list;
    //////////////////////////
    private MyAsyncTask myAsyncTask;
    List<String> list = new ArrayList<>();
    private Thread mainThread;

    ///////////////////////////////

    String alarm_time;
    String alarm_title;
    String roomcode;
    String userID_alarm;
    List<Alarm> userAlarm = new ArrayList<>();

    List<String> userRoomID = new ArrayList<>();
    List<String> userRoomCode = new ArrayList<>();
    List<String> userlist = new ArrayList<>();
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///////////////////////
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("", "", "");

        //툴바
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //왼쪽에 home버튼 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setListView();

        //하단에 아이디 출력
        tv_id = findViewById(R.id.idView);
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        tv_id.setText("userID: " + userID);
        Context context = this.getApplicationContext();

        ////여기서부터 디비에서 받아오는것 하기
        listView = findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);


        listView.setAdapter(adapter);
        //사용자에게 해당되는 방 리스트를 불러옴
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        userID = jsonObject.getString("userID");
                        roomID = jsonObject.getString("roomID");
                        subName = jsonObject.getString("subName");
                        roomName = jsonObject.getString("roomName");
                        Room room = new Room(roomName);
                        room.setCode(roomID);
                        room.setUserID(userID);
                        room.setRoomName(roomName);
                        room.setSubName(subName);

                        items.add(room.getRoomName());
                        roomlist.add(room);
                        adapter.notifyDataSetChanged();
                    }
//                    getUser();
//                    checkAlarm();
                    NotificationSomethings();
                    if (roomlist.size() == 0) {
                        Toast.makeText(context, "방목록이 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        getRoomListRequest getRoomListRequest = new getRoomListRequest(userID, responseListener); //여기서 userID로 보내는거 같음
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(getRoomListRequest);


    }

    //방 추가 버튼 클릭하면 userID랑 방 정보보냄//
    public void addBtnClick(View v) {
        //하단에 아이디 출력
        Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
        //bundle로 uerID랑 room userID보냄
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("roomlist", items);
        bundle.putString("userID", userID);

        intent.putExtras(bundle);
//        intent.putExtra("userID", userID);
        startActivityForResult(intent, NEW_ROOM);
    }

    protected void setListView() {
        listView = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        //list의 방을 클릭하면 roompost로 넘어감
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ForumActivity.class);
                Room room = roomlist.get(position);
                intent.putExtra("room_code", room.getRoomID());
                intent.putExtra("room_name", room.getRoomName());
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_ROOM) {
            if (resultCode == RESULT_OK) {
                Room room = data.getParcelableExtra("newroom"); //새 방 받아옴
                items.add(room.getRoomName());
                roomlist.add(room);
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

public void checkAlarm() {

    Toast.makeText(this, "따란.", Toast.LENGTH_SHORT).show();
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
                    userAlarm.add(alarm);

                }
                for(int k =0; k < userAlarm.size(); k++){
                    for(int j = 0; j < userlist.size(); j++){
                        if(userAlarm.get(k).getUser().equals(userlist.get(j))){
                            timeAlarm(userAlarm);
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
    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
    queue.add(alarmRequest);

}
    public void getUser(){
        Toast.makeText(this, "뾰룡.", Toast.LENGTH_SHORT).show();
        Response.Listener<String> responseListener_a = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String user = jsonObject.getString("userID");
                        userlist.add(user);

                    }
                   // timeAlarm(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        for(int i = 0; i < roomlist.size();i++){
            getRoomUserRequest roomUserRequest = new getRoomUserRequest(roomlist.get(i).getRoomID(), responseListener_a);
            RequestQueue q = Volley.newRequestQueue(MainActivity.this);
            q.add(roomUserRequest);
        }
    }
    public void timeAlarm(List list) {
        String currentTime = finddate();
        String[] temp;
        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i).toString();
            temp = text.toString().split("/");
            int day = Integer.parseInt(temp[2]);
            day--;
            if (day < 10) {
                temp[2] = "0" + String.valueOf(day);
            } else {
                temp[2] = String.valueOf(day);
            }
            String time = temp[0] + "/" + temp[1] + "/" + temp[2];
            if (currentTime.equals(time)) {
                NotificationSomethings();
            }
        }
    }

    public String finddate() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String fmdate = sdf.format(date);
        return fmdate;
    }

    public void NotificationSomethings() {


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, content_notice.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground)) //BitMap 이미지 요구
                .setContentTitle("schedule!")
                .setContentText("일정까지 하루 남았습니다.")
                // 더 많은 내용이라서 일부만 보여줘야 하는 경우 아래 주석을 제거하면 setContentText에 있는 문자열 대신 아래 문자열을 보여줌
                //.setStyle(new NotificationCompat.BigTextStyle().bigText("더 많은 내용을 보여줘야 하는 경우..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // 사용자가 노티피케이션을 탭시 ResultActivity로 이동하도록 설정
                .setAutoCancel(true);

        //OREO API 26 이상에서는 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남
            CharSequence channelName = "노티페케이션 채널";
            String description = "오레오 이상을 위한 것임";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            channel.setDescription(description);

            // 노티피케이션 채널을 시스템에 등록
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

        } else
            builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

        assert notificationManager != null;
        notificationManager.notify(1234, builder.build()); // 고유숫자로 노티피케이션 동작시킴

    }

    //TOOLBAR설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//여기서 e/menu_main UI를 가져옴
        return true;
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
            Intent intent = new Intent(this, content_notice.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
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
