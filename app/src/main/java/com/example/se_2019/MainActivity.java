package com.example.se_2019;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items = new ArrayList<String>();
    ArrayAdapter<String> adapter ;
    ArrayList<Room> roomlist = new ArrayList<Room>();
    ListView listView;
    final int REST_INFO=21;//
    final int NEW_ROOM = 22; //
    private TextView tv_id;
    String userID="";

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
        setListView();//이건 방에 대한 코드

        //하단에 아이디 출력
        tv_id = findViewById(R.id.idView);
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        tv_id.setText("userID: "+userID);

        //가지고 있는 리스트 출력해야함
        ////여기서부터
//        Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
//        intent.putExtra("roomlist", items);
//        startActivityForResult(intent, NEW_ROOM);

    }
    //방 추가 버튼 클릭하면 userID랑 방 정보보냄///?
    public void addBtnClick(View v){
        //하단에 아이디 출력
        Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
        //bundle로 uerID랑 room info보냄
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("roomlist", items);
        bundle.putString("userID", userID);
        intent.putExtras(bundle);
//        intent.putExtra("userID", userID);
        startActivityForResult(intent, NEW_ROOM);
    }
    protected void setListView() {
        listView = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);

        //꾹 눌렀을때 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                //정보를 삭제하는지 묻는 대화상자 나타남
                AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                dlg.setTitle("삭제확인")

                        .setMessage("선택한 방을 정말 삭제하시겠습니까?")
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //삭제 클릭시 아래꺼
                                items.remove(position);
                                items.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();
                return true;
            }
        });
        //list의 방을 클릭하면 roompost로 넘어감
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(MainActivity.this, RoomPost.class);
                Room room = roomlist.get(position);
                intent.putExtra("roominfo", room);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == NEW_ROOM)
        {
            if(resultCode == RESULT_OK)
            {
                Room room = data.getParcelableExtra("newroom"); //새 방 받아옴
                items.add(room.getName());
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
