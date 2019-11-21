package com.example.se_2019.Activity;

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

import com.example.se_2019.Note;
import com.example.se_2019.Post;
import com.example.se_2019.R;
import com.example.se_2019.Schedule;
import com.example.se_2019.Vote;

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

        setListView();
        listClick();
        list_itemArrayList = new ArrayList<Post>();

//        //여기 목록에 띄워질 애들
//        //함수 만들어서 다시 불러와야함
//        list_itemArrayList.add(
//            new Post(R.mipmap.ic_launcher,"조현아","제목",new Date(System.currentTimeMillis()),"안녕하세요"));
//    myListAdapter = new MyListAdapter(ForumActivity.this,list_itemArrayList);


    }

    protected void setListView() {
        listView = findViewById(R.id.my_listview);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        //  listClick();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "클릭 "+position, Toast.LENGTH_SHORT).show();
//                String temp = adapter.getItem(position).substring(0,3);
//                Toast.makeText(getApplicationContext(), "클릭 "+temp, Toast.LENGTH_SHORT).show();
//                if(temp.equals("[게시")){
//                    Intent intent = new Intent(ForumActivity.this, ReadPostActivity.class);
//                    //intent.putExtra("post", postlist.get(position));
//                    startActivity(intent);
//                }
//                else if(temp.equals("[투표")) {
//
//                }
//                else if(temp.equals("[일정")){
//
//                }
//            }
//        });

    }

    protected void listClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "클릭 "+position, Toast.LENGTH_SHORT).show();
                String temp = adapter.getItem(position).substring(0,3);
                Toast.makeText(getApplicationContext(), "클릭 "+temp, Toast.LENGTH_SHORT).show();
                if(temp.equals("[게시")){
                    Intent in = new Intent(ForumActivity.this, ReadPostActivity.class);
                    Note n = postlist.get(list_itemArrayList.get(position).getNum());
                    in.putExtra("post",n);
                    startActivity(in);
                    // finish();
                }
                else if(temp.equals("[투표")) {
                    Intent in = new Intent(ForumActivity.this, ReadPostActivity.class);
                    Vote v = votelist.get(list_itemArrayList.get(position).getNum());
                    in.putExtra("vote",v);
                    startActivity(in);
                    //finish();
                }
                else if(temp.equals("[일정")){
                    Intent in = new Intent(ForumActivity.this, ReadPostActivity.class);
                    Schedule s = callist.get(list_itemArrayList.get(position).getNum());
                    in.putExtra("cal",s);
                    startActivity(in);
                    //finish();
                }
            }
        });
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_POST) {
            if (resultCode == 0) {
                Note n = data.getParcelableExtra("postinfo");
                // list.add(0, "[게시글]  " + n.getTitle() + "\n" + n.getName() + "\t\t" + n.getDate());
                list.add("[게시글]  " + n.getTitle() + "\n" + n.getName() + "\t\t" + n.getDate());
                postlist.add(n);
                //list_itemArrayList.add(new Post(n.getName(),n.getTitle(),n.getDate(), postlist.size()-1));
                list_itemArrayList.add(new Post(n.getName(),n.getDate(), n.getTitle(), n.getContent(), null, null, 0, postlist.size()-1));
                adapter.notifyDataSetChanged();
            }
            if (resultCode == 1) {
                Vote v = data.getParcelableExtra("voteinfo");
                // list.add(0,"[투표]  " + v.getTitle() + "\n" + v.getName() + "\t\t" + v.getDate());
                list.add("[투표]  " + v.getTitle() + "\n" + v.getName() + "\t\t" + v.getDate());
                votelist.add(v);
                // list_itemArrayList.add(new Post(v.getName(),v.getTitle(),v.getDate(), votelist.size()-1));
                list_itemArrayList.add(new Post(v.getName(),v.getDate(), v.getTitle(), v.getContent(), null, null,1,votelist.size()-1));
                //여기 체크리스트 다시 넘겨주기
                adapter.notifyDataSetChanged();
            }
            if (resultCode == 2) {
                Schedule s = data.getParcelableExtra("calinfo");
                //   list.add(0, "[일정]  " + s.getTitle() + "\n" + s.getName() + "\t\t" + s.getDate());
                list.add( "[일정]  " + s.getTitle() + "\n" + s.getName() + "\t\t" + s.getDate());
                callist.add(s);
                // list_itemArrayList.add(new Post(s.getName(),s.getTitle(),s.getDate(), callist.size()-1));
                list_itemArrayList.add(new Post(s.getName(),s.getDate(),s.getTitle(),s.getContent(),null,s.getDday(),2, callist.size()-1));
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void writeClick(View view) {
        Intent intent = new Intent(ForumActivity.this, AddPostActivity.class);
        //intent.putExtra("postinfo", postlist);
        intent.putExtra("postinfo", postlist);
        intent.putExtra("postinfo", votelist);
        intent.putExtra("postinfo", callist);
        startActivityForResult(intent, NEW_POST);
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