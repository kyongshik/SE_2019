package com.example.se_2019;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class RoomPost extends AppCompatActivity {
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<Post> postlist = new ArrayList<Post>();
    ArrayAdapter<String> adapter ;
    final int NEW_POST = 10; //
    ListView postView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //왼쪽에 home버튼 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setListView();

    }

    public void writeBtnClick(View view){
        Intent intent = new Intent(RoomPost.this, AddPost.class);
        intent.putExtra("postlist", items);
        startActivityForResult(intent, NEW_POST);
    }
    protected void setListView() {
        postView = (ListView) findViewById(R.id.postview);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        postView.setAdapter(adapter);
        postView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(RoomPost.this, AddPost.class);
                Post post = postlist.get(position);
                intent.putExtra("postinfo", post);
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == NEW_POST)
        {
            if(resultCode == RESULT_OK)
            {
                Post post = data.getParcelableExtra("newroom"); //새 레스토랑 받아옴
                items.add(post.getName());
                postlist.add(post);
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
