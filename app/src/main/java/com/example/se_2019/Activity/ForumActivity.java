package com.example.se_2019.Activity;

import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.se_2019.DBRequest.AddAlarm;
import com.example.se_2019.Note;
import com.example.se_2019.Post;
import com.example.se_2019.R;
import com.example.se_2019.Schedule;
import com.example.se_2019.Vote;
import com.example.se_2019.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity {

    private ListView listView;
    //MyListAdapter myListAdapter;
    AddPostActivity mynote;
    ArrayList<Post> list_itemArrayList;
    //ArrayList<Note> list_itemArrayList;
    final int REQUESTCODE = 21;
    final int NEW_POST = 22;

    ArrayAdapter<String> adapter;
    ArrayList<String> list = new ArrayList<>(); //제목 가져올 리스트
    ArrayList<Note> postlist = new ArrayList<>();
    ArrayList<Schedule> callist = new ArrayList<>();
    ArrayList<Vote> votelist = new ArrayList<>();


    String roomCode, roomName;
    TextView room_name, room_code;
    String userID;


    String alarm_time = null;
    String alarm_title = null;
    Alarm alarm;
    boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        //툴바
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //왼쪽에 home버튼 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final Intent intent_roominfo = getIntent();

        roomCode = intent_roominfo.getExtras().getString("room_code");
        roomName = intent_roominfo.getExtras().getString("room_name");

        room_name = findViewById(R.id.et_roomName);
        room_name.setText(roomName);

        room_code = findViewById(R.id.et_Code);
        room_code.setText(roomCode);

        userID = intent_roominfo.getExtras().getString("userID");


        setListView();
        listClick();


        list_itemArrayList = new ArrayList<Post>();


    }

    protected void setListView() {
        listView = findViewById(R.id.my_listview);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    protected void listClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //list에 있는 게시글들을 클릭하면 ReadPost로 넘어감
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp = adapter.getItem(position).substring(0, 3);
                Toast.makeText(getApplicationContext(), "클릭 " + temp, Toast.LENGTH_SHORT).show();
                if (temp.equals("[게시")) {
                    Intent in = new Intent(ForumActivity.this, ReadPostActivity.class);
                    Note n = postlist.get(list_itemArrayList.get(position).getNum());
                    in.putExtra("post", n);
                    startActivity(in);
                } else if (temp.equals("[투표")) {
                    Intent in = new Intent(ForumActivity.this, ReadPostActivity.class);
                    Vote v = votelist.get(list_itemArrayList.get(position).getNum());
                    in.putExtra("vote", v);
                    startActivity(in);
                } else if (temp.equals("[일정")) {
                    Intent in = new Intent(ForumActivity.this, ReadPostActivity.class);
                    Schedule s = callist.get(list_itemArrayList.get(position).getNum());
                    in.putExtra("cal", s);
                    startActivity(in);
                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_POST) {
            if (resultCode == RESULT_OK) {
                Post p = data.getParcelableExtra("postinfo");
                alarm_time = data.getExtras().getString("alarm_time");
                alarm_title = data.getExtras().getString("alarm_title");
                check_server();
                //여기서 게시글인지 뭔지 나눠야함
                if (p.getPosi() == 0) {
                    list.add("[게시글]  " + p.getTitle() + "\n" + p.getName() + "\t\t" + p.getWrite_date());
                    Note n = new Note(p.getName(), p.getWrite_date(), p.getTitle(), p.getContent());
                    postlist.add(n);
                }
                if (p.getPosi() == 1) {
                    list.add("[투표]  " + p.getTitle() + "\n" + p.getName() + "\t\t" + p.getWrite_date());
                    Vote v = new Vote(p.getName(), p.getWrite_date(), p.getTitle(), p.getContent(), null);
                    //여기 체크리스트 넣어야함 지금은 null
                    votelist.add(v);
                }
                if (p.getPosi() == 2) {
                    list.add("[일정]  " + p.getTitle() + "\n" + p.getName() + "\t\t" + p.getWrite_date());
                    Schedule s = new Schedule(p.getName(), p.getWrite_date(), p.getTitle(), p.getContent(), p.getDday());
                    callist.add(s);
                }
                list_itemArrayList.add(p);
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void writeClick(View view) {
        Intent intent = new Intent(ForumActivity.this, AddPostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("postlist", list);//게시판 리스트에 게시글 정보 추가
        bundle.putString("userID", userID);
        bundle.putString("room_code", roomCode);
        intent.putExtras(bundle);

        startActivityForResult(intent, NEW_POST);
    }
    public void check_server() {
        Response.Listener<String> responseListener_a = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    success = jsonObject.getBoolean("successalarm");
                    //Toast.makeText(getApplicationContext(), String.valueOf(success), Toast.LENGTH_LONG).show();
                    if (success) { //방등록에 성공한 경우
                        Toast.makeText(getApplicationContext(), "서버등록에 성공하였습니다.", Toast.LENGTH_LONG).show();
                        //intent로 다른 창에 뜨게 함
                        Intent intent = getIntent();
                        intent.putExtra("roomID",roomCode);
                        setResult(RESULT_OK, intent);
                        finish();

                    } else { //등록에 실패한 경우
                        Toast.makeText(getApplicationContext(), "서버등록에 실패하였습니다.", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AddAlarm addAlarmRequest = new AddAlarm(alarm_time, alarm_title,roomCode, responseListener_a);
        RequestQueue queue_a = Volley.newRequestQueue(ForumActivity.this);
        queue_a.add(addAlarmRequest);
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
            intent.putExtra("alarm_time", alarm_time);
            intent.putExtra("alarm_title", alarm_title);
            intent.putExtra("check_alarm",success);
            intent.putExtra("roomID", roomCode);
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