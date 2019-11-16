package com.example.se_2019.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_2019.Post;
import com.example.se_2019.R;

import java.util.ArrayList;
import java.util.Date;

public class ForumActivity extends AppCompatActivity {

    ListView listView;
    MyListAdapter myListAdapter;
    AddPostActivity mynote;
    ArrayList<Post> list_itemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        listView = (ListView)findViewById(R.id.my_listview);

        list_itemArrayList = new ArrayList<Post>();
        //여기 목록에 띄워질 애들
        //함수 만들어서 다시 불러와야함
        list_itemArrayList.add(
                new Post(R.mipmap.ic_launcher,"조현아","제목",new Date(System.currentTimeMillis()),"안녕하세요"));
        myListAdapter = new MyListAdapter(ForumActivity.this,list_itemArrayList);
        listView.setAdapter(myListAdapter);
    }
    public void writeClick(View view){
        Intent intent = new Intent(ForumActivity.this, AddPostActivity.class);
        startActivity(intent);
    }

////이 밑은 메뉴
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
