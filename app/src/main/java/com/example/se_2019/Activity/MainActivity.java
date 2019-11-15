package com.example.se_2019.Activity;

import android.content.Intent;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.se_2019.R;
import com.example.se_2019.Room;
import com.example.se_2019.DBRequest.getRoomListRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ArrayList<Room> roomlist = new ArrayList<Room>();
    ListView listView;
    final int NEW_ROOM = 22;
    private TextView tv_id;
    String userID = "";
    String roomID = "";
    String subName = "";
    String roomName = "";
    private Button btn_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                intent.putExtra("roominfo", room);
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
