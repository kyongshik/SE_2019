package com.example.se_2019.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_2019.Note;
import com.example.se_2019.R;

import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity {

    ListView listView;
    //MyListAdapter myListAdapter;
    AddPostActivity mynote;
    // ArrayList<Post> list_itemArrayList;
    //ArrayList<Note> list_itemArrayList;
    final int REQUESTCODE = 21;
    final int NEW_POST = 22;

    ArrayAdapter<String> adapter;
    ArrayList<String> list = new ArrayList<>(); //제목 가져올 리스트
    ArrayList<Note> postlist = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_forum);
        setListView();

        //  list_itemArrayList = new ArrayList<Post>();

//        //여기 목록에 띄워질 애들
//        //함수 만들어서 다시 불러와야함
//        list_itemArrayList.add(
//            new Post(R.mipmap.ic_launcher,"조현아","제목",new Date(System.currentTimeMillis()),"안녕하세요"));
//    myListAdapter = new MyListAdapter(ForumActivity.this,list_itemArrayList);


    }

    protected void setListView(){
        listView = findViewById(R.id.my_listview);

        if(list != null){
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
        }

//    list_itemArrayList = new ArrayList<>();
//    ArrayAdapter<MyListAdapter> adater;
//   // myListAdapter = new ArrayAdapter<MyListAdapter>(this, android.app..layout.content_my_list_adapter);
//    myListAdapter = new MyListAdapter(ForumActivity.this,list_itemArrayList);
//    listView.setAdapter(myListAdapter);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == NEW_POST)
        {
            if(resultCode == RESULT_OK){
//                Intent intent = data;
                Note n = data.getParcelableExtra("postinfo");

                // list.add(n.getTitle());//여기서 리스트 맨앞으로 넣는걸로 바꾸면됨
                list.add(0,n.getTitle());//여기서 리스트 맨앞으로 넣는걸로 바꾸면됨
                postlist.add(n);
//                list_itemArrayList.add(n);
                adapter.notifyDataSetChanged();
                // myListAdapter.notifyDataSetChanged();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void writeClick(View view){
        Intent intent = new Intent(ForumActivity.this, AddPostActivity.class);
        intent.putExtra("postinfo",postlist);
        startActivityForResult(intent, NEW_POST);
    }

}